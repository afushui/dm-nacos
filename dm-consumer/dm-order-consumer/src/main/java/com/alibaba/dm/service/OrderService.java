package com.alibaba.dm.service;


import com.alibaba.dm.client.item.ItemClient;
import com.alibaba.dm.client.order.OrderClient;
import com.alibaba.dm.client.schedule.SchedulerClient;
import com.alibaba.dm.client.user.UserClient;
import com.alibaba.dm.config.AlipayConfig;
import com.alibaba.dm.entity.item.Item;
import com.alibaba.dm.entity.order.Order;
import com.alibaba.dm.entity.order.OrderLinkUser;
import com.alibaba.dm.entity.scheduler.SchedulerSeat;
import com.alibaba.dm.entity.scheduler.SchedulerSeatPrice;
import com.alibaba.dm.entity.user.LinkUser;
import com.alibaba.dm.vo.*;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alibaba.dm.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    OrderClient orderClient;
    @Autowired
    ItemClient itemClient;
    @Autowired
    SchedulerClient schedulerClient;
    @Autowired
    UserClient userClient;



    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    //使用jedis的set时同一个key在被set后通过get返回一个OK 再次set的化会返回null

    //创建订单
    public CommonResponse<Map>createOrder(CreateOrderRequest orderRequest){

        //查询商品是否存在
        Item dmItem = itemClient.findByItemId(orderRequest.getItemId());
        if (dmItem==null){
            return VoUtil.returnFailure("1201","商品不存在");
        }
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.auth("chaopeng");
        String lockKey =orderRequest.getSeatPositions()+orderRequest.getCinemaId();
        String result = jedis.set(lockKey, orderRequest.getUserId().toString(), SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 60000 * 30);
        String result1 = jedis.set(lockKey, orderRequest.getUserId().toString(), SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 60000 * 30);
        System.out.println(result+"++++++++++++++++++++++++++++++"+result1);
        //生成订单编号 唯一
        String orderNo= IdWorker.getId();
        Map<String ,String> map= new HashMap<>();

        //获取所有的座位坐标
        String[] seatArrays = orderRequest.getSeatPositions().split(",");
        //获取所有的座位总价
        double totalPrice = 0d;
        //获取的每个座位的价钱
        double[] seatPrices = new double[seatArrays.length];
        for (int i = 0; i <seatArrays.length ; i++) {
            SchedulerSeat schedulerSeat = new SchedulerSeat();
            schedulerSeat.setX(Long.parseLong(seatArrays[i].split("_")[0]));
            schedulerSeat.setY(Long.parseLong(seatArrays[i].split("_")[1]));
            schedulerSeat.setScheduleId(orderRequest.getSchedulerId());

            SchedulerSeat schedulerSeat1 = schedulerClient.getSchedulerSeatByOrder(schedulerSeat);
            if (schedulerSeat1.getUserId()!=null && schedulerSeat1.getStatus() != 1L){
                return VoUtil.returnFailure("9812","该座位已购买,请重新选座");
            }
            schedulerSeat1.setStatus(2L);
            schedulerSeat1.setUserId(orderRequest.getUserId());
            schedulerSeat1.setOrderNo(orderNo);
            schedulerClient.updateSchedulerSeat(schedulerSeat1);

            SchedulerSeatPrice schedulerSeatPrice = schedulerClient.getSchedulerSeatPriceBySchedulerIdAndArea(orderRequest.getSchedulerId(), schedulerSeat1.getAreaLevel());
            double subTotal = schedulerSeatPrice.getPrice();
            seatPrices[i] = subTotal;
            totalPrice += seatPrices[i];
        }

        if (LOCK_SUCCESS.equals(result)) {
            //订单新增
            Order dmOrder = new Order();
            dmOrder.setOrderNo(orderNo);//订单编号
            dmOrder.setUserId(orderRequest.getUserId());
            dmOrder.setSchedulerId(orderRequest.getSchedulerId());
            dmOrder.setItemId(orderRequest.getItemId());
            dmOrder.setItemName(dmItem.getItemName());
            dmOrder.setOrderType(0L);
            dmOrder.setPayType("2");
            dmOrder.setSourceType(0L);
            dmOrder.setTotalCount((long)seatArrays.length);
            dmOrder.setIsNeedInsurance(0L);
            dmOrder.setInvoiceType(2L);
            dmOrder.setInvoiceHead(orderRequest.getInvoiceHead());
            dmOrder.setInvoiceNo(orderRequest.getInvoiceNo());
            dmOrder.setTotalAmount(totalPrice);
            dmOrder.setInsuranceAmount(20D);

            //添加订单
            orderClient.addOrder(dmOrder);

            //新增订单用户信息
            String[] linkIds = orderRequest.getLinkIds().split(",");
            for (int i = 0; i <linkIds.length ; i++) {
                //查询联系人用户信息
                userClient.queryLinkUser(Long.parseLong(linkIds[i]));
                LinkUser dmLinkUser = userClient.queryLinkUserById(Long.parseLong(linkIds[i]));
                //当订单联系人不存在的时候 ,执行 排气座位数据回滚,  执行 订单删除
//            if (linkUser == null) {
//                //重置座位信息
//                sendResetSeatMsg(schedulerSeat.getScheduleId(),seatArrays);
//                //联系人添加失败后删除创建的订单
//                sendDelOrderMsg(orderNo);
////                //重置订单联系人
////                sendResetLinkUser(orderNo);
//            }
                OrderLinkUser orderLinkUser = new OrderLinkUser();
                orderLinkUser.setOrderId(Long.parseLong(orderNo));
                orderLinkUser.setLinkUserId(Long.parseLong(linkIds[i]));
                orderLinkUser.setLinkUserName(dmLinkUser.getName());
                orderLinkUser.setPrice(seatPrices[i]);
                orderLinkUser.setX(Long.parseLong(seatArrays[i].split("_")[0]));
                orderLinkUser.setY(Long.parseLong(seatArrays[i].split("_")[1]));
                try{
                    orderClient.addOrderLinkUser(orderLinkUser);
                }catch (Exception e){
//                //重置座位信息
//                sendResetSeatMsg(schedulerSeat.getScheduleId(),seatArrays);
//                //联系人添加失败后删除创建的订单
//                sendDelOrderMsg(orderNo);
////                //重置订单联系人
//                sendResetLinkUser(orderNo);
                }
            }
            map.put("orderNo",orderNo);
        }

//        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//        Object delresult = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(orderRequest.getUserId().toString()));
//
//        if (RELEASE_SUCCESS.equals(delresult)) {
//
//        }


        return VoUtil.returnSuccess("0000",map);

    }


    //根据订单号查询订单
    public CommonResponse<OrderResponse>queryOrderByOrderNo(String orderNo){
        OrderResponse orderResponse = new OrderResponse();
        Order dmOrder = orderClient.findByOrderNo(orderNo);
        orderResponse.setItemName(dmOrder.getItemName());
        orderResponse.setOrderNo(dmOrder.getOrderNo());
        orderResponse.setTotalAmount(dmOrder.getTotalAmount());
        orderResponse.setSeatCount(dmOrder.getTotalCount());
        // seatNames 逗号相隔  seatCount  座位数量  totalAmount  订单金额
        String seatNames="";
//        List<DmOrderLinkUser> dmOrderLinkUserList = orderClient.findByOrderId(dmOrder.getId());
//        for (DmOrderLinkUser dmOrderLinkUser : dmOrderLinkUserList) {
//            seatNames=dmOrderLinkUser.getX().toString()+"_"+dmOrderLinkUser.getY().toString()+",";
//        }
//        String seatName =seatNames.substring(0,seatNames.length()-1);
        orderResponse.setSeatNames(seatNames);
        return VoUtil.returnSuccess("0000",orderResponse);
    }






    //订单管理
    public CommonResponse<List<orderVo>> queryorderlist( orderTermVo orderTermVo,HttpServletRequest request){

        String[] tokens = request.getHeader("token").split("-");
        String token = tokens[1];
        orderTermVo.setUserId(Long.parseLong(token));
        System.out.println(orderTermVo.getUserId());
        List<Order> dmOrderList = orderClient.queryorderlist(orderTermVo);
        List<orderVo> orderVoList = new ArrayList<>();
        dmOrderList.forEach(dmOrder -> {
            orderVo orderVo = new orderVo();
            orderVo.setId(dmOrder.getId());
            orderVo.setItemName(dmOrder.getItemName());
            orderVo.setNum(dmOrder.getTotalCount());
            orderVo.setOrderNo(dmOrder.getOrderNo());
            orderVo.setOrderType(dmOrder.getOrderType());
            orderVo.setTotalAmount(dmOrder.getTotalAmount());
            //根据商品得id查询商品得单价
            //DmItem dmItem = itemClient.findItemById(dmOrder.getItemId());
            Item dmItem = itemClient.findByItemId(dmOrder.getItemId());
            orderVo.setUnitPrice(dmItem.getMinPrice());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            orderVo.setSellTime(dmOrder.getCreatedTime().toString());
            //orderVo.setSellTime(sdf.format(dmOrder.getCreatedTime().toString()));

            orderVo.setTotalAmount(dmOrder.getTotalAmount());
            orderVoList.add(orderVo);
        });
        return VoUtil.returnSuccess("订单查询成功", orderVoList);
    }

    //pay


    public String zhiFuBao(String orderNo) {
        StringBuilder sb = null;
        try {
            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
            Order order = orderClient.findByOrderNo(orderNo);
            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

            alipayRequest.setBizContent("{\"out_trade_no\":\"" + order.getOrderNo() + "\","
                    + "\"total_amount\":\"" + order.getTotalAmount() + "\","
                    + "\"subject\":\"" + order.getItemName() + "\","
                    + "\"body\":\"" + order.getOrderNo() + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            Integer resultLen = result.length();
            sb = new StringBuilder(result);
            sb.delete(resultLen - 109, resultLen - 52);
            sb.insert(resultLen - 109, "<input id=\"submitId\" type=\"submit\" value=\"立即支付\" style=\"display:none\" >");
            System.out.println(sb.toString());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "zhiFuBao";
        }
        return sb.toString();
    }


    public String alipayReturnNotice(Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintStream out = System.out;
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                params.put(name, valueStr);
            }

            boolean signVerified = AlipaySignature.rsaCheckV1(
                    params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            if (signVerified) {
                //商户订单号
                String orderNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), "UTF-8");

                //支付宝交易号
                String tradingNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), "UTF-8");

                Order order = orderClient.findByOrderNo(orderNo);
                order.setOrderType(2L);
                order.setPayType("2");
                order.setAliTradeNo(tradingNo);//支付宝交易号
                orderClient.updateOrderStatus(order);//修改状态
                Long schedulerId = Long.parseLong(order.getSchedulerId() + "");//排期id
                List<OrderLinkUser> byOrderId = orderClient.findByOrderId(Long.parseLong(order.getId().toString()));//查询座位
                for (OrderLinkUser l : byOrderId) { //找到票的 id
                    SchedulerSeat schedulerSeat = new SchedulerSeat();
                    schedulerSeat.setScheduleId(schedulerId);
                    schedulerSeat.setY(l.getY());
                    schedulerSeat.setX(l.getX());
                    SchedulerSeat seat = schedulerClient.getSchedulerSeatByOrder(schedulerSeat);
                    seat.setStatus(3L);//修改座位状态
                    schedulerClient.updateSchedulerSeat(seat);
                }
            } else {
                out.println("验签失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "alipayReturnNotice方法错误";
        }
        return "redirect:http://localhost:8081/";
    }

    //删除订单所有信息
    public CommonResponse deleteOrder(Long orderId){
        try {
            orderClient.delByOrderId(orderId);
            //orderClient.delOrderLinkById(orderId);
            return VoUtil.returnSuccess("0000",null);
        }catch (Exception e){
            return VoUtil.returnFailure("1009",e.getMessage());
        }
    }

}

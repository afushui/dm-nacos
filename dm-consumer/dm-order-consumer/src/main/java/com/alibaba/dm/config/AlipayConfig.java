package com.alibaba.dm.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：com.shishaopeng.dm.config.AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016101800717458";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCTpn/Jwvp8ppR8izyjD+tcvLuvRO8C5TLle4z/dHnA66qJoTp2KwiGPY6FhXdlCoOklWr5DmcOUbyspnP5ESE+ub0K2a6NOU2jUa8zGD+bwknOl9/qjucf44YDWQMwPGC+ThRGHafplURY4F0faGFKO6KLAvdHqJd/WIDFUh/cYos8V6uq/8N4/8ZnKTFuTquAWvchPRvwAecXTUTU2BEz01iODHxGsdT5S4aec6HKDGpNwoxsJSEbqtNnFTiCfQiAyFt5xF8ZoSDhr9GSAL9n4o2fIVa0YjvDq7mWVpZ99DnPuqhntFuBUHjnh0R+1C+d/hDX6+KVfikSrNhzdzAVAgMBAAECggEASfrBypEUOsKoYaLONhGy5xt1msMaUsQOdWcrZY+xtZUfNKiBB1ZWhhRvxbzy7sCO5zcqizHJNyaDN6qLLz+C/c/IxZf+WqpcmTlDZIGA1YVKv+Xe/sKtUZAJxMGj1FZcTt4GUH9pc6MjxLheUG9XV/97WNWamuABUIBCvRuB9UTzWZ1/8uWo049fj/mLtWemeo2Ob82xdMBzkKLkwb6A8qQ5b9yZWATQVt+vCYBC9GNRNfU3tBfB6hRCNkUZv8eAzXlPKKtTyWrMma8Xh93WYY+IKFrkBHgmyW6Hg7+oiN/VufmWDafAOYlSMB1g03BRreTbk8TttaDlI2o+81Et3QKBgQDUAohSDhD5uqfJiu98WvTRVeThreAdD/ah+FhfW2ON2vGR7cKeeklC7yMRJ7sVCeo7f0xSfCY87G0TCH6YCCr4amgdueSVfNHM3sj7N9lIeufFTSVm8UWXX7Namw0BS3iEu6XtVwkaxLqJVKa1sDXbsgZqpyfNMXTawbmLHQv0fwKBgQCySVdHNg8G6u2N/UuqB2eTAGqtnp1roIexamdPA0mxC/TmKR4JA+BawJYSjauU1Hqr6HlY7qqvj+9IFl7udoWbI3b2PHem1hFKaxtHpyk3fGGnbfDFCWxm4fXSdnrUFYSVF7QnEfbyLFyXCbta+uwnu1zGu/gIbejGUaQpRLyBawKBgFW+JHXTHIhzNfKrzwljG8ohy+x9zrrSPvPopY0DP/MaDkLvfptrZ3+7iVoh0GbE2MdxR2PAtBJFqCNfdxtzijVni24iRVEz5wHNREldn/HRSzUfazYD9BoxaS8GsC7K4BYGcifX4GXEFJD//GBUwKN2j2PI8K7rBpCGcszRVYlvAoGBAKNbYfYkyTYMcifky7x6VmOyQQzeRd1E1LAyD4CtQYtQqk9to1CdVaexyJAtcObInbIAK5fZ71k3qAbEQK18ucTBhRHvq66ZfHRUmUVZHAH0HMI9kRvpG4NVTVlPKBJFuRqnAnG+Gyggj6Hwz9N4EIQDP+n0dzq6AkwcqC8MBTELAoGAFJCvoVwZCHzU510azY6d2b0y2bVcsXNUuP+aesnZlv03ZlGhl+JHSb4pxI3LRAKIQIo6n/etDrxTvKhVDFOVJ/DYNIHh1/eG/H8tt32d3792bcHhY7JQLmDiBZA5VlhC9N7kj9HBYTqUvAGi5k2tXoNHDVkyGo47oopUuov8Gwc=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjWu16EJ6LeesClRyql2+cqRjyBd3/e8+7XnEN822Giajea+3cucuUfNJOaaTSaggFX5A+PYLPcJIBevoDuICpqdqPj1htXWBIRFKjs7dF6VmNpI3moM59Kw/vtQHXF6kfZD3a7taKR0Q6EIkh3MtBmWIGH0C2kERFm2CfqZARfBdJKNOlRVLR8mzvK7tQdcaqnlSA06wOYrMBfEJNhH/XULp2l6+Ht+TovTTRavzkJzsdZ32B4UsAZDKRDb/wAaeYyqqaw/pXaF+vfCUwU6E9V17ng684FCXwlnvYWVL0sn0XupbYsFsuGiwoWb6oLVPnt4ri36YkaYhcLyU28Ax/QIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://127.0.0.1:7818/alipayReturnNotice";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


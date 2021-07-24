package com.alibaba.dm.service;

import com.alibaba.dm.client.base.ImageClient;
import com.alibaba.dm.client.item.ItemClient;

import com.alibaba.dm.entity.base.Image;
import com.alibaba.dm.entity.item.Cinema;
import com.alibaba.dm.entity.item.Item;
import com.alibaba.dm.es.ItemRepository;
import com.alibaba.dm.es.EsItem;
import com.alibaba.dm.vo.*;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    ItemClient ItemClient;
    @Autowired
    ImageClient ImageClient;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    //商品详情查询
    public CommonResponse<ItemDescResponse> queryItemDetail(Long id){
        ItemDescResponse itemByIdResponse = new ItemDescResponse();
        Item dmItem = ItemClient.findByItemId(id);
        BeanUtils.copyProperties(dmItem,itemByIdResponse);
        Cinema cinema = ItemClient.findCinemaById(dmItem.getCinemaId());
        itemByIdResponse.setStartTime(dmItem.getStartTime().toString());
        itemByIdResponse.setEndTime(dmItem.getEndTime().toString());
        itemByIdResponse.setAddress(cinema.getAddress());
        itemByIdResponse.setLatitude(cinema.getLatitude());
        itemByIdResponse.setLongitude(cinema.getLongitude());
        itemByIdResponse.setState(dmItem.getState().toString());
        Image itemImage = ImageClient.findByItemImage(dmItem.getId());
        if (itemImage!=null) {
            itemByIdResponse.setImgUrl(itemImage.getImgUrl());
        }
        return VoUtil.returnSuccess("0000",itemByIdResponse);
    }



    //商品列表 根据城市、一级分类、二级分、时间、排序字段查询商品信息
    public CommonResponse<PageResult> queryItemList(com.alibaba.dm.vo.EsItem esItem){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //组合查询 {"bool":{}} //term精确查询 //match匹配度查询 //range 范围查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(esItem.getAreaId()!=0){
            boolQueryBuilder.must(QueryBuilders.termQuery("areaid",esItem.getAreaId()));
        }
        if(esItem.getItemTypeId1()!=0){
            boolQueryBuilder.must(QueryBuilders.termQuery("itemtype1id",esItem.getItemTypeId1()));
        }
        if(esItem.getItemTypeId2()!=0){
            boolQueryBuilder.must(QueryBuilders.termQuery("itemtype2id",esItem.getItemTypeId2()));
        }

        if(esItem.getKeyword().trim()!=""){
            boolQueryBuilder.must(QueryBuilders.matchQuery("itemname",esItem.getKeyword()));
            //给查询结果“加分”
            boolQueryBuilder.should(QueryBuilders.matchQuery("abstractmessage",esItem.getKeyword()));
        }

        if(esItem.getStartTime()!="" && esItem.getEndTime()!=""){
            //yyyy-MM-DDTHH:mm:ssZ
            boolQueryBuilder.must(QueryBuilders.rangeQuery("starttime").gte(esItem.getStartTime()+"'T'00:00:00'Z'"));
            boolQueryBuilder.must(QueryBuilders.rangeQuery("endtime").lte(esItem.getStartTime()+"'T'23:59:59'Z'"));
        }

        //排序
        Sort sort = Sort.by(Sort.Order.desc("starttime"));
        if(esItem.getSort()!="" && esItem.getSort().equals("recommend")){
            sort = Sort.by("isrecommend").descending();
        }
        if(esItem.getSort()!="" && esItem.getSort().equals("recentSell")){
            sort = Sort.by("createdtime").descending();
        }
        if(esItem.getSort()!="" && esItem.getSort().equals("recentShow")){
            sort = Sort.by(Sort.Order.desc("starttime"));//Sort.by("starttime").descending();
        }

        Query query = nativeSearchQueryBuilder.withQuery(boolQueryBuilder).build();
        Pageable pageable = PageRequest.of(esItem.getCurrentPage()-1,esItem.getPageSize(),sort);
        query.setPageable(pageable);
        SearchHits<EsItem> searchHits = elasticsearchRestTemplate.search(query, EsItem.class);
        PageResult pageResult = new PageResult();
        pageResult.setCurrentPage(esItem.getCurrentPage());
        pageResult.setPageSize(esItem.getPageSize());

        int totalCount = (int)searchHits.getTotalHits();

        if(totalCount % pageResult.getPageSize() == 0){
            pageResult.setPageCount(totalCount / pageResult.getPageSize());
        }else{
            pageResult.setPageCount(totalCount / pageResult.getPageSize()+1);
        }

        List<EsRows> vos = searchHits.stream().map(new Function<SearchHit<EsItem>, EsRows>() {
            @Override
            public EsRows apply(SearchHit<EsItem> itemSearchHit) {
                EsRows rows = new EsRows();
                BeanUtils.copyProperties(itemSearchHit.getContent(),rows);
                rows.setItemName(itemSearchHit.getContent().getItemname());
                rows.setId(Long.parseLong(itemSearchHit.getContent().getId().toString()));
                rows.setAbstractMessage(itemSearchHit.getContent().getAbstractmessage());
                rows.setAreaName(itemSearchHit.getContent().getAdress());
                rows.setAdress(itemSearchHit.getContent().getAddress());
                rows.setImgUrl(itemSearchHit.getContent().getImgurl());
                rows.setEndTime(itemSearchHit.getContent().getEndtime());
                rows.setStartTime(itemSearchHit.getContent().getStarttime().toString());
                rows.setMaxPrice(itemSearchHit.getContent().getMaxprice());
                rows.setMinPrice(itemSearchHit.getContent().getMinprice());

                return rows;
            }
        }).collect(Collectors.toList());

        pageResult.setRows(vos);
        return VoUtil.returnSuccess("0000",pageResult);
    }
}

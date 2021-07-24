package com.alibaba.dm.es;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@Mapper
public interface ItemRepository extends ElasticsearchRepository<EsItem,Long> {
}

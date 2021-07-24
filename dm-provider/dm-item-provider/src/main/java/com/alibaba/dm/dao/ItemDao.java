package com.alibaba.dm.dao;

import com.alibaba.dm.entity.item.Item;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemDao extends BaseMapper<Item> {
}

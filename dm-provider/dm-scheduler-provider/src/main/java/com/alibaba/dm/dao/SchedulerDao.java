package com.alibaba.dm.dao;


import com.alibaba.dm.entity.scheduler.Scheduler;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchedulerDao extends BaseMapper<Scheduler> {
}

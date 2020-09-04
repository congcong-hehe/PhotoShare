package com.example.back.dao;

import com.example.back.pojo.photocollectKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("photocollectMapper")
public interface photocollectMapper {
    int deleteByPrimaryKey(photocollectKey key);

    int insert(photocollectKey record);

    int insertSelective(photocollectKey record);

    photocollectKey selectByPrimaryKey(photocollectKey record);

    int deleteByName(String name);
}
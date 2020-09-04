package com.example.back.dao;

import com.example.back.pojo.followKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("follow")
public interface followMapper {
    int deleteByPrimaryKey(followKey key);

    int insert(followKey record);

    int insertSelective(followKey record);

    followKey selectByPrimaryKey(followKey record);

    int deleteByName(String name);

    List<String> selectByUserName(String userName);
}
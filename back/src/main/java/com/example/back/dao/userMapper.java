package com.example.back.dao;

import com.example.back.pojo.user;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("userMapper")
public interface userMapper {
    int deleteByPrimaryKey(String userName);

    int insert(user record);

    int insertSelective(user record);

    user selectByPrimaryKey(String userName);

    int updateByPrimaryKeySelective(user record);

    int updateByPrimaryKey(user record);

    String selectPdByName(String userName);
}
package com.example.back.dao;

import com.example.back.pojo.photo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("photoMapper")
public interface photoMapper {
    int deleteByPrimaryKey(String location);

    int insert(photo record);

    int insertSelective(photo record);

    photo selectByPrimaryKey(String location);

    int updateByPrimaryKeySelective(photo record);

    int updateByPrimaryKey(photo record);

    List<photo> selectTop12ByLike();

    List<photo> selectNewPhoto();

    List<photo>  selectPhotoByName(String name);

    int updatePhotoLikesPos(String location);

    int updatePhotoLikesNeg(String location);

    int updatePhotoCollectsPos(String location);

    int updatePhotoCollectsNeg(String location);

    List<photo> selectCollectPhotoList(String name);

    List<photo> selectPhotoByTag(String tag);

    int deleteByName(String name);

}
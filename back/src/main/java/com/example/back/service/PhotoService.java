package com.example.back.service;

import com.example.back.pojo.photo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public interface PhotoService {
    photo uploadPhoto(MultipartFile upload, String name, String des, String tag) throws IOException;

    /**
     * 得到喜爱值在前列的图片列表
     * @return
     */
    List<photo> getShowPhoto();

    /**
     * 得到最新的图片列表
     * @return
     */
    List<photo> getNewPhotoList();

    /**
     * 得到最新的图片列表
     * @param name
     * @return
     */
    List<photo> getMyPhotoList(String name);

    /**
     * 根据图片名称返回图片的信息
     * @param name
     * @return
     */
    photo getPhotoDetail(String name);

    int photoLike(String location, int num);

    boolean getIsCollect(String location, String name);

    int photoCollect(String location, String name, int num);

    List<photo>  getCollectPhotoList(String name);

    int deletePhoto(String location) throws IOException;

    List<photo> getSearchPhotoList(String tag);

}

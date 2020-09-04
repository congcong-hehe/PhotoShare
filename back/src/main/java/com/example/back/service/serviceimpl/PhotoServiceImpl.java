package com.example.back.service.serviceimpl;

import com.example.back.blockchain.BlockChain;
import com.example.back.dao.photoMapper;
import com.example.back.dao.photocollectMapper;
import com.example.back.pojo.photo;
import com.example.back.pojo.photocollectKey;
import com.example.back.service.PhotoService;
import com.example.back.util.PhotoHash;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Resource
    photoMapper photoMapper;
    @Resource
    photocollectMapper photocollectMapper;
    @Override
    public photo uploadPhoto(MultipartFile upload, String name, String des, String tag) throws IOException {
        //得到图片的hash值
        InputStream inputStream = upload.getInputStream();
        String photoHash = PhotoHash.getPhotoHash(inputStream);
        //System.out.println(photoHash);

        //判断图片是否属于上传者
        /*String userName = BlockChain.queryPhoto(photoHash);
        if("notfound".equals(userName)) {
            BlockChain.photoRecord(photoHash,name);
        }else if(!userName.equals(name)) {
            return null;
        }*/

        //得到绝对路径
        String filePath = ResourceUtils.getURL("classpath:static").getPath()+"/upload/photo/";
        filePath  = filePath.replace('/', '\\').substring(1,filePath.length());
        File file = new File(filePath);
        if(!file.exists()) {
            boolean isMake = file.mkdirs();
            if(isMake) {
                System.out.println("创建文件夹成功，路径为: " + filePath);
            }
        }
        //随机生成文件的名称
        String newFileName = UUID.randomUUID() + upload.getOriginalFilename();
        String newFilePath = filePath + newFileName;

        //写入文件
        upload.transferTo(new File(newFilePath));
        System.out.println("写入文件：" + newFilePath);

        //将照片信息存入数据库
        photo photo = new photo();
        photo.setLocation("/upload/photo/" + newFileName);
        photo.setUserName(name);
        photo.setPhotoLikes(0);
        photo.setPhotoCollects(0);
        photo.setStoreTime(new Date());
        photo.setPhotoDes(des);
        photo.setPhotoTag(tag);
        photoMapper.insert(photo);
        System.out.println("照片写入数据库成功！，用户为：" + name);

        return  photo;
    }

    @Override
    public List<photo> getShowPhoto() {
        return photoMapper.selectTop12ByLike();
    }

    @Override
    public List<photo> getNewPhotoList() {
        return photoMapper.selectNewPhoto();
    }

    @Override
    public List<photo> getMyPhotoList(String name) {
        return photoMapper.selectPhotoByName(name);
    }

    @Override
    public photo getPhotoDetail(String name) {
        name = "/upload/photo/" + name;
        return photoMapper.selectByPrimaryKey(name);
    }

    @Override
    public int photoLike(String location, int num) {
        if(num == 1) {
            return photoMapper.updatePhotoLikesPos(location);
        }else {
            return photoMapper.updatePhotoLikesNeg(location);
        }
    }

    @Override
    public boolean getIsCollect(String location, String name) {
        photocollectKey photocollectKey = new photocollectKey();
        photocollectKey.setLocation(location);
        photocollectKey.setUserName(name);
        return photocollectMapper.selectByPrimaryKey(photocollectKey) != null;
    }

    @Override
    public int photoCollect(String location, String name, int num) {
        photocollectKey photocollectKey = new photocollectKey();
        photocollectKey.setUserName(name);
        photocollectKey.setLocation(location);
        if(num == 1) {
            return photocollectMapper.insert(photocollectKey) + photoMapper.updatePhotoCollectsPos(location);
        }else {
            return photocollectMapper.deleteByPrimaryKey(photocollectKey) + photoMapper.updatePhotoCollectsNeg(location);
        }
    }

    @Override
    public List<photo>  getCollectPhotoList(String name){
        return photoMapper.selectCollectPhotoList(name);
    }

    @Override
    public int deletePhoto(String location) throws IOException {
        String filePath = ResourceUtils.getURL("classpath:static").getPath()+ location;
        filePath  = filePath.replace('/', '\\').substring(1,filePath.length());
        File file = new File(filePath);
        if(file.delete()) {
            System.out.println(filePath+ "删除成功");
        }
        return photoMapper.deleteByPrimaryKey(location);
    }

    @Override
    public List<photo> getSearchPhotoList(String tag){
        return photoMapper.selectPhotoByTag(tag);
    }
}

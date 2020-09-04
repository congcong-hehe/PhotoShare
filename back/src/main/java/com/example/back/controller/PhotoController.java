package com.example.back.controller;

import com.example.back.pojo.photo;
import com.example.back.service.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/photo")
public class PhotoController {
    @Resource
    private PhotoService photoService;

    @RequestMapping("/upload")
    @ResponseBody
    public photo uploadPhoto(@RequestParam("file") MultipartFile upload, @RequestParam String name, @RequestParam String des,@RequestParam String tag) throws IOException {
        return photoService.uploadPhoto(upload,name,des,tag);
    }

    @RequestMapping("/getShowPhoto")
    public List<photo> getShowPhoto() {
        return photoService.getShowPhoto();
    }

    @RequestMapping("/getNewPhotoList")
    public List<photo> getNewPhotoList() {
        return photoService.getNewPhotoList();
    }

    @RequestMapping("/getMyPhotoList")
    public List<photo> getMyPhotoList(@RequestParam String name) {
        return photoService.getMyPhotoList(name);
    }

    @RequestMapping("/getPhotoDetail")
    public photo getPhotoDetail(@RequestParam String name) {
        return photoService.getPhotoDetail(name);
    }

    @RequestMapping("/photoLike")
    public int photoLike(@RequestParam String location, @RequestParam int num) {
        return photoService.photoLike(location,num);
    }

    @RequestMapping("/getIsCollect")
    public boolean photoLike(@RequestParam String location, @RequestParam String name) {
        return photoService.getIsCollect(location, name);
    }

    @RequestMapping("/photoCollect")
    public int photoCollect(@RequestParam String location, @RequestParam String name, @RequestParam int num) {
        return photoService.photoCollect(location,name,num);
    }

    @RequestMapping("/getCollectPhotoList")
    public List<photo>  getCollectPhotoList(@RequestParam String name) {
        return photoService.getCollectPhotoList(name);
    }

    @RequestMapping("/deletePhoto")
    public int deletePhoto(@RequestParam String location) throws IOException {
        return photoService.deletePhoto(location);
    }

    @RequestMapping("/getSearchPhotoList")
    public List<photo> getSearchPhotoList(@RequestParam String tag) {
        return photoService.getSearchPhotoList(tag);
    }
}

package com.example.back.service;

import com.example.back.pojo.user;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    /**
     * 用户登录
     * @param name
     * @param password
     * @return
     */
    boolean login(String name, String password);

    /**
     * 注册用户
     * @param postInfo
     * @return
     */
    int register(String postInfo);

    /**
     *
     * @param name
     * @return
     */
    user getUserDetail(String name);

    boolean getIsFollow(String followName, String userName);

    int userFollow(String userName,String followName,int num);

    int signOff(String name);

    List<String> getFollowList(String userName);
}

package com.example.back.service.serviceimpl;

import com.example.back.dao.followMapper;
import com.example.back.dao.photoMapper;
import com.example.back.dao.photocollectMapper;
import com.example.back.dao.userMapper;
import com.example.back.pojo.followKey;
import com.example.back.pojo.user;
import com.example.back.service.UserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author congcong
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    userMapper userMapper;
    @Resource
    followMapper followMapper;
    @Resource
    photocollectMapper photocollectMapper;
    @Resource
    photoMapper photoMapper;

    @Override
    public boolean login(String name, String password){
        if(password.equals(userMapper.selectPdByName(name))) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int register(String postInfo){
        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(postInfo).getAsJsonObject();
        user user = new user();
        user.setUserName(jo.get("name").getAsString());
        user.setUserPd(jo.get("password").getAsString());
        user.setUserDes(jo.get("description").getAsString());
        try {
            int i = userMapper.insert(user);
            if(i == 1) {
                System.out.println("注册用户" + user.getUserName() + "成功");
                return 1;
            }else {
                return 2;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return 2;
        }
    }

    @Override
    public user getUserDetail(String name) {
        return userMapper.selectByPrimaryKey(name);
    }

    @Override
    public boolean getIsFollow(String followName, String userName) {
        followKey followKey = new followKey();
        followKey.setFollowName(followName);
        followKey.setUserName(userName);
        return followMapper.selectByPrimaryKey(followKey) != null;
    }

    @Override
    public int userFollow(String userName,String followName,int num) {
        followKey followKey = new followKey();
        followKey.setFollowName(followName);
        followKey.setUserName(userName);
        if(num == 1) {
            return followMapper.insert(followKey);
        }else {
            return followMapper.deleteByPrimaryKey(followKey);
        }
    }

    @Override
    public int signOff(String name) {
        int num = 0;
        num += followMapper.deleteByName(name);
        num += photocollectMapper.deleteByName(name);
        num += photoMapper.deleteByName(name);
        num += userMapper.deleteByPrimaryKey(name);

        if(num > 0) {
            System.out.println("注销用户" + name + "成功");
        }
        return num;
    }

    @Override
    public List<String> getFollowList(String userName) {
        return followMapper.selectByUserName(userName);
    }
}

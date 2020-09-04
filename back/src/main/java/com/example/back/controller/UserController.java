package com.example.back.controller;

import com.example.back.pojo.user;
import com.example.back.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @RequestMapping("/login")
    public boolean login(@RequestParam String name, @RequestParam String password) {
        return userService.login(name,password);
    }

    @RequestMapping("/register")
    public int register(@RequestBody String postInfo){
        return userService.register(postInfo);
    }

    @RequestMapping("/getUserDetail")
    public user getUserDetail(@RequestParam String name){
        return userService.getUserDetail(name);
    }

    @RequestMapping("/getIsFollow")
    public boolean getIsFollow(@RequestParam String followName, @RequestParam String userName){
        return userService.getIsFollow(followName, userName);
    }

    @RequestMapping("/userFollow")
    public int userFollow(@RequestParam String userName, @RequestParam String followName, @RequestParam int num) {
        return userService.userFollow(userName,followName,num);
    }

    @RequestMapping("/signOff")
    public int signOff(@RequestParam String name) {
        return userService.signOff(name);
    }

    @RequestMapping("/cancelFollow")
    public int cancelFollow(@RequestParam String userName, @RequestParam String followName) {
        return userService.userFollow(userName,followName,-1);
    }

    @RequestMapping("/getFollowList")
    public List<String> getFollowList(@RequestParam String userName) {
        return userService.getFollowList(userName);
    }
}

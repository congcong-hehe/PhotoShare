package com.example.back.blockchain;

import com.example.back.util.HttpRequest;


public class BlockChain {
    public static void photoRecord(String photoHash, String userName) {
        //发送 POST 请求
        //String params = "userName=" + userName + "&" + "photoHash=" + photoHash;
        //HttpRequest.sendPost("http://47.96.134.136:3001/recordPhoto", params);

        System.out.println("写入区块链 photoHash :" + photoHash + " userName: " + userName);
    }

    public static String queryPhoto(String photoHash) {

        //发送 GET 请求
        String params = "photoHash=" + photoHash;
        return HttpRequest.sendGet("http://47.96.134.136:3000/queryPhoto", params);
    }

}

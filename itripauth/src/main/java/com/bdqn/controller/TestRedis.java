package com.bdqn.controller;

import cn.itrip.common.RedisAPI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestRedis {


    @Resource
    RedisAPI redisAPI;


    @RequestMapping("/testRedis")
    @ResponseBody
    public Object getRedis(){
        redisAPI.set("kk",60,"测试一下");
        return "我OK";
    }
}

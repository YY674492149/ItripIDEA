package com.bdqn.biz;

import cn.itrip.dao.itripUser.ItripUserMapper;
import cn.itrip.pojo.ItripUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserBiz {

    @Resource
    ItripUserMapper itripUsecrMapper;

    public ItripUser login(String name,String password){



        return itripUsecrMapper.getUserByPhone(name,password);
    }

}

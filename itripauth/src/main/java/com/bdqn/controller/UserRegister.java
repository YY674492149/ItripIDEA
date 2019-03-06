package com.bdqn.controller;

import cn.itrip.common.Dto;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisAPI;
import cn.itrip.dao.itripUser.ItripUserMapper;
import cn.itrip.pojo.ItripUser;
import cn.itrip.pojo.userinfo.ItripUserVO;
import com.bdqn.biz.Registerbiz;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api")
public class UserRegister {

    @Resource
    Registerbiz biz;
    @Resource
    RedisAPI redisAPI;
    @Resource
    ItripUserMapper itripUserMapper;

    @RequestMapping(value="/registerbyphone",method=RequestMethod.POST,produces = "application/json")

    public @ResponseBody Dto PhoneRegister(@RequestBody ItripUserVO userVO) throws Exception {

        String code = ""+(int)((Math.random()*9+1)*1000);
        //根据手机号  发验证码
        biz.setPhone(userVO.getUserCode(),code);
        //验证码放Redis中
        redisAPI.set("code:"+userVO.getUserCode(),60*60*2,code);

        //加入数据库
        ItripUser n = new ItripUser();
        n.setUserCode(userVO.getUserCode());
        n.setUserName(userVO.getUserName());
        n.setUserPassword(MD5.getMd5(userVO.getUserPassword(),32));
        n.setActivated(0);
        itripUserMapper.insertItripUser(n);


        return DtoUtil.returnSuccess("发送成功！","1111");
    }


    @RequestMapping("/validatephone")
    @ResponseBody
    public Dto validatephone(String user,String code) throws Exception {

        //Redis查看

        String thiscode =  redisAPI.get("code:"+user);

        if(thiscode!=null){
            //更换库里状态
            ItripUser n = new ItripUser();
            n.setUserCode(user);
            n.setActivated(1);
            itripUserMapper.updateItripUser(n);
            return DtoUtil.returnSuccess("激活成功！");
        }





        return DtoUtil.returnFail("激活失败","0000");
    }
}

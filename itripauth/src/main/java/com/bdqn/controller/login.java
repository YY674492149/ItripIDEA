package com.bdqn.controller;

import cn.itrip.common.Dto;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.MD5;
import cn.itrip.pojo.ItripTokenVO;
import cn.itrip.pojo.ItripUser;
import com.bdqn.biz.TokenService;
import com.bdqn.biz.UserBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
@RequestMapping("/api")
@Api(value="login",description = "用户模块")
public class login {

    @Resource
    UserBiz userBiz;
    @Resource
    TokenService tokenService;



//    @ApiOperation(value = "根据用户名密码返回数据",notes = "如果正确返回一个json")
//    @ApiImplicitParams({
////            @ApiImplicitParam(paramType="form",required=true,value="用户名",name="name",defaultValue="itrip@163.com"),
////            @ApiImplicitParam(paramType="form",required=true,value="密码",name="password",defaultValue="123456"),
//
//            @ApiImplicitParam(allowableValues="itrip@163.com",paramType="form",required=true,value="用户名",name="name",defaultValue="itrip@163.com"),
//            @ApiImplicitParam(allowableValues="123456",paramType="form",required=true,value="密码",name="password",defaultValue="123456")
//    })

//    @ApiOperation(value = "用户登录",httpMethod = "GET",
//            protocols = "HTTP", produces = "application/json",
//            response = Dto.class,notes="根据用户名、密码进行统一认证")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="query",required=true,value="用户名",name="name",defaultValue="itrip@163.com"),
//            @ApiImplicitParam(paramType="query",required=true,value="密码",name="password",defaultValue="123456"),
//    })

    @RequestMapping(value = "/dologin",method = RequestMethod.POST)
    @ResponseBody
    public Object dologin(HttpServletRequest request,String name,String password){

        //验证一下用户名密码是否正确
        ItripUser itripUser =  userBiz.login(name,MD5.getMd5(password,32));

        if(itripUser!=null){
            //生成一个token
            String token =  tokenService.generateToken(request.getHeader("User-Agent"),itripUser);
            //存
            tokenService.save(token,itripUser);

            ItripTokenVO itripTokenVO = new ItripTokenVO(token,60*60*2,Calendar.getInstance().getTimeInMillis());

            //返回前台数据
            return DtoUtil.returnSuccess("成功！","200");
        }



        //把登陆后的用户信息，存进去



        return DtoUtil.returnFail("错误","401");
    }
}

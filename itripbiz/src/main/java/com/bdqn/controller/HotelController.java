package com.bdqn.controller;

import cn.itrip.common.Dto;
import cn.itrip.common.DtoUtil;
import cn.itrip.dao.itripHotelTempStore.ItripHotelTempStoreMapper;
import cn.itrip.dao.itripHotelTempStore.RoomStoreVO;
import cn.itrip.dao.itripHotelTempStore.StoreVO;
import cn.itrip.pojo.ItripAreaDic;
import cn.itrip.pojo.ItripLabelDic;
import com.bdqn.biz.hotelbiz;
import com.bdqn.entities.ValidateRoomStoreVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class HotelController {

    @Resource
    hotelbiz biz;

    @Resource
    ItripHotelTempStoreMapper ihtsm;

    @RequestMapping(value="/hotel/queryhotcity/{type}",method = RequestMethod.GET)
    @ResponseBody
    public Dto hotcity(@PathVariable Integer type) throws Exception {
        List<ItripAreaDic> idc = biz.findhot(type);
        if(idc!=null){
            return DtoUtil.returnDataSuccess(idc);
        }
        return DtoUtil.returnFail("查询错误!","10000");
    }


    @RequestMapping(value="/hotel/queryhotelfeature",method = RequestMethod.GET)
    @ResponseBody
    public Dto fethotel() throws Exception {
        List<ItripLabelDic> idc = biz.tese();
        if(idc!=null){
            return DtoUtil.returnDataSuccess(idc);
        }
        return DtoUtil.returnFail("查询错误!","10000");
    }

    @RequestMapping(value="/hotelorder/getpreorderinfo",method = RequestMethod.POST)
    @ResponseBody
    public Dto doit(@RequestBody ValidateRoomStoreVO vo) throws Exception {
        //{CALL pro2_325_itrip(#{hid},#{rid},#{god},#{eod})}
        //导入数据
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("hid",vo.getHotelId());
        map.put("rid",vo.getRoomId());
        map.put("god",vo.getCheckInDate());
        map.put("eod",vo.getCheckOutDate());

        ihtsm.doitjust(map);


        //

        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("rid",vo.getRoomId());
        map2.put("god",vo.getCheckInDate());
        map2.put("eod",vo.getCheckOutDate());
        List<StoreVO> list =  ihtsm.selectIfHave(map2);
        //

        RoomStoreVO rsvo = new RoomStoreVO();
        rsvo.setHotelName("酒店名称：");
        rsvo.setStore(list.get(0).getStore());

        return DtoUtil.returnSuccess("获取成功",rsvo);
    }

}

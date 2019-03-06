package com.bdqn.controller;

import cn.itrip.common.Dto;
import cn.itrip.common.DtoUtil;
import cn.itrip.dao.itripAreaDic.ItripAreaDicMapper;
import cn.itrip.dao.itripAreaDic.ItripAreaDicVO;
import cn.itrip.dao.itripComment.ItripCommentMapper;
import cn.itrip.dao.itripHotel.*;
import cn.itrip.dao.itripHotelFeature.ItripHotelFeatureMapper;
import cn.itrip.dao.itripHotelFeature.ItripSearchDetailsHotelVO;
import cn.itrip.dao.itripHotelRoom.ItripHotelRoomMapper;
import cn.itrip.dao.itripHotelRoom.ItripHotelRoomVO;
import cn.itrip.dao.itripImage.ItripImageMapper;
import cn.itrip.dao.itripImage.ItripImageVO;
import cn.itrip.dao.itripLabelDic.ItripLabelDicMapper;
import cn.itrip.pojo.ItripHotelRoom;
import cn.itrip.pojo.ItripImage;
import com.alibaba.fastjson.JSONArray;
import com.bdqn.entities.DateUtil;
import com.bdqn.entities.EmptyUtils;
import com.bdqn.entities.SearchHotelRoomVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("api")
public class roomController {

    @Resource
    ItripHotelRoomMapper trm;

    @Resource
    ItripImageMapper iim;

    @Resource
    ItripAreaDicMapper irdm;

    @Resource
    ItripHotelFeatureMapper ihfm;

    @Resource
    ItripHotelMapper itm;

    @Resource
    ItripLabelDicMapper ildm;

    @Resource
    ItripCommentMapper icm;

    @RequestMapping(value="/hotelroom/queryhotelroombyhotel",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto roomfind(@RequestBody SearchHotelRoomVO vo) throws Exception {
        List<List<ItripHotelRoomVO>> hotelRoomVOList = null;

        List<Date> list =  DateUtil.getBetweenDates(vo.getStartDate(),vo.getEndDate());

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("hotelId",vo.getHotelId());
        map.put("list",list);


        vo.setIsHavingBreakfast(EmptyUtils.isEmpty(vo.getIsHavingBreakfast()) ? null : vo.getIsHavingBreakfast());
        vo.setIsBook(EmptyUtils.isEmpty(vo.getIsBook()) ? null : vo.getIsBook());
        vo.setIsTimelyResponse(EmptyUtils.isEmpty(vo.getIsTimelyResponse()) ? null : vo.getIsTimelyResponse());
        vo.setRoomBedTypeId(EmptyUtils.isEmpty(vo.getRoomBedTypeId()) ? null : vo.getRoomBedTypeId());
        vo.setIsCancel(EmptyUtils.isEmpty(vo.getIsCancel()) ? null : vo.getIsCancel());
        vo.setPayType(EmptyUtils.isEmpty(vo.getPayType()) ? null : vo.getPayType());



        map.put("isBook", vo.getIsBook());
        map.put("isHavingBreakfast", vo.getIsHavingBreakfast());
        map.put("isTimelyResponse", vo.getIsTimelyResponse());
        map.put("roomBedTypeId", vo.getRoomBedTypeId());
        map.put("isCancel", vo.getIsCancel());
        map.put("payType", vo.getPayType()==3?null:vo.getPayType());

//        List<ItripHotelRoomVO> listtwo =  trm.getItripRoom(map);
//        System.out.println(JSONArray.toJSONString(listtwo));
        List<ItripHotelRoomVO> listtwo =  trm.getItripHotelRoomListByMap(map);
        System.out.println(JSONArray.toJSONString(listtwo));






        hotelRoomVOList = new ArrayList();
        for (ItripHotelRoomVO roomVO : listtwo) {
            List<ItripHotelRoomVO> tempList = new ArrayList<ItripHotelRoomVO>();
            tempList.add(roomVO);
            hotelRoomVOList.add(tempList);
        }


        return DtoUtil.returnSuccess("获取成功", hotelRoomVOList);
    }


    @RequestMapping(value="/hotelroom/getimg/{targetId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto imgfind(@PathVariable("targetId") String targetId) throws Exception {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("type","0");
        map.put("targetId",targetId);
        List<ItripImageVO> ii =  iim.getItripImageListByMap2(map);

        return DtoUtil.returnSuccess("获取成功",ii);
    }


    @RequestMapping(value="/hotel/querytradearea/{cityId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto shangquan(@PathVariable("cityId") int cityId) throws Exception {

        List<ItripAreaDicVO> list =  irdm.getItripAreaDicById(cityId);


        return DtoUtil.returnDataSuccess(list);
    }


    @RequestMapping(value="/hotel/queryhoteldetails/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto HotDetails(@PathVariable("id") int id) throws Exception {


        List<ItripSearchDetailsHotelVO> list  =  ihfm.getItripHotelDatils(id);

        return DtoUtil.returnDataSuccess(list);
    }


    @RequestMapping(value="/hotel/queryhotelfacilities/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto htopo(@PathVariable("id") int id) throws Exception {


        ItripSearchFacilitiesHotelVO list  =  itm.getItripHotelByF(id);

        return DtoUtil.returnDataSuccess(list.getFacilities());
}

    @RequestMapping(value="/hotel/queryhotelpolicy/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto htopoli(@PathVariable("id") int id) throws Exception {


        ItripSearchPolicyHotelVO list  =  itm.getItripHotelByP(id);

        return DtoUtil.returnDataSuccess(list.getHotelPolicy());
    }


    @RequestMapping(value="/hotelroom/queryhotelroombed",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto htopoli() throws Exception {
        return DtoUtil.returnSuccess("成功",ildm.getItripLabelDicBED());
    }


    @RequestMapping(value="/comment/gethotelscore/{hotelId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto hapi(@PathVariable("hotelId") String id) throws Exception {
        //return DtoUtil.returnDataSuccess(icm.getItripCommentSSS(id));
        return DtoUtil.returnSuccess("成功",icm.getItripCommentSSS(Long.valueOf(id)));
    }

    @RequestMapping(value="/hotel/getvideodesc/{hotelId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto youyouyou(@PathVariable("hotelId") int id) throws Exception {
        UserIt ut =  itm.getItripVideo(id);



        String[] arr =  ut.getFraturestring().split(",");
        List<String> list2 = new ArrayList<String>();
        for(String n:arr){
            list2.add(n);
        }


        List<String> list = new ArrayList<String>();
        list.add(ut.getShangquan());

        HotelVideoDescVO hvv = new HotelVideoDescVO();
        hvv.setHotelName( ut.getHname());
        hvv.setHotelFeatureList(list2);
        hvv.setTradingAreaNameList(list);



        return DtoUtil.returnSuccess("成功",hvv);
    }

}

package com.bdqn.biz;

import cn.itrip.dao.itripAreaDic.ItripAreaDicMapper;
import cn.itrip.dao.itripLabelDic.ItripLabelDicMapper;
import cn.itrip.pojo.ItripAreaDic;
import cn.itrip.pojo.ItripLabelDic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class hotelbiz {

    @Resource
    ItripAreaDicMapper mapper;
    @Resource
    ItripLabelDicMapper dicMapper;

    public List<ItripAreaDic> findhot(int type) throws Exception {
        return mapper.getItripAreaDicListByType(type);
    }

    public List<ItripLabelDic> tese() throws Exception {
        return dicMapper.getItripLabelDicComeOn();
    }
}

package com.bdqn.controller;

import cn.itrip.common.Dto;
import cn.itrip.common.DtoUtil;
import com.bdqn.entity.ItripHotelVO;
import com.bdqn.entity.Page;
import com.bdqn.entity.SearchHotCityVO;
import com.bdqn.entity.SearchHotelVO;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api")
public class HotelController {

    @RequestMapping(value = "/hotellist/searchItripHotelListByHotCity",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto con(@RequestBody SearchHotCityVO searchHotCityVO){
        String url="http://localhost:8097/solr/hotel";
        HttpSolrClient httpSolrClient = new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser()); // 设置响应解析器
        httpSolrClient.setConnectionTimeout(500); // 建立连接的最长时间

        SolrQuery query=new SolrQuery();
        query.setQuery("cityId:"+searchHotCityVO.getCityId());
        query.setRows(searchHotCityVO.getCount());
        query.setSort("id",SolrQuery.ORDER.asc);

        try {
            QueryResponse response=httpSolrClient.query(query);
            List<ItripHotelVO> list=response.getBeans(ItripHotelVO.class);
            return DtoUtil.returnDataSuccess(list);
        } catch (SolrServerException e) {
            e.printStackTrace();
            return  null;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        } finally {
        }
    }


    @RequestMapping(value = "/hotellist/searchItripHotelPage",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto page(@RequestBody SearchHotelVO searchHotelVO){
        String url="http://localhost:8097/solr/hotel";
        HttpSolrClient httpSolrClient = new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser()); // 设置响应解析器
        httpSolrClient.setConnectionTimeout(500); // 建立连接的最长时间

        if(searchHotelVO.getPageNo()==null){
            searchHotelVO.setPageNo(1);
        }

        SolrQuery query=new SolrQuery("*:*");
        query.setStart((searchHotelVO.getPageNo()-1)*6);
        query.setRows(6);
        //query.setSort("id",SolrQuery.ORDER.asc);

//        StringBuilder str=new StringBuilder();
//        str.append("*:*");
        query.addFilterQuery("*:*");
        if(searchHotelVO.getKeywords()!=null)
        {
            //str.append(" and keyword:"+searchHotelVO.getKeywords());
            query.addFilterQuery("keyword:"+searchHotelVO.getKeywords());
        }
        if(searchHotelVO.getMinPrice()!=null)
        {
            //str.append(" and minPrice:["+ searchHotelVO.getMinPrice()+" TO *]");
            query.addFilterQuery("minPrice:["+ searchHotelVO.getMinPrice()+" TO *]");
        }
        if (searchHotelVO.getMaxPrice()!=null)
        {
            //str.append(" and maxPrice:[* TO "+searchHotelVO.getMaxPrice()+"]");
            query.addFilterQuery("maxPrice:[* TO "+searchHotelVO.getMaxPrice()+"]");
        }

        if(searchHotelVO.getTradeAreaIds()!=null&&searchHotelVO.getTradeAreaIds()!=""){
            //   ,17,115,116,117,
            String[] substr = searchHotelVO.getFeatureIds().split(",");

            //    featureIds:[*17*]
            for (String i : substr) {
                //str.append(" and featureIds:[*" + i + "*]");
                query.addFilterQuery("tradingAreaIds:*," + i + ",*");
            }
        }


        if(searchHotelVO.getFeatureIds()!=null&&searchHotelVO.getFeatureIds()!="") {
            //   ,17,115,116,117,
            String[] substr = searchHotelVO.getFeatureIds().split(",");

            //    featureIds:[*17*]
            for (String i : substr) {
                //str.append(" and featureIds:[*" + i + "*]");
                query.addFilterQuery("featureIds:*," + i + ",*");
            }
        }

        if(searchHotelVO.getHotelLevel()!=null){
            query.addFilterQuery("hotelLevel:"+searchHotelVO.getHotelLevel());
        }

        if(searchHotelVO.getDescSort()!=""&&searchHotelVO.getDescSort()!=null&&searchHotelVO.getDescSort().equals("avgScore")){
            query.setSort("avgScore",SolrQuery.ORDER.desc);
        }
        if(searchHotelVO.getDescSort()!=""&&searchHotelVO.getDescSort()!=null&&searchHotelVO.getDescSort().equals("minPrice")){
            query.setSort("minPrice",SolrQuery.ORDER.desc);
        }
        if(searchHotelVO.getDescSort()!=""&&searchHotelVO.getDescSort()!=null&&searchHotelVO.getDescSort().equals("hotelLevel")){
            query.setSort("hotelLevel",SolrQuery.ORDER.desc);
        }
        if(searchHotelVO.getAscSort()!=""&&searchHotelVO.getAscSort()!=null&&searchHotelVO.getAscSort().equals("isOkCount")){
            query.setSort("isOkCount",SolrQuery.ORDER.asc);
        }
        //query.addFilterQuery(str.toString());



        try {
            QueryResponse response=httpSolrClient.query(query);
            List<ItripHotelVO> list=response.getBeans(ItripHotelVO.class);
            SolrDocumentList sdl = response.getResults();
            Page<ItripHotelVO> listpage = new Page<>(searchHotelVO.getPageNo(),6,new Long(sdl.getNumFound()).intValue());
            listpage.setRows(list);
            return DtoUtil.returnDataSuccess(listpage);
        } catch (SolrServerException e) {
            e.printStackTrace();
            return  null;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        } finally {
        }



    }
}

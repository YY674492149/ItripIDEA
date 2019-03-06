package com.bdqn.entity;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.List;

public class hotel {

    public static void main(String[] args) throws IOException, SolrServerException {
        String url="http://localhost:8097/solr/hotel";
        HttpSolrClient httpSolrClient = new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser()); // 设置响应解析器
        httpSolrClient.setConnectionTimeout(500); // 建立连接的最长时间

        SolrQuery query=new SolrQuery();
        query.setQuery("*:*");
        query.setStart(2);
        query.setRows(2);
        query.setSort("id",SolrQuery.ORDER.asc);

        QueryResponse response=httpSolrClient.query(query);
        List<ItripHotelVO> list=response.getBeans(ItripHotelVO.class);
        for (ItripHotelVO i:list){

            System.out.println(i.getHotelName());

        }

    }


    @Field
    private String id;
    @Field
    private String hotelName;
    @Field
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

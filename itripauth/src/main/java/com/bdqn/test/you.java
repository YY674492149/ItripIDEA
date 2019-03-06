package com.bdqn.test;

import com.bdqn.utill.Animal;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class you {

    public static void main(String[] args) throws Exception {

        you.test2();

    }

    public void test1() throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("v1","呦呦");


        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("E:\\Maven\\Itrips\\itripauth\\src\\main\\resources"));

        Template template = configuration.getTemplate("freemarker.flt");

        template.process(map,new FileWriter("C:\\Users\\yuyang\\Desktop\\a.html"));
    }

    public static void test2() throws Exception {
        Animal dog = new Animal("小狗",25);
        Animal cat = new Animal("小猫",785);
        List<Animal> list = new ArrayList();
        list.add(dog);
        list.add(cat);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("li",list);


        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("E:\\Maven\\Itrips\\itripauth\\src\\main\\resources"));

        Template template = configuration.getTemplate("newfreemarker.flt");

        template.process(map,new FileWriter("C:\\Users\\yuyang\\Desktop\\a.html"));
    }
}

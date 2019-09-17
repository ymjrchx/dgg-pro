package net.dgg.dqc.backservice.framework.mongo.test;


import net.dgg.dqc.backservice.framework.mongo.AbstractModel;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangsh on 2018/5/9
 */
public class PersonInfo extends AbstractModel {

    private String id;
    private String name;
    private String age;
    private List<Map<String, Object>> list;

    public PersonInfo() {
    }

    public PersonInfo(String id, String name, String age, List<Map<String, Object>> list) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.list = list;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }
}

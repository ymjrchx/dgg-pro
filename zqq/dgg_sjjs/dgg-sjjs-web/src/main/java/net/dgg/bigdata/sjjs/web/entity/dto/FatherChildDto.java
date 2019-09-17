package net.dgg.bigdata.sjjs.web.entity.dto;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/28 13:36
 * @Description:
 */
public class FatherChildDto {

    private Object father;

    private List<Object> child;


    public Object getFather() {
        return father;
    }

    public void setFather(Object father) {
        this.father = father;
    }

    public List<Object> getChild() {
        return child;
    }

    public void setChild(List<Object> child) {
        this.child = child;
    }
}

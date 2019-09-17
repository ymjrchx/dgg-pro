//package net.dgg.dqc.backservice.framework.mongo.test;
//
//import com.alibaba.fastjson.JSON;
//import net.dgg.qcc.framework.mongo.MongoFactory;
//import net.dgg.qcc.framework.mongo.MongoQuery;
//import net.dgg.qcc.framework.mongo.TransConstant;
//import org.apache.commons.lang.StringUtils;
//import org.jongo.MongoCollection;
//import org.jongo.MongoCursor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.*;
//
///**
// * Created by jiangsh on 2018/5/9.
// */
//
//@RestController
//@RequestMapping("/mongoTestCon")
//public class MongoTestController {
//    private static final Logger logger = LoggerFactory.getLogger(MongoTestController.class);
//    private MongoCollection person;
//
//    private MongoCollection getM(String dbName, String colName) {
//        return MongoFactory.getColByDb(dbName, colName);
//    }
//
//    @RequestMapping(value="/", method = RequestMethod.GET)
//    public String hello() {
//        person = getM("local_db", TransConstant.PERSON);
//        System.out.println("person col--->" + person);
//        return "h";
//    }
//
//    /**
//     * 测试访问地址： http://localhost:8080/mongoTestCon/in
//     * @return
//     */
//    @RequestMapping(value="/in", method = RequestMethod.GET)
//    public String insert() {
//        for (int i = 1; i < 11; i++) {
//            String id = net.dgg.qcc.framework.mongo.ObjectId.get().toHexString();
//            List<Map<String, Object>> list = new ArrayList<>();
//            for (int j = 0; j < 5; j++) {
//                Map<String, Object> m = new HashMap<>();
//                m.put("key"+j, "value"+j);
//                list.add(m);
//            }
//            PersonInfo personInfo = new PersonInfo(id, "zhangsan"+i, "20"+i, list);
//            person.insert(personInfo);
//        }
//        return "success";
//    }
//
//    /**
//     * 查询数据
//     * @return
//     */
//    @RequestMapping(value = "/select", method = RequestMethod.GET)
//    public List<PersonInfo> selectData() {
//        Set<String> set = new HashSet<>();
//        set.add("201");
//        set.add("202");
//        set.add("203");
//
//        List<PersonInfo> list = new ArrayList<>();
//        MongoCursor<PersonInfo> iterator = person.find("{'age':{'$in':#}}", set).as(PersonInfo.class);
//        while (iterator != null && iterator.hasNext()) {
//            list.add(iterator.next());
//        }
//        logger.info(" list msg -> {}", JSON.toJSON(list));
//        return list;
//    }
//
//    /**
//     * 查询数据
//     * @return
//     */
//    @RequestMapping(value = "/select2", method = RequestMethod.GET)
//    public List<PersonInfo> selectData2() {
//        Set<String> set = new HashSet<>();
//        set.add("201");
//        set.add("202");
//        set.add("203");
//
//        String q = "{'age':{'$in':#}}";
//        String sort = "{'age':1}";
//        return MongoQuery.build(person).sortQuery(PersonInfo.class, p -> p , q, sort, set);
//    }
//
//    /**
//     * 保存/更新 数据
//     * @param personInfo
//     * @return
//     */
//    @RequestMapping(value = "/save", method = RequestMethod.GET)
//    public boolean saveData(PersonInfo personInfo) {
//        if (StringUtils.isNotEmpty(personInfo.getId())) { //update data
//            this.person.save(personInfo);
//        } else {  // insert data
//            String newId = net.dgg.qcc.framework.mongo.ObjectId.get().toHexString();
//            personInfo.setId(newId);
//            this.person.insert(personInfo);
//        }
//        return true;
//    }
//
//    /**
//     * 移除数据
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/remove", method = RequestMethod.GET)
//    public boolean removeData(String id) {
//        if (StringUtils.isNotEmpty(id)) {
//            person.remove(new org.bson.types.ObjectId(id));
//            return true;
//        } else {
//            logger.debug("id -> {}", id);
//            throw new IllegalArgumentException("参数异常！");
//        }
//    }
//
//}

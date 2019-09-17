//package net.dgg.tmd.foundation.platform.user.service;
//
//import net.dgg.tmd.foundation.platform.StartApplication;
//import net.dgg.tmd.foundation.platform.common.http.HttpUtils;
//import net.dgg.tmd.foundation.platform.role.service.RoleService;
//import net.dgg.tmd.foundation.platform.user.dao.InitTableDAO;
//import net.dgg.tmd.foundation.platform.user.dao.OrgRecorderDAO;
//import net.dgg.tmd.foundation.platform.user.entity.OrganizationDTO;
//import net.dgg.tmd.foundation.platform.user.entity.OrganizationEntity;
//import net.dgg.tmd.foundation.platform.user.entity.UserDTO;
//import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
//import net.fblock.cachemodule.CacheModuleManager;
//import net.fblock.core.common.KeyWorker;
//import net.fblock.core.util.JsonUtil;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = StartApplication.class)
//public class DatabaseTest {
//    @Autowired
//    private UserManager userManager;
//    @Autowired
//    private OrganizationManager organizationManager;
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private InitTableDAO initTableDAO;
//    @Autowired
//    private OrgRecorderDAO orgRecorderDAO;
//    @Value("${usercenter.url}/${usercenter.getuser.url}")
//    private String USERCENTER_USER_APIURL;
//    @Value("${usercenter.apptoken}")
//    private String USERCENTER_APPTOKEN;
//    @Value("${usercenter.url}/${usercenter.getorg.url}")
//    private String USERCENTER_ORG_APIURL;
//    @Value("${table_schema}")
//    private String TABLESCHEMA;
//
//    @Before
//    public void init(){
//        CacheModuleManager.getModuleManager().init();
//    }
//
//    @Test
//    public void testSelectByUserId(){
//        System.out.println(userManager.findUserById(7655556833174188032L));
//    }
//
//    @Test
//    public void testCreateTable(){
//        initTableDAO.createUserModuleTables();
//    }
//
//    @Test
//    public void testHasUserInfoTable(){
//        Map<String,String> map = new HashMap<>();
//        map.put("tableName","user_info");
//        map.put("tableSchema",TABLESCHEMA);
//        System.out.println("**************" + initTableDAO.hasTable(map));
//    }
//
//    @Test
//    public void testLoadValueMethod(){
//        System.out.println("**************" + userManager.loadValue("userLoginName:admin"));
//    }
//
//    @Test
//    public void testFindUserByKeyWords(){
//        List<UserEntity> userList = new ArrayList<>();
//        Map<String,Object> keyWords = new HashMap<>();
//        keyWords.put("phone",15608020035L);
//        userList = userManager.queryUser(keyWords);
//        Iterator iter = userList.iterator();
//        while (iter.hasNext()){
//            System.out.println(iter.next());
//        }
//    }
//
//    @Test
//    public void testSelectByOrgId() {
//        OrganizationEntity organizationEntity = new OrganizationEntity();
//        organizationEntity = organizationManager.findOrgEntityByOrgId(1);
//        System.out.println(organizationEntity);
//    }
//
//    @Test
//    public void testFindOrgByKeyWords(){
//        List<OrganizationEntity> orgList = new ArrayList<>();
//        Map<String,Object> keyWords = new HashMap<>();
//        keyWords.put("name","成都");
//        orgList = organizationManager.queryOrganization(keyWords);
//        Iterator iter = orgList.iterator();
//        while (iter.hasNext()){
//            System.out.println(iter.next());
//        }
//    }
//
//    @Test
//    public void testGetUserEntityFromCache(){
////        System.out.println(userManager.findUserById(1L));
//        System.out.println(userManager.findUserByLoginName("admin"));
//    }
//
//    @Test
//    public void testGetUserInfoFromServer(){
////        HttpUtils.sendPost(USERCENTER_USER_APIURL,USERCENTER_APPTOKEN,"userId=1");
////        HttpUtils.sendPost(USERCENTER_ORG_APIURL, USERCENTER_APPTOKEN, "orgId=1");
//    }
//
//    @Test
//    public void testGetUserFromServer(){
//        userManager.loadAndSaveUserFromServer(1);
//    }
//
//    @Test
//    public void testGetOrgFromServer(){
//        organizationManager.loadAndSaveOrgFromServer(1);
//    }
//
//    @Test
//    public void testDeleteFromDatabase(){
//        organizationManager.removeOrgByOrgId(99999);
//    }
//
//    @Test
//    public void testJsonTransform() {
//        String json = "{\"data\":\"{\\\"consumerKey\\\":\\\"dggbase\\\",\\\"createTime\\\":1519806689077,\\\"event\\\":\\\"ORG_SAVE_EVENT\\\",\\\"id\\\":7657106600875921408,\\\"message\\\":\\\"7657105347613691904\\\"}\",\"id\":7657106601428189184}";
//        HashMap data = JsonUtil.json2Obj(json,HashMap.class);
//        HashMap data2 = JsonUtil.json2Obj(data.get("data").toString(),HashMap.class);
//        System.out.println(data2.get("event") + ":" + data2.get("message"));
//    }
//
//    @Test
//    public void testFindOrgClosure() {
//        List<Integer> resultList1 = new ArrayList<>();
//        resultList1 = organizationManager.findDistanceByOrgId(68);
//        for (Integer result : resultList1) {
//            System.out.println(result);
//        }
//
//        List<Long> resultList2 = new ArrayList<>();
//        resultList2 = organizationManager.findAncestorIdByOrgId(83);
//        for (Long result : resultList2) {
//            System.out.println(result);
//        }
//
//        List<Long> resultList3 = new ArrayList<>();
//        resultList3 = organizationManager.findOrgIdByAncestorId(83);
//        for (Long result : resultList3) {
//            System.out.println(result);
//        }
//    }
//
//    @Test
//    public void testFindEntityByPid(){
//        List<OrganizationDTO> resultList = new ArrayList<>();
//        resultList = organizationManager.findOrgEntityListByPId(1);
//        for (OrganizationDTO result : resultList) {
//            System.out.println(result);
//        }
//    }
//
//    @Test
//    public void testFindUserDTO(){
//        Map<String,Object> map = new HashMap<>();
//        map.put("orgId",1);
//        map.put("start",1);
//        map.put("limit",10);
//        List<UserDTO> list = userManager.findUserDTOByOrgIdWithPage(map);
//        for (UserDTO userDTO : list) {
//            System.out.println(userDTO);
//        }
//    }
//
//    @Test
//    public void testTotalUsersByOrgId() {
//        System.out.println(userManager.findUserTotalByOrgId(1));
//    }
//    @Test
//    public void test_insertUserRole() {
//        roleService.saveUserRole(KeyWorker.nextId(),1L,2L,10L,new Date());
//    }
//}

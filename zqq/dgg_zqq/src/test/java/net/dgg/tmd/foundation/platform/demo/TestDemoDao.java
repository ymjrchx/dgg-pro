//package net.dgg.tmd.foundation.platform.demo;
//
//import net.dgg.tmd.foundation.platform.StartApplication;
//import net.dgg.tmd.foundation.platform.demo.dao.DemoDao;
//import net.dgg.tmd.foundation.platform.role.dao.RoleDao;
//import net.dgg.tmd.foundation.platform.role.service.RoleService;
//import net.dgg.tmd.foundation.platform.session.SessionManager;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.util.Assert;
//
//import java.util.*;
//
///**
// * @author nature
// * @create 2018-02-21 19:05
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = StartApplication.class)
//public class TestDemoDao {
//
//	private Logger logger= LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private DemoDao demoDao;
//
//	@Autowired
//	private RoleDao roleDao;
//
//	@Autowired
//	private RoleService roleService;
//
//	@Autowired
//	private SessionManager sessionManager;
//
//
//	@Test
//	public void testDemoSelect(){
//
////		List<Map> result=demoDao.demoSelect();
////
////		Assert.notNull(result,"result不可谓null");
////		Assert.isTrue(result.size()>0,"查询有结果");
//	}
//
//	@Test
//	public void testDemoWithPage(){
//		Map params=new HashMap<>();
//		params.put("startCount",0);
//		params.put("pageSize",2);
//		List<Map> data= demoDao.selectDemoWithPage(params);
//
//		logger.info("总条数为："+params.get("count"));
//		Assert.notNull(params.get("count"),"未获得总条数");
//		Assert.notNull(data,"数据不为空");
//	}
//
//	@Test
//	public void testInsertRole(){
////		UserRoleDTO userRoleDTO = new UserRoleDTO();
////		userRoleDTO.setId(KeyWorker.nextId());
////		userRoleDTO.setUserId(1);
////		userRoleDTO.setRoleId(1);
////		userRoleDTO.setCreatorId(1);
////		userRoleDTO.setCreatetime(new Date());
////		roleService.saveRoleWithRoleDTO(userRoleDTO);
////		roleDao.insertRoleWithRoleDTO(userRoleDTO);
//		Map<String,Object> map = new HashMap<>();
//		map.put("userId",1393);
//		map.put("roleName","系统管理员");
//		roleDao.delRoleWithUserNameAndRoleId(map);
////		System.out.println(roleDao.findExistRoleByUserIdAndRoleId(map));
//	}
//
//}

package net.dgg.tmd.foundation.platform.permission.service;

import net.dgg.tmd.foundation.platform.StartApplication;
import net.dgg.tmd.foundation.platform.permission.entity.OperatePermissionEntity;
import net.dgg.zqq.framework.redis.RedisUtils;
import net.fblock.core.common.KeyWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class DatabaseTest {
    @Autowired
    OperatePermissionService operatePermissionService;

    @Test
    public void TestInsertAndDelete() {
        OperatePermissionEntity operatePermissionEntity = new OperatePermissionEntity();
        long id = KeyWorker.nextId();
        operatePermissionEntity.setOperatePermissionId(id);
        operatePermissionEntity.setOperatePermissionName("TEST "+ new Date());
        operatePermissionEntity.setCode("Test");
        operatePermissionEntity.setCreatorId(12345L);
        operatePermissionEntity.setCreatetime(new Date());
        operatePermissionService.saveOperatePermission(operatePermissionEntity);
        System.out.println(operatePermissionService.findPermsEntityByPermsId(id));
        operatePermissionService.deleteOperatePermission(id);
    }

    @Test
    public void TestUpdate() {
        OperatePermissionEntity operatePermissionEntity = new OperatePermissionEntity();
        operatePermissionEntity.setOperatePermissionId(2l);
        operatePermissionEntity.setOperatePermissionName("TEST "+ new Date());
        operatePermissionEntity.setCreatorId(12345L);
        operatePermissionEntity.setCreatetime(new Date());
        operatePermissionService.updateOperatePermission(operatePermissionEntity);
    }

    @Test
    public void TestFindById(){
        System.out.println(operatePermissionService.findPermsEntityByPermsId(2));
    }

    @Test
    public void TestFindByKeyWords(){
        Map<String,Object> keyWords = new HashMap<>();
        keyWords.put("name","测试");
        List<OperatePermissionEntity> list = new ArrayList<>();
        list = operatePermissionService.queryOperatePermission(keyWords);
        for (OperatePermissionEntity operatePermissionEntity : list) {
            System.out.println(operatePermissionEntity);
        }
    }
    @Test
    public void clearIpRecord(){
      String  key = String.format("ZQQ_IP_COUNT_%s", "127.0.0.1");
        RedisUtils.del(key);
    }
}

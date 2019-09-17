package net.dgg;

import com.google.gson.Gson;
import lombok.Cleanup;
import lombok.SneakyThrows;
import net.dgg.dqc.backservice.StartApplication;
import net.dgg.dqc.backservice.dao_mybatis.TreeBookDao;
import net.dgg.dqc.backservice.service.dealProvinceName.ProvinceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExportData {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    TreeBookDao treeBookDao;

    @Test
    @SneakyThrows
    public void export() {
        List<String> ss = treeBookDao.getAllSonTreeBook(7453706779315408896L).stream().map(t -> new Gson().toJson(t)).collect(Collectors.toList());
        System.out.println(ss);
        @Cleanup FileOutputStream fos = new FileOutputStream("d:\\data\\list");
        @Cleanup ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ss);
        oos.flush();
    }

}

package net.dgg;

import net.dgg.dqc.backservice.StartApplication;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

/**
 * Created by 李程 on 2018/11/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class TestOCR {

    @Test
    public void testOCR() {
        String path = "D:\\soft\\data\\";        //我的项目存放路径
        File file = new File(path + "//verifyhandler.jpg");
        ITesseract instance = new Tesseract();

        File directory = new File(path);
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置训练库的位置
        instance.setDatapath("D:\\soft\\data\\Tess4J-3.4.8-src\\Tess4J\\tessdata");

        instance.setLanguage("eng");//chi_sim ：简体中文， eng	根据需求选择语言库
        String result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = instance.doOCR(file);
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        System.out.println("result: ");
        System.out.println(result);

    }

}

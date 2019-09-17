package net.dgg.bigdata.sjjs.web.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 刘阳
 * @ClassName <SimsunFontBean>
 * @despriction 宋体初始化bean
 * @create 2018/10/16 16:32
 **/
@Component
public class SimsunFontBean implements ApplicationRunner {
    private AtomicBoolean run = new AtomicBoolean(false);
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Font font = null;

    public void init() {
        if (run.getAndSet(true)) {
            return;
        }
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            ClassPathResource resource = new ClassPathResource("font/simsun.ttc");
            is = resource.getInputStream();
            bis = new BufferedInputStream(is);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            logger.info("宋体字体初始化成功！");
        } catch (FontFormatException e) {
            e.printStackTrace();
            logger.error("宋体字体格式化失败！");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("宋体字体创建失败！");
        } finally {
            try {
                if (null != bis) {
                    bis.close();
                }
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void run(ApplicationArguments args) {
        this.init();
    }

    public Font getFont(int style) {
        return font.deriveFont(style);
    }

    public Font getFont(int style, Float f) {
        return font.deriveFont(style, f);
    }

    public Font getFont(Float f) {
        return font.deriveFont(f);
    }
}

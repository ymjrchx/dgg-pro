package net.dgg.tmd.foundation.platform;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dgg.zqq.services.payment.PaymentOrderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 李程 on 2018/10/22.
 */
@SpringBootTest(classes = StartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class XTest {

    @Autowired
    PaymentOrderService paymentOrderService;

    @Before
    public void before() {
        log.info("开始测试->");
    }

    static {
        System.setProperty("java.awt.headless", "false");
    }

    @Test
    @SneakyThrows
    public void testWeChat() {
        /**
         Map<String, Object> action = paymentOrderService.generateTrade("test", "test", "wechat", "0.01", "测试Logo");
         String qrCode = (String) action.get("qrcode");
         log.debug("{}", qrCode);
         Toolkit toolkit = Toolkit.getDefaultToolkit();
         Dimension screen = toolkit.getScreenSize();
         int windowSize = 300;
         int imgSize = windowSize - 100;
         int paddingSize = (windowSize - imgSize) / 2;
         JFrame frame = new JFrame() {
         {
         setSize(windowSize, windowSize);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLocation((int)screen.getWidth() /2 - windowSize / 2,(int)screen.getHeight() /2 - windowSize/2);
         }

         @Override public void paint(Graphics g) {
         try {
         int index = qrCode.indexOf(",");
         String base64 = qrCode.substring(index + 1);
         BufferedImage image = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(base64)));
         Image img = image.getScaledInstance(imgSize, imgSize, Image.SCALE_DEFAULT);
         g.drawImage(img, paddingSize, paddingSize, imgSize, imgSize, null);
         } catch (Exception e) {
         e.printStackTrace();
         }
         }

         @Override public void repaint() {
         Graphics g = this.getContentPane().getGraphics();
         try {
         int index = qrCode.indexOf(",");
         String base64 = qrCode.substring(index + 1);
         BufferedImage image = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(base64)));
         Image img = image.getScaledInstance(imgSize, imgSize, Image.SCALE_DEFAULT);
         g.drawImage(img, paddingSize, paddingSize, imgSize, imgSize, null);
         } catch (Exception e) {
         e.printStackTrace();
         }
         }
         };
         frame.setVisible(true);
         Thread.sleep(Long.MAX_VALUE);
         */
    }

    @Test
    @SneakyThrows
    public void testImg() {
        /**
         InputStream inputStream = new ByteArrayInputStream(IOUtils.toByteArray(XTest.class.getResourceAsStream("/temps/default.png")));
         BufferedImage image = ImageIO.read(inputStream);
         int width;
         Assert.assertTrue("错误", (width = image.getWidth()) > 0);
         log.info("图片的宽度是：{}", width);
         */
    }

    @Test
    public void testPaymentSign() {
        String sign = paymentOrderService.generateSign("test", "test", "0.01", "测试");
        log.info(sign);
    }

    @After
    public void end() {
        log.info("-结束测试");
    }

}

package net.dgg.tmd.foundation.platform.session;

import net.dgg.tmd.foundation.platform.StartApplication;
import net.fblock.cachemodule.CacheModuleManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * @author nature
 * @create 2018-02-23 22:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class TestCommonSession {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SessionManager sessionManager;

	@Before
	public void init(){
		CacheModuleManager.getModuleManager().init();
	}

	@Test
	public void testGetValueSetValue(){
		long sessionId=sessionManager.newSession();
		CommonSession commonSession=sessionManager.getSession(sessionId);
		commonSession.putValue("test","testGetValueSetValue");

		Assert.isTrue(commonSession.getValue("test",String.class).equals("testGetValueSetValue"),"获取的值不正确");

		CommonSession commonSession2=sessionManager.getSession(sessionId);
		Assert.isTrue(commonSession2.getValue("test",String.class).equals("testGetValueSetValue"),"获取的值不正确");

	}

}

package net.dgg.tmd.foundation.platform.session;

import net.dgg.tmd.foundation.platform.StartApplication;
import net.fblock.cachemodule.CacheModuleManager;
import net.fblock.core.common.KeyWorker;
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
 * @create 2018-02-23 21:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class TestSessionManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SessionManager sessionManager;

	@Before
	public void init(){
		CacheModuleManager.getModuleManager().init();
	}

	@Test
	public void testNewSession(){
		long sessionId= sessionManager.newSession();

		CommonSession commonSession = sessionManager.getSession(sessionId);

		Assert.notNull(commonSession,"未获取到指定的session");
	}

	@Test
	public void testGetNotExistSession(){
		long sessionId= KeyWorker.nextId();

		CommonSession commonSession = sessionManager.getSession(sessionId);

		Assert.isNull(commonSession,"不存在的sessionid应该获取到null");
	}

	@Test
	public void testCloseSession(){
		long sessionId= sessionManager.newSession();
		sessionManager.closeSession(sessionId);

		CommonSession commonSession = sessionManager.getSession(sessionId);

		Assert.isNull(commonSession,"已关闭的session的sessionid应该获取到null");
	}

	@Test
	public void testSessionExist(){
		long sessionId= sessionManager.newSession();

		Assert.isTrue(sessionManager.sessionExist(sessionId),"新生成的session应该存在");
	}

	@Test
	public void testSessionNotExist(){
		long sessionId=KeyWorker.nextId();

		Assert.isTrue(!sessionManager.sessionExist(sessionId),"不存在的session应该不存在");
	}

	@Test
	public void testFlushEsssion(){
		long sessionId= sessionManager.newSession();
		sessionManager.flushSession(sessionId);
	}
}

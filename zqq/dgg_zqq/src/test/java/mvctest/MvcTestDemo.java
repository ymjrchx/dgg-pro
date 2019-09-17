package mvctest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;


import net.dgg.tmd.foundation.platform.StartApplication;

/**
 * @author LuoChang
 */
@RunWith(SpringJUnit4ClassRunner.class)   
@SpringBootTest(classes = StartApplication.class)
@WebAppConfiguration   
public class MvcTestDemo {

    static Logger logger = LoggerFactory.getLogger(MvcTestDemo.class);
    
    @Autowired protected WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void before() {
        this.mvc = webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void test_monitorStrategy() {
        ResultActions result;
        try {
            result = this.mvc.perform(post("/monitor/strategy/list")
                    .param("page", "2")
                    .param("limit", "10"));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    @Test
    public void test_monitorSite() {
        ResultActions result;
        try {
            result = this.mvc.perform(post("/monitor/site/list")
                    .param("page", "2")
                    .param("limit", "10"));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    @Test
    public void test_roleUserManager() {
        ResultActions result;
        try {
            result = this.mvc.perform(post("/role/usermanager/userpage")
                    .param("page", "2")
                    .param("limit", "10"));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    @Test
    public void test_searchUserWithOrg() {
        ResultActions result;
        try {
            result = this.mvc.perform(post("/role/usermanager/search")
                    .param("page", "2")
                    .param("limit", "10"));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    @Test
    public void test_showDetailTable() {
        ResultActions result;
        try {
            result = this.mvc.perform(post("/workSpace/showDetails")
                    .param("page", "2")
                    .param("limit", "10"));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    @Test
    public void test_userpage() {
        ResultActions result;
        try {
            result = this.mvc.perform(post("/role/usermanager/userpage")
                    .param("page", "2")
                    .param("limit", "10"));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    @Test
    public void test_search() {
        ResultActions result;
        try {
            result = this.mvc.perform(post("/role/usermanager/search")
                    .param("page", "2")
                    .param("mainKey", "2")
                    .param("selectCode", "1")
                    .param("limit", "10"));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}

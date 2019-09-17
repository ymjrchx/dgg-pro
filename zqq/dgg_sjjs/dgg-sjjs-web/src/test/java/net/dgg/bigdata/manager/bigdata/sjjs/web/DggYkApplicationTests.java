package net.dgg.bigdata.manager.bigdata.sjjs.web;

import net.dgg.bigdata.sjjs.web.DggYkApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DggYkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DggYkApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testMongoTemplate() {
        System.out.println(mongoTemplate);
        System.out.println(mongoTemplate.getDb().getCollection("detail_results").countDocuments());
    }

}

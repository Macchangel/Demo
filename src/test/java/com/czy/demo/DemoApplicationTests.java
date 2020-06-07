package com.czy.demo;

import com.czy.demo.dao.DemoDao;
import com.czy.demo.service.DemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    DemoService demoService;

    @Autowired
    DemoDao demoDao;

    @Test
    void contextLoads() {
        demoService.getData("", "", "");
    }

    @Test
    void testDemoDao() throws IOException {
        Float f = demoDao.getTopicProByIdAndTopic("1102020-05-0153", 499, "news_test", "topic");
        System.out.println(f);
    }

}

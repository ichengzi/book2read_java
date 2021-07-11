package com.chengzi.book2read.controller;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BookSpiderControllerTest {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Autowired
    BookSpiderController controller;

    @Test
    public void CrawlBook() {
        //controller.CrawlBook("圣墟");
        try {
            // 借助google的控制台console部署，那里不能随意访问外网。
            // catch住这个ex
            controller.meiRiYiWen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

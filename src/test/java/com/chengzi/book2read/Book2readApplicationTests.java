package com.chengzi.book2read;

import com.google.appengine.api.datastore.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Book2readApplicationTests {

	@Test
	public void contextLoads() throws IOException, EntityNotFoundException {
        BookCrawlerController x = new BookCrawlerController();
		x.CrawlBook("圣墟");
	}

}

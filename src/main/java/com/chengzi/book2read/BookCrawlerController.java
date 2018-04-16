package com.chengzi.book2read;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Imports the Google Cloud client library

import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

@RestController
public class BookCrawlerController {

    @GetMapping("/CrawlBook")
    public String CrawlBook(@RequestParam("name") String name) throws IOException {

        String url = "";
        if (name.equals("圣墟"))
            url = "https://www.piaotian.com/html/8/8253/index.html";
        else if (name.equals("凡人仙界篇"))
            url = "https://www.piaotian.com/html/9/9102/index.html";
        else
            return name + " is not support now!";

        String html = Helper.basicGetRequest(url);
        Document doc = Jsoup.parse(html);
        Elements items = doc.select("body > div:nth-child(5) > div.mainbody > div.centent ul:nth-child(n+3) a");
        int count = 10;
        if (items.size() < 10)
            count = items.size();
        List<Element> items2 = items.subList(items.size() - count, items.size());
        Collections.reverse(items2);

        // Instantiates a client
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        int new_atricle_count = 0;
        for (Element item : items2) {
            String id = item.attr("href").replaceAll(".html", "");
            String title = item.ownText();

            Key key = datastore.newKeyFactory().setKind(name).newKey(Long.parseLong(id));
            Entity entity = datastore.get(key);
            if (entity == null) {
                new_atricle_count += 1;
                String urlDetail = "";
                if (name.equals("圣墟"))
                    urlDetail = "https://www.piaotian.com/html/8/8253/" + id + ".html";
                else
                    urlDetail = "https://www.piaotian.com/html/9/9102/" + id + ".html";
                String htmlDetail = Helper.basicGetRequest(urlDetail);
                String[] liststr = htmlDetail.split("\r\n");
                String content = liststr[53].replaceAll("<br />", "")
                        .replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;", "\r\n");

                // Prepares the new entity
                Entity article = Entity.newBuilder(key)
                        .set("title", title)
                        .set("content", content)
                        .set("create_time", Timestamp.now())
                        .set("data_lastchangetime", Timestamp.now())
                        .build();

                // Saves the entity
                datastore.put(article);
            }
        }
        return Timestamp.now() + " new article " + new_atricle_count + " .";
    }
}

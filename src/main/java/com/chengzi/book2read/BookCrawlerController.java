package com.chengzi.book2read;

//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.google.appengine.api.datastore.*;
import com.google.apphosting.api.ApiProxy;
import com.google.auth.appengine.AppEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.Timestamp;
//import com.google.cloud.datastore.*;
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
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

// Imports the Google Cloud client library

//import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

@RestController
public class BookCrawlerController {

    private static final Logger log = Logger.getLogger(HomeController.class.getName());

    @GetMapping("/CrawlBook")
    public String CrawlBook(@RequestParam("name") String name) throws IOException, EntityNotFoundException {

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

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        int new_atricle_count = 0;
        for (Element item : items2) {
            String id = item.attr("href").replaceAll(".html", "");
            String title = item.ownText();

            String tablename = "";
            if (name.equals("圣墟"))
                tablename = "shengxu";
            if (name.equals("凡人仙界篇"))
                tablename = "fanren";

            Key key = KeyFactory.createKey(tablename, Long.parseLong(id));
            Entity entity;
            try {
                entity = datastore.get(key);
            } catch (EntityNotFoundException e) {
                entity = null;
            }
            if (entity == null) {

                entity = new Entity(key);

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


                Text tmp = new Text(content);
                // Prepares the new entity
                entity.setProperty("title", title);
                entity.setProperty("content", tmp);
                entity.setProperty("create_time", new Date());
                entity.setProperty("data_lastchangetime", new Date());

                // Saves the entity
                datastore.put(entity);

                MailSender sender = new MailSender();
                sender.sendSimpleMail(title, content);
            }
        }
        String res = name + ", " + new Date() + ", Generate new article " + new_atricle_count + ".";
        log.info(res);
        return res;
    }
}

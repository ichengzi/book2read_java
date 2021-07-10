package com.chengzi.book2read.controller;

import com.chengzi.book2read.service.MailSender;
import com.chengzi.book2read.util.Helper;
import com.google.appengine.api.datastore.*;
import com.google.common.base.Charsets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author admin
 */
@RestController
@Slf4j
@RequestMapping("/book")
public class BookCrawlerController {

    @Autowired
    MailSender mailSender;
    @Autowired
    DatastoreService datastoreService;

    @GetMapping("/CrawlBook")
    @SneakyThrows
    public String CrawlBook(@RequestParam("name") String name) {

        String url = "";
        if (name.equals("圣墟")) {
            url = "https://www.biqiuge.com/book/4772/";
        } else if (name.equals("凡人仙界篇")) {
            url = "https://www.biqiuge.com/book/26182/";
        } else {
            return name + " is not support now!";
        }

        String html = Helper.basicGetRequest(url);
        Document doc = Jsoup.parse(html);
        Elements items = doc.select("body > div.listmain > dl > dd > a");
        int count = 10;
        if (items.size() < 10) {
            count = items.size();
        }
        List<Element> items2 = items.subList(items.size() - count, items.size());
        Collections.reverse(items2);

        int newArticleCount = 0;
        for (Element item : items2) {
            String id = item.attr("href")
                    .replaceAll(".html", "")
                    .replaceAll("/book/4772/", "")
                    .replaceAll("/book/26182/", "");
            String title = item.ownText();

            String tablename = "";
            if (name.equals("圣墟")) {
                tablename = "shengxu";
            }
            if (name.equals("凡人仙界篇")) {
                tablename = "fanren";
            }

            Key key = KeyFactory.createKey(tablename, Long.parseLong(id));
            Entity entity = null;
            try {
                entity = datastoreService.get(key);
            } catch (EntityNotFoundException ignored) {
            }

            if (entity == null) {
                entity = new Entity(key);

                newArticleCount += 1;
                String urlDetail = "";
                if (name.equals("圣墟")) {
                    urlDetail = "https://www.biqiuge.com/book/4772/" + id + ".html";
                } else {
                    urlDetail = "https://www.biqiuge.com/book/26182/" + id + ".html";
                }
                String htmlDetail = Helper.basicGetRequest(urlDetail);

                Document docDetail = Jsoup.parse(htmlDetail);
                docDetail.outputSettings().prettyPrint(false);
                StringBuilder builder = new StringBuilder();
                List<TextNode> nodes = docDetail.selectFirst("#content").textNodes();
                nodes = nodes.subList(0, nodes.size() - 2);
                for (TextNode node : nodes) {
                    builder.append(node.text() + "\r\n");
                }
                String content = builder.toString();

                // Prepares the new entity
                Date now = new Date();
                entity.setProperty("title", title);
                entity.setProperty("content", new Text(content));
                entity.setProperty("create_time", now);
                entity.setProperty("data_lastchangetime", now);

                datastoreService.put(entity);
                mailSender.sendMultipartMail(name + " - " + title, content);
            }
        }
        String res = name + ", " + new Date() + ", Generate new article " + newArticleCount + ".";
        log.info(res);
        return res;
    }

    @GetMapping("/meiRiYiWen")
    @SneakyThrows
    public String meiRiYiWen() {
        String html = Helper.getRsp("https://meiriyiwen.com");
        Document doc = Jsoup.parse(html);

        String title = doc.selectFirst("#article_show > h1").text();
        String author = doc.selectFirst("#article_show > p > span").text();
        String article = doc.select("#article_show > div.article_text > p").stream()
                .map(Element::text)
                .collect(Collectors.joining("\r\n\r\n"));
        int hash = (title + author).hashCode();

        Key key = KeyFactory.createKey("meiriyiwen", hash);
        Entity entity = null;
        try {
            entity = datastoreService.get(key);
        } catch (EntityNotFoundException ignored) {
        }

        if (entity != null) {
            throw new Exception();
        } else {
            entity = new Entity(key);
            entity.setProperty("title", title);
            entity.setProperty("author", author);
            entity.setProperty("article", new Text(article));
            entity.setProperty("create_time", new Date());
            datastoreService.put(entity);
            mailSender.sendMultipartMail(title + " - " + author, article);
            return "ok";
        }
    }
}
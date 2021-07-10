package com.chengzi.book2read.controller;

import com.chengzi.book2read.util.Helper;
import com.chengzi.book2read.entity.LinkModel;
import com.google.appengine.api.datastore.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

/**
 * @author chengzi
 */
@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/hello")
    public String sayHello(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/articleList")
    @SneakyThrows
    public String articleList(@RequestParam("name") String name, Model model) {
        String url = "https://www.piaotian.com/html/9/9102/index.html";
        if ("圣墟".equals(name)) {
            url = "https://www.piaotian.com/html/8/8253/index.html";
        }

        String html = Helper.basicGetRequest(url);
        Document doc = Jsoup.parse(html);

        Elements items = doc.select("body > div:nth-child(5) > div.mainbody > div.centent ul:nth-child(n+3) a");
        int count = Math.min(items.size(), 10);
        List<Element> items2 = items.subList(items.size() - count, items.size());
        Collections.reverse(items2);

        List<LinkModel> articles = new ArrayList<>();
        for (Element item : items2) {
            LinkModel link = new LinkModel();
            String id = item.attr("href").replaceAll(".html", "");
            link.setHref("/articleDetail?name=" + name + "&id=" + id + "&title=" + item.ownText());
            link.setContent(item.ownText());
            articles.add(link);
        }

        model.addAttribute("name", name);
        model.addAttribute("articles", articles);
        return "articleList";
    }

    @GetMapping("/articleDetail")
    public String articleDetail(@RequestParam("id") String id,
                                @RequestParam("name") String name,
                                @RequestParam("title") String title,
                                Model model) throws IOException {
        log.info("[[title={}]], args:{}", "articleDetail", name);
        String url = "";
        if (name.equals("圣墟")) {
            url = "https://www.piaotian.com/html/8/8253/" + id + ".html";
        } else {
            url = "https://www.piaotian.com/html/9/9102/" + id + ".html";
        }


        String html = Helper.basicGetRequest(url);
        String[] liststr = html.split("\r\n");
        String content = liststr[53].replaceAll("<br />", "")
                .replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;", "\r\n");
        String[] paragraphs = content.split("\r\n");

        model.addAttribute("title", title);
        model.addAttribute("paragraphs", paragraphs);
        return "articleDetail";
    }

    @GetMapping("/articleList2")
    public String articleList2(@RequestParam("name") String name, Model model) throws IOException {
        log.info("[[title={}]], args:{}", "articleList2", name);
        String tablename = "";
        if ("圣墟".equals(name)) {
            tablename = "shengxu";
        }
        if ("凡人仙界篇".equals(name)) {
            tablename = "fanren";
        }


        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query(tablename).addSort("create_time", Query.SortDirection.DESCENDING);
        PreparedQuery preparedQuery = datastore.prepare(query);
        List<Entity> items = preparedQuery.asList(FetchOptions.Builder.withLimit(10));

        List<LinkModel> articles = new ArrayList<LinkModel>();
        for (Entity item : items) {
            LinkModel link = new LinkModel();
            String id = Long.toString(item.getKey().getId());
            String title = item.getProperty("title").toString();
            link.setHref("/articleDetail2?name=" + name + "&id=" + id + "&title=" + title);
            link.setContent(title);

            articles.add(link);
        }

        model.addAttribute("name", name);
        model.addAttribute("articles", articles);
        return "articleList";
    }

    @GetMapping("/articleDetail2")
    public String articleDetail2(@RequestParam("id") String id,
                                 @RequestParam("name") String name,
                                 @RequestParam("title") String title,
                                 Model model) {

        String tablename = "";
        if (name.equals("圣墟"))
            tablename = "shengxu";
        if (name.equals("凡人仙界篇"))
            tablename = "fanren";

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = KeyFactory.createKey(tablename, Long.parseLong(id));
        Entity article;
        try {
            article = datastore.get(key);
        } catch (EntityNotFoundException e) {
            log.info(id + " not found.");
            return "404";
        }


        Text text = (Text) article.getProperty("content");
        String content = text.getValue();
        String[] paragraphs = content.split("\r\n");
        model.addAttribute("title", title);
        model.addAttribute("paragraphs", paragraphs);

        log.info(content);
        return "articleDetail";
    }
}

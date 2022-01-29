package com.chengzi.book2read.controller;

import com.chengzi.book2read.service.MailSender;
import com.chengzi.book2read.util.HttpUtils;
import com.google.appengine.api.datastore.*;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/book")
public class BookSpiderController {

    @Autowired
    MailSender mailSender;
    @Autowired
    DatastoreService datastoreService;
    @Resource(name = "freeMarkerConfig")
    FreeMarkerConfigurer freeMarkerConfig;

    @GetMapping("/meiRiYiWen")
    @SneakyThrows
    public Map<String, Object> meiRiYiWen() {
        log.info("[[title={}]], spider start:{}", "meiRiYiWen", "meiRiYiWen");
        String rawHtml = HttpUtils.getRsp("https://meiriyiwen.com");
        Document doc = Jsoup.parse(rawHtml);

        String title = doc.selectFirst("#article_show > h1").text();
        String author = doc.selectFirst("#article_show > p > span").text();
        String subject = title + " - " + author;
        List<String> items = doc.select("#article_show > div.article_text > p").stream()
                .map(Element::text).collect(Collectors.toList());

        Key key = KeyFactory.createKey("meiriyiwen", subject.hashCode());
        Assert.isNull(queryFromGoogleDataStore(key), subject + " is already exist in database");

        Entity entity = new Entity(key);
        entity.setProperty("title", title);
        entity.setProperty("author", author);
        entity.setProperty("article", new Text(String.join("\r\n", items)));
        entity.setProperty("create_time", new Date());
        datastoreService.put(entity);
        log.info("[[title={}]], spider write data store:{}", "meiRiYiWen", subject);

        Map<String, Object> model = new HashMap<>();
        model.put("title", title);
        model.put("author", author);
        model.put("items", items);
        Template template = freeMarkerConfig.getConfiguration().getTemplate("meiArticle.ftl");
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        mailSender.sendMultipartMailV2(
                "chengzi12130+spider@gmail.com", "chengzi12130@gmail.com",
                subject, content);
        log.info("[[title={}]], spider send mail:{}", "meiRiYiWen", subject);
        return model;

        // 邮送域：m3kw2wvrgufz5godrsrytgd7.apphosting.bounces.google.com
        // 这个 邮送域 导致 onenote 无法识别, me@onenote.com 无法把文章添加到onenote
        // mailSender.sendMultipartMailV2("chengzi12130@gmail.com", "me@onenote.com", subject + "@每日一文", content);
    }

    /**
     * @return query data with key. if not exist, return null.
     */
    private Entity queryFromGoogleDataStore(Key key) {
        try {
            return datastoreService.get(key);
        } catch (EntityNotFoundException ignored) {
            return null;
        }
    }
}
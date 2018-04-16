/**
 * Copyright 2017 Google Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chengzi.book2read;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
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
//import org.springframework.web.bind.annotation.RestController;

@Controller
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
    public String articleList(@RequestParam("name") String name, Model model) throws IOException {

        List<LinkModel> articles = new ArrayList<LinkModel>();
        String url = "";
        if(name.equals("圣墟"))
            url = "https://www.piaotian.com/html/8/8253/index.html";
        else
            url = "https://www.piaotian.com/html/9/9102/index.html";

        String html = Helper.basicGetRequest(url);
        Document doc = Jsoup.parse(html);

        Elements items = doc.select("body > div:nth-child(5) > div.mainbody > div.centent ul:nth-child(n+3) a");
        int count =10;
        if(items.size() <10)
            count = items.size();
        List<Element> items2 = items.subList(items.size()-count,items.size());
        Collections.reverse(items2);

        for (Element item : items2) {
            LinkModel tmp =  new LinkModel();
            String id = item.attr("href").replaceAll(".html","");
            tmp.setHref("/articleDetail?name="+name+"&id="+id+"&title="+item.ownText());
            tmp.setContent(item.ownText());
            articles.add(tmp);
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

        String url = "";
        if(name.equals("圣墟"))
            url= "https://www.piaotian.com/html/8/8253/" + id + ".html";
        else
            url = "https://www.piaotian.com/html/9/9102/" + id + ".html";


        String html = Helper.basicGetRequest(url);
        String[] liststr = html.split("\r\n");
        String content = liststr[53].replaceAll("<br />","")
                .replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;", "\r\n");
        String[] paragraphs = content.split("\r\n");

        //System.out.println(String.join("\r\n",paragraphs));

        model.addAttribute("title", title);
        model.addAttribute("paragraphs", paragraphs);
        return "articleDetail";
    }

}

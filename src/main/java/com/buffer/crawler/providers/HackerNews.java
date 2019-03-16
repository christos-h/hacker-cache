package com.buffer.crawler.providers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class HackerNews extends ContentProvider {
    private static final String baseURl = "https://news.ycombinator.com/";
    private static final String nextPage = "news?p=";

    public HackerNews(){
        super(baseURl, nextPage, true);
    }

    @Override
    List<WebPage> extract(String baseUrl) throws IOException {
        Document doc = Jsoup.connect(baseUrl).get();
        Elements links = doc.select(".storylink");
        return links.stream()
                .map(e -> new WebPage(e.html(), e.attr("href")))
                .filter(wp -> !isHackerNewsItem(wp.urlString()))
                .collect(Collectors.toList());
    }

    @Override
    Topic topic() {
        return Topic.TECH;
    }

    private boolean isHackerNewsItem(String urlString) {
        return urlString.contains("item?");
    }


}

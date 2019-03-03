package com.buffer.crawler.pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class HackerNews extends NewsPage {
    private static final String baseURl = "https://news.ycombinator.com/";
    private static final String nextPage = "news?p=";

    public HackerNews(){
        this(baseURl, nextPage, true);
    }

    private HackerNews(String baseUrl, String nextPage, boolean isPaginated) {
        super(baseUrl, nextPage, isPaginated);
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

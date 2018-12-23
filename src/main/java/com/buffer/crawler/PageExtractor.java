package com.buffer.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class PageExtractor {


    List<WebPage> extract(ExtractableUrl extractableUrl, int nPages) throws IOException {
        List<WebPage> pages = new LinkedList<>();
        for (int i = 0; i < nPages; i++) {
            pages.addAll(extract(extractableUrl.page(i)));
        }
        return pages;
    }

    private List<WebPage> extract(String baseUrl) throws IOException {
        Document doc = Jsoup.connect(baseUrl).get();
        Elements links = doc.select(".storylink");
        return links.stream()
                .map(e -> new WebPage(e.html(), e.attr("href")))
                .filter(wp -> !wp.isHackerNewsItem()) //TODO this is internal HN stuff
                .collect(Collectors.toList());
    }


}

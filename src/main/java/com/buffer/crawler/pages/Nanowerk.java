package com.buffer.crawler.pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Nanowerk extends NewsPage {
    private static final String MOZILLA_USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:63.0) Gecko/20100101 Firefox/63.0";

    private static final String baseURl = "https://www.nanowerk.com/category-spotlight.php";
    private static final String nextPage = "?page=";

    public Nanowerk() {
        this(baseURl, nextPage, true);
    }

    private Nanowerk(String baseUrl, String nextPage, boolean isPaginated) {
        super(baseUrl, nextPage, isPaginated);
    }

    @Override
    List<WebPage> extract(String baseUrl) throws IOException {
        Document doc = Jsoup
                .connect(baseUrl)
                .userAgent(MOZILLA_USER_AGENT)
                .get();

        Elements links = doc.select("article");

        return links.stream()
                .map(e -> {

                    String title = e.select("h2").select("a").html();
                    String link = e.select("h2").select("a").attr("href");

                    return new WebPage(title, link);
                })
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    Topic topic() {
        return Topic.NANOTECH;
    }

}

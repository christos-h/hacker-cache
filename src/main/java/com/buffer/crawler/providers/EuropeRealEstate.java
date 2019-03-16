package com.buffer.crawler.providers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class EuropeRealEstate extends ContentProvider {
    private static final String MOZILLA_USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:63.0) Gecko/20100101 Firefox/63.0";

    private static final String baseURL = "http://europe-re.com/category/news/";
    private static final String nextPage = "";
    public EuropeRealEstate() {
        super(baseURL, nextPage, true);
    }

    @Override
    List<WebPage> extract(String baseUrl) throws IOException {
        Document doc = Jsoup
                .connect(baseUrl)
                .userAgent(MOZILLA_USER_AGENT)
                .get();

        Elements links = doc.select("h5.section-title.no-margin-top");

        return links.stream()
                .map(e -> {
                    String title = e.select("a").html();
                    String link = e.select("a").attr("href");

                    // Link is not fully qualified
                    return new WebPage(title,"http://europe-re.com" + link);
                })
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Topic topic() {
        return Topic.REALESTATE;
    }
}

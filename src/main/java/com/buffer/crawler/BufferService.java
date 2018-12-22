package com.buffer.crawler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BufferService {
    private static final String HACKER_NEWS_URL = "https://news.ycombinator.com/";


    private String webPageString = "No content yet...";

    @Scheduled(initialDelay = 0, fixedDelay = 1000 * 60 * 10)
    public void getPage() {
        try {
            List<WebPage> pages = new PageExtractor().extract(HACKER_NEWS_URL);
            String collapsibles = pages.parallelStream()
                    .filter(page -> !page.hasSameOriginPolicy())
                    .map(WebPage::putInFrame)
                    .collect(Collectors.joining());

            webPageString = new Template().getTemplate().replace("REPLACE_ME", collapsibles);
        } catch (IOException ignored) {

        }
    }

    public String getContent() {
        return webPageString;
    }


}

package com.buffer.crawler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BufferService {
    private static final ExtractableUrl HACKER_NEWS = new ExtractableUrl("https://news.ycombinator.com/", "news?p=");

    private List<WebPage> pages = new LinkedList<>();

    @Scheduled(initialDelay = 0, fixedDelay = 1000 * 60 * 10)
    public void getPage() {
        try {
            pages = new PageExtractor().extract(HACKER_NEWS, 5)
                    .parallelStream()
                    .filter(p -> !p.hasSameOriginPolicy())
                    .collect(Collectors.toList());
        } catch (IOException ignored) {

        }
    }

    public String getContent(int nPages) {
        if (pages.isEmpty()) return "No content yet...";
        String pageString = pages.stream()
                .limit(nPages)
                .map(WebPage::putInFrame)
                .collect(Collectors.joining());
        return new Template().getTemplate().replace("REPLACE_ME", pageString);
    }


}

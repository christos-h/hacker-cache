package com.buffer.crawler;

import com.buffer.crawler.pages.ExtractablePage;
import com.buffer.crawler.pages.Nanowerk;
import com.buffer.crawler.pages.WebPage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BufferService {
    private static final ExtractablePage HACKER_NEWS = new Nanowerk();

    private List<WebPage> pages = new LinkedList<>();

    @Scheduled(initialDelay = 0, fixedDelay = 1000 * 60 * 10)
    public void getPage() {
        try {
            pages = HACKER_NEWS.extract(5)
                    .parallelStream()
                    .filter(p -> !p.hasSameOriginPolicy())
                    .collect(Collectors.toList());
        } catch (IOException ignored) {

        }
    }

    public String getContent(String topic, int nPages) {
        if (pages.isEmpty()) return "Loading content. Please refresh in ~1 minute.";
        String pageString = pages.stream()
                .limit(nPages)
                .map(WebPage::putInFrame)
                .collect(Collectors.joining());
        return new Template().getTemplate().replace("REPLACE_ME", pageString);
    }


}

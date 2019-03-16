package com.buffer.crawler;

import com.buffer.crawler.providers.*;
import com.buffer.crawler.util.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BufferService {
    private static final ContentProvider NANOWERK = new Nanowerk();
    private static final ContentProvider HACKER_NEWS = new HackerNews();

    private List<WebPage> pagesNanowerk = new LinkedList<>();
    private List<WebPage> pagesHackernews = new LinkedList<>();

    private List<ContentProvider> pages = Arrays.asList(
            new Nanowerk(),
            new HackerNews(),
            new ConstructionEnquirer()
    );

    @Scheduled(initialDelay = 0, fixedDelay = 1000 * 60 * 10)
    public void getPage() {
        try {
            pagesNanowerk = NANOWERK.extract(5)
                    .parallelStream()
                    .filter(p -> !p.hasSameOriginPolicy())
                    .collect(Collectors.toList());

            pagesHackernews = HACKER_NEWS.extract(5)
                    .parallelStream()
                    .filter(p -> !p.hasSameOriginPolicy())
                    .collect(Collectors.toList());


        } catch (IOException ignored) {

        }
    }

    public String getContent(String topic, int nPages) {
        switch (topic) {
            case "tech":
                if (pagesHackernews.isEmpty()) return "Loading content. Please refresh in ~1 minute.";
                return getContent(pagesHackernews, nPages);
            case "nanotech":
                if (pagesNanowerk.isEmpty()) return "Loading content. Please refresh in ~1 minute.";
                return getContent(pagesNanowerk, nPages);
            default:
                return "No such topic";
        }
    }

    private String getContent(List<WebPage> pages, int nPages) {
        String pageString = pages.stream()
                .limit(nPages)
                .map(WebPage::putInFrame)
                .collect(Collectors.joining());
        return new ResourceLoader().getTemplate().replace("REPLACE_ME", pageString);
    }


}

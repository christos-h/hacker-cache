package com.buffer.crawler;

import com.buffer.crawler.providers.*;
import com.buffer.crawler.util.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class BufferService {

    private List<ContentProvider> providers = Arrays.asList(
            new Nanowerk(),
            new HackerNews(),
            new ConstructionEnquirer()
    );

    private Map<Topic, List<WebPage>> topicMap = new ConcurrentHashMap<>();

    @Scheduled(initialDelay = 0, fixedDelay = 1000 * 60 * 10)
    public synchronized void getPage() {
        try {
            for (ContentProvider provider : providers) {
                List<WebPage> pages = provider.extract(5)
                        .parallelStream()
                        .filter(p -> !p.hasSameOriginPolicy())
                        .collect(Collectors.toList());

                topicMap.put(provider.topic(), pages);
            }


        } catch (IOException ignored) {

        }
    }

    public String getContent(String topic, int nPages) {
        try {
            List<WebPage> pages = topicMap.get(Topic.valueOf(topic.toUpperCase()));
            if (pages == null || pages.isEmpty()) return "Loading... Please try again in ~1 minute";
            return getContent(pages, nPages);
        } catch (IllegalArgumentException iae) {
            return "No such topic...";
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

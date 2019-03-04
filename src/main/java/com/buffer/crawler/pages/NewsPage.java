package com.buffer.crawler.pages;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class NewsPage {
    private final String baseUrl;
    private boolean isPaginated;
    private final String nextPage;

    NewsPage(String baseUrl, String nextPage, boolean isPaginated){
        this.baseUrl = baseUrl;
        this.nextPage = nextPage;
        this.isPaginated = isPaginated;
    }

    public List<WebPage> extract(int nPages) throws IOException {
        List<WebPage> pages = new LinkedList<>();
        for (int i = 1; i <= nPages; i++) {
            pages.addAll(extract(this.page(i)));

        }
        return pages.stream()
                .filter(p -> !p.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    private String page(int n){
        if(!isPaginated()) throw new IllegalStateException("Cannot get page on non-paginated ExtractableURL");
        return baseUrl + nextPage + n;
    }

    private boolean isPaginated() {
        return isPaginated;
    }

    abstract List<WebPage> extract(String baseUrl) throws IOException;

    abstract Topic topic();
}
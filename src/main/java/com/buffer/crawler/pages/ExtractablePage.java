package com.buffer.crawler.pages;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class ExtractablePage {
    private String baseUrl;
    private boolean isPaginated;
    private String nextPage;

    public ExtractablePage(String baseUrl, String nextPage, boolean isPaginated){
        this.baseUrl = baseUrl;
        this.nextPage = nextPage;
        this.isPaginated = isPaginated;
    }

    boolean isPaginated() {
        return isPaginated;
    }

    String page(int n){
        if(!isPaginated()) throw new IllegalStateException("Cannot get page on non-paginated ExtractableURL");
        return baseUrl + nextPage + n;
    }

    public List<WebPage> extract(int nPages) throws IOException {
        List<WebPage> pages = new LinkedList<>();
        for (int i = 1; i <= nPages; i++) {
            pages.addAll(extract(this.page(i)));

        }
        return pages;
    }

    abstract List<WebPage> extract(String baseUrl) throws IOException;
}
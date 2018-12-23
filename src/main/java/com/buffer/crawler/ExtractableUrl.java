package com.buffer.crawler;

class ExtractableUrl {
    private String baseUrl;
    private String nextPage;

    ExtractableUrl(String baseUrl, String nextPage){
        this.baseUrl = baseUrl;
        this.nextPage = nextPage;
    }

    String page(int n){
        return baseUrl + nextPage + n;
    }


}
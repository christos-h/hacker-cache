package com.buffer.crawler;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

class WebPage {
    private static final String MOZILLA_USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:63.0) Gecko/20100101 Firefox/63.0";
    private String name;
    private String urlString;

    WebPage(String name, String urlString) {
        this.name = name;
        this.urlString = urlString;
    }

    boolean isHackerNewsItem() {
        return urlString.contains("item?");
    }

    boolean hasSameOriginPolicy() {
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", MOZILLA_USER_AGENT);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            return connection.getHeaderFields().containsKey("X-Frame-Options");
        } catch (IOException e) {
            return false;
        }
    }

    String putInFrame() {
        return "<button class=\"collapsible\">" + name + "</button>\n" +
                "<div class=\"content\">" +
                "<iframe src=\"" + urlString + "\" width=\"100%\" height=1000 sandbox=\"allow-forms allow-scripts\">\n" +
                "  <p>Your browser does not support iframes.</p>\n" +
                "</iframe>" +
                "</div>";
    }
}

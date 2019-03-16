package com.buffer.crawler.providers;

import com.buffer.crawler.util.ResourceLoader;
import org.apache.logging.log4j.util.Strings;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class WebPage {
    private static final String MOZILLA_USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:63.0) Gecko/20100101 Firefox/63.0";
    private final String name;
    private final String urlString;

    WebPage(String name, String urlString) {
        this.name = name;
        this.urlString = urlString;
    }

    boolean isEmpty() {
        return Strings.isEmpty(name) || Strings.isEmpty(urlString);
    }

    public boolean hasSameOriginPolicy() {
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

    String urlString() {
        return urlString;
    }

    public String putInFrame() {
        return new ResourceLoader().getIframe()
                .replace("NAME", name)
                .replace("LINK", urlString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebPage webPage = (WebPage) o;
        return Objects.equals(name, webPage.name) &&
                Objects.equals(urlString, webPage.urlString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, urlString);
    }
}

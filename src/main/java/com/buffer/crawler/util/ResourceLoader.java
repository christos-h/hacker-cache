package com.buffer.crawler.util;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

public class ResourceLoader {
    public String getTemplate() {
        return getTextFile("main.html");
    }

    public String getIframe() {
        return getTextFile("iframe.html");
    }

    private String getTextFile(String fileName) {
        try (BufferedReader br = getBrFromResource(fileName)) {
            String line;
            StringBuilder template = new StringBuilder();
            while ((line = br.readLine()) != null) {
                template.append(line);
            }
            return template.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private BufferedReader getBrFromResource(String fileName) throws IOException {
        return new BufferedReader(new InputStreamReader(new ClassPathResource(fileName).getInputStream()));
    }
}

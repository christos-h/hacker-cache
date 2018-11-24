package com.buffer.crawler;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

class Template {
    String getTemplate() {
        try (BufferedReader br = new BufferedReader(
                new FileReader(
                        new ClassPathResource("main.html").getFile()))) {
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
}

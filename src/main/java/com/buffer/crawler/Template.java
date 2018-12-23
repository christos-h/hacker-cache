package com.buffer.crawler;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

class Template {
    String getTemplate() {
        try (BufferedReader br = getBrFromResource("main.html")) {
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

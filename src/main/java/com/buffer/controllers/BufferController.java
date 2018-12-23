package com.buffer.controllers;

import com.buffer.crawler.BufferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BufferController {
    private final BufferService bufferService;

    @Autowired
    public BufferController(BufferService bufferService) {
        this.bufferService = bufferService;
    }

    @RequestMapping(value = "/")
    public String index(){
        return bufferService.getContent(50);
    }

    @RequestMapping(value = "/pages")
    public String pages(@RequestParam("n") int n){
        return bufferService.getContent(n);
    }

}

package com.sbzl.framework.learn.controller;

import com.sbzl.framework.learn.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DownloadController {

    @Autowired
    private DownloadService downloadService;


    @PostMapping("download")
    public void downloadDoc() throws IOException {
        downloadService.downloadDoc();
    }
}

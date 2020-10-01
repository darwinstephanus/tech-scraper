package com.darwin.techscraper.controller;

import com.darwin.techscraper.database.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class TestController {

    @Autowired
    DataLoader dataLoader;

    @GetMapping("/test")
    public String test() throws IOException, ParseException {
//        dataLoader.loadDataTechMeme();
        dataLoader.loadDataComputerWorld();
        return "ABCD";
    }



}

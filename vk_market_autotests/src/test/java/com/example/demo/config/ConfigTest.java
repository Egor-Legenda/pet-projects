package com.example.demo.config;


import com.codeborne.selenide.Configuration;

public class ConfigTest {
    static {
        Configuration.browser = "chrome";
//        Configuration.browserVersion = "6.0";
        Configuration.browserSize = "1280x800";
       // Configuration.headless = true;
        Configuration.timeout = 1000;
    }
}

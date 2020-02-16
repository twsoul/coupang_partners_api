package com.coupang_api.Web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebRestControler {
    @GetMapping("/hello")
    public String hello() {
        return "HelloWorld";
    }
}

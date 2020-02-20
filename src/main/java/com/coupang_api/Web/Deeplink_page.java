package com.coupang_api.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class Deeplink_page {
    @GetMapping("/deeplink")
    public String hello() {
        return "HelloWorld";
    }
}

package com.coupang_api.Web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class trand {

    @GetMapping("/trand")
    public String trand(Model model, HttpServletRequest request) {


        return "trand";
    }
}

package com.coupang_api.Web;

import com.coupang_api.Coupang_api.OpenApiTestApplication;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class WebController {
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("msg","test");
        return "main";
    }

    @PostMapping("/")
    public String request_deep(@RequestParam("_search")String search_Str, Model model) throws IOException, ParseException {

        if (search_Str.isEmpty()|| search_Str.equals("")){

        }else{
            String result = OpenApiTestApplication.deepLink_total(search_Str);
            model.addAttribute("_result1",result);
        }

//        model.addAttribute("_result","test");
//        model.addAttribute("_result1",result);

        return "main";
    }
}

package com.coupang_api.Web;

import com.coupang_api.Coupang_api.OpenApiTestApplication;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String request_deep(
            @RequestParam("_search")String search_Str,
            @RequestParam("_secret_key")String SECRET_KEY,
            @RequestParam("_access_key")String ACCESS_KEY
            ,Model model
            , HttpServletResponse response
            , HttpServletRequest request) throws IOException, ParseException {

        //아무 문자도 입력하지 않았을때,
        if (search_Str.isEmpty()|| search_Str.equals("")){

        }else{
            String result = OpenApiTestApplication.deepLink_total(search_Str,ACCESS_KEY,SECRET_KEY);
            model.addAttribute("_result1",result);

            Cookie cookie_ACCESS_KEY = new Cookie("ACCESS_KEY", ACCESS_KEY);
            Cookie cookie_SECRET_KEY = new Cookie("SECRET_KEY", SECRET_KEY);

            cookie_ACCESS_KEY.setMaxAge(0);
            cookie_SECRET_KEY.setMaxAge(0);

            cookie_ACCESS_KEY.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정
            cookie_SECRET_KEY.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정

            response.addCookie(cookie_ACCESS_KEY);
            response.addCookie(cookie_SECRET_KEY);

        }

        Cookie[] Cookies = request.getCookies();
        for(int i =0; i<Cookies.length;i++){
            if(Cookies[i].getName().equals("ACCESS_KEY")){
                model.addAttribute("_access_key",Cookies[i].getValue());

            }else if(Cookies[i].getName().equals("SECRET_KEY")){
                model.addAttribute("_secret_key",Cookies[i].getValue());
            }
        }


        return "main";
    }



}

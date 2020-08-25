//package com.coupang_api.Web;
package com.coupang_api.Web;

import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class WebController {


    @GetMapping("/")
    public String main(Model model, HttpServletRequest request) {
        model.addAttribute("msg","test");

        Cookie[] myCookies = request.getCookies();


//        HttpSession session = request.getSession();
//        String ACCESS_KEY = (String)session.getAttribute("_access_key");
//        String SECRET_KEY = (String)session.getAttribute("_secret_key");
//
//        model.addAttribute("_access_key",ACCESS_KEY);
//        model.addAttribute("_secret_key",SECRET_KEY);

        return "main";
    }

    @PostMapping("/")
    public String request_deep(
            @RequestParam("_search")String search_Str,
            @RequestParam("_secret_key")String SECRET_KEY,
            @RequestParam("_access_key")String ACCESS_KEY,
            @RequestParam("_sub_id")String SubID
            ,Model model
            , HttpServletResponse response) throws IOException, ParseException {

        //아무 문자도 입력하지 않았을때,
        if (search_Str.isEmpty()|| search_Str.equals("")){

        }else{
            // 파트너스 api 리턴 결과
            String result = OpenApiTestApplication.deepLink_total(search_Str,ACCESS_KEY,SECRET_KEY,SubID);
            model.addAttribute("_result1",result);

            //검색 후에도 값 남아 있을 수 있도록,
            model.addAttribute("_access_key",ACCESS_KEY);
            model.addAttribute("_secret_key",SECRET_KEY);
            model.addAttribute("_sub_id",SubID);
//request의 getSession() 메서드는 서버에 생성된 세션이 있다면 세션을 반환하고, 없다면 새 세션을 생성하여 반환

            // 쿠키와 입력값 비교, 다르면 쿠키 덮어쓰기

            //쿠키 생성
            Cookie _access_key = new Cookie("access_key", ACCESS_KEY);
            _access_key.setMaxAge(8000);
            _access_key.setPath("/");

            Cookie _secret_key = new Cookie("secret_key", SECRET_KEY);
            _secret_key.setMaxAge(8000);
            _secret_key.setPath("/");

        }


//쿠키


       return "main";
    }

    @GetMapping("/manual")
    public String manual_page(Model model, HttpServletRequest request) {

        return "manual";
    }


}

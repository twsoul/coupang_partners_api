//package com.coupang_api.Web;
package com.coupang_api.Web;

import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        // 쿠키 읽어 오기.

        // 쿠키와 입력값 비교, 다르면 쿠키 덮어쓰기
        Cookie[] Cookies = request.getCookies();

        if (Cookies != null) {
            for (Cookie cookie : Cookies) {
                if (cookie.getName().equals("access_key")) {
                    model.addAttribute("_access_key",cookie.getValue());
                }
                if (cookie.getName().equals("secret_key")) {
                    model.addAttribute("_secret_key",cookie.getValue());
                }
            }
        }

//        HttpSession session = request.getSession();
//        String ACCESS_KEY = (String)session.getAttribute("_access_key");
//        String SECRET_KEY = (String)session.getAttribute("_secret_key");
//
//        model.addAttribute("_access_key",ACCESS_KEY);
//        model.addAttribute("_secret_key",SECRET_KEY);

        return "main";
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/")
    public String request_deep(
            @RequestParam("_search")String search_Str,
            @RequestParam("_secret_key")String SECRET_KEY,
            @RequestParam("_access_key")String ACCESS_KEY,
            @RequestParam("_sub_id")String SubID
            ,Model model
            , HttpServletResponse response
            ,HttpServletRequest request) throws IOException, ParseException {

        //사람들 키워드 데이터 저장
        jdbcTemplate.execute("INSERT INTO str_log VALUES ('"+search_Str+"')");

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
            Cookie[] Cookies = request.getCookies();

            if (Cookies != null) {
                for (Cookie cookie : Cookies) {
                    if (cookie.getName().equals("access_key")&&!cookie.getValue().equals(ACCESS_KEY)) {
                        Cookie _access_key_coo = new Cookie("access_key", ACCESS_KEY);
                        _access_key_coo.setMaxAge(3600*24*30);
                        response.addCookie(_access_key_coo);

                    }
                    if (cookie.getName().equals("secret_key")&&!cookie.getValue().equals(SECRET_KEY)) {
                        Cookie _secret_key_coo = new Cookie("secret_key", SECRET_KEY);
                        _secret_key_coo.setMaxAge(3600*24*30);
                        response.addCookie(_secret_key_coo);
                    }

                }
            }else{ //쿠키 존재하지 않을때,
                Cookie _access_key_coo = new Cookie("access_key", ACCESS_KEY);
                _access_key_coo.setMaxAge(3600*24*30);
                _access_key_coo.setPath("/");
                response.addCookie(_access_key_coo);
                Cookie _secret_key_coo = new Cookie("secret_key", SECRET_KEY);
                _secret_key_coo.setMaxAge(3600*24*30);
                _secret_key_coo.setPath("/");
                response.addCookie(_secret_key_coo);
            }
        }




       return "main";
    }

    @GetMapping("/manual")
    public String manual_page(Model model, HttpServletRequest request) {

        return "manual";
    }
    @GetMapping("/header_menu")
    public String header_menu(Model model, HttpServletRequest request) {

        return "header_menu";
    }
    @GetMapping("/footer")
    public String footer(Model model, HttpServletRequest request) {

        return "footer";
    }
    @GetMapping("/dev_ing")
    public String dev_ing(Model model, HttpServletRequest request) {


        return "dev_ing";
    }



}

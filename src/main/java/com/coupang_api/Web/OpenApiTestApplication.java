package com.coupang_api.Web;

import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static com.coupang_api.Coupang_api.DeepLink.deepLink;
import static com.coupang_api.Coupang_api.DeepLink.make_request_json_20;
import static com.coupang_api.String_store.High_end_cpu;

@SpringBootApplication
public class OpenApiTestApplication {
    public static String total_str;


    public static void main(String[] args) throws IOException, ParseException {
        SpringApplication.run(OpenApiTestApplication.class, args);

    }

    public static String deepLink_total(
            String search_str
            ,String ACCESS_KEY
            ,String SECRET_KEY
    ) throws IOException, ParseException {
        total_str ="";

        // 특수문자 제거.
        search_str = StringReplace(search_str);
        // enter 단위로 항목 나누기.
        String[] split_str = search_str.split("\\r\\n");

        // note  큐에 넣어서  20개씩 나눠서 요청하기.
        // note 1. 큐 삽입
        Queue<String> que = new LinkedList<String>();

        for(int i=0; i<split_str.length;i++){
            //split_str[i]= split_str[i].replace(" ","+");
            que.offer(split_str[i]);
        }
        // note 2. 큐 20개씩 나눠서 deeplink 요청
        String[] temp_str;

        while(!que.isEmpty()) {
            if(que.size()>=20){
                temp_str= new String[20];
            }else {
                temp_str= new String[que.size()];
            }

            //System.out.println(que.size());

            for(int i =0; i<temp_str.length;i++){
                temp_str[i] = que.poll();
//                System.out.println(temp_str[i]);
            }
//            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
            deepLink(make_request_json_20(temp_str),ACCESS_KEY,SECRET_KEY);
        }

        return total_str;
    }


    public static String StringReplace(String str){
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        str =str.replaceAll(match, "");
        return str;
    }


}
package com.coupang_api.Coupang_api;

import com.coupang_api.CoupangApiWebApplication;
import com.coupang_api.Secret_Key_cls;
import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.coupang_api.Coupang_api.DeepLink.deepLink;
import static com.coupang_api.Coupang_api.DeepLink.make_request_json_20;
import static com.coupang_api.Secret_Key_cls.ACCESS_KEY;
import static com.coupang_api.Secret_Key_cls.SECRET_KEY;
import static com.coupang_api.String_store.High_end_cpu;

public class OpenApiTestApplication {
    public static String total_str;


    public static void main(String[] args) throws IOException, ParseException {
        SpringApplication.run(OpenApiTestApplication.class, args);

    }

    private void Convert_myLink()throws IOException, ParseException{
        total_str ="";


        String[] split_str = High_end_cpu.split("\\n");

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
            deepLink(make_request_json_20(temp_str),"","");
        }

        System.out.println(total_str);

    }

    public static String deepLink_total(String search_str, String ACCESS_KEY, String SECRET_KEY) throws IOException, ParseException {
        total_str ="";


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


//    public static String coupang_api_answer(String request_method, String url_tpye, String access_key, String secret_key, String request_json, String subId) throws IOException {
//        String BaseUrl = "https://api-gateway.coupang.com";
//        // Generate HMAC string
//        String authorization = HmacGenerator.generate(request_method, url_tpye, SECRET_KEY, ACCESS_KEY);
//
//        List<NameValuePair> list = new ArrayList<NameValuePair>();
////        list.add(new BasicNameValuePair("keyword", "자전거"));
//        list.add(new BasicNameValuePair("limit", "5"));
//        list.add(new BasicNameValuePair("subId", subId));
//
//        NameValuePair[] params = new NameValuePair[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            params[i] = list.get(i);
//        }
//
//
//        // Send request
//        StringEntity entity = new StringEntity(request_json, "UTF-8");
//        entity.setContentEncoding("UTF-8");
//        entity.setContentType("application/json");
//
//        org.apache.http.HttpHost host = org.apache.http.HttpHost.create(BaseUrl);
//        org.apache.http.HttpRequest request = org.apache.http.client.methods.RequestBuilder
//                .get(url_tpye)
//                .addParameters(params)
////                .setEntity(entity)
//                .addHeader("Authorization", authorization)
////                .addHeader("Content-type", "application/json")
//                .build();
//
//        org.apache.http.HttpResponse httpResponse = org.apache.http.impl.client.HttpClientBuilder.create().build().execute(host, request);
//
//        return EntityUtils.toString(httpResponse.getEntity());
//    }



}
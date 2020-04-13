package com.coupang_api.Coupang_api;

import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Random;

import static com.coupang_api.Coupang_api.OpenApiTestApplication.total_str;
import static com.coupang_api.Secret_Key_cls.ACCESS_KEY;
import static com.coupang_api.Secret_Key_cls.SECRET_KEY;
import static com.coupang_api.String_store.High_end_cpu;

public class    DeepLink {

    private final static String REQUEST_METHOD = "POST";
    private final static String DOMAIN = "https://api-gateway.coupang.com";
    private final static String deepLink_URL = "/v2/providers/affiliate_open_api/apis/openapi/deeplink";
    private final static String URL = "/v2/providers/affiliate_open_api/apis/openapi/deeplink";
    // Replace with your own ACCESS_KEY and SECRET_KEY

//    private final static String REQUEST_JSON = "{\"coupangUrls\": [\"https://www.coupang.com/np/search?component=&q=good&channel=user\",\"https://www.coupang.com/np/coupangglobal\"]}";
    private final static String REQUEST_JSON = High_end_cpu;


    // TODO deepLink url을  내 id url로 변경
    public static String deepLink(String reauest_json, String access_key,String screte_key) throws IOException, ParseException {
        String authorization = null;
        // 수수료 10% 내 링크 로직
        Random random = new Random();
        int mylink = random.nextInt(100);
        if (mylink<10){
            authorization = HmacGenerator.generate(REQUEST_METHOD, deepLink_URL, screte_key, access_key);
        }else{
            authorization = HmacGenerator.generate(REQUEST_METHOD, deepLink_URL, SECRET_KEY, ACCESS_KEY);
        }




        // Send request
        StringEntity entity = new StringEntity(reauest_json, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");

        org.apache.http.HttpHost host = org.apache.http.HttpHost.create(DOMAIN);
        org.apache.http.HttpRequest request = org.apache.http.client.methods.RequestBuilder
                .post(deepLink_URL).setEntity(entity)
                .addHeader("Authorization", authorization)
                .build();

        org.apache.http.HttpResponse httpResponse = org.apache.http.impl.client.HttpClientBuilder.create().build().execute(host, request);

        String result_json = EntityUtils.toString(httpResponse.getEntity());
//         verify
        System.out.println(result_json);
        // Note JSON 결과 값
//        {
//          "rCode":"0",
//          "rMessage":"",
//          "data":[
//          {
//          "originalUrl":"https://www.coupang.com/np/search?component=&q=AMD Ryzen Threadripper 3970X",
//          "shortenUrl":"https://coupa.ng/bpa2vh",
//          "landingUrl":"https://link.coupang.com/re/AFFSRP?lptag=AF4923099&pageKey=AMD+Ryzen+Threadripper+3970X&traceid=V0-183-e10a6a9438dda4f9"},
//          {
//          "originalUrl":"https://www.coupang.com/np/search?component=&q=AMD EPYC 7742",
//          "shortenUrl":"https://coupa.ng/bpa2vi",
//          "landingUrl":"https://link.coupang.com/re/AFFSRP?lptag=AF4923099&pageKey=AMD+EPYC+7742&traceid=V0-183-be730bbe020f38d2"}]}


        // Note 결과 파싱
        String jsonData =result_json;
        JSONParser jsonParse = new JSONParser();

        JSONObject jsonObj = (JSONObject) jsonParse.parse(jsonData);

        JSONArray personArray = (JSONArray) jsonObj.get("data");

        try {
            for(int i=0; i < personArray.size(); i++) {
                JSONObject personObject = (JSONObject) personArray.get(i);
                total_str = total_str+personObject.get("shortenUrl")+"\n";
//            System.out.println(personObject.get("shortenUrl"));
            }
        }catch (NullPointerException e){
            return String.valueOf(e);
        }




        return "good";
    }

    public static String make_request_json_20(String[] str_arr){
        String str_result ="";
        System.out.println(str_arr.length);
        for(int i =0; i<str_arr.length;i++){
            str_result = str_result + "\"https://www.coupang.com/np/search?component=&q="+str_arr[i]+"\",";
        }

        // 마지막 콤마 지워주기.
        str_result= str_result.substring(0, str_result.length()-1);

        return "{\"coupangUrls\":[" +str_result+ "]}";
    }
}

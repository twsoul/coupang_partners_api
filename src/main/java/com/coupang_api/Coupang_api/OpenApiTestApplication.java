package com.coupang_api.Coupang_api;

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


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.coupang_api.Secret_Key_cls.ACCESS_KEY;
import static com.coupang_api.Secret_Key_cls.SECRET_KEY;
import static com.coupang_api.String_store.High_end_cpu;

public class OpenApiTestApplication {
    private final static String REQUEST_METHOD = "POST";
    private final static String DOMAIN = "https://api-gateway.coupang.com";
    private final static String deepLink_URL = "/v2/providers/affiliate_open_api/apis/openapi/deeplink";
    private final static String URL = "/v2/providers/affiliate_open_api/apis/openapi/deeplink";
    // Replace with your own ACCESS_KEY and SECRET_KEY

    private final static String REQUEST_JSON = High_end_cpu;

    public static void main(String[] args) throws IOException, ParseException {
        deepLink();
    }


    public static String coupang_api_answer(String request_method, String url_tpye, String access_key, String secret_key, String request_json, String subId) throws IOException {
        String BaseUrl = "https://api-gateway.coupang.com";
        // Generate HMAC string
        String authorization = HmacGenerator.generate(request_method, url_tpye, SECRET_KEY, ACCESS_KEY);

        List<NameValuePair> list = new ArrayList<NameValuePair>();
//        list.add(new BasicNameValuePair("keyword", "자전거"));
        list.add(new BasicNameValuePair("limit", "5"));
        list.add(new BasicNameValuePair("subId", subId));

        NameValuePair[] params = new NameValuePair[list.size()];
        for (int i = 0; i < list.size(); i++) {
            params[i] = list.get(i);
        }


        // Send request
        StringEntity entity = new StringEntity(request_json, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");

        org.apache.http.HttpHost host = org.apache.http.HttpHost.create(BaseUrl);
        org.apache.http.HttpRequest request = org.apache.http.client.methods.RequestBuilder
                .get(url_tpye)
                .addParameters(params)
//                .setEntity(entity)
                .addHeader("Authorization", authorization)
//                .addHeader("Content-type", "application/json")
                .build();

        org.apache.http.HttpResponse httpResponse = org.apache.http.impl.client.HttpClientBuilder.create().build().execute(host, request);

        return EntityUtils.toString(httpResponse.getEntity());
    }
// TODO deepLink url을  내 id url로 변경
    public static String deepLink() throws IOException, ParseException {
        String authorization = HmacGenerator.generate(REQUEST_METHOD, deepLink_URL, SECRET_KEY, ACCESS_KEY);

        // Send request
        StringEntity entity = new StringEntity(REQUEST_JSON, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");

        org.apache.http.HttpHost host = org.apache.http.HttpHost.create(DOMAIN);
        org.apache.http.HttpRequest request = org.apache.http.client.methods.RequestBuilder
                .post(deepLink_URL).setEntity(entity)
                .addHeader("Authorization", authorization)
                .build();

        org.apache.http.HttpResponse httpResponse = org.apache.http.impl.client.HttpClientBuilder.create().build().execute(host, request);

        String result_json =EntityUtils.toString(httpResponse.getEntity());
//         verify
        System.out.println(result_json);
        // 파싱
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

        String jsonData =result_json;
        JSONParser jsonParse = new JSONParser();

        JSONObject jsonObj = (JSONObject) jsonParse.parse(jsonData);

        JSONArray personArray = (JSONArray) jsonObj.get("data");

        for(int i=0; i < personArray.size(); i++) {
            JSONObject personObject = (JSONObject) personArray.get(i);
            System.out.println(personObject.get("shortenUrl"));
        }


        return "good";
    }
}
package com.coupang_api.Coupang_api;

import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenApiTestApplication {
    private final static String REQUEST_METHOD = "POST";
    private final static String DOMAIN = "https://api-gateway.coupang.com";
    private final static String deepLink_URL = "/v2/providers/affiliate_open_api/apis/openapi/deeplink";
    // Replace with your own ACCESS_KEY and SECRET_KEY
    private final static String ACCESS_KEY = "6c0c710e-86fe-41e9-9eb5-bbab14fd8753";
    private final static String SECRET_KEY = "a657346175df7ab6aa25950c372e7a7cb8298ffb";

    private final static String REQUEST_JSON
            = "{\"coupangUrls\": [\"https://www.coupang.com/np/search?component=&q=good&channel=user\",\"https://www.coupang.com/np/coupangglobal\"]}";

    public static void main(String[] args) throws IOException {
        String request_url = "{\"keyword\": [\"자전거\",\"백팩\"]}";

    String answer_json = coupang_api_answer(
            "GET",
            "/v2/providers/affiliate_open_api/apis/openapi/products/coupangPL",
            "b",
            "a",
            request_url,
            "autoPartners");

        System.out.println(answer_json);
//        String deepLink = deepLink();
//        System.out.println(deepLink);

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

    public static String deepLink() throws IOException {
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

//         verify
        return EntityUtils.toString(httpResponse.getEntity());
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Administrator
 */
public class CoinCheck {
    
     private final String BASE_API = "https://coincheck.jp/";

    private String internalEncoding = "UTF-8";

    private HttpClient client;

    private String accessKey;

    private String secretKey;

    public static void main(String[] args) throws Exception {
        CoinCheck http = new CoinCheck("oyme8ECw_YZxEb2M", "WDCxxHJ4EHZZg8zLFvlwYsEj6p6hSANT");
        Map<String, String> params = new HashMap<>();
        http.sendGet("api/accounts/balance", params);
        http.sendPost();
    }
    
    public CoinCheck(String accessKey, String secretKey) throws Exception {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        HttpClient client = new DefaultHttpClient();
        this.client = client;
    }

    // HTTP GET request
    public String sendGet(String path, Map<String, String> params) throws Exception {
        String url = BASE_API + path;
        HttpGet request = new HttpGet(url);
        long nonce = System.currentTimeMillis();
        String message = nonce + url + httpBuildQuery(params);
        String signature = HmacSha256.createHmacSha256(message, this.secretKey);
        // add request header
        request.addHeader("Content-Type", "application/json");
        request.addHeader("ACCESS-KEY", this.accessKey);
        request.addHeader("ACCESS-NONCE", String.valueOf(nonce));
        request.addHeader("ACCESS-SIGNATURE", signature);
        HttpResponse response = this.client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        
        return result.toString();
    }

    private String httpBuildQuery(Map<String, String> params) throws UnsupportedEncodingException {
        String result = "";
        for (Map.Entry<String, String> e : params.entrySet()) {
            if (e.getKey().isEmpty()) {
                continue;
            }
            if (!result.isEmpty()) {
                result += "&";
            }
            result += URLEncoder.encode(e.getKey(), internalEncoding) + "=" + URLEncoder.encode(e.getValue(), internalEncoding);
        }

        return result;
    }
    
    // HTTP POST request
    public void sendPost() throws Exception {
        String url = "https://selfsolve.apple.com/wcResults.do";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        // add header
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
        urlParameters.add(new BasicNameValuePair("cn", ""));
        urlParameters.add(new BasicNameValuePair("locale", ""));
        urlParameters.add(new BasicNameValuePair("caller", ""));
        urlParameters.add(new BasicNameValuePair("num", "12345"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
    }
}

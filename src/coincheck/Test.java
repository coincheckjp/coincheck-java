/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class Test {

    public static void main(String[] args) throws Exception {
//        CoinCheck client = new CoinCheck("jINurs_XfStHU2tI", "6Y8R5jdGMzZ6VuL86MOIISiV9titktbK");
//        Map<String, String> params = new HashMap<>();
//        //JSONObject response = client.account().info(params);
//        List<NameValuePair> bankAccount = new ArrayList<>();
//        bankAccount.add(new BasicNameValuePair("bank_name", "熊本"));
//        bankAccount.add(new BasicNameValuePair("branch_name", "田中"));
//        bankAccount.add(new BasicNameValuePair("bank_account_type", "futsu"));
//        bankAccount.add(new BasicNameValuePair("number", "0123456"));
//        bankAccount.add(new BasicNameValuePair("name", "カタカナ")); 
//        
//        client.bankAccount().create(bankAccount);
        //client.bankAccount().all(params);
//
//        Map<String, String> param = new HashMap<>();
        OkHttpClient client = new OkHttpClient();
        long nonce = System.currentTimeMillis();
        String url = "https://coincheck.jp/api/bank_accounts";
        Test test = new Test();
        String message = nonce + url;
        String signature = HmacSha256.createHmacSha256(message, "6Y8R5jdGMzZ6VuL86MOIISiV9titktbK");
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, "{\"bank_name\":\"熊本\",\"branch_name\":\"田中\", \"bank_account_type\":\"futsu\", \"number\":\"0123456\", \"name\":\"hoge\"}");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json; charset=utf-8")
                .addHeader("access-key", "jINurs_XfStHU2tI")
                .addHeader("access-nonce", String.valueOf(nonce))
                .addHeader("access-signature", signature)
                .addHeader("cache-control", "no-cache")
                //.addHeader("postman-token", "18e0368f-e0c5-6dfa-c04b-1edab911c166")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.toString());
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
            String internalEncoding = "UTF-8";
            result += URLEncoder.encode(e.getKey(), internalEncoding) + "=" + URLEncoder.encode(e.getValue(), internalEncoding);
        }

        return result;
    }
}

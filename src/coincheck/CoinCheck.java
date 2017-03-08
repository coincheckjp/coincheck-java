/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson.JacksonFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Administrator
 */
public class CoinCheck {

    private final String BASE_API = "https://coincheck.jp/";

    private String internalEncoding = "UTF-8";
    
    private final String USER_AGENT = "Mozilla/5.0";

    private HttpClient client;

    private String accessKey;

    private String secretKey;
    
    private Account account;

    private BankAccount bankAccount;

    private Borrow borrow;

    private Deposit deposit;

    private Leverage leverage;

    private Order order;

    private OrderBook orderBook;

    private Send send;

    private Ticker ticker;

    private Trade trade;

    private Transfer transfer;

    private Withdraw withdraw;

    public CoinCheck(String accessKey, String secretKey) throws Exception {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.client = new DefaultHttpClient();
        this.account = new Account(this);
        this.bankAccount = new BankAccount(this);
        this.borrow = new Borrow(this);
        this.deposit = new Deposit(this);
        this.leverage = new Leverage(this);
        this.order = new Order(this);
        this.orderBook = new OrderBook(this);
        this.send = new Send(this);
        this.ticker = new Ticker(this);
        this.trade = new Trade(this);
        this.transfer = new Transfer(this);
        this.withdraw = new Withdraw(this);
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
    public String sendPost(String path, List<NameValuePair> params) throws Exception {
        String url = BASE_API + path;
//        HttpPost post = new HttpPost(url);
//        Map<String, String> param = new HashMap<>();
//        long nonce = System.currentTimeMillis();
//        String message = nonce + url + httpBuildQuery(param);
//        String signature = HmacSha256.createHmacSha256(message, this.secretKey);
//        
//        String json = "{'bank_name':'aaabbb','branch_name':'aaabbb', 'bank_account_type':'futsu', 'number':'1234567', 'name':'カタカナ'}";
//        StringEntity entity = new StringEntity(json);
//        post.setEntity(entity);
//        // add request header
//        post.setHeader("Accept", "application/json");
//        post.addHeader("Content-Type", "application/json");
//        post.addHeader("ACCESS-KEY", this.accessKey);
//        post.addHeader("ACCESS-NONCE", String.valueOf(nonce));
//        post.addHeader("ACCESS-SIGNATURE", signature);
//       
//        //post.setEntity(new UrlEncodedFormEntity(params));
//        HttpResponse response = client.execute(post);
//        System.out.println("\nSending 'POST' request to URL : " + url);
//        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
//        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//        StringBuffer result = new StringBuffer();
//        String line = "";
//        while ((line = rd.readLine()) != null) {
//            result.append(line);
//        }
//        System.out.println(result.toString());
//
//        return result.toString();
          ApacheHttpTransport transport = new ApacheHttpTransport();
        HttpRequestFactory factory = transport.createRequestFactory();
        String jsonString;
        try {
            //String json = "{'bank_name':'aaabbb','branch_name':'aaabbb', 'bank_account_type':'1', 'number':'1234567', 'name':'カタカナ'}";
            Map<String, String> json11 = new HashMap();
            json11.put("bank_name", "aaabbb");
            json11.put("branch_name", "aaabbb");
            json11.put("bank_account_type", "futsu");
            json11.put("number", "1234567");
            json11.put("name", "カタカナ");
            final HttpContent content = new JsonHttpContent(new JacksonFactory(), json11);
            
            //String requestBody = "{'name': 'newIndia','columns': [{'name': 'Species','type': 'STRING'}],'description': 'Insect Tracking Information.','isExportable': true}";
            HttpRequest request = factory.buildPostRequest(new GenericUrl(url), content);
            request.getHeaders().setContentType("application/json");
            long nonce = System.currentTimeMillis();
            String message = nonce + url;
            String signature = HmacSha256.createHmacSha256(message, this.secretKey);

            String json = "{'bank_name':'aaabbb','branch_name':'aaabbb', 'bank_account_type':'futsu', 'number':'1234567', 'name':'カタカナ'}";
            StringEntity entity = new StringEntity(json);
            request.setConnectTimeout(0);
            request.setReadTimeout(0);
            request.setParser(new JacksonFactory().createJsonObjectParser());
            final HttpHeaders httpHeaders = new HttpHeaders();
            //String nonce = createNonce();
            httpHeaders.set("ACCESS-KEY", this.accessKey);
            httpHeaders.set("ACCESS-NONCE", nonce);
            httpHeaders.set("ACCESS-SIGNATURE", signature);
            request.setHeaders(httpHeaders);
            request.getHeaders().setContentType("application/json");
            //request.getHeaders().setAccept("application/json");
            
            com.google.api.client.http.HttpResponse response = request.execute();
            jsonString = response.parseAsString();
        } catch (IOException e) {
            e.printStackTrace();
            jsonString = null;
        }
        return jsonString;
    }

    public Account account() {
        return account;
    }

    public BankAccount bankAccount() {
        return bankAccount;
    }

    public Borrow borrow() {
        return borrow;
    }

    public Leverage leverage() {
        return leverage;
    }

    public Order order() {
        return order;
    }

    public OrderBook orderBook() {
        return orderBook;
    }

    public Send send() {
        return send;
    }

    public Ticker ticker() {
        return ticker;
    }

    public Trade trade() {
        return trade;
    }

    public Transfer transfer() {
        return transfer;
    }

    public Withdraw withdraw() {
        return withdraw;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Administrator
 */
public class CoinCheck {

    private final String BASE_API = "https://coincheck.jp/";

    private String internalEncoding = "UTF-8";

    private HttpsURLConnection con;

    private String accessKey;

    private String secretKey;
    
    private final String USER_AGENT = "Mozilla/5.0";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Map<String, String> paramData = new HashMap<String, String>();
        paramData.put("name", "Order #1995");
        paramData.put("email", "lammn90@gmail.com");
        paramData.put("currency", "JPY");
        paramData.put("amount", "1");
        //paramData.put("success_url", "");
        paramData.put("max_times", "1");

        CoinCheck coinCheck = new CoinCheck("345nRE4Z8ShzPPCN", "PmFURn7vBaWQ4pteEKw8xYZqZ7LYCW7p");
        coinCheck.request("GET", "api/accounts/balance", paramData);

    }

    public CoinCheck(String accessKey, String secretKey) throws Exception {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        Map<String, String> paramData = new HashMap<String, String>();
        URL obj = new URL(BASE_API + "ec/buttons");
        this.con = (HttpsURLConnection) obj.openConnection();
        //this.con.setRequestMethod("GET");
        this.con.setRequestProperty("Content-Type", "application/json");
        this.con.setRequestProperty("ACCESS-KEY", this.accessKey);
        //add request header
        this.con.setRequestProperty("User-Agent", USER_AGENT);
//        setSignature(BASE_API, paramData);
//        // Send post request
//        int responseCode = con.getResponseCode();
//        System.out.println("Response Code : " + responseCode);
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        //print result
//        System.out.println(response.toString());
    }

    public void setSignature(String path, Map<String, String> params) throws Exception {
        long nonce = System.currentTimeMillis();
        String url = BASE_API + path;
        String message = nonce + url + httpBuildQuery(params);
        String signature = HmacSha256.createHmacSha256(message, this.secretKey);
        this.con.setRequestProperty("ACCESS-NONCE", String.valueOf(nonce));
        this.con.setRequestProperty("ACCESS-SIGNATURE", signature);
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
    public String request(String method, String path, Map<String, String> paramData) throws Exception {
        //add request header
        con.setRequestMethod(method);
        setSignature(path, paramData);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(httpBuildQuery(paramData));
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print result
        System.out.println(response.toString());

        return response.toString();
    }

}

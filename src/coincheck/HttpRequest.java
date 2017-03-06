package coincheck;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

public class HttpRequest {

    private final String BASE_API = "https://coincheck.jp/";
    
    private String internalEncoding = "UTF-8";
    
    private HttpsURLConnection con;
    
    private String accessKey;
    
    private String secretKey;

    public HttpRequest(String accessKey, String secretKey) throws Exception {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        URL obj = new URL(BASE_API);
        this.con = (HttpsURLConnection) obj.openConnection();
        this.con.addRequestProperty("Content-Type", "application/json");
        this.con.addRequestProperty("ACCESS-KEY", this.accessKey);
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
    public void request(String method, String path, Object paramData) throws Exception {
        //add request header
        con.setRequestMethod(method);
        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("Post parameters : " + urlParameters);
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
    }
}

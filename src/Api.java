import com.google.api.client.http.*;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Api {
    private String apiKey;
    private String apiSecret;

    public static void main(String[] args) {
        String key = "oyme8ECw_YZxEb2M";
        String secret = "WDCxxHJ4EHZZg8zLFvlwYsEj6p6hSANT";
        Api api = new Api(key, secret);
        //System.out.println(api.getTicker());
        System.out.println(api.getBalance());
    }

    public Api(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public String getTicker() {
        String url = "https://coincheck.jp/api/accounts/ticker";
        String jsonString = requestByUrlWithHeader(url, createHeader(url));
        return jsonString;
    }

    public String getBalance() {
        String url = "https://coincheck.jp/api/bank_accounts";
        String jsonString = requestByUrlWithHeader(url, createHeader(url));
        return jsonString;
    }

    private Map createHeader(String url) {
        Map map = new HashMap();
        String nonce = createNonce();
        map.put("ACCESS-KEY", apiKey);
        map.put("ACCESS-NONCE", nonce);
        map.put("ACCESS-SIGNATURE", createSignature(apiSecret, url, nonce));
        return map;
    }

    private String createSignature(String apiSecret, String url, String nonce) {
        String message = nonce + url;
        return HMAC_SHA256Encode(apiSecret, message);
    }

    private String createNonce() {
        long currentUnixTime = System.currentTimeMillis();
        String nonce = String.valueOf(currentUnixTime);
        return nonce;
    }

    private String requestByUrlWithHeader(String url, Map headers){
        ApacheHttpTransport transport = new ApacheHttpTransport();
        HttpRequestFactory factory = transport.createRequestFactory((final HttpRequest request) -> {
            //request.setConnectTimeout(0);
            //request.setReadTimeout(0);
            //request.setParser(new JacksonFactory().createJsonObjectParser());
            final HttpHeaders httpHeaders = new HttpHeaders();
            Iterator it = headers.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                //System.out.println(pair.getKey() + " = " + pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
                httpHeaders.set((String) pair.getKey(), pair.getValue());
            }
            request.setHeaders(httpHeaders);
        });
        String jsonString;
        try {
            //JacksonFactory jsonFactory = new JacksonFactory();
            Map<String, String> json = new HashMap<>();
            json.put("bank_name", "aaaaaaa");
            json.put("branch_name", "ccccddd");
            json.put("bank_account_type", "futsu");
            json.put("number", "0123456");
            json.put("name", "カタカナ");
            String requestBody = "{\"bank_name\":\"gggggg\",\"branch_name\":\"vvvvvvv\", \"bank_account_type\":\"futsu\", \"number\":\"1234567\", \"name\":\"カタカナ\"}";
            final HttpContent content = new JsonHttpContent(new JacksonFactory() , json);
            //HttpRequest request = factory.buildGetRequest(new GenericUrl(url));
            HttpRequest request = factory.buildPostRequest(new GenericUrl(url), content);
            request.getHeaders().setContentType("application/json; charset=utf-8");
            //System.out.println(request.getHeaders().get("ACCESS-KEY"));
            //request.getHeaders().setAccept("application/json");
            
            HttpResponse response = request.execute();
            jsonString = response.parseAsString();
        } catch (IOException e) {
            e.printStackTrace();
            jsonString = null;
        }
        return jsonString;
    }


    public static String HMAC_SHA256Encode(String secretKey, String message) {

        SecretKeySpec keySpec = new SecretKeySpec(
                secretKey.getBytes(),
                "hmacSHA256");

        Mac mac = null;
        try {
            mac = Mac.getInstance("hmacSHA256");
            mac.init(keySpec);
        } catch (NoSuchAlgorithmException e) {
            // can't recover
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            // can't recover
            throw new RuntimeException(e);
        }
        byte[] rawHmac = mac.doFinal(message.getBytes());
        return Hex.encodeHexString(rawHmac);
    }
}
 
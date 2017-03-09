import com.google.api.client.http.*;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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
        String url = "https://coincheck.com/api/accounts/ticker";
        String jsonString = requestByUrlWithHeader(url, createHeader(url));
        return jsonString;
    }

    public String getBalance() {
        String url = "https://coincheck.com/api/bank_accounts";
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

    private String requestByUrlWithHeader(String url, final Map headers){
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
            String nonce = createNonce();
            request.getHeaders().set("ACCESS-KEY", apiKey);
            request.getHeaders().set("ACCESS-NONCE", nonce);
            request.getHeaders().set("ACCESS-SIGNATURE", createSignature(apiSecret, url, nonce));
//            request.setConnectTimeout(0);
//            request.setReadTimeout(0);
            //request.setParser(new JacksonFactory().createJsonObjectParser());
//            final HttpHeaders httpHeaders = new HttpHeaders();
//            String nonce = createNonce();
//            httpHeaders.set("ACCESS-KEY", apiKey);
//            httpHeaders.set("ACCESS-NONCE", nonce);
//            httpHeaders.set("ACCESS-SIGNATURE", createSignature(apiSecret, url, nonce));
//            request.setHeaders(httpHeaders);
            //HttpRequest request = factory.buildGetRequest(new GenericUrl(url));
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

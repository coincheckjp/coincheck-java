/*
 * HmacSha256.java
 * Copyright (C) 2015 kaoru <kaoru@localhost>
 */
package coincheck;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

public class HmacSha256 {

    public HmacSha256(){}
    
    public static String createHmacSha256(String message, String secretKey) {
        String algo = "HmacSHA256";
        try {
            SecretKeySpec sk = new SecretKeySpec(secretKey.getBytes(), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(sk);

            byte[] mac_bytes = mac.doFinal(message.getBytes());

            StringBuilder sb = new StringBuilder(2 * mac_bytes.length);
            for (byte b : mac_bytes) {
                sb.append(String.format("%02x", b & 0xff));
            }
            
            return sb.toString();
            
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class Test {

    public static void main(String[] args) throws Exception {
        CoinCheck client = new CoinCheck("oyme8ECw_YZxEb2M", "WDCxxHJ4EHZZg8zLFvlwYsEj6p6hSANT");
        Map<String, String> params = new HashMap<>();
        JSONObject response = client.account().info(params);
        
    }
}

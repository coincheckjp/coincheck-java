/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;

import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class OrderBook {

   private CoinCheck client;

    public OrderBook(CoinCheck client) {
        this.client = client;
    }

    /**
     * 板情報を取得できます。
     *
     * @param params
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject positions(Map<String, String> params) throws Exception {
        String response = this.client.sendGet("api/order_books", params);
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
    
}

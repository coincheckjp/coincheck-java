/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;

import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class Deposit {

    private CoinCheck client;

    public Deposit(CoinCheck client) {
        this.client = client;
    }

    /**
     * Deposit Bitcoin Faster
     *
     * @param params
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject fast(List<NameValuePair> params) throws Exception {
        String response = this.client.sendPost("api/lending/borrows", params);
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * You Get Deposit history
     *
     * @param params
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject all(Map<String, String> params) throws Exception {
        String response = this.client.sendGet("api/deposit_money", params);
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
}

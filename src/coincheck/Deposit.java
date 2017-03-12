/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;

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
    public JSONObject fast(JSONObject params) throws Exception {
        String response = this.client.request("POST", "api/lending/borrows", params.toString());
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * You Get Deposit history
     *
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject all() throws Exception {
        String response = this.client.request("GET", "api/deposit_money", "");
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
}

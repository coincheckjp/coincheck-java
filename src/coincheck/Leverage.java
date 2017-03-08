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
public class Leverage {

    private CoinCheck client;

    public Leverage(CoinCheck client) {
        this.client = client;
    }

    /**
     * Get a leverage positions list.
     *
     * @param params
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject positions(Map<String, String> params) throws Exception {
        String response = this.client.sendGet("api/exchange/leverage/positions", params);
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

}

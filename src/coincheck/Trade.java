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
public class Trade {

    private CoinCheck client;

    public Trade(CoinCheck client) {
        this.client = client;
    }

    /**
     * 最新の取引履歴を取得できます。
     *
     * @param params
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject all(Map<String, String> params) throws Exception {
        String response = this.client.sendGet("api/trades", params);
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

}

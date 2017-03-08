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
public class Order {

    private CoinCheck client;

    public Order(CoinCheck client) {
        this.client = client;
    }

    /**
     * Create a order object with given parameters. In live mode, this issues a
     * transaction.
     *
     * @param params
     * @return jsonObj
     * @throws java.lang.Exception
     */
    public JSONObject create(List<NameValuePair> params) throws Exception {
        String response = this.client.sendPost("api/exchange/orders", params);
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * cancel a created order specified by order id. Optional argument amount is
     * to refund partially.
     *
     * @param params
     * @return jsonObj
     * @throws java.lang.Exception
     */
    public JSONObject cancel(List<NameValuePair> params) throws Exception {
        String response = this.client.sendPost("api/exchange/orders", params);
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * List charges filtered by params
     *
     * @param params
     * @return jsonObj
     * @throws java.lang.Exception
     */
    public JSONObject opens(Map<String, String> params) throws Exception {
        String response = this.client.sendGet("api/exchange/orders", params);
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * Get Order Transactions
     *
     * @param params
     * @return jsonObj
     * @throws java.lang.Exception
     */
    public JSONObject transactions(Map<String, String> params) throws Exception {
        String response = this.client.sendGet("api/exchange/orders/transactions", params);
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
}

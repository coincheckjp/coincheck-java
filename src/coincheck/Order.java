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
    public JSONObject create(JSONObject params) throws Exception {
        String response = this.client.request("POST", "api/exchange/orders", params.toString());
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
    public JSONObject cancel(JSONObject params) throws Exception {
        String response = this.client.request("POST", "api/exchange/orders", params.toString());
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * List charges filtered by params
     *
     * @return jsonObj
     * @throws java.lang.Exception
     */
    public JSONObject opens() throws Exception {
        String response = this.client.request("GET", "api/exchange/orders", "");
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * Get Order Transactions
     *
     * @return jsonObj
     * @throws java.lang.Exception
     */
    public JSONObject transactions() throws Exception {
        String response = this.client.request("GE", "api/exchange/orders/transactions", "");
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
}

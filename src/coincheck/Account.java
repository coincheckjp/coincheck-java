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
public class Account{

    private CoinCheck client;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public Account(CoinCheck client) {
        this.client = client;
    }
    
     /**
     * Make sure a balance.
     *
     * @params Map<String, String> 
     * @return JSONObject
     */
    public JSONObject balance(Map<String, String> params) throws Exception {
        String response = this.client.sendGet("api/accounts/balance", params);
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
    
    /**
     * Make sure a leverage balance.
     *
     * @params Map<String, String> 
     * @return JSONObject
     */
    public JSONObject leverageBalance(Map<String, String> params) throws Exception {
        String response = this.client.sendGet("api/accounts/leverage_balance", params);
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
    
    /**
     * Get account information.
     *
     * @params Map<String, String> 
     * @return JSONObject
     */
    public JSONObject info(Map<String, String> params) throws Exception {
        String response = this.client.sendGet("api/accounts/balance", params);
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
}

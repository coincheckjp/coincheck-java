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
public class Send {

    private CoinCheck client;

    public Send(CoinCheck client) {
        this.client = client;
    }

    /**
     * Sending Bitcoin to specified Bitcoin addres.
     *
     * @param params
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject create(JSONObject params) throws Exception {
        String response = this.client.request("POST", "api/send_money", params.toString());
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * You Get Send history
     *
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject all() throws Exception {
        String response = this.client.request("GET", "api/send_money", "");
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

}

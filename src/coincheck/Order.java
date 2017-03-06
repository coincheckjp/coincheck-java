/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;

import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Administrator
 */
public class Order extends CoinCheck{

    private HttpsURLConnection client;

    public Order(String accessKey, String secretKey) throws Exception {
        super(accessKey, secretKey);
        
    }
    
    /**
     * Create a order object with given parameters.
     * In live mode, this issues a transaction.
     */
    public void create(Map<String, String> paramData)
    {
//        $arr = $params;
//        $rawResponse = $this->client->request('post', 'api/exchange/orders', $arr);
//        return $rawResponse;
        
    }
   
}

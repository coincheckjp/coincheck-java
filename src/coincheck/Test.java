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
public class Test {

    public static void main(String[] args) throws Exception {
        CoinCheck client = new CoinCheck("29Yxe5YPf1GD-cgL", "E94BREB0Ki1-sWDGNCVdo3sL-rqEqmbs");
        //client.account().info();
        JSONObject obj = new JSONObject();
        obj.put("bank_name", "kakaka");
        obj.put("branch_name", "lalala");
        obj.put("bank_account_type", "futsu");
        obj.put("number", "1234567");
        obj.put("name", "name___name");      
        //client.bankAccount().create(obj);
        client.bankAccount().all();
        client.bankAccount().delete("23329");
    }
}

package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Main {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String CAS_URL = "https://ds2.ficoh.com/loginserver/login";
    private static final String REST_BASE_URL = "https://ds2.ficoh.com/agencyportal"; 
    
    public static void main(String[] args) throws IOException {
        
        CasLogin casLogin = new CasLogin("foo@pyramidins.com", "asdf142536", CAS_URL);
        
        String serviceTicket = casLogin.getServiceTicket("REST_BASE_URL");
        
//        RestClient client = new RestClient();
//        
//        /* example GET */
//        Main main = new Main();
//        String getSimple =  REST_BASE_URL + "/rest/api/v1/messages";
//        main.printContent( client.get(getSimple + "?ticket=" + casLogin.getServiceTicket(getSimple)) );
//        
//        String getParams =  REST_BASE_URL + "/rest/api/v1/messages?page=1";
//        main.printContent( client.get(getParams + "&ticket=" + casLogin.getServiceTicket(getParams)));
//        
//        String getSimple2 = REST_BASE_URL + "/rest/api/v1/messages/4405078";
//        main.printContent( client.get(getSimple2 + "?ticket=" + casLogin.getServiceTicket(getSimple2)));
//        
//        /* Example POST */
//        // see https://groups.google.com/forum/#!searchin/jasig-cas-user/post$20to$20rest$20resource/jasig-cas-user/NWmFahj9usk/YBECPJULN3sJ
//        // looks like <property name="redirectAfterValidation" value="true" /> should be false in our webapp's spring security context (casContext.xml)
//        String postMessage = REST_BASE_URL + "/rest/api/v1/messages";
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("body", "message body");
//        params.put("toAddress", "11220"); // from address lookup
//        params.put("subject", "message subject");
//        main.printContent( client.post(postMessage + "?ticket=" + casLogin.getServiceTicket(postMessage), params));
    }

    private void printContent(HttpURLConnection con) {
        if (con != null) {

            try {
                
                LOGGER.info("Response Code -> " + con.getResponseCode());
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String input;
                StringBuilder content = new StringBuilder();
                while ((input = br.readLine()) != null) {
                    content.append(input);
                }
                br.close();
                LOGGER.info("Content -> " + content.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
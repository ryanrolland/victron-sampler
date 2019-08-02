package utility;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.security.sasl.AuthenticationException;

public class CasLogin {
    
    private static final Logger LOGGER = Logger.getLogger(CasLogin.class.getName());
    
    private String username;
    private String password;
    private String casUrl;
    private RestClient restClient;
    
    public CasLogin(String username, String password, String casUrl) {
        this.username = username;
        this.password = password;
        this.casUrl = casUrl;
        restClient = new RestClient();
    }
    
    public String getServiceTicket(String serviceUrl) throws IOException {
        // get TGT
        String location = getTicketGrantingTicket(username, password);
        
        // get SGT
        return getServiceGrantingTicket(location, serviceUrl);
        
    }
    
    /**
     * With the TGT location and service url this will get the SGT
     * @param tgtLocation
     * @param serviceUrl
     * @return
     * @throws IOException
     */
    private String getServiceGrantingTicket(String tgtLocation, String serviceUrl) throws IOException {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("service", serviceUrl);
        params.put("method", "POST");
        
        
        HttpURLConnection conn = restClient.post(tgtLocation, params);
        StringBuilder responseBuilder = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String input;
        while ((input = in.readLine()) != null) {
            responseBuilder.append(input);
        }
        in.close();
        
        String response = responseBuilder.toString();
        LOGGER.info("SGT -> " + response);
         
        return response;
    }
    
    /**
     * Gets the TGT for the given username and password
     * @param username
     * @param password
     * @return
     * @throws IOException
     */
    private String getTicketGrantingTicket(String username, String password) throws IOException {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("username", username);
        params.put("password", password);
        HttpURLConnection conn = restClient.post(casUrl, params);
        
        if(conn.getResponseCode() == 400) {
            throw new AuthenticationException("bad username or password");
        }
        String location = conn.getHeaderField("Location");
        LOGGER.info("TGT LOCATION -> " + location);
        return location;
    }
    
}
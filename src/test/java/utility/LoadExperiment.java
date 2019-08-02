package utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.UrlEncodedParser;
import com.google.api.client.http.javanet.NetHttpTransport;

public class LoadExperiment {

  
  static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
  
  public static void main(String[] args) throws IOException {

    HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
      @Override
    public void initialize(HttpRequest request) {
      //request.setParser(new JsonObjectParser(JSON_FACTORY));
        
        request.setParser(new UrlEncodedParser());
        
    }
  });    
    
//    GenericUrl url = new GenericUrl("https://ds2.ficoh.com/loginserver");
//
//    HttpRequest request = requestFactory.buildGetRequest(url); 
//    
//    HttpResponse response = request.execute();
//    
//    String parseAsString = response.parseAsString();
//    
//    System.out.println(parseAsString);
    
    GenericUrl postUrl = new GenericUrl("https://ds2.ficoh.com/loginserver/login?service=https://ds2.ficoh.com/agencyportal");
        Map<String, String> params = new HashMap<String, String>();
    
      params.put("username", "foo@pyramidins.com");
      params.put("password", "asdf142536");
      params.put("lt", "LT-7-UFosocz0JysNxufEVrI4PZfVAbfcGe-f01mtomq01.ficoh.com");
      params.put("execution", "e1s1");
      params.put("_eventId", "submit");
      params.put("submit", "LOGIN");
      
        UrlEncodedContent content = new UrlEncodedContent(params);
        
        HttpRequest request = requestFactory.buildPostRequest(postUrl, content);
        
        HttpResponse response;
        
        try {
        
        response = request.execute();
        
        } catch(HttpResponseException e) {
          
          if (e.getStatusCode() == 302) {
            System.out.println(e.getHeaders().getLocation());
            GenericUrl url = new GenericUrl("http://ds2.ficoh.com"+e.getHeaders().getLocation());            
          //GenericUrl url = new GenericUrl("https://ds2.ficoh.com/loginserver");

                HttpRequest getRequest = requestFactory.buildPostRequest(url,content);            

            getRequest.setUrl(url);
            response = getRequest.execute();
            String parseAsString = response.parseAsString();
            System.out.println(parseAsString);            
          }
        }
        
        

  }

}

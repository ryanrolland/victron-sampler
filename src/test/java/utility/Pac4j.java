package utility;

import org.pac4j.cas.credentials.authenticator.CasRestAuthenticator;

public class Pac4j {
public static void main(String[] args) {
  
  String casUrlPrefix = "https://ds2.ficoh.com/loginserver/login";
  CasRestAuthenticator authenticator = new CasRestAuthenticator();
  
  authenticator.getConfiguration().setLoginUrl(casUrlPrefix);
    
  //CasRestFormClient client = new CasRestFormClient(authenticator);

  // The request object must contain the CAS credentials
//  final WebContext webContext = new J2EContext(request, response);
//  final HttpTGTProfile profile = client.requestTicketGrantingTicket(context);
//  
//  final CasCredentials casCreds = client.requestServiceTicket("<SERVICE_URL>", profile);
//  final CasProfile casProfile = client.validateServiceTicket("<SERVICE_URL>", casCreds);  
  
}
}

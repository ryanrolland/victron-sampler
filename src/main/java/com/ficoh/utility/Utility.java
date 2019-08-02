package com.ficoh.utility;

import java.io.IOException;
import java.nio.CharBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import static org.springframework.security.jwt.codec.Codecs.b64UrlDecode;
import static org.springframework.security.jwt.codec.Codecs.b64UrlEncode;
public class Utility {
	
	
  public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    
//    Charset defaultCharset = Charset.defaultCharset();
//    
//    System.out.println(defaultCharset.name());

    //String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImE2NmUwZTkyLTk3ZDktNDYxNC1iZmVkLWZiOGExYWNkNTU2NiJ9.eyJqdGkiOiIzNjc0MDA5ZC1hNjYxLTQxNTYtYjM5MS01Nzg1ZTlmZjEwZGEiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvY2FzL29pZGMiLCJhdWQiOiJ1YWEiLCJleHAiOjE1MjgyNTYwNTIsImlhdCI6MTUyODI0ODg1MiwibmJmIjoxNTI4MjQ4NTUyLCJzdWIiOiJycm9sbGFuZCIsImFtciI6WyJMZGFwQXV0aGVudGljYXRpb25IYW5kbGVyIl0sInN0YXRlIjoiIiwibm9uY2UiOiJhanh1RnB4dzhxVEgiLCJhdF9oYXNoIjoiQWxhVDFaVmVGVFhKaFlVTldYemtDZz09IiwicHJlZmVycmVkX3VzZXJuYW1lIjoicnJvbGxhbmQifQ.Mf1XIJFRx7mjRAse_Lb1xEz3CaTtzxNXJolt_W7Is3yPvk_9RBmUBnD-__8_zmLwGfxflA_dCBRXbuKuDgoz3ot5uO-Ac5ef8Sny1jmZqjjGBI_4QgWBAAHLfWJqLN3tVUd7lvhpm1DZgNKzGDqacqinYQ63ZPIjSvgQrjhqNAIBdaKT2QzjHtTHBcchnCS45qRasWKvMD2bIDgF0OBL8YRxGBaunKcWFnW2eq7xniomgKTceLgzVHdkeaD1ipESsZzy6BtBIKU9dXf-8jvgop81wW7N1zkMSIoETNb0LnngfT2gS11lGhh69grgDQAJNpgAe6yPBgQCepwD4lJRlw";
    
    String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImRjNGVkMWE2LTkzMDctNGUwOS04MGM1LTliODEyYjhhMjkwOSJ9.eyJqdGkiOiJjODNjNDcxYy02OTBkLTQ5M2YtODI0NC1lYTI2ZjZmMTlkZDUiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvY2FzL29pZGMiLCJhdWQiOiJ1YWEiLCJleHAiOjE1MjgyNjE4MzQsImlhdCI6MTUyODI1NDYzNCwibmJmIjoxNTI4MjU0MzM0LCJzdWIiOiJycm9sbGFuZCIsImFtciI6WyJMZGFwQXV0aGVudGljYXRpb25IYW5kbGVyIl0sInN0YXRlIjoiIiwibm9uY2UiOiJad1RTNGl3dXRrZkciLCJhdF9oYXNoIjoiaG0wVlc1VTd2ekpibzdidDZhSHBCZz09IiwicHJlZmVycmVkX3VzZXJuYW1lIjoicnJvbGxhbmQifQ.CalqB7eH3e2MXfrpHrY4KEFREMmqQM9CVxnGLiWFILSW4yMOJkXQbtT9Gmu5LFGv-ylzS3vdaYfK6sEajkG17DtD9n4OjueN1PiCDS3viophn6hOvgV2bvnglhZS1Hf6iUE3B_lyUFIeyUVeTJDvIVwOOeDjVBScm6txfyBSKIiS9jkiyYjFP-9QUG9Fnph0gWL76c0IdS8f67HFToYrfNKL-14zMT_Q-tLEGgQ0qazx6PD6lKZi25u2G2OjbqdXI-GgmeYnX0--pygLDGbEhLSSDFVr4KC1Vhr0GRvpnYDP_lMzv-8rikAN3WqOeD2oIrUWZtfY07LNL4Gw7prLVA";    
    
    String part1Header = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImE2NmUwZTkyLTk3ZDktNDYxNC1iZmVkLWZiOGExYWNkNTU2NiJ9";
    String part2Content = "eyJqdGkiOiIzNjc0MDA5ZC1hNjYxLTQxNTYtYjM5MS01Nzg1ZTlmZjEwZGEiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvY2FzL29pZGMiLCJhdWQiOiJ1YWEiLCJleHAiOjE1MjgyNTYwNTIsImlhdCI6MTUyODI0ODg1MiwibmJmIjoxNTI4MjQ4NTUyLCJzdWIiOiJycm9sbGFuZCIsImFtciI6WyJMZGFwQXV0aGVudGljYXRpb25IYW5kbGVyIl0sInN0YXRlIjoiIiwibm9uY2UiOiJhanh1RnB4dzhxVEgiLCJhdF9oYXNoIjoiQWxhVDFaVmVGVFhKaFlVTldYemtDZz09IiwicHJlZmVycmVkX3VzZXJuYW1lIjoicnJvbGxhbmQifQ";
    String part3Crypto = "Mf1XIJFRx7mjRAse_Lb1xEz3CaTtzxNXJolt_W7Is3yPvk_9RBmUBnD-__8_zmLwGfxflA_dCBRXbuKuDgoz3ot5uO-Ac5ef8Sny1jmZqjjGBI_4QgWBAAHLfWJqLN3tVUd7lvhpm1DZgNKzGDqacqinYQ63ZPIjSvgQrjhqNAIBdaKT2QzjHtTHBcchnCS45qRasWKvMD2bIDgF0OBL8YRxGBaunKcWFnW2eq7xniomgKTceLgzVHdkeaD1ipESsZzy6BtBIKU9dXf-8jvgop81wW7N1zkMSIoETNb0LnngfT2gS11lGhh69grgDQAJNpgAe6yPBgQCepwD4lJRlw";
    
//    int firstPeriod = token.indexOf('.');
//    int lastPeriod = token.lastIndexOf('.');
//
//    if (firstPeriod <= 0 || lastPeriod <= firstPeriod) {
//        throw new IllegalArgumentException("JWT must have 3 tokens");
//    }
//    CharBuffer buffer = CharBuffer.wrap(token, 0, firstPeriod);
//
//    String header = buffer.toString();

    byte[] content = b64UrlDecode(part2Content);
    
    byte[] crypto = b64UrlDecode(part3Crypto);
    
    
    Signature signature = Signature.getInstance("SHA256withRSA");
    
    String key = "          -----BEGIN PUBLIC KEY-----"+
          "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk0mdERXjgIZhbSNNMsQh"+
          "1/ARt4QwTfZ4npESqAUF3NFQ2B2lLcHgPaDHhOA1h5mbBwfoigtDiHZV+oicHHzl"+
          "p8+LWQ7q7/pPLVAFBMRtHAT9nzv6Jw8L/9ECDJtCHdlw0k67nL0F3ab+TYESxjlW"+
          "uqJTqyqIKhN83RXHCDq9Q4LSjWCkm5r3uLBhQ+RWhPmZZwIGbZZpOV4aIc5zDO0W"+
          "+I/76ieD1/GbLq8R9vFggk/H3aXI8UOoS7v6ABHRXts5bpijYTE1PKvQ/jX+L9Iq"+
          "jMSd9u5emP8KUrVT4hlWwEoOrTQ+V1OVriluXNnQW3EavCKLsjb8NAlDCHN+gu7f"+
          "EwIDAQAB"+
          "-----END PUBLIC KEY-----";
    
String privateKeyNon64x = "NrC76M825c-AN7VHAhW_913GRl2ITjq5-S8nPUp4LdFvcoA3iHEYIeoki9txJQPNg-PU3gVmszI1pMy7IvzhjdlMBeq7JBbO9aVj56xEU4Yq4sizj3NP4lKqryGrgEfwU7c2hPHPTsmmXgMShJZf9Pc3mDQ30V_hGnrup8if5IQLVjJe12UEXrOOTSbXZ690bSCzsDGsXtCdKFiMMe-3P6hwEeBwDMUyb8hIpnTljCTvpDIbDdcNKzsi_hq7aO_Tw0Ds-EUHifXJCIgAvCjJ4QdLQoDrvEWFgiZBdj_NPbq6EqZuQ4NO7PbdzEjK-V2FqJ_BuSLztIwNRc_7-nto0Q";

    byte[] b64UrlEncode2 = b64UrlEncode("          -----BEGIN PRIVATE KEY-----"+privateKeyNon64x+"-----END PRIVATE KEY-----");    
    

    
    RSAPublicKey publicKey = RsaKeyHelper.parsePublicKey(key.trim());
    
    signature.initVerify(publicKey);
    
    signature.update(content);
    
    boolean verify = signature.verify(crypto);
    
    System.out.println(verify);
    
  }
  
    
	
}
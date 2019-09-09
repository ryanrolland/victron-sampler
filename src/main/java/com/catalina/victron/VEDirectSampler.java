package com.catalina.victron;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.catalina.victron.com.SimpleRead;
import com.catalina.victron.frame.Framehandler;

import purejavacomm.CommPortIdentifier;

public class VEDirectSampler {

	//60V
	private static final String baseDir = "/home/pi/A123/";	
	private static String port = "ttyUSB0";
	
	//12V
	//private static final String baseDir = "/home/pi/LeadAcid/";	
	//private static String port = "ttyUSB1";

	
	  ConcurrentLinkedQueue<Byte> queue = new ConcurrentLinkedQueue<Byte>();

	  public VEDirectSampler(String defaultPort) {
		  
		  boolean           portFound = false;
		 
		  Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();

		  while (portList.hasMoreElements()) {
		      CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
		      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
		    	  System.out.println("COM Port with ID:"+portId.getName());
		      if (portId.getName().equals(defaultPort)) {
		          System.out.println("Found port: "+defaultPort);
		          portFound = true;
		          SimpleRead reader = new SimpleRead(portId,"SimpleReadApp", queue);
		      } 
		      } 
		  } 
		  if (!portFound) {
		      System.out.println("port " + defaultPort + " not found.");
		  } 
		  		  
	  }
	  
	  
	  public static void main(String[] args) throws InterruptedException, IOException {
		  

		  VEDirectSampler sampler = new VEDirectSampler(port);
		  sampler.process();
		  
	  }
	  
	  
	  private int manualSearch(String value) {
		  return manualSearch(value,false);
	  }
	  
	  private String printBool(boolean value) {
		  String result = "0";
		  if(value) {
			  result = "1";
		  }
		  return result;
	  }
	  
	  
	  /**
	   * 
	   * PROBLEM: indexOf is not working consistently
	   * it seems different values are returned for char references vs. system.out.println
	   * for receive buffer.
	   */
	  private int manualSearch(String value, boolean debug) {
		  int result = -1;
		  if(value.length() >= 7) {
			  for(int i=0;i<value.length()-8;i++) {

				  if((debug) && (value.charAt(i) == 'C'))  {
						System.out.println("===BEFORE CHECK DUMP START ===="); 
						
						 System.out.println(value.substring(i));
						 
						
							System.out.println("===BEFORE CHECK DUMP END ====");
						
							int end = (value.length() - i) / 2;
							end = i + end;
							String smallSample = value.substring(i,end);
							
							System.out.println("====SECOND SMALL DUMP START ====");
							System.out.println(smallSample);
							System.out.println("====SECOND SMALL DUMP END ====");
							
							StringBuilder b = new StringBuilder();
							
							b.append(value.charAt(i)) ;
							b.append(value.charAt(i+1));
							b.append(value.charAt(i+2));
							b.append(value.charAt(i+3));
							b.append(value.charAt(i+4));
							b.append(value.charAt(i+5));
							b.append(value.charAt(i+6));
							b.append(value.charAt(i+7));
							System.out.println(b.toString());
						 System.out.println(printBool(value.charAt(i) == 'C') + printBool(value.charAt(i+1) == 'h') + printBool(value.charAt(i+2) == 'e') + printBool(value.charAt(i+3) == 'c') + printBool(value.charAt(i+4) == 'k') + printBool(value.charAt(i+5) == 's') + printBool(value.charAt(i+6) == 'u') + printBool(value.charAt(i+7) == 'm'));
						 System.out.println("============");
						 
					 
					}
				  
				  if((value.charAt(i) == 'C') && (value.charAt(i+1) == 'h') && (value.charAt(i+2) == 'e') && (value.charAt(i+3) == 'c') && (value.charAt(i+4) == 'k') && (value.charAt(i+5) == 's') && (value.charAt(i+6) == 'u') && (value.charAt(i+7) == 'm')){
					  return i;
				  }

			  }  
		  }
		  
		  return result;
	  }
	  
//	  private static String cleanTextContent(String text)
//	  {
//	      // strips off all non-ASCII characters
//	      text = text.replaceAll("[^\\x00-\\x7F]", "");
//	   
//	      // erases all the ASCII control characters
//	      //text = text.replaceAll("[\\p{Cntrl}&&[^]]", "");
//	       
//	      // removes non-printable characters from Unicode
//	      text = text.replaceAll("\\p{C}", "");
//	   
//	      return text;
//	  }
	  
	  String receivedBuffer = "";
	  
	  public void process() throws InterruptedException, IOException {

		  Framehandler handler = new Framehandler(baseDir);
		  
		  while(true) {
			  
			  Byte poll = queue.poll();
			  if(poll != null) {
				  
				  handler.rxData(poll);
				  
			  } else {
				  Thread.sleep(1000);
				  //System.out.println("sleeping");
			  } 
		  }
	  }
	
//  public static void main(String[] args) throws Exception {
//    
//  //computeChecksum();
//  
//    String batteryCapacityResult = ":7001000C80076";
//    
//    int data = convertDataToInt(batteryCapacityResult,true);//batteryCapacityResult.substring(8, batteryCapacityResult.length()-2);
//    System.out.println(data);
//    
//    
//    
//    //String command = getPowerCommandWithChecksum();
//    //System.out.println(command);
//    //String commandWithCheckSum = addChecksumToCommand(command);
//    //System.out.println(commandWithCheckSum);
//    
//  }

  public static String addChecksumToCommand(String command) {
    return command + computeChecksum(command);
  }
  
/**
 * Takes a raw command without a checksum, e.g.
 * String command = ":7FF0F00";
 * 
 * The sum of all data bytes and the check must equal 0x55
 * the structure here is the command followed by a 16 bit address, followed by an 8 bit set of flags, followed by an 8 bit checksum
 * @param command
 * @return
 */
  public static String computeChecksum(String command) {

    command = command.replace(":", "");


    if (command.length() % 2 != 0) {
      command = "0" + command;
    }

    byte checksum = (byte) (Integer.parseInt("55", 16) & 0xff);

    for (int i = 0; i < command.length(); i = i + 2) {
      String substring = command.substring(i, i + 2);
      byte value = (byte) (Integer.parseInt(substring, 16) & 0xff);
      checksum = (byte) (checksum - value);
    }
    
    String format = String.format("%02X ", checksum);
    
    return format;
    
  }
  
  public static int convertDataToInt(String received, boolean isSigned) throws Exception {
    
    String data = received.substring(8, received.length()-2);
    String buffer = "";
    for(int i=0;i<data.length();i=i+2) {
      String substring = data.substring(i, i+2);
      buffer = substring+buffer;
    }
    
    data = buffer;

    
    
    int length = data.length();
    
    int result;
    switch(length) {
      case 2:
      case 4:
        result = parseHexString(isSigned, data);
      break;
      default:
        throw new Exception("Unsupported length of data:"+length);
      
    }
    
    
    return result;
    
  }

  public static int parseHexString(boolean isSigned, String data) {
    int result;
    if(isSigned) {
      result = Integer.parseInt(data, 16);
    } else {
      result = Integer.parseUnsignedInt(data, 16);
    }
    return result;
  }
  
  
  
  public static String getVoltageCommandWithChecksum() {
    //checksum 0x55 - 7 - ED - 8D - 00 = 
    return getVoltageCommand()+"D4";
  }
  
  public static String getVoltageCommand() {
    return ":7ED8D00";
  }
  
  public static String getCurrentCommandWithChecksum() {
    return getCurrentCommand()+"D5";
  }
  
  public static String getCurrentCommand() {
    return ":7ED8C00";
  }
  
  public static String getPowerCommandWithChecksum() {
    return getPowerCommand()+"D3";
  }
  
  public static String getPowerCommand() {
    return ":7ED8E00";
  }
  
  public static String getBatteryCapacityCommandWithChecksum() {
    return getBatteryCapacityCommand()+"3E";
  }
  
  public static String getBatteryCapacityCommand() {
    return ":7001000";
  }
  
  /*
  
Checksum	n

PID	0x203

V	59237
I	190
P	11
CE	-2
3926
SOC	908
TTG	-1
Alarm	O
FF
Relay	OFF
AR	0
BMV	700
F
W	0308
Checksum	I

  
  
   */
}

package com.catalina.victron;

public class VEDirectSampler {

  
  public static void main(String[] args) {
    
  //computeChecksum();
  
    String batteryCapacityResult = ":7001000C80076";
    
    String data = batteryCapacityResult.substring(8, batteryCapacityResult.length()-2);
    System.out.println(data);
    
    
    
    //String command = getPowerCommandWithChecksum();
    //System.out.println(command);
    //String commandWithCheckSum = addChecksumToCommand(command);
    //System.out.println(commandWithCheckSum);
    
  }

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
  
  
}

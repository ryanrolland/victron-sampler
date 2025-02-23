package com.catalina.victron.com;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import com.catalina.victron.frame.Framehandler;

import purejavacomm.CommPortIdentifier;
import purejavacomm.PortInUseException;
import purejavacomm.SerialPort;
import purejavacomm.SerialPortEvent;
import purejavacomm.SerialPortEventListener;
import purejavacomm.UnsupportedCommOperationException;

public class SimpleRead implements Runnable, SerialPortEventListener {

  InputStream           inputStream;
  SerialPort            serialPort;
  Thread            readThread;
  
  
  int rxCount = 0;
  ConcurrentLinkedQueue<Byte> queue;

  /**
   * Constructor declaration
   *
   *
   * @see
   */
  public SimpleRead(CommPortIdentifier portId, String comReaderName, ConcurrentLinkedQueue<Byte> queue) {
  this.queue = queue;
	  
	  try {
      serialPort = (SerialPort) portId.open(comReaderName, 2000);
  } catch (PortInUseException e) {}

  try {
      inputStream = serialPort.getInputStream();
  } catch (IOException e) {}

  try {
      serialPort.addEventListener(this);
  } catch (TooManyListenersException e) {}

  serialPort.notifyOnDataAvailable(true);

  try {
      serialPort.setSerialPortParams(19200, SerialPort.DATABITS_8, 
                     SerialPort.STOPBITS_1, 
                     SerialPort.PARITY_NONE);
  } catch (UnsupportedCommOperationException e) {}

  readThread = new Thread(this);

  readThread.start();
  }

  /**
   * Method declaration
   *
   *
   * @see
   */
  public void run() {
  try {
      while(true) {
    	  Thread.sleep(Integer.MAX_VALUE);
    	  //System.out.println("Main Receive Thread still sleeping");
      }
  } catch (InterruptedException e) {}
  } 

  /**
   * Method declaration
   *
   *
   * @param event
   *
   * @see
   */
  public void serialEvent(SerialPortEvent event) {
  switch (event.getEventType()) {

  case SerialPortEvent.BI:

  case SerialPortEvent.OE:

  case SerialPortEvent.FE:

  case SerialPortEvent.PE:

  case SerialPortEvent.CD:

  case SerialPortEvent.CTS:

  case SerialPortEvent.DSR:

  case SerialPortEvent.RI:

  case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
      break;

  case SerialPortEvent.DATA_AVAILABLE:

			try {
				//int total = 0;
				if (inputStream.available() > 0) {
					//int numBytes = inputStream.read(readBuffer);
					//total+=numBytes;

					byte value = (byte) inputStream.read();
					queue.add(value);
					
					rxCount = rxCount + 1;
					if(rxCount % 1000 == 0) {
						System.out.println("RX Thread Still Alive with count:"+rxCount+ " Queue Size:"+queue.size());
					}
					
					
					//Framehandler
					
					//byte[] received = ByteUtils.subArray(readBuffer, 0, numBytes);
					//String string = new String(received, java.nio.charset.StandardCharsets.US_ASCII);				
					//queue.add(string);				
				}
				
				
			} catch (IOException e) {
			}

      break;
  }
  } 

}
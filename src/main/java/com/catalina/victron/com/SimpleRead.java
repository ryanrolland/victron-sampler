package com.catalina.victron.com;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.concurrent.ConcurrentLinkedQueue;

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

  ConcurrentLinkedQueue<String> queue;

  /**
   * Constructor declaration
   *
   *
   * @see
   */
  public SimpleRead(CommPortIdentifier portId, String comReaderName, ConcurrentLinkedQueue<String> queue) {
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
      Thread.sleep(20000);
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
			byte[] readBuffer = new byte[1024];

			try {
				//int total = 0;
				if (inputStream.available() > 0) {
					int numBytes = inputStream.read(readBuffer);
					//total+=numBytes;
				}
				
				String string = new String(readBuffer, java.nio.charset.StandardCharsets.US_ASCII);				
				queue.add(string);
				
			} catch (IOException e) {
			}

      break;
  }
  } 

}
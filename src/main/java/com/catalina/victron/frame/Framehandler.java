package com.catalina.victron.frame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import com.catalina.victron.persist.TimeSeries;

public class Framehandler {


// The name of the record that contains the checksum.
final String CHECKSUM_TAG_NAME = "CHECKSUM";

HashMap<String,TimeSeries> fields = new HashMap<String,TimeSeries>();


boolean mStop = false;

private states mState;


//states listed here but not implemented:
//CHECKSUM, RECORD_HEX
enum states {
	IDLE, RECORD_NAME, RECORD_BEGIN, RECORD_HEX, RECORD_VALUE, CHECKSUM;
	
}

public Framehandler(String baseDir) throws IOException {
	mState = states.IDLE;
	fields.put("V", new TimeSeries("V",baseDir));
	fields.put("I", new TimeSeries("I",baseDir));
	fields.put("P", new TimeSeries("P",baseDir));
	fields.put("SOC", new TimeSeries("SOC",baseDir));
	
}

byte[] workingBuffer = new byte[1024];
int workingIndex = 0;

String workingName;

void addWorkingByte(byte rx) {
	workingBuffer[workingIndex] = rx;
	workingIndex = workingIndex + 1;
}

String convertWorkingToString() {
	byte[] subArray = ByteUtils.subArray(workingBuffer, 0,workingIndex);
	String string = new String(subArray, java.nio.charset.StandardCharsets.US_ASCII);
	return string;
}

void resetWorking() {
	workingIndex = 0;
	
}

public void rxData(byte inbyte) throws IOException
{
	//System.out.println(mState.name());
	
	if (mStop) return;
//	if ( (inbyte == ':') && (mState != states.CHECKSUM) ) {
//		mState = states.RECORD_HEX;
//	}
//	if (mState != states.RECORD_HEX) {
//		//mChecksum += inbyte;
//	}
	//inbyte = toupper(inbyte);

	switch(mState) {
	case IDLE:
		/* wait for \n of the start of an record */
		switch(inbyte) {
		case '\n':
			mState = states.RECORD_BEGIN;
			break;
		case '\r': /* Skip */
		default:
			break;
		}
		break;
	case RECORD_BEGIN:
		addWorkingByte(inbyte);	
		mState = states.RECORD_NAME;
		break;
	case RECORD_NAME:
		// The record name is being received, terminated by a \t
		switch(inbyte) {
		case '\t':
			
			workingName = convertWorkingToString();
			workingName = workingName.toUpperCase();
			// the Checksum record indicates a EOR

			resetWorking();		
			
//			if(workingName.contentEquals(CHECKSUM_TAG_NAME)) {
//				mState = states.CHECKSUM;
//				break;
//			}
			
			mState = states.RECORD_VALUE;
			break;
		default:
			addWorkingByte(inbyte);
			break;
		}
		break;
	case RECORD_VALUE:
		// The record value is being received.  The \r indicates a new record.
		switch(inbyte) {
		case '\n':
			 //forward record, only if it could be stored completely
			if ( workingIndex > 0 ) {
				//*mTextPointer = 0; // make zero ended
				
				String mValue = convertWorkingToString();
				resetWorking();
				textRxEvent(workingName, mValue);
				workingName = "";
			}
			mState = states.RECORD_BEGIN;
			break;
		case '\r': /* Skip */
			break;
		default:
			addWorkingByte(inbyte);
			break;
		}
		break;
//	case CHECKSUM:
//	
//		//bool valid = mChecksum == 0;
//		//if (!valid)
//		//	logE(MODULE,"[CHECKSUM] Invalid frame");
//		//mChecksum = 0;
//		//mState = IDLE;
//		//frameEndEvent(valid);
//		break;
//	
//	case RECORD_HEX:
//		//if (hexRxEvent(inbyte)) {
//		//	mChecksum = 0;
//		//	mState = IDLE;
//		//}
//		break;
	}
}


private void textRxEvent(String name, String value) throws IOException {
	if (fields.containsKey(name)) {
		System.out.println("Name:"+name+" : "+ value);
		TimeSeries timeSeries = fields.get(name);
		timeSeries.write(value);
	}
	
}

}

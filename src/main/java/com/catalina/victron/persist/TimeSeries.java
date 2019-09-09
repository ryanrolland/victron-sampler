package com.catalina.victron.persist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSeries {
	
	Format formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private Date currentDate;
	
	FileOutputStream fos;
	String currentFileDate;
	
	String fieldName;
	
	String baseDir;
	
	public TimeSeries(String fieldName, String baseDir) throws IOException {
		this.baseDir = baseDir;
		this.fieldName = fieldName;
		currentDate = new Date();
		
		currentFileDate = formatter.format(currentDate);
		
		initTimeSeriesFile();
		
	}

	private void initTimeSeriesFile() throws IOException, FileNotFoundException {
		File file = new File(baseDir+fieldName+"_"+currentFileDate+".txt");
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		fos = new FileOutputStream(file,true);
	}
	
	public void write(String value) throws IOException {
		
		long timestamp = System.currentTimeMillis();
		Date now = new Date(timestamp);
		String format = formatter.format(now);
		if(format != currentFileDate) {
			currentFileDate = format;
			fos.close();
			initTimeSeriesFile();
		}
		String entry = timestamp+", "+value+"\r\n";
		fos.write(entry.getBytes());
	}
	
	
	
	
}

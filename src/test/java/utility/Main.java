package utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

public class Main {
     
    
    public static void main(String[] args) throws IOException {
        
      
      File file = new File("c:\\tmp\\sampler\\experiment1.txt");
      FileOutputStream fos = new FileOutputStream(file);
      
      fos.write("date, value\r\n".getBytes());
      
     Date now = new Date();
     Date start = atStartOfDay(now);
     Date end = atEndOfDay(now);
     
     Date moment = new Date(start.getTime());
     DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS000");
     
     while(moment.before(end)) {
       String format = dateFormat.format(moment)+","+Math.sin(moment.getTime())+" \r\n";
       fos.write(format.getBytes());
       moment.setTime(moment.getTime()+100);
       
     }
     
     System.out.println(start);
     System.out.println(end);
      
      fos.close();
    
    }

    
    
    

    public static Date atStartOfDay(Date date) {
      LocalDateTime localDateTime = dateToLocalDateTime(date);
      LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
      return localDateTimeToDate(startOfDay);
  }

  public static Date atEndOfDay(Date date) {
      LocalDateTime localDateTime = dateToLocalDateTime(date);
      LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
      return localDateTimeToDate(endOfDay);
  }

  private static LocalDateTime dateToLocalDateTime(Date date) {
      return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  private static Date localDateTimeToDate(LocalDateTime localDateTime) {
      return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
}
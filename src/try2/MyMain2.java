package try2;


import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MyMain2 {
    public static void main(String[] args) {
	String regx = "^.*\\.txt$";
	System.out.println("text.txt".matches(regx));
	
	Calendar fileLastUpdatedTime = Calendar.getInstance();
	fileLastUpdatedTime.setTime(new Date());
	  
	Date fileLastUpdatedTimeMS = fileLastUpdatedTime.getTime();
	long ts = System.currentTimeMillis();
	Date localTime = new Date(ts);
	Date localDate = new Date(fileLastUpdatedTimeMS.getTime()
			+ TimeZone.getDefault().getOffset(localTime.getTime()));
	System.out.println(localDate);
    }
}

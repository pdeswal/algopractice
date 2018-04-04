import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestOSCmd {
	
	
	TestOSCmd(){
	}
	
	public static void main(String[] args) {
		if(args.length < 1)
			return;
		int intervalInSecs = Integer.parseInt(args[0]);
		final TestOSCmd testOSCmd = new TestOSCmd();
		Runnable r1 = new Runnable() {			
			@Override
			public void run() {
					testOSCmd.task();				
			}
		};
		
		ScheduledThreadPoolExecutor scheduledThreadPool = new ScheduledThreadPoolExecutor(5);
		scheduledThreadPool.scheduleAtFixedRate(r1, 0, intervalInSecs, TimeUnit.SECONDS);
	}
	
	public void task(){
		BufferedReader reader = null;
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		try {
			System.out.println("\n");
			Process p = Runtime.getRuntime().exec("uname -m");
			InputStream in = p.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(timeStamp + " " + line);
			}
		} catch (Exception e) {
				System.out.println(timeStamp + " Error in executing uname. Message is: " + e.getMessage());				
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}
	}
}

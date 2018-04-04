package try2;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class TryingFile {
    public static void main(String[] args) throws Exception {
	String spoolFileName = "pd_spool_" + System.currentTimeMillis();
	
	//InputStream in = TryingFile.class.getClassLoader().get
	
	//BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
	String labelData = "^FT0290,0658^A1N,22,32^FD再^FS";
	System.out.println(labelData);
	
	
	BufferedWriter bw = null;

        try {
           //bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(spoolFileName), "UTF-8"));
           bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(spoolFileName)));
           for(int i=0; i<1; ++i)
           {
               bw.write(labelData);
               bw.newLine();            	
           }
           bw.flush();
        }
        catch (IOException ex) {
           throw new Exception("Could not write to spool " + spoolFileName, ex);
        }
        finally {
           if (bw != null) {
              try {
                 bw.close();
              }
              catch (IOException ex) {
                 /* Eat */
              }
           }
        }
    }}


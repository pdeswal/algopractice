package FileWriting;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MyMain {
    public static void main(String[] args) throws Exception {
	//System.out.println(Boolean.valueOf("null"));
	/*String fileBufferFileutf8 = "C:\\FileBufferUtf8.txt";
	
	String fileBufferFilesms932 = "C:\\FileWritersms932s.txt";
	String fileBufferFilesshiftjis = "C:\\FileWritersshiftjis.txt";

	String content = "";
	System.out.println(content);

	//tryWithBufferedWriterWithUtf8(fileBufferFileutf8, content);
	//tryWithBufferedWriterWithms932(fileBufferFilesms932, content);
	tryWithBufferedWriterWithShiftJIS(fileBufferFilesshiftjis, content);*/
	Object o = new Object();
	System.out.println(System.identityHashCode(o));
	System.out.println(o);
	
    }

   

    public static void tryWithBufferedWriterWithUtf8(String spoolFileName, String labelData) throws Exception{
	BufferedWriter bw = null;

	try {
	    //@CUSTBASE_PSO_NITO_EX13_CINS_START
	    //bw = new BufferedWriter(new FileWriter(spoolFileName, true));
	    bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(spoolFileName, true), "UTF-8"));
	    // using UTF-8 for Japanese Char compatibility.
	    //@CUSTBASE_PSO_NITO_EX13_CINS_END
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
    }

    public static void tryWithBufferedWriterWithms932(String spoolFileName, String labelData) throws Exception{
	BufferedWriter bw = null;

	try {
	    //@CUSTBASE_PSO_NITO_EX13_CINS_START
	    //bw = new BufferedWriter(new FileWriter(spoolFileName, true));
	    bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(spoolFileName, true), "ms932"));
	    // using UTF-8 for Japanese Char compatibility.
	    //@CUSTBASE_PSO_NITO_EX13_CINS_END
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
    }
    
    public static void tryWithBufferedWriterWithShiftJIS(String spoolFileName, String labelData) throws Exception{
	BufferedWriter bw = null;

	try {
	    //@CUSTBASE_PSO_NITO_EX13_CINS_START
	    //bw = new BufferedWriter(new FileWriter(spoolFileName, true));
	    bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(spoolFileName, true), "SHIFT_JIS"));
	    // using UTF-8 for Japanese Char compatibility.
	    //@CUSTBASE_PSO_NITO_EX13_CINS_END
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
    }
}

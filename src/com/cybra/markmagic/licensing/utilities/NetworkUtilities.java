package com.cybra.markmagic.licensing.utilities;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Bin4;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.ProgramParameter;
import com.ibm.as400.access.ServiceProgramCall;
import com.ibm.as400.access.SystemValue;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import org.apache.log4j.Logger;
import planetj.common.AS400Manager;

public final class NetworkUtilities
{
  private static final String MAC_PATTERN = "^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$";
  private static final String OS = System.getProperty("os.name");
  public static final String getMacAddress()
    throws IOException
  {
    try
    {
      String macAdress = "";
      if (isWindows()) {
	  macAdress = getMAC_Windows();
	  macAdress = "36:37:f6:c3:43:02";
	  return macAdress;
      }
      if (isLinux()) {
	  macAdress = getMAC_Linux();
	  macAdress = "36:37:f6:c3:43:02";
	  return macAdress;
      }
      if (isAS400()) {
	  macAdress = getId_AS400();
	  macAdress = "36:37:f6:c3:43:02";
	  return macAdress;
      }
      if (isAIX()) {
	  macAdress = getMAC_AIX();
	  macAdress = "36:37:f6:c3:43:02";
	  return macAdress;
      }
      if (isHPUX()) {
	  macAdress = getId_HPUX();
	  macAdress = "36:37:f6:c3:43:02";
	  return macAdress;
      }
      throw new IOException("Unknown Operating System: " + OS);
    }
    catch (Exception e)
    {
      Logger.getLogger(NetworkUtilities.class).error("Problem parsing MAC Address.", e);
      throw new IOException(e.getMessage(), e);
    }
  }
  
  private static final boolean isWindows()
  {
    return OS.startsWith("Windows");
  }
  
  private static final boolean isLinux()
  {
    return OS.startsWith("Linux");
  }
  
  private static final boolean isAS400()
  {
    return OS.startsWith("OS/400");
  }
  
  private static final boolean isAIX()
  {
    return OS.startsWith("AIX");
  }
  
  private static final boolean isHPUX()
  {
    return OS.startsWith("HP-UX");
  }
  
  private static final String getMAC_Windows()
    throws IOException
  {
    try
    {
      String getMacResult = runCommand("getmac /fo csv /nh");
      
      String[] lines = getMacResult.split("\n");
      for (int i = 0; i < lines.length; i++)
      {
        String line = lines[i];
        
        line = line.replaceAll("\"", "");
        String[] parts = line.split(",");
        if (parts.length == 2)
        {
          String mac = parts[0].trim();
          String status = parts[1];
          if (mac.matches("^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$")) {
            return mac;
          }
        }
      }
      throw new Exception("Unable to parse MAC Address from \"getmac /fo csv /nh\" with results:\n" + getMacResult);
    }
    catch (Exception e)
    {
      Logger.getLogger(NetworkUtilities.class).warn("Unable to get MAC address from Window's 'getmac' command. Will attempt to use the 'ipconfig' command. " + e.getMessage(), e);
      try
      {
        String result = runCommand("ipconfig /all");
        result = result.replaceAll("\r", "");
        result = result.substring(result.indexOf("Physical Address"));
        result = result.substring(result.indexOf(":") + 2, result.indexOf("\n"));
        if ((result != null) && (result.trim().length() > 0)) {
          return result;
        }
      }
      catch (Exception e1)
      {
        Logger.getLogger(NetworkUtilities.class).error("Problem parsing Windows MAC Address using 'ipconfig /all' command.", e1);
      }
      throw new IOException("Unable to obtain the Server Id for the Windows Server using both the 'getmac' and 'ipconfig' commands.");
    }
  }
  
  private static final String getMAC_Linux()
    throws IOException
  {
    Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
    while (en.hasMoreElements())
    {
      NetworkInterface i = (NetworkInterface)en.nextElement();
      Enumeration<InetAddress> en2 = i.getInetAddresses();
      while (en2.hasMoreElements())
      {
        InetAddress addr = (InetAddress)en2.nextElement();
        if (((addr instanceof Inet4Address)) && (!addr.isLoopbackAddress()))
        {
          NetworkInterface network = NetworkInterface.getByInetAddress(addr);
          byte[] mac = network.getHardwareAddress();
          StringBuilder sb = new StringBuilder();
          for (int ii = 0; ii < mac.length; ii++) {
            sb.append(String.format("%02X%s", new Object[] { Byte.valueOf(mac[ii]), ii < mac.length - 1 ? ":" : "" }));
          }
          return sb.toString();
        }
      }
    }
    throw new IOException("Unable to obtain the Server Id from the Linux Server.");
  }
  
  private static final String getId_AS400()
    throws IOException
  {
    try
    {
      return "I5-" + as400_GetSerialNumber() + "-" + as400_GetPartitionNumber();
    }
    catch (IOException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      Logger.getLogger(NetworkUtilities.class).error("Problem obtaining AS400 ID.", e);
      throw new IOException("Error encountered getting the Server Id for AS400: " + e.getMessage());
    }
  }
  
  private static final String as400_GetSerialNumber()
    throws IOException
  {
    try
    {
      AS400 as400 = AS400Manager.singleton().getAS400Connection("MPI80_ALIAS");
      SystemValue systemValue = new SystemValue(as400, "QSRLNBR");
      return ((String)systemValue.getValue()).trim();
    }
    catch (Exception e)
    {
      Logger.getLogger(NetworkUtilities.class).error("Problem obtaining AS400 Serial Number.", e);
      throw new IOException("Error retrieving the AS400 System's Serial Number: " + e.getCause());
    }
  }
  
  private static final String as400_GetPartitionNumber()
    throws IOException
  {
    try
    {
      AS400 as400 = AS400Manager.singleton().getAS400Connection("MPI80_ALIAS");
      if (as400 != null)
      {
        ProgramParameter[] parameterList = new ProgramParameter[3];
        
        ProgramParameter parm0 = new ProgramParameter(2, 48);
        parameterList[0] = parm0;
        
        AS400Bin4 bin4 = new AS400Bin4();
        parameterList[1] = new ProgramParameter(bin4.toBytes(1));
        
        parameterList[2] = new ProgramParameter(bin4.toBytes(48));
        
        ServiceProgramCall sPGMCall = new ServiceProgramCall(as400);
        
        sPGMCall.setProgram("/QSYS.LIB/QPMLPMGT.SRVPGM", parameterList);
        
        sPGMCall.setProcedureName("dlpar_get_info");
        
        sPGMCall.setReturnValueFormat(0);
        if (sPGMCall.run() != true)
        {
          AS400Message[] messageList = sPGMCall.getMessageList();
          for (int i = 0; i < messageList.length; i++) {
            Logger.getLogger(NetworkUtilities.class).error(messageList[i].getText());
          }
        }
        else
        {
          return String.valueOf(getIntegerFromByteArray(parm0.getOutputData(), 40));
        }
      }
    }
    catch (Exception e)
    {
      Logger.getLogger(NetworkUtilities.class).error("Problem obtaining AS400 LPAR.", e);
    }
    throw new IOException("Unable to retrieve AS400 System's LPAR Number.");
  }
  
  private static final String getMAC_AIX()
    throws IOException
  {
    String netstatResult = runCommand("netstat -v");
    if (netstatResult != null)
    {
      String[] lines = netstatResult.split("\n");
      for (int i = 0; i < lines.length; i++)
      {
        String line = lines[i];
        if (line != null)
        {
          line = line.toUpperCase();
          if (line.startsWith("HARDWARE ADDRESS:")) {
            return line.replaceAll("HARDWARE ADDRESS:", "").trim();
          }
        }
      }
    }
    throw new IOException("Error encountered gertting the Server ID for AIX, using 'netstat -v'.");
  }
  
  private static final String getId_HPUX()
    throws IOException
  {
    String result = runCommand("getconf _CS_MACHINE_IDENT");
    if ((result != null) && (result.trim().length() > 0)) {
      return result.trim().toUpperCase();
    }
    throw new IOException("Unable to obtain the Unique Server ID for the HP-UX Server using 'getconf _CS_MACHINE_IDENT'. Result: " + result);
  }
  
  private static final String runCommand(String command)
    throws IOException
  {
    Process process = Runtime.getRuntime().exec(command);
    BufferedInputStream bis = null;
    try
    {
      bis = new BufferedInputStream(process.getInputStream());
      StringBuffer buffer = new StringBuffer();
      int c = bis.read();
      while (c != -1)
      {
        buffer.append((char)c);
        c = bis.read();
      }
      return buffer.toString();
    }
    finally
    {
      if (bis != null) {
        try
        {
          bis.close();
        }
        catch (Exception localException1) {}
      }
    }
  }
  
  private static int getIntegerFromByteArray(byte[] bytes, int offset)
  {
    int value = 0;
    for (int i = 0; i < 4; i++)
    {
      int shift = (3 - i) * 8;
      value += ((bytes[(i + offset)] & 0xFF) << shift);
    }
    return value;
  }
  
  public static String trim(String source)
  {
    return ltrim(rtrim(source));
  }
  
  public static String ltrim(String source)
  {
    return source.replaceAll("^\\s+", "");
  }
  
  public static String rtrim(String source)
  {
    return source.replaceAll("\\s+$", "");
  }
}

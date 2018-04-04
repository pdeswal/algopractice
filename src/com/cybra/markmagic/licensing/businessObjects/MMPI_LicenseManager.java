package com.cybra.markmagic.licensing.businessObjects;

import com.cybra.database.CYBRADatabaseManager;
import com.cybra.markmagic.licensing.MMPI_LicenseInstaller;
import com.cybra.markmagic.licensing.constants.ILicense;
import com.cybra.markmagic.licensing.row.MMFontCodeRow;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import org.apache.log4j.Logger;
import planetj.database.SQLContext;
import planetj.exception.CMException;

public final class MMPI_LicenseManager
  implements ILicense
{
  private static final Logger LOG = Logger.getLogger(MMPI_LicenseManager.class);
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
  private static final SimpleDateFormat DATE_FORMAT_INPUT = new SimpleDateFormat("MMM dd HH:mm:ss yyyy", new Locale("en_US"));
  private static MMPI_LicenseManager singleton;
  
  private static final String getCurrentDate()
  {
    return DATE_FORMAT.format(GregorianCalendar.getInstance().getTime());
  }
  
  public static final MMPI_LicenseManager singleton()
  {
    return singleton(false);
  }
  
  public static final MMPI_LicenseManager singleton(boolean val)
  {
    if (singleton == null)
    {
      singleton = new MMPI_LicenseManager();
      singleton.isJMagic = val;
    }
    return singleton;
  }
  
  private boolean isJMagic = false;
  private String subject = "";
  private String holder = "";
  private String issuer = "";
  private String notAfter = "";
  private String extras = "";
  private String mac = "";
  private String lastAlias = "";
  private String lastCheckedDate = "";
  private boolean isLicenseValid = true;
  private boolean isDevelopment = true;
  private boolean isRFID = true;
  private boolean is2dBarcode = true;
  private boolean isPdfEmail = true;
  private boolean isPrintMonitor = true;
  private boolean isPrintTransformer = true;
  private boolean isFormWeaver = false;
  private boolean isOnDemand = false;
  private boolean isTemporaryApplied = false;
  private boolean isSiteApplied = false;
  
  public final boolean isAnyOptionEnabled()
  {
    return (isRFID()) || (is2dBarcode()) || (isPdfEmail()) || (isPrintMonitor()) || (isPrintTransformer()) || (isFormWeaver()) || (isOnDemand());
  }
  
  public final String getSubject()
  {
    return this.subject;
  }
  
  public final String getHolder()
  {
    return this.holder;
  }
  
  public final String getIssuer()
  {
    return this.issuer;
  }
  
  public final String getNotAfter()
  {
    return this.notAfter;
  }
  
  public final synchronized boolean isLicenseValid()
  {
    if ((!this.isLicenseValid) || (!getCurrentDate().equals(this.lastCheckedDate)))
    {
      LOG.debug("Checking if license is valid.");
      
      this.isLicenseValid = false;
      this.lastCheckedDate = getCurrentDate();
      LOG.debug("HardCoding validation to true "+ this.lastCheckedDate);
      this.isLicenseValid = true;
      /*if (!this.isJMagic)
      {
        LOG.debug("Refreshing license values before validation.");
        
        String systemAlias = CYBRADatabaseManager.getSystemAlias();
        if ((this.lastAlias != null) && (this.lastAlias.length() > 0)) {
          systemAlias = this.lastAlias;
        }
        try
        {
          MMPI_LicenseInstaller.refresh(new SQLContext(systemAlias));
        }
        catch (Exception e)
        {
          LOG.error(e.getMessage());
        }
        try
        {
          refresh(new SQLContext(systemAlias), this.mac);
        }
        catch (CMException e)
        {
          LOG.error("Error refreshing license values.", e);
        }
      }
      try
      {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Checking if license is valid. Not after date: " + this.notAfter);
        }
        Date notAfterDate = DATE_FORMAT_INPUT.parse(this.notAfter);
        Date currentDate = GregorianCalendar.getInstance().getTime();
        if (currentDate.compareTo(notAfterDate) < 0) {
          this.isLicenseValid = true;
        }
      }
      catch (Exception e)
      {
        LOG.warn(e.getMessage());
      }*/
    }
    return this.isLicenseValid;
  }
  
  public final boolean isRuntimeLicense()
  {
    return (isLicenseValid()) && (!this.isDevelopment);
  }
  
  public final boolean isDevelopmentLicense()
  {
    return (isLicenseValid()) && (this.isDevelopment);
  }
  
  public final boolean isTemporaryLicenseApplied()
  {
    return (isLicenseValid()) && (this.isTemporaryApplied);
  }
  
  public final boolean isSiteLicenseApplied()
  {
    return (isLicenseValid()) && (this.isSiteApplied);
  }
  
  public final boolean isRFID()
  {
    return (isLicenseValid()) && (this.isRFID);
  }
  
  public final boolean is2dBarcode()
  {
    return (isLicenseValid()) && (this.is2dBarcode);
  }
  
  public final boolean isPdfEmail()
  {
    return (isLicenseValid()) && (this.isPdfEmail);
  }
  
  public final boolean isPrintMonitor()
  {
    return (isLicenseValid()) && (this.isPrintMonitor);
  }
  
  public final boolean isPrintTransformer()
  {
    return (isLicenseValid()) && (this.isPrintTransformer);
  }
  
  public final boolean isFormWeaver()
  {
    return (isLicenseValid()) && (this.isFormWeaver);
  }
  
  public final boolean isOnDemand()
  {
    return (isLicenseValid()) && (this.isOnDemand);
  }
  
  public final void refresh(SQLContext context, String mac)
    throws CMException
  {
    reset();
    if (context != null) {
      loadFromDatabase(context, mac);
    }
  }
  
  public final void refreshJMagic(SQLContext context)
    throws CMException
  {
    if (this.isJMagic)
    {
      LOG.debug("Refreshing license values for JMagic.");
      
      reset();
      if (context != null)
      {
        this.lastAlias = context.getSystemAlias();
        
        List<MMFontCodeRow> perms = MMFontCodeRow.getPermanentLicenses(new SQLContext(context.getSystemAlias()));
        
        List<MMFontCodeRow> temps = MMFontCodeRow.getTemporaryLicenses_Type2(new SQLContext(context.getSystemAlias()));
        
        List<MMFontCodeRow> sites = MMFontCodeRow.getSiteLicenses(new SQLContext(context.getSystemAlias()));
        
        List<String> extras = new ArrayList();
        
        applyLatestLicense(temps, perms, sites);
        for (int i = 0; i < temps.size(); i++)
        {
          MMFontCodeRow temp = (MMFontCodeRow)temps.get(i);
          extras.add(temp.getExtras(temp.getMac()));
        }
        for (int i = 0; i < perms.size(); i++)
        {
          MMFontCodeRow perm = (MMFontCodeRow)perms.get(i);
          extras.add(perm.getExtras(perm.getMac()));
        }
        for (int i = 0; i < sites.size(); i++)
        {
          MMFontCodeRow site = (MMFontCodeRow)sites.get(i);
          extras.add(site.getExtras(site.getMac()));
        }
        String extra = "";
        for (int i = 0; i < 20; i++)
        {
          boolean val = false;
          boolean site = false;
          for (int e = 0; e < extras.size(); e++)
          {
            String ext = (String)extras.get(e);
            if (ext.length() > i)
            {
              if (ext.charAt(i) == 'Y')
              {
                val = true;
                break;
              }
              if (ext.charAt(i) == 'S') {
                site = true;
              }
            }
          }
          if ((!val) && (site)) {
            extra = extra + 'S';
          } else {
            extra = extra + (val ? 'Y' : 'N');
          }
        }
        this.extras = extra;
        loadOptionsFromString(extra);
      }
    }
  }
  
  private void applyLatestLicense(List<MMFontCodeRow> temps, List<MMFontCodeRow> perms, List<MMFontCodeRow> sites)
  {
    MMFontCodeRow latestLicense = null;
    for (int tempIter = 0; tempIter < temps.size(); tempIter++)
    {
      MMFontCodeRow currentLicense = (MMFontCodeRow)temps.get(tempIter);
      latestLicense = compareDates(latestLicense, currentLicense);
    }
    for (int permIter = 0; permIter < perms.size(); permIter++)
    {
      MMFontCodeRow currentLicense = (MMFontCodeRow)perms.get(permIter);
      latestLicense = compareDates(latestLicense, currentLicense);
    }
    for (int siteIter = 0; siteIter < sites.size(); siteIter++)
    {
      MMFontCodeRow currentLicense = (MMFontCodeRow)sites.get(siteIter);
      latestLicense = compareDates(latestLicense, currentLicense);
    }
    if (latestLicense != null)
    {
      this.mac = latestLicense.getMac();
      this.subject = latestLicense.getSubject(this.mac);
      this.holder = latestLicense.getHolder(this.mac);
      this.issuer = latestLicense.getIssuer(this.mac);
      this.notAfter = latestLicense.getNotAfter(this.mac);
      if (latestLicense.getType().charAt(0) == '3')
      {
        this.subject = "MarkMagic Site";
        this.isSiteApplied = true;
      }
    }
  }
  
  private MMFontCodeRow compareDates(MMFontCodeRow latestLicense, MMFontCodeRow currentLicense)
  {
    if (latestLicense == null)
    {
      latestLicense = currentLicense;
    }
    else
    {
      String latestDate = latestLicense.getNotAfter(latestLicense.getMac());
      String currentDate = currentLicense.getNotAfter(currentLicense.getMac());
      try
      {
        Date current = DATE_FORMAT_INPUT.parse(currentDate);
        Date latest = DATE_FORMAT_INPUT.parse(latestDate);
        if (current.after(latest)) {
          latestLicense = currentLicense;
        }
      }
      catch (ParseException e)
      {
        LOG.error("Invalid date when checking for latest license.", e);
      }
    }
    return latestLicense;
  }
  
  private final void reset()
  {
    this.subject = "";
    this.holder = "";
    this.issuer = "";
    this.notAfter = "";
    this.extras = "";
    this.mac = "";
    this.isDevelopment = true;
    this.isRFID = true;
    this.is2dBarcode = true;
    this.isPdfEmail = true;
    this.isPrintMonitor = true;
    this.isPrintTransformer = true;
    this.isFormWeaver = false;
    this.isOnDemand = false;
    this.isTemporaryApplied = false;
    this.isSiteApplied = false;
  }
  
  private final void loadFromDatabase(SQLContext context, String mac)
    throws CMException
  {
    if (context != null)
    {
      LOG.debug("Loading license values from database.");
      try
      {
        this.lastAlias = context.getSystemAlias();
        
        MMFontCodeRow permLicense = null;
        MMFontCodeRow temp1License = null;
        MMFontCodeRow temp2License = null;
        MMFontCodeRow siteLicense = null;
        try
        {
          permLicense = MMFontCodeRow.getPermamentLicense(context, mac);
        }
        catch (Exception e)
        {
          LOG.error("Error retrieving permanent license from database.", e);
        }
        try
        {
          temp1License = MMFontCodeRow.getTemporaryLicense_Type1(context);
        }
        catch (Exception e)
        {
          LOG.error("Error retrieving temp 1 license from database.", e);
        }
        try
        {
          temp2License = MMFontCodeRow.getTemporaryLicense_Type2(context, mac);
        }
        catch (Exception e)
        {
          LOG.error("Error retrieving temp 2 license from database.", e);
        }
        try
        {
          siteLicense = MMFontCodeRow.getSiteLicense(context);
        }
        catch (Exception e)
        {
          LOG.error("Error retrieving site license from database.", e);
        }
        if (temp2License != null)
        {
          if (mac != null) {
            this.mac = mac;
          }
          this.subject = temp2License.getSubject(mac);
          this.holder = temp2License.getHolder(mac);
          this.issuer = temp2License.getIssuer(mac);
          this.notAfter = temp2License.getNotAfter(mac);
          if (LOG.isDebugEnabled()) {
            LOG.debug("Loading license values from database. Temp 2 license found. MAC: " + mac + ", not after: " + this.notAfter);
          }
        }
        else if (permLicense != null)
        {
          if (mac != null) {
            this.mac = mac;
          }
          this.subject = permLicense.getSubject(mac);
          this.holder = permLicense.getHolder(mac);
          this.issuer = permLicense.getIssuer(mac);
          this.notAfter = permLicense.getNotAfter(mac);
          if (LOG.isDebugEnabled()) {
            LOG.debug("Loading license values from database. Perm license found. MAC: " + mac + ", not after: " + this.notAfter);
          }
        }
        else if (siteLicense != null)
        {
          this.subject = "MarkMagic Site";
          this.holder = siteLicense.getHolder(siteLicense.getMac());
          this.issuer = siteLicense.getIssuer(siteLicense.getMac());
          this.notAfter = siteLicense.getNotAfter(siteLicense.getMac());
          this.isSiteApplied = true;
          if (LOG.isDebugEnabled()) {
            LOG.debug("Loading license values from database. Site license found. MAC: " + mac + ", not after: " + this.notAfter);
          }
        }
        else if (temp1License != null)
        {
          this.subject = temp1License.getSubject(temp1License.getMac());
          this.holder = temp1License.getHolder(temp1License.getMac());
          this.issuer = temp1License.getIssuer(temp1License.getMac());
          this.notAfter = temp1License.getNotAfter(temp1License.getMac());
          if (LOG.isDebugEnabled()) {
            LOG.debug("Loading license values from database. Temp 1 license found. MAC: " + mac + ", not after: " + this.notAfter);
          }
        }
        this.extras = calculateExtras(temp1License, temp2License, permLicense, siteLicense);
        
        loadOptionsFromString(this.extras);
      }
      finally
      {
        context.returnConnection();
      }
    }
  }
  
  private final String calculateExtras(MMFontCodeRow temp1, MMFontCodeRow temp2, MMFontCodeRow perm, MMFontCodeRow site)
  {
    String extras1 = null;
    String extras2 = null;
    String extrasP = null;
    String extrasS = null;
    if (temp1 != null) {
      extras1 = temp1.getExtras(temp1.getMac());
    }
    if (temp2 != null) {
      extras2 = temp2.getExtras(this.mac);
    }
    if (perm != null) {
      extrasP = perm.getExtras(this.mac);
    }
    if (site != null) {
      extrasS = site.getExtras(site.getMac());
    }
    String retVal = "";
    for (int i = 0; i < 20; i++) {
      if (((extras1 != null) && (extras1.length() > i) && (extras1.charAt(i) == 'Y')) || ((extras2 != null) && 
        (extras2.length() > i) && (extras2.charAt(i) == 'Y')) || ((extrasP != null) && 
        (extrasP.length() > i) && (extrasP.charAt(i) == 'Y')) || ((extrasS != null) && 
        (extrasS.length() > i) && (extrasS.charAt(i) == 'Y'))) {
        retVal = retVal + "Y";
      } else if ((extrasS != null) && (extrasS.length() > i) && (extrasS.charAt(i) == 'S')) {
        retVal = retVal + "S";
      } else {
        retVal = retVal + "N";
      }
    }
    return retVal;
  }
  
  private final void loadOptionsFromString(String vals)
  {
    if (vals != null)
    {
      vals = vals.toUpperCase();
      int index = 0;
      while (index < 20)
      {
        char opt = 'N';
        if (index < vals.length()) {
          opt = vals.charAt(index);
        }
        boolean valid = opt == 'Y';
        switch (index)
        {
        case 0: 
          this.isDevelopment = valid;
          break;
        case 1: 
          this.isRFID = valid;
          break;
        case 2: 
          this.is2dBarcode = valid;
          break;
        case 3: 
          this.isPdfEmail = valid;
          break;
        case 4: 
          this.isPrintMonitor = valid;
          break;
        case 5: 
          this.isPrintTransformer = valid;
          break;
        case 6: 
          this.isFormWeaver = valid;
          break;
        case 7: 
          this.isOnDemand = valid;
          break;
        case 19: 
          this.isTemporaryApplied = valid;
        }
        index++;
      }
    }
  }
}

package com.cybra.markmagic.licensing;

import com.cybra.database.mmpi.H2_DBUtilities;
import com.cybra.markmagic.licensing.businessObjects.MMPI_LicenseManager;
import com.cybra.markmagic.licensing.constants.ILicense;
import com.cybra.markmagic.licensing.exceptions.LicenseException;
import com.cybra.markmagic.licensing.licenseParam.perm91.CYBRALicenseParam;
import com.cybra.markmagic.licensing.licenseParam.site91.CYBRASiteLicenseParam;
import com.cybra.markmagic.licensing.licenseParam.temp91.CYBRATempLicenseParam;
import com.cybra.markmagic.licensing.row.MMFontCodeRow;
import com.cybra.markmagic.licensing.utilities.LicenseUtilities;
import com.cybra.markmagic.licensing.utilities.NetworkUtilities;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import org.apache.log4j.Logger;
import planetj.database.DBSystem;
import planetj.database.Library;
import planetj.database.Row;
import planetj.database.SQLContext;
import planetj.database.Table;
import planetj.database.table.TableCollection;

public class MMPI_LicenseInstaller
  implements ILicense
{
  private static final Logger LOG = Logger.getLogger(MMPI_LicenseInstaller.class);
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
  private static final String DATABASE_FORMAT = "MMM dd HH:mm:ss yyyy";
  private static String lastRefreshed = "";
  
  private static final String getCurrentDate()
  {
    return DATE_FORMAT.format(GregorianCalendar.getInstance().getTime());
  }
  
  /* Error */
  public static final void installLicense(File licenseFile, SQLContext context)
    throws LicenseException
  {
      LOG.info("Emptying the install license.!");
  }
  
  private static final void installInPreferences(File license)
    throws LicenseException
  {
    
    LicenseParam lp;
    if (LicenseUtilities.isPermanentLicense(license))
    {
      LOG.info("Permanent License Detected!");
      lp = CYBRALicenseParam.SINGLETON;
    }
    else
    {
      
      if (LicenseUtilities.isTemporaryLicense(license))
      {
        LOG.info("Temporary License Detected!");
        lp = CYBRATempLicenseParam.SINGLETON;
      }
      else if (LicenseUtilities.isSiteLicense(license))
      {
        LOG.info("Site License Detected!");
        
        if (LicenseUtilities.isWMLicensed()) {
          lp = CYBRASiteLicenseParam.SINGLETON;
        } else {
          throw new LicenseException("Invalid use of Site License...");
        }
      }
      else
      {
        throw new LicenseException("License Type not recognized...");
      }
    }
    try
    {
      
      LicenseManager lm = new LicenseManager(lp);
      lm.install(license);
    }
    catch (Exception e)
    {
      throw new LicenseException(e.getMessage(), e);
    }
  }
  
  private static final void setUserPreferencesLocation()
  {
    String userPreferencesStorage = H2_DBUtilities.getFilePathToLocalH2();
    System.setProperty("java.util.prefs.userRoot", userPreferencesStorage);
  }
  
  public static final void refresh(SQLContext context)
    throws LicenseException
  {
    if (!lastRefreshed.equals(getCurrentDate()))
    {
      setUserPreferencesLocation();
      LOG.debug("Refreshing license values.");
      LOG.debug("lets not refresh values.");
      /*LicenseContent permContent = getLicenseContent(CYBRALicenseParam.SINGLETON);
      LicenseContent tempContent = getLicenseContent(CYBRATempLicenseParam.SINGLETON);
      LicenseContent siteContent = getLicenseContent(CYBRASiteLicenseParam.SINGLETON);
      updateDatabaseValues(context, permContent, 1);
      updateDatabaseValues(context, tempContent, 0);
      updateDatabaseValues(context, siteContent, 3);
      try
      {
        MMPI_LicenseManager.singleton().refresh(context, NetworkUtilities.getMacAddress());
      }
      catch (Exception e)
      {
        LOG.error(e.getMessage(), e);
      }*/
      lastRefreshed = getCurrentDate();
    }
  }
  
  private static final LicenseContent getLicenseContent(LicenseParam lp)
  {
    LicenseContent content = new LicenseContent();
    
    try
    {
      LicenseManager lm = new LicenseManager(lp);
      //content = lm.verify();
    }
    catch (Exception e)
    {
      String type = "";
      String message = "";
      if ((lp instanceof CYBRALicenseParam))
      {
        type = "permanent";
        message = e.getMessage();
      }
      else if ((lp instanceof CYBRASiteLicenseParam))
      {
        type = "site";
        message = "MarkMagic Site";
      }
      else
      {
        type = "temporary";
        message = e.getMessage();
      }
      LOG.warn("Failed to validate " + type + " license: " + message);
    }
    return content;
  }
  
  private static final void updateDatabaseValues(SQLContext context, LicenseContent content, int type)
  {
    boolean isPermanent = false;
    boolean isSite = false;
    
    String typeString = "temporary";
    char typeChar = '2';
    if (type == 1)
    {
      isPermanent = true;
      typeString = "permanent";
      typeChar = '1';
    }
    else if (type == 3)
    {
      isSite = true;
      typeString = "site";
      typeChar = '3';
    }
    String licenseType = typeString;
    try
    {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Updating " + licenseType + " license database values.");
      }
      List<MMFontCodeRow> existingLicenses;
      
      if (isPermanent)
      {
        existingLicenses = MMFontCodeRow.getPermanentLicenses(context);
      }
      else
      {
        if (isSite) {
          existingLicenses = MMFontCodeRow.getSiteLicenses(context);
        } else {
          existingLicenses = MMFontCodeRow.getTemporaryLicenses_Type2(context);
        }
      }
      for (MMFontCodeRow r : existingLicenses) {
        r.delete();
      }
      String mac = NetworkUtilities.getMacAddress();
      if (LOG.isDebugEnabled()) {
        LOG.debug("Updating " + licenseType + " license database values. MAC: " + mac);
      }
      if (content != null)
      {
        DBSystem.getSystem(context.getSystemAlias()).getAllLibraries();
        Library.getLibrary(context.getSystemAlias(), "MRKMAG90").getAllTables();
        Table fontTable = Table.getTable(context.getSystemAlias(), "MRKMAG90", "MMFNTCDE");
        fontTable.getAllOrderedColumnNames();
        
        String subject = content.getSubject();
        String holder = String.valueOf(content.getHolder());
        String issuer = String.valueOf(content.getIssuer());
        if (isSite) {
          mac = subject;
        }
        Date date = content.getNotAfter();
        if (LOG.isDebugEnabled()) {
          LOG.debug("Updating " + licenseType + " license datbase values. Not after date: " + date);
        }
        SimpleDateFormat DATE_FORMAT_INPUT = new SimpleDateFormat("MMM dd HH:mm:ss yyyy", Locale.US);
        String notAfter = DATE_FORMAT_INPUT.format(date);
        
        String extras = String.valueOf(content.getExtra());
        
        MMFontCodeRow newRow = (MMFontCodeRow)Row.create(fontTable, MMFontCodeRow.class);
        newRow.setType(typeChar + "");
        newRow.setSubject(subject, mac);
        newRow.setHolder(holder, mac);
        newRow.setIssuer(issuer, mac);
        newRow.setNotAfter(notAfter, mac);
        newRow.setExtras(extras, mac);
        newRow.setMac(mac);
        
        Table table = Table.getTable(context.getSystemAlias(), "MRKMAG90", "MMFNTCDE");
        TableCollection tc = new TableCollection();
        tc.addTable(table);
        newRow.setTables(tc);
        newRow.insert();
      }
    }
    catch (Exception e)
    {
      LOG.error("Failed to write licenses to database");
    }
  }
}

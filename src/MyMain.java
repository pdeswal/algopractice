import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;


public class MyMain {
    public static final Set<Integer> dateFormatStyles = new HashSet<Integer>();
    public static final String UI_LONG_DT_FORMAT_FR = "yyyy-MM-dd";//as mentioned in CR 1199
    public static final String YYYYMMDD = UI_LONG_DT_FORMAT_FR;

    public static final String SQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss" ;


    public static final String UI_DTTM_FORMAT_STRING = "MM/dd/yy HH:mm";


    public static final String UI_LONG_DTTM_FORMAT = "MM/dd/yy HH:mm:ss";


    public static final String UI_LONG_YYYY_DTTM_FORMAT = "MM/dd/yyyy HH:mm:ss";

    public static final String UI_DTTM_FORMAT_STRING_WITHOUT_SEC = "MM/dd/yyyy HH:mm";

    public static final String UI_LONG_SIMPLE_DTTM_FORMAT = "M/d/yyyy HH:mm";

    public static final String UI_LONG_SIMPLE_DTTM_FORMAT_WITHOUT_TIME = "M/d/yyyy";

    public static final String UI_SHORT_DT_FORMAT = "MM/dd/yy";

    public static final String UI_SHORT_DTTM_FORMAT = "MM/dd/yy HH:mm";

    public static final String UI_SHORT_DTTM_FORMAT_WITH_TZ = UI_SHORT_DTTM_FORMAT + " z";

    public static final String UI_LONG_DT_FORMAT = "MM/dd/yyyy";
    public static final String UI_LONG_DT_FORMAT_UK = "dd/MM/yyyy";

    public static final String FILTER_SQL_DATE_FORMAT = "yyyy-MM-dd HH:mm" ;

    public static final String ISO_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSz";

    public static final Locale LOCALE_MEXICO = new Locale( "es", "MX" );

    public static final int LONGYEAR=100;

    public static final String UI_SHORT_TIME_FORMAT = "HH:mm";
    public static final	String UI_MEDIUM_TIME_FORMAT = "HH:mm:ss";
    public static final String UI_LONG_TIME_FORMAT = "HH:mm:ss z";
    public static final	String UI_FULL_TIME_FORMAT = "HH:mm:ss zzz";
    public static final String UI_QUICK_DATE_TIME_FORMAT =	"MMddyy HHmm";

    public static final Set<String> dateFormatStringPatterns = new HashSet<String>();
    static
    { 	
	dateFormatStyles.add( new Integer( DateFormat.SHORT )); 
	dateFormatStyles.add( new Integer( DateFormat.MEDIUM ));
	dateFormatStyles.add( new Integer( DateFormat.LONG ));
	dateFormatStyles.add( new Integer( DateFormat.FULL ));

	dateFormatStringPatterns.add(UI_LONG_DT_FORMAT_FR);
	dateFormatStringPatterns.add(YYYYMMDD);
	dateFormatStringPatterns.add(SQL_DATE_FORMAT);
	dateFormatStringPatterns.add(UI_DTTM_FORMAT_STRING);
	dateFormatStringPatterns.add(UI_LONG_DTTM_FORMAT);
	dateFormatStringPatterns.add(UI_LONG_YYYY_DTTM_FORMAT);
	dateFormatStringPatterns.add(UI_SHORT_DT_FORMAT);
	dateFormatStringPatterns.add(UI_SHORT_DTTM_FORMAT);
	dateFormatStringPatterns.add(UI_SHORT_DTTM_FORMAT_WITH_TZ);
	dateFormatStringPatterns.add(UI_LONG_DT_FORMAT);
	dateFormatStringPatterns.add(UI_LONG_DT_FORMAT_UK);
	dateFormatStringPatterns.add(FILTER_SQL_DATE_FORMAT);
	dateFormatStringPatterns.add(ISO_PATTERN);
	dateFormatStringPatterns.add(UI_QUICK_DATE_TIME_FORMAT);
	dateFormatStringPatterns.add(UI_LONG_SIMPLE_DTTM_FORMAT);
	dateFormatStringPatterns.add(UI_DTTM_FORMAT_STRING_WITHOUT_SEC);
	dateFormatStringPatterns.add(UI_LONG_SIMPLE_DTTM_FORMAT_WITHOUT_TIME);
    }



    public static void main(String[] args) throws Exception {
	Locale machineLocale = Locale.getDefault();
	String dateString = "02/22/17 00:00";
	Timestamp timeStamp = stringToTimestamp(dateString, machineLocale, TimeZone.getTimeZone("Asia/Tokyo"));

	System.out.println("machineLocale" + machineLocale + " dateString: "+dateString+" timeStamp: "+timeStamp);
    }
    public static Timestamp stringToTimestamp( String timestampString, Locale locale, TimeZone timeZone, int dateStyle, int timeStyle, boolean lenient)
	    throws Exception
    {
	System.out.println(
		"stringToTimestamp() for string " + timestampString +
		" in locale " + locale +
		" and timezone " + (timeZone == null ? "null" : timeZone.getDisplayName()) +
		" dateStyle = " + dateStyle + " timeStyle = " + timeStyle);



	if( !dateFormatStyles.contains( new Integer( dateStyle ) ) || 
		!dateFormatStyles.contains( new Integer( timeStyle ) )	) 
	{
	    throw new Exception( "stringToTimestamp() :: Date or time style is not supported. Input dateStyle = " + dateStyle + 
		    ", timeStyle = " + timeStyle );
	}

	DateFormat dateFormat = getDateTimeInstance( dateStyle, timeStyle, locale );
	dateFormat.setTimeZone( timeZone );
	dateFormat.setLenient( lenient );

	return stringToTimestamp( timestampString, dateFormat, lenient);
    }
    public static Timestamp stringToTimestamp( String timestampString, Locale locale, TimeZone timeZone )
	    throws Exception
    {
	return stringToTimestamp( timestampString, locale, timeZone, DateFormat.SHORT, DateFormat.SHORT );
    }
    public static Timestamp stringToTimestamp( String timestampString, Locale locale, TimeZone timeZone, int dateStyle, int timeStyle )
	    throws Exception
    {
	return stringToTimestamp(timestampString, locale, timeZone, dateStyle, timeStyle, false);
    }
    public static DateFormat getDateInstance( int style, Locale locale )
    {
	return DateFormat.getDateInstance( style, locale );
    }
    public static DateFormat getDateTimeInstance(
	    int dateStyle,
	    int timeStyle,
	    Locale locale )
    {
	//CR 3131 - remove local declarations, use public static final versions.
	DateFormat returnFormat = null;
	if( 	Locale.US.equals( locale ) ||
		Locale.CANADA.equals( locale) ||
		LOCALE_MEXICO.equals( locale ) )
	{
	    SimpleDateFormat dateFormat =
		    (SimpleDateFormat)getDateInstance( dateStyle, locale );
	    SimpleDateFormat timeFormat = null;

	    if( timeStyle == DateFormat.SHORT )
	    {
		// Use the short format
		timeFormat =  new SimpleDateFormat( UI_SHORT_TIME_FORMAT );
	    }
	    else
	    {
		// MEDIUM & FULL are just LONG for simplicity
		timeFormat =  new SimpleDateFormat( UI_LONG_TIME_FORMAT );
	    }

	    returnFormat = new SimpleDateFormat( dateFormat.toPattern() + " " + timeFormat.toPattern() );
	}
	else
	{
	    returnFormat = DateFormat.getDateTimeInstance( dateStyle, timeStyle, locale );
	}

	return returnFormat;
    }
    public static Timestamp stringToTimestamp( String timestampString, DateFormat dateFormat, boolean lenient)
	    throws ParseException
    {
	System.out.println(

		"stringToTimestamp() for string " + timestampString +
		" in format " + dateFormat);



	Timestamp timestamp = new Timestamp(0);
	java.util.Date utilDate = null;

	dateFormat.setLenient(lenient);
	utilDate = dateFormat.parse( timestampString );
	if( utilDate == null )
	{
	    throw new ParseException( "stringToTimestamp() :: The String " + timestampString + " could not be parsed into a timestamp.",0 );
	}
	timestamp.setTime( utilDate.getTime() );
	return timestamp;
    }
}

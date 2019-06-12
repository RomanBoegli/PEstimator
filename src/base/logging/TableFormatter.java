package base.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author Roman Boegli
 */

public class TableFormatter extends Formatter {
	
	
	private static final DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
	private static final DateFormat tf = new SimpleDateFormat("kk:mm:ss.SSS");  //kk = Hours in 24h-format
		
	@Override
	public String format(LogRecord record) {
		
		//assemble helpful information
		String cDate = df.format(new Date(record.getMillis()));
		String cTime = tf.format(new Date(record.getMillis()));
		String cSourceClass = record.getSourceClassName();
		String cLevel =  record.getLevel().toString();
		String cMessage = formatMessage(record);
		
		//combine values with tabs in between
		String cReturn = cDate + "\t" + cTime + "\t" + cSourceClass + "\t" + cLevel + "\t" + cMessage + "\r\n";
		
		return cReturn;
	}
	
}	
	
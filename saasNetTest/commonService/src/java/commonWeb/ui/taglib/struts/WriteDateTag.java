/*
 * $Id: WriteTag.java 421129 2006-07-12 05:13:54Z wsmoak $
 *
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package commonWeb.ui.taglib.struts;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;
import commonTool.util.DateUtil;
import commonWeb.base.BaseActionBase;
import commonWeb.security.authentication.UserinfoSession;

/**
 * retrive date from request/session, then transfer to user's timezone date time
 * generally, it will transfer db GMT time to user's timezone time
 */
public class WriteDateTag extends TagSupport {
	
	static Logger log = Logger.getLogger(WriteDateTag.class.getName());
		
    /**
     * The key to search default format string for java.sql.Timestamp in
     * resources.
     */
    public static final String SQL_TIMESTAMP_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.sql.timestamp";

    /**
     * The key to search default format string for java.sql.Date in
     * resources.
     */
    public static final String SQL_DATE_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.sql.date";

    /**
     * The key to search default format string for java.sql.Time in
     * resources.
     */
    public static final String SQL_TIME_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.sql.time";

    /**
     * The key to search default format string for java.util.Date in
     * resources.
     */
    public static final String DATE_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.date";

    /**
     * The key to search default format string for int (byte, short, etc.) in
     * resources.
     */
    public static final String INT_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.int";

    /**
     * The key to search default format string for float (double, BigDecimal)
     * in resources.
     */
    public static final String FLOAT_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.float";

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources(
            "org.apache.struts.taglib.bean.LocalStrings");

    // ------------------------------------------------------------- Properties

    /**
     * Name of the bean that contains the data we will be rendering.
     */
    protected String name = null;

    /**
     * Name of the property to be accessed on the specified bean.
     */
    protected String property = null;

    /**
     * The scope to be searched to retrieve the specified bean.
     */
    protected String scope = null;

    /**
     * The format string to be used as format to convert value to String.
     */
    protected String formatStr = null;

    /**
     * The key to search format string in applciation resources
     */
    protected String formatKey = null;

    /**
     * The session scope key under which our Locale is stored.
     */
    protected String localeKey = null;

    /**
     * The servlet context attribute key for our resources.
     */
    protected String bundle = null;
    
    /** the time string, if input an static time, e.g: 2013.05.04 11:10:00 **/
    protected String value = null;
    
    /** whether show timezone, e.g: 2013.05.04 11:10:00 GMT+08:00 **/
    protected boolean showtimezone = false;
    
    /** auto check whether format string has year, if not, compare the date value, 
     * if the date value is this year, then don't add year prefix, if it is not this year, then add year prefix **/
    protected boolean autoaddyear = true;
        

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return (this.property);
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getScope() {
        return (this.scope);
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getFormat() {
        return (this.formatStr);
    }

    public void setFormat(String formatStr) {
        this.formatStr = formatStr;
    }

    public String getFormatKey() {
        return (this.formatKey);
    }

    public void setFormatKey(String formatKey) {
        this.formatKey = formatKey;
    }

    public String getLocale() {
        return (this.localeKey);
    }

    public void setLocale(String localeKey) {
        this.localeKey = localeKey;
    }

    public String getBundle() {
        return (this.bundle);
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Process the start tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
    	Object dateObj = null;
    	if(value!=null && value.trim().length()>0){
    		dateObj = DateUtil.parseStrToDate(value, formatStr);
    	}else {
            // Look up the requested property value
            dateObj = TagUtils.getInstance().lookup(pageContext, name, property, scope);	
    	}
        
        // it is not good to involve the class I18nLogicImpl and ConstantLogicImpl
        UserinfoSession userinfo = BaseActionBase.getLoginInfo(true);
        String timezone = userinfo.getTimezoneNum();
        Locale locale = userinfo.getLocale();
        if(dateObj!=null){
        	if(dateObj instanceof Date){
        		Date date = (Date)dateObj;
        		dateObj = DateUtil.getDateFromGMT(date, timezone);
        	}else {
        		throw new JspException("the value is not the time");
        	}
        	
        }
        
        if (dateObj == null) {
            return (SKIP_BODY); // Nothing to output
        }

        // Convert value to the String with some formatting
        String output = formatValue(dateObj, locale);
        if(showtimezone){
        	output += " "+timezone;
        }
        // Print this property value to our output writer, suitably filtered
        TagUtils.getInstance().write(pageContext, output);
        
        // Continue processing this page
        return (SKIP_BODY);
    }

    /**
     * Retrieve format string from message bundle and return null if message
     * not found or message string.
     *
     * @param formatKey value to use as key to search message in bundle
     * @throws JspException if a JSP exception has occurred
     */
    protected String retrieveFormatString(String formatKey)
        throws JspException {
        String result =
            TagUtils.getInstance().message(pageContext, this.bundle,
                this.localeKey, formatKey);

        if ((result != null)
            && !(result.startsWith("???") && result.endsWith("???"))) {
            return result;
        } else {
            return null;
        }
    }

    /**
     * Format value according to specified format string (as tag attribute or
     * as string from message resources) or to current user locale.
     *
     * When a format string is retrieved from the message resources,
     * <code>applyLocalizedPattern</code> is used. For more about localized
     * patterns, see
     * <http://www.dei.unipd.it/corsi/fi2ae-docs/source/jdk1.1.7/src/java/text/resources/>.
     * (To obtain the correct value for some characters, you may need to view
     * the file in a hex editor and then use the Unicode escape form in the
     * property resources file.)
     *
     * @param valueToFormat value to process and convert to String
     * @throws JspException if a JSP exception has occurred
     */
    protected String formatValue(Object valueToFormat, Locale locale)
        throws JspException {
        Format format = null;
        Object value = valueToFormat;
        if(locale==null) {
        	locale = TagUtils.getInstance().getUserLocale(pageContext, this.localeKey);
        }
        String formatString = formatStr;

        // Try to retrieve format string from resources by the key from
        // formatKey.
        if ((formatString == null) && (formatKey != null)) {
            formatString = retrieveFormatString(this.formatKey);
        }

        // Prepare format object for numeric values.
        if (formatString == null) {
            if (value instanceof java.sql.Timestamp) {
                formatString =
                    retrieveFormatString(SQL_TIMESTAMP_FORMAT_KEY);
            } else if (value instanceof java.sql.Date) {
                formatString =
                    retrieveFormatString(SQL_DATE_FORMAT_KEY);
            } else if (value instanceof java.sql.Time) {
                formatString =
                    retrieveFormatString(SQL_TIME_FORMAT_KEY);
            } else if (value instanceof java.util.Date) {
                formatString = retrieveFormatString(DATE_FORMAT_KEY);
            }
        }else {
        	if(autoaddyear && formatString.indexOf("yyyy")==-1){
        		if((value instanceof java.sql.Date)||(value instanceof java.util.Date)){
        			GregorianCalendar gc1 = new GregorianCalendar();
        			gc1.setTime(DateUtil.getInstance().getNowtime_GLNZ());
        			
        			GregorianCalendar gc2 = new GregorianCalendar();
        			gc2.setTime((java.util.Date)value);
        			
        			if(gc1.get(Calendar.YEAR)!=gc2.get(Calendar.YEAR)){
        				if(formatString.indexOf("-")!=-1) {
        					formatString = "yyyy-"+formatString;
        				}else if(formatString.indexOf("/")!=-1){
        					formatString = "yyyy/"+formatString;
        				}
        			}
        		}
        	}
        }

        if (formatString != null) {
            format = new SimpleDateFormat(formatString, locale);
        }
        
        if (format != null) {
            return format.format(value);
        } else {
            return value.toString();
        }
    }

    /**
     * Release all allocated resources.
     */
    public void release() {
        super.release();
        name = null;
        property = null;
        scope = null;
        formatStr = null;
        formatKey = null;
        localeKey = null;
        bundle = null;
        value = null;
        showtimezone = false;
    }

	public boolean isShowtimezone() {
		return showtimezone;
	}

	public void setShowtimezone(boolean showtimezone) {
		this.showtimezone = showtimezone;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

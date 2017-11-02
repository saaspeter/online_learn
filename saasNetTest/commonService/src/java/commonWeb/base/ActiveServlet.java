package commonWeb.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;

import javax.servlet.ServletException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimeConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.struts.action.ActionServlet;

import commonTool.util.DateConverter;

public class ActiveServlet extends ActionServlet {

	public void init() throws ServletException {
        super.init();
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
        ConvertUtils.register(new BigIntegerConverter(null), BigInteger.class);
        ConvertUtils.register(new FloatConverter(null), Float.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new ShortConverter(null), Short.class);
        ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
        ConvertUtils.register(new SqlTimeConverter(null), Time.class);
        ConvertUtils.register(new DateConverter(), java.util.Date.class);
        ConvertUtils.register(new SqlTimestampConverter(null), Timestamp.class);
    }
	
    public void destroy() {
        ConvertUtils.deregister();
    }
	
}

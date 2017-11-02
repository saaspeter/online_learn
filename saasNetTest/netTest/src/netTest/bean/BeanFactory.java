
package netTest.bean;

import org.springframework.context.ApplicationContext;
import commonTool.bean.ApplicationContextProvider;

public class BeanFactory {
	
//	private static final String[] xmlFiles = new String[]{"/netTest/bean/applicationContext.xml"};
//	
//	private static ApplicationContext instance = new ClassPathXmlApplicationContext(xmlFiles);
//	
//	public static ApplicationContext getBeanFactory()
//	{
//		return  instance;
//	}
	
	public static ApplicationContext getBeanFactory()
	{
		return  ApplicationContextProvider.getApplicationContext();
	}
	
}

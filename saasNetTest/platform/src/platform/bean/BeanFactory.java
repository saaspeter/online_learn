package platform.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import commonTool.bean.ApplicationContextProvider;


public class BeanFactory {
	
//	private static final String[] xmlFiles = new String[]{"/platform/bean/applicationContext_initdata.xml"};
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
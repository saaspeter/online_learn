package commonWeb.security.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import commonTool.bean.ApplicationContextProvider;

public class BeanFactory {
	
//	 为了security初始化数据的程序定义的bean, 导入resource数据时使用。
//	public static ApplicationContext getBeanFactory()
//	{
//	    String[] xmlFiles = new String[]{"/commonWeb/security/bean/applicationContext_ResourceTemp.xml"};
//		ApplicationContext instance = new ClassPathXmlApplicationContext(xmlFiles);
//		return  instance;
//	}
	
	//正式的web应该用下面的方法
	public static ApplicationContext getBeanFactory()
	{
		return  ApplicationContextProvider.getApplicationContext();
	}
	
}
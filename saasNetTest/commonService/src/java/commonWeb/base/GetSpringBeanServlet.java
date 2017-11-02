package commonWeb.base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import commonTool.bean.WebSpringBeanFactory;

public class GetSpringBeanServlet extends HttpServlet
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7480827740324627735L;

	public void init() throws ServletException
    {
    	WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        WebSpringBeanFactory.getInstance().setContext(context);
    }
    
}

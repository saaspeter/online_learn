/*
 * DisplayMenuTag.java
 *
 * Created on February 14, 2001, 11:44 AM
 */
package commonWeb.ui.taglib.menu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import net.sf.navigator.displayer.MenuDisplayer;
import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;
import commonTool.util.ObjUtil;


/**
 * <p>The major behavior of this tag is to set the value for the usage in the
 * HTML hyperlink 'href' attribute. The value is determined by the following
 * attributes defined in menu-config.xml, in this prioritize order:
 * 'location', 'page', 'forward', 'action'.
 *
 * <p>You can now define a 'forward' or  'action' attribute in the
 * &lt;Item&gt; element in your menu-config.xml. The 'action' attribute takes
 * the value of a Logical Struts Action name for which to look up the
 * context-relative URI. The resultant URI will carry the Context
 * Path (if any), Module Prefix (if any), Session ID (if any),
 * and Servlet Mapping (path mapping or extension mapping). Here is
 * an example:
 * <pre>
 * &lt;Menu name="indexMenuMore" title="More Examples"&gt;
 *   &lt;Item name="actionExample"  title="Example - 'action' attribute"
 *         <B>action="/menu/bullet"</B>/&gt;
 *   &lt;Item name="pageExample" title="Example - 'page' attribute"
 *         <B>page="/bulletmenu.jsp"</B>/&gt;
 * &lt;/Menu&gt;
 * </pre>
 * 修改文件 net.sf.navigator.taglib.DisplayMenuTag.java ，其中新增方法displayMenu，修改方法doStartTag，
 * 主要是如果name属性为null，则轮流显示repository中的所有最顶层菜单。  -- 2008-08-23，by peter.fan
 * @author ssayles, mraible;;;;modified by peter.fan 2008.08.23
 * @version $Revision: 1.23 $ $Date: 2006/10/22 07:16:39 $
 */
public class DisplayMenuTag extends TagSupport {
    //~ Instance fields ========================================================

    protected final static Log log = LogFactory.getLog(DisplayMenuTag.class);
    private String name;
    private String target;

    //~ Methods ================================================================
    public void setName(String name) {
        this.name = name;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int doStartTag() throws JspException {
        MenuDisplayer displayer =
                (MenuDisplayer) pageContext.getAttribute(UseMenuDisplayerTag.DISPLAYER_KEY);

        if (displayer == null) {
            throw new JspException("Could not retrieve the menu displayer.");
        }

        // This is set by the parent tag - UseMenuDisplayerTag
        MenuRepository repository =
                (MenuRepository) pageContext.getAttribute(UseMenuDisplayerTag.PRIVATE_REPOSITORY);

        if (repository == null) {
            throw new JspException("Could not obtain the menu repository");
        }

        MenuComponent menu = null;
        if(this.name!=null&&this.name.trim().length()>0){
           menu = repository.getMenu(this.name);
           this.displayMenu(menu, displayer);  
        }else{
        	// if the name property is empty,then display the top menus of the repository
        	List topList = repository.getTopMenus();
        	for(int i=0;i<topList.size();i++){
        		menu = (MenuComponent)topList.get(i);
        		this.displayMenu(menu, displayer); 
        	}
        }    
        // 逼不得已，只有在这里调用显示结束的标签例如</ul>等.晕啊，接口没有扩展性，只有通过反射调用
        try {
			ObjUtil.invokeMethod(displayer, "endDisplay", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return SKIP_BODY;
    }

    /**
     * display MenuComponent
     * @param menu
     * @param displayer
     * @throws JspException
     */
    private void displayMenu(MenuComponent menu,MenuDisplayer displayer) throws JspException{
        if (menu != null) {
            try {
                // use the overridden target
                if (target != null) {
                    displayer.setTarget(this.target);
                }

                // set the location value to use
                // the context relative page attribute
                // if specified in the menu
                try {
                    setPageLocation(menu);
                } catch (MalformedURLException m) {
                    log.error("Incorrect action or forward: " + m.getMessage(), m);
                    log.warn("Menu '" + menu.getName() + "' location set to #");
                    menu.setLocation("#");
                } 

                displayer.display(menu);
                displayer.setTarget(null);
            } catch (Exception e) {
                // don't swallow the exception
                e.printStackTrace();
                throw new JspException(e);
            }
        } else {
            String error = UseMenuDisplayerTag.messages.getString("menu.not.found") +
                    " " + this.name;
            log.warn(error);
            try {
                pageContext.getOut().write(error);
            } catch (IOException io) {
                throw new JspException(error);
            }
        }
    }
    
    /**
     * Sets the value for the menu location to the
     * appropriate value if location is null.  If location
     * is null, and the page attribute exists, it's value
     * will be set to the the value for page prepended with
     * the context path of the application.
     *
     * If the page is null, and the forward attribute exists,
     * it's value will be looked up in struts-config.xml.
     *
     *                                     FIXME - ssayles - 121102
     * Ideally, this should happen at menu initialization but
     * I was unable to find a reliable way to get the context path
     * outside of a request.  The performance impact is probably
     * negligable, but it would be better to check for this only once.
     *
     * @param menu The menu component to set the location for.
     */
    protected void setPageLocation(MenuComponent menu) throws MalformedURLException, JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        setLocation(menu);
        String url = menu.getLocation();

        // Check if there are parameters on the value
        if ((url != null) && (url.indexOf("${") > -1)) {
            String queryString = null;

            if (url.indexOf("?") > -1) {
                queryString = url.substring(url.indexOf("?") + 1);
                url = url.substring(0, url.indexOf(queryString));
            }

            // variable is in the URL
            if (queryString != null) {
                menu.setUrl(url + parseString(queryString, request));
            } else {
                // parse the URL, rather than the queryString
                menu.setUrl(parseString(url, request).toString());
            }
        } else {
            menu.setUrl(url);
        }

        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        if (menu.getUrl() != null) {
            menu.setUrl(response.encodeURL(menu.getUrl()));
        }

        // do all contained menus
        MenuComponent[] subMenus = menu.getMenuComponents();

        if (subMenus.length > 0) {
            for (int i = 0; i < subMenus.length; i++) {
                this.setPageLocation(subMenus[i]);
            }
        }
    }

    protected void setLocation(MenuComponent menu) throws MalformedURLException {
        // if the location attribute is null, then set it with a context relative page
        // attribute if it exists
        if (menu.getLocation() == null) {
            try {
                if (menu.getPage() != null) {
                    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
                    menu.setLocation(request.getContextPath() + getPage(menu.getPage()));
                    HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
                    menu.setLocation(response.encodeURL(menu.getLocation()));
                } else if (menu.getForward() != null) {
                    menu.setLocation(TagUtils.getInstance().computeURL(pageContext, menu.getForward(),
                                        null, null, null, menu.getModule(), null, null, false));
                } else if (menu.getAction() != null) {
                    // generate Struts Action URL,
                    // this will append Context Path (if any),
                    // Servlet Mapping (path mapping or extension mapping)
                    // Module Prefix (if any) & Session ID (if any)
                    menu.setLocation(TagUtils.getInstance().computeURL(pageContext, null, null, null,
                                        menu.getAction(), menu.getModule(), null, null, false));
                }
            } catch (NoClassDefFoundError e) {
                if (menu.getForward() != null) {
                    throw new MalformedURLException("forward '" + menu.getForward() + "' invalid - no struts.jar");
                } else if (menu.getAction() != null) {
                    throw new MalformedURLException("action '" + menu.getAction() + "' invalid - no struts.jar");
                }
            }
        }
    }

    /**
     * Returns the value with page prepended with a "/"
     * if it is not already.
     *
     * @param page The value for the page.
     */
    protected String getPage(String page) {
        if (page.startsWith("/")) {
            return page;
        } else {
            page = "/" + page;
        }

        return page;
    }
    
    private StringBuffer parseString(String str, HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();

        while (str.indexOf("${") >= 0) {
            sb.append(str.substring(0, str.indexOf("${")));

            String variable = str.substring(str.indexOf("${") + 2, str.indexOf("}"));
            String value = String.valueOf(pageContext.findAttribute(variable)); 

            if (value == null) {
                // look for it as a request parameter
                value = request.getParameter(variable);
            }

            // is value still null?!
            if (value == null) {
                log.warn("Value for '" + variable +
                        "' not found in pageContext or as a request parameter");
            }

            sb.append(value);
            str = str.substring(str.indexOf("}") + 1, str.length());
        }

        return sb.append(str);
    }

    public void release() {
        this.name = null;
        this.target = null;
    }
}

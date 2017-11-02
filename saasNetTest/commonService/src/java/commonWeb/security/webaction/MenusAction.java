
package commonWeb.security.webaction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.constant.SysparamConstant;
import commonWeb.base.BaseAction;
import commonWeb.security.authentication.UserinfoSession;
import commonWeb.security.dao.MenusDao;
import commonWeb.security.logic.BOFactory;
import commonWeb.security.vo.Menus;

/** 
 * @deprecated
 */
public class MenusAction extends BaseAction {
	
	static Logger log = Logger.getLogger(MenusAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		//BaseForm theForm = (BaseForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("leftMenu".equals(myaction)) {
			myforward = leftMenu(mapping, actionform, request,response);
		} else if ("topMenu".equals(myaction)) {
			myforward = topMenu(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method leftMenu
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward leftMenu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		UserinfoSession loginInfo = getLoginInfo();
		Long userid = loginInfo.getUserid();
		Long shopid = loginInfo.getShopid();
		try {
            this.doMenu(request, userid,shopid);			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("----error in Class:MenusAction Method:leftMenu", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.list");
		}
		
		return mapping.findForward("leftMenu");
	}
	
	/** 
	 * Method leftMenu
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward topMenu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		UserinfoSession loginInfo = getLoginInfo();
		Long userid = null;
		userid = loginInfo.getUserid();
		Long shopid = loginInfo.getShopid();
		try {
            this.doMenu(request, userid,shopid);			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("----error in Class:MenusAction Method:topMenu", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.list");
		}
		
		return mapping.findForward("topMenu");
	}
	
	
	private void doMenu(HttpServletRequest request,Long userid,Long shopid){
		MenuRepository repository = new MenuRepository();
 		String syscode = SysparamConstant.syscode;
//        MenuRepository defaultRepository = 
//        	(MenuRepository)this.getServlet().getServletContext().getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
//        repository.setDisplayers(defaultRepository.getDisplayers());
		
		MenusDao dao = BOFactory.getMenusDao();
		List list = dao.selectForDisplay(syscode, null, userid, null, shopid);
		
		for (int i=0; i < list.size(); i++) {
			Menus vo = (Menus)list.get(i);
            MenuComponent mc = new MenuComponent();
            //String name = (String) vo.getName();
            mc.setName(String.valueOf(vo.getId()));
            Long upId = vo.getPid();
            
            if (upId!=null&&upId.longValue()!=-1) {
                MenuComponent parentMenu = repository.getMenu(String.valueOf(upId));
                if (parentMenu == null) {
                    System.out.println("parentMenu '" + upId + "' doesn't exist!");
                    // create a temporary parentMenu
                    parentMenu = new MenuComponent();
                    parentMenu.setName(String.valueOf(upId));
                    repository.addMenu(parentMenu);
                }

                mc.setParent(parentMenu);
            }
            
            mc.setTitle(vo.getTitle());
            mc.setAction(vo.getAction());
            //mc.setLocation(vo.getResString());
            mc.setAltImage(vo.getAltimage());
            mc.setForward(vo.getForward());
            mc.setHeight(vo.getHeight());
            mc.setImage(vo.getImage());
            mc.setOnclick(vo.getOnclick());
            mc.setOndblclick("dbloclickTest");
            mc.setPage(vo.getPage());
            mc.setTarget(vo.getTarget());
            mc.setToolTip(vo.getTooltip());
            mc.setUrl(vo.getResString());
            mc.setWidth(vo.getWidth());
            repository.addMenu(mc);
        }
	
		request.setAttribute("repository", repository);
	}
	
	
}

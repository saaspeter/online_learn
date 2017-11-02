package netTestWeb.social.action;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netTest.bean.BOFactory;
import netTest.prodcont.vo.Contentcate;
import netTest.product.constant.UserproductConstant;
import netTest.product.dao.impl.ProductbaseDaoImpl;
import netTest.product.vo.Learnactivity;
import netTest.product.vo.Productbase;
import netTest.util.ResourceBundleUtil;
import netTestWeb.base.BaseAction;
import netTestWeb.base.BaseForm;
import netTestWeb.social.form.ProductcommentsForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logicImpl.ShopLogicImpl;
import platform.vo.ShopMini;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.util.AssertUtil;

 
public class CommentsAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("listprodcommentsuser".equals(myaction)) {
			request.setAttribute("listtype", "user");
			myforward = listProdComments(mapping, actionform, request,response);
		}else if ("listprodcommentsmag".equals(myaction)) {
			request.setAttribute("listtype", "mag");
			myforward = listProdComments(mapping, actionform, request,response);
		} else {
			myforward = super.execute(mapping, actionform, request, response);
		}
		return myforward;
	}
	
	private ActionForward listProdComments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ProductcommentsForm theForm = (ProductcommentsForm) form;
		Long productid = theForm.getProductid();
		AssertUtil.assertNotNull(productid, null);
		Long userid = getLoginInfo(true).getUserid();
		String contentcatename = request.getParameter("contentcatename");
	    String contentcateidStr = request.getParameter("contentcateid");
	    Long contentcateid = null;
	    if(contentcateidStr!=null && contentcateidStr.trim().length()>0){
	    	contentcateid = Long.parseLong(contentcateidStr.trim());
	    }
	    Productbase prodVO = ProductbaseDaoImpl.getInstance().selectByPK(productid);
	    theForm.setProductvo(prodVO);
	    
		String listtype = (String)request.getAttribute("listtype");
		// go url
		String url = "";
		if("user".equals(listtype)){
			url = "listproduct_user";
		}else if("mag".equals(listtype)){
			url = "listproduct_mag";
		}

	    // 设置最后一次学习该课程的章节目录
	    if(userid!=null && contentcateid==null){
	    	Learnactivity activityVO = BOFactory.getLearnactivityDao().selectLearnLastest(userid, productid);
	        if(activityVO!=null){
	    	   request.setAttribute("contentcateid", activityVO.getContentcateid());
	        }
	    }
	    if(contentcateid!=null && (contentcatename==null||contentcatename.trim().length()<1)){
	    	Contentcate catevo = BOFactory.getContentcateDao().selectByPK(contentcateid);
	    	if(catevo!=null){
	    		request.setAttribute("contentcatename", catevo.getContentcatename());
	    	}
	    }
		
		return mapping.findForward(url);
	}
	
	/** 
	 * 暂时没有learncontent的comments，所以暂时不用这个方法
	 */
//	private ActionForward listcomments(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception 
//	{
//		CommentsForm theForm = (CommentsForm) form;
//		CommentsQuery queryVO = theForm.getQueryVO();
//		
//		if(Learncontent.ObjectType.equals(queryVO.getObjecttype())
//			&& (queryVO.getObjectid()==null||queryVO.getObjectid().trim().length()<1)
//			&& queryVO.getObjectparentid()!=null && queryVO.getObjectparentid().trim().length()>0){
//			String contidStr = BOFactory.getLearncontentDao().selectIdsByProdId(new Long(queryVO.getObjectparentid()));
//			queryVO.setObjectidStr(contidStr);
//		}
//		
//		CommentsLogic logic = CommentsLogicImpl.getInstance();
//		if((queryVO.getObjectid()!=null||queryVO.getObjectidStr()!=null)&&queryVO.getObjecttype()!=null){
//			List list = logic.qryComments(queryVO);
//			theForm.setCommentList(list);
//		}
//
//		String url = "list";
//		return mapping.findForward(url);
//	}
	
}


package netTestWeb.product.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.product.dao.UserprodstatuslogDao;
import netTest.product.dto.UserprodstatuslogQuery;
import netTest.product.vo.Userprodstatuslog;
import netTestWeb.base.BaseAction;
import netTestWeb.product.form.UserprodstatuslogForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.util.AssertUtil;

import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;


public class UserprodstatuslogAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UserprodstatuslogAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		//UserproductForm theForm = (UserproductForm) actionform;
		if ("".equals(myaction)) { 
			myforward = mapping.findForward("failure");
		} else if ("listuserprodstatuslog".equals(myaction)) {
			myforward = listUserprodStatusLog(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * list出所有的用户产品状态改变日志
	 */
	public ActionForward listUserprodStatusLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserprodstatuslogForm theForm = (UserprodstatuslogForm) form;
		//Long shopid = getLoginInfo().getShopid();
		Long shopid = null;
		UserprodstatuslogQuery queryVO = theForm.getQueryVO();
		//queryVO.setShopid(shopid);
		if(queryVO.getUserproductid()==null){
		   AssertUtil.assertNotNull(queryVO.getUserid(), null);
		}
		AssertUtil.assertNotNull(queryVO.getUserproductid(), null);
		queryVO.setOrder_By_Clause("happendate desc");
		UserprodstatuslogDao dao = BOFactory.getUserprodstatuslogDao();
		List<Userprodstatuslog> list = (List<Userprodstatuslog>)dao.selectByVO(queryVO);
		// 设置操作者信息
		if(list!=null && list.size()>0){
			List<Long> useridList = new ArrayList<Long>();
			for(Userprodstatuslog vo : list){
				useridList.add(vo.getOpertorid());
				if(shopid==null){
					shopid = vo.getShopid();
				}
			}
			UsershopLogic usershoplogic = BOFactory_Platform.getUsershopLogic();
			Map<Long,String> nameMap = usershoplogic.qryUsernameByIdList(shopid, useridList);
			for(Userprodstatuslog vo1 : list){
				vo1.setOpertorDisplayname(nameMap.get(vo1.getOpertorid()));
			}
		}
		
		theForm.setDatalist(list);
		return mapping.findForward("listpage");
	}

}


package netTestWeb.exercise.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.exception.ExceptionConstant;
import netTest.exercise.dao.ExerciseDao;
import netTest.exercise.dao.ExeruserDao;
import netTest.exercise.dto.ExeruserQuery;
import netTest.exercise.vo.Exercise;
import netTest.exercise.vo.Exeruser;
import netTestWeb.base.BaseAction;
import netTestWeb.exercise.form.ExeruserForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logic.UsershopLogic;
import platform.logicImpl.UsershopLogicImpl;

import commonTool.base.Page;
import commonTool.util.AssertUtil;

public class ExeruserAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ExeruserAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
//		ExeruserForm theForm = (ExeruserForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listExeruser".equals(myaction)) {
			myforward = listExeruser(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * 查询出某一次练习的所有学员
	 */
	public ActionForward listExeruser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ExeruserForm theForm = (ExeruserForm) form;
		ExeruserQuery queryVO = theForm.getQueryVO();
		AssertUtil.assertNotNull(queryVO.getExerid(), ExceptionConstant.Error_Need_Paramter);
		
		ExerciseDao exerDao = BOFactory.getExerciseDao();
		ExeruserDao exeruserDao = BOFactory.getExeruserDao();
		Exercise exerVO = exerDao.selectByPK(queryVO.getExerid());
		AssertUtil.assertNotNull(exerVO, null);
		theForm.setExerVO(exerVO);
		
		Page page = exeruserDao.selectExerUser(queryVO, getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
		// 循环page中的exeruser，然后查询这些user的usershop值，设置loginname等用户信息
		if(page!=null&&page.getList()!=null&&page.getList().size()>0){
			UsershopLogic usershopLogic = UsershopLogicImpl.getInstance();
			List pageList = page.getList();
			List<Long> userList = new ArrayList<Long>();
			Exeruser voTemp = null;
			for(int i=0;i<pageList.size();i++){
				voTemp = (Exeruser)pageList.get(i);
				userList.add(voTemp.getUserid());
			}
			Map<Long,String> userMap = usershopLogic.qryUsernameByIdList(exerVO.getShopid(), userList);
			if(userMap!=null){
				String displayname = null;
				for(int i=0;i<pageList.size();i++){
					voTemp = (Exeruser)pageList.get(i);
					displayname = userMap.get(voTemp.getUserid());
					if(displayname!=null){
					   voTemp.setDisplayname(displayname);
					}
				}
			}
		}
		
		this.setPage(request, page);
		
		return mapping.findForward("list");
	}
	
	/**
	 * 学员查看练习，需要查询出学员对练习的状态
	 */
//	public ActionForward listUserExer(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		ExeruserForm theForm = (ExeruserForm) form;
//		ExeruserQuery queryVO = theForm.getQueryVO();
//		Long shopid = theForm.getShopid();
//		Long productid = theForm.getProductbaseid();
//		Long userid = getLoginInfo().getUserid();
//		queryVO.setUserid(userid);
//		
//        // 处理切换用户的商店
//		switchShop(shopid,queryVO,false);
//		// 处理切换用户产品
//		switchProduct(productid,queryVO,UserproductConstant.ProdUseType_userNormal);
//		if(queryVO.getProductbaseid()!=null){
//			// 初始化上次学习的课程章节
//			if(queryVO.getContentcateid()==null){
//			   String loadlastlearn = request.getParameter("loadlastlearn");
//			   if("1".equals(loadlastlearn)){
//				   Learnactivity activityVO2 = BOFactory.getLearnactivityDao().selectLearnLastest(userid, queryVO.getProductbaseid());
//			       if(activityVO2!=null&&activityVO2.getContentcateid()!=null){
//			    	  queryVO.setContentcateid(activityVO2.getContentcateid());
//			       }
//			   }
//			   // 如果是第一次学习，则取第一个目录作为学习内容
//			   if(queryVO.getContentcateid()==null) {
//				   List<Contentcate> list = BOFactory.getContentcateDao().getAllInOne(productid);
//				   if(list!=null && list.size()>0){
//					  queryVO.setContentcateid(list.get(0).getContentcateid());
//				   }
//			   }
//			}else if(CommonConstant.TreeTopnodePid.equals(queryVO.getContentcateid())){
//			   queryVO.setContentcateid(null);
//		    }
//			
//			ExeruserDao dao = BOFactory.getExeruserDao();
//			List<Exeruser> datalist = dao.selectMyExerPage(queryVO);
//            
//			// 设置所属章节名
//			if(datalist!=null&&datalist.size()>0){
//				Exeruser voTemp = null;
//				List<Long> pkList = new ArrayList<Long>();
//				for(int i=0;i<datalist.size();i++){
//					voTemp = (Exeruser)datalist.get(i);
//					if(voTemp.getContentcateid()!=null){
//						pkList.add(voTemp.getContentcateid());
//					}
//					// 设置默认用户练习状态
//					if(voTemp.getStatus()==null){
//						voTemp.setStatus(TestuserConstant.Status_unStart);
//					}
//				}
//				if(pkList.size()>0){
//					ContentcateLogic cateLogic = BOFactory.getContentcateLogic();
//					Map<Long,Contentcate> map = cateLogic.selectByPKList(pkList);
//					Contentcate catevoTemp = null;
//					for(int i=0;i<datalist.size();i++){
//						voTemp = (Exeruser)datalist.get(i);
//						catevoTemp = map.get(voTemp.getContentcateid());
//						if(catevoTemp!=null){
//							voTemp.setContentcatename(catevoTemp.getContentcatename());
//						}
//					}
//				}
//				
//				theForm.setDatalist(datalist);
//			}
//			
//		}
//		
//		return mapping.findForward("list");
//	}
	
//	/** 
//	 * 查看用户某个练习的答题情况，主要用于ajax call
//	 */
//	private ActionForward viewoneexeruser(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		ExeruserForm theForm = (ExeruserForm) form;
//		Exeruser vo = theForm.getVo();
//		if(vo.getExerid()==null){
//			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
//		}
//		LoginInfoEdu loginfo = getLoginInfo();
//		vo = BOFactory.getExeruserDao().selectByLogicPK(vo.getExerid(), loginfo.getUserid());
//		
//		String result = String.valueOf(CommonConstant.success);
//		String info = null;
//		if(vo!=null){
//			if(vo.getStarttime()!=null){
//				info = DateUtil.printDate(vo.getStarttime(), DateUtil.DATE_TIME_FORMAT);
//			}else if(vo.getEndtime()!=null){
//				info = DateUtil.printDate(vo.getEndtime(), DateUtil.DATE_TIME_FORMAT);
//			}
//		}
//		this.writeAjaxRtn(result, info, null, response);
//		return null;
//	}
	
}

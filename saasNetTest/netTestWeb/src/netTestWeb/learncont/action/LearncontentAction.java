package netTestWeb.learncont.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exception.ExceptionConstant;
import netTest.exercise.vo.Exercise;
import netTest.learncont.constant.LearncontentConstant;
import netTest.learncont.dao.LearncontentDao;
import netTest.learncont.dto.LearncontentQuery;
import netTest.learncont.logic.LearncontentLogic;
import netTest.learncont.vo.Learncontent;
import netTest.prodcont.logic.ContentcateLogic;
import netTest.prodcont.vo.Contentcate;
import netTest.product.constant.UserproductConstant;
import netTest.product.logic.impl.ProductLogicImpl;
import netTest.product.vo.Learnactivity;
import netTest.product.vo.Productbase;
import netTest.util.QiniuFileUtil;
import netTest.util.UploadFileUtilNettest;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.learncont.form.LearncontentForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logic.ShopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.social.constant.SocialoathtokenConstant;
import platform.social.logic.impl.SocialoathtokenLogicImpl;
import platform.vo.Shop;

import commonTool.base.BaseEmptyEntity;
import commonTool.constant.CommonConstant;
import commonTool.exception.HasReferenceException;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;
import commonWeb.security.authentication.UserinfoSession;

public class LearncontentAction extends BaseAction {
	
	static Logger log = Logger.getLogger(LearncontentAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		LearncontentForm theForm = (LearncontentForm) actionform;
		
		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listLearncontent".equals(myaction)) {
			myforward = listmag(mapping, actionform, request,response);
		} else if ("selfLearncontent".equals(myaction)) {
			myforward = listuse(mapping, actionform, request,response);
		} else if ("listcontentsidebar".equals(myaction)) {
			theForm.setListType(3);
			myforward = listuse(mapping, actionform, request,response);
		} else if ("downloadlearnfile".equals(myaction)) {
			theForm.setListType(4);
			myforward = listuse(mapping, actionform, request,response);
		} else if ("listtryprodcont".equals(myaction)) {
			myforward = listtry(mapping, actionform, request,response);
		} else if ("saveLearncontent".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editLearncontent".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewLearncontent".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addLearncontent".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteLearncontent".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else if ("markfinishlearn".equals(myaction)) {
			myforward = markFinishLearn(mapping, actionform, request,response);
		} else if ("autolearn".equals(myaction)) {
			myforward = autolearn(mapping, actionform, request,response);
		} else if("openuploadpage".equals(myaction)) {
			myforward = openUploadFilePage(mapping, actionform, request, response);
		} else if("afteruploadfilecb".equals(myaction)) {
			myforward = afterUploadFile(mapping, actionform, request, response);
		} else if("deleteexistfile".equals(myaction)) {
			myforward = deleteExistFile(mapping, actionform, request, response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;  
	}
	
	/** 
	 * 列出学习资料
	 * 用户学习使用 或 管理员管理课程使用
	 */
	private ActionForward listmag(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long userid = getLoginInfo().getUserid();
		LearncontentForm theForm = (LearncontentForm) form;
		Long productid = theForm.getProductbaseid();
		LearncontentQuery queryVO = theForm.getQueryVO();
		// set product vo
		Productbase productvo = BOFactory.getProductbaseDao().selectByPK(productid);
		theForm.setProductvo(productvo);
		AssertUtil.assertNotNull(productid, null);
		
		String retPage = "list";
		// 选择了课程才查询
		queryVO.setProductbaseid(productid);
		Long contcateid = queryVO.getContentcateid();
		if(productid!=null){
			// 查询加载课程目录及学些内容
			Object[] rtnArr = loadCateLearncontent(productid, contcateid, userid);
			List<Contentcate> catelist = (List<Contentcate>)rtnArr[0];
			Contentcate topcatevo = (Contentcate)rtnArr[1];
			Contentcate useCatevo = (Contentcate)rtnArr[2];
			
			theForm.setCatelist(catelist);
			theForm.setTopcatevo(topcatevo);
			if(useCatevo!=null){
			   theForm.setUsecatevo(useCatevo);
			   queryVO.setContentcateid(useCatevo.getContentcateid());
			}
		}
		return mapping.findForward(retPage);
	}
	
	/** 
	 * 列出学习资料
	 * @security 用户学习使用 或 管理员管理课程使用
	 */
	private ActionForward listmag_nouse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long userid = getLoginInfo().getUserid();
		LearncontentForm theForm = (LearncontentForm) form;
		int listType = theForm.getListType();
		Long shopid = theForm.getShopid();
		Long productid = theForm.getProductbaseid();
		
		LearncontentQuery queryVO = theForm.getQueryVO();
		String retPage = "list";
		Short produsetype = UserproductConstant.ProdUseType_operatorMag;
		boolean loadauthority = true;
        // 处理切换用户的商店
		switchShop(shopid,queryVO,loadauthority);
		// 处理切换用户产品
		switchProduct(productid,queryVO,produsetype);
		
		List<Learncontent> datalist = null;
		// 选择了课程才查询
		if(queryVO.getProductbaseid()!=null){
			// 初始化上次学习的课程章节
//			if(queryVO.getContentcateid()==null){
//			   String loadlastlearn = request.getParameter("loadlastlearn");
//			   if("1".equals(loadlastlearn)){
//				   Learnactivity activityVO2 = BOFactory.getLearnactivityDao().selectLearnLastest(userid, queryVO.getProductbaseid());
//			       if(activityVO2!=null && activityVO2.getContentcateid()!=null){
//		    		   queryVO.setContentcateid(activityVO2.getContentcateid());
//			       }else {
//			    	   // 如果是第一次学习，则取第一个目录作为学习内容
//			    	   List<Contentcate> list = BOFactory.getContentcateDao().getAllInOne(productid);
//					   if(list!=null && list.size()>0){
//						  queryVO.setContentcateid(list.get(0).getContentcateid());
//					   }
//			       }
//			   }
//		    }else if(CommonConstant.TreeTopnodePid.equals(queryVO.getContentcateid())){
//			   queryVO.setContentcateid(null);
//		    }
			if(CommonConstant.TreeTopnodePid.equals(queryVO.getContentcateid())){
			   queryVO.setContentcateid(null);
		    }
			// 目前只有管理learncontent的时候才需要查询
			if(listType==1 && queryVO.getContentcateid()!=null){
				Contentcate catevo1 = BOFactory.getContentcateDao().selectByPK(queryVO.getContentcateid());
		  		queryVO.setContentcatename(catevo1.getContentcatename());
			}

			// list
			LearncontentDao dao = BOFactory.getLearncontentDao();
			datalist = dao.selectContents(queryVO.getProductbaseid(), queryVO.getContentcateid(), null);
			// 设置课程章节名和最后学习时间
			if(datalist!=null&&datalist.size()>0){
				Learncontent voTemp = null;
				List<Long> cateidList = new ArrayList<Long>();
				List<Long> contidList = new ArrayList<Long>();
				for(int i=0;i<datalist.size();i++){
					voTemp = (Learncontent)datalist.get(i);
					if(voTemp.getContentcateid()!=null){
						cateidList.add(voTemp.getContentcateid());
					}
					contidList.add(voTemp.getContentid());
				}
				ContentcateLogic cateLogic = BOFactory.getContentcateLogic();
				Map<Long,Contentcate> map = cateLogic.selectByPKList(cateidList);
				Contentcate catevoTemp = null;
				Map<String, Learnactivity> activityMap = BOFactory.getLearnactivityDao().selectUserLearnActivity(userid, 
						                                 queryVO.getProductbaseid(), Learncontent.ObjectType);
				Learnactivity activityVO = null;
				
				for(int i=0;i<datalist.size();i++){
					voTemp = (Learncontent)datalist.get(i);
					catevoTemp = map.get(voTemp.getContentcateid());
					if(catevoTemp!=null){
						voTemp.setContentcatename(catevoTemp.getContentcatename());
					}
					activityVO = activityMap.get(Learncontent.ObjectType+":"+voTemp.getContentid());
					if(activityVO!=null){
						voTemp.setLastaccesstime(activityVO.getStarttime());
					}
				}
			}
		}
		theForm.setDatalist(datalist);
		return mapping.findForward(retPage);
	}
	
	// 列出用户需要学习的课程目录和课程学习内容及练习
	private ActionForward listuse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long userid = getLoginInfo().getUserid();
		LearncontentForm theForm = (LearncontentForm) form;
		Long productid = theForm.getProductbaseid();
		LearncontentQuery queryVO = theForm.getQueryVO();
		Long contcateid = queryVO.getContentcateid();
		
		String retPage = "list_main";
		int listtype = theForm.getListType();
		if(listtype==3){
			// 用户学习的侧面side bar
			retPage = "listinside";
		}else if(listtype==4){
			// download file的界面
			retPage = "downloadpage";
		}else { // 用户自己的学习界面
			// 处理切换用户产品
			Short produsetype = UserproductConstant.ProdUseType_userNormal;
			switchProduct(productid,queryVO,produsetype);
			productid = queryVO.getProductbaseid();
		}
				
		// 选择了课程才查询
		if(productid!=null){
			// 加载用户上次学习的情况
			Learnactivity activityVO = BOFactory.getLearnactivityDao()
                    .selectLearnLastest(userid, productid);
			// if input contcateid is null, use last learn cate as current
			if(contcateid==null){
			   if(activityVO!=null && activityVO.getContentcateid()!=null){
			   	   contcateid = activityVO.getContentcateid();
			   }
			}
			// 查询加载课程目录及学些内容
			Object[] rtnArr = loadCateLearncontent(productid, contcateid, userid);
			List<Contentcate> catelist = (List<Contentcate>)rtnArr[0];
			Contentcate topcatevo = (Contentcate)rtnArr[1];
			Contentcate useCatevo = (Contentcate)rtnArr[2];
			
			theForm.setCatelist(catelist);
			theForm.setTopcatevo(topcatevo);
			theForm.setUsecatevo(useCatevo);
			// set last learn info into Form
			if(activityVO!=null && activityVO.getContentcateid()!=null){
				theForm.setLastcontentcateid(activityVO.getContentcateid());
				Contentcate lastcateVO = BOFactory.getContentcateLogic().getCateWithLearncont(catelist, activityVO.getContentcateid(), productid);
				theForm.setLastcontentcatename(lastcateVO.getContentcatename());
				theForm.setLastlearndate(activityVO.getStarttime());
			}
		}
		
		return mapping.findForward(retPage);
	}
	
	/**
	 * 
	 */
	private Object[] loadCateLearncontent(Long productid, Long contcateid, Long userid)
	{
		ContentcateLogic catelogic = BOFactory.getContentcateLogic();
		// 查询本课程的目录树
		List<Contentcate> catelist_return = catelogic.getProdCatetory(productid, true, true);
		
		// 查询仅属于课程本身，但不属于任何目录的学习内容和练习，并且虚拟出一个对象来
		Contentcate topcatevo_return = catelogic.getCateWithLearncont(catelist_return, CommonConstant.TreeTopnodePid, productid);
		
		// 设置当前选中的学习资料对应的目录
		Contentcate useCatevo_return = null;
	    // 如果没有选择课程目录，则把第一个目录作为要学习的目录
		if(contcateid==null){
			if(catelist_return!=null && catelist_return.size()>0){
				contcateid = catelist_return.get(0).getContentcateid();
			}
			useCatevo_return = catelogic.getCateWithLearncont(catelist_return, contcateid, productid);
		}else if(CommonConstant.TreeTopnodePid.equals(contcateid)){
			useCatevo_return = topcatevo_return;
	    }else {
	    	useCatevo_return = catelogic.getCateWithLearncont(catelist_return, contcateid, productid);
	    }
		
		// load last learn data
//		if(loadLastLearn){
//		    LearnactivityDao learnactivityDao = BOFactory.getLearnactivityDao();
//			Map<Long, Learnactivity> activityMap = BOFactory.getLearnactivityDao().selectByBatchID(userid, 
//                    queryVO.getProductbaseid(), contidList, Learncontent.ObjectType);
//			Learnactivity activityVO = null;
//			
//			for(int i=0;i<datalist.size();i++){
//			    voTemp = (Learncontent)datalist.get(i);
//			    catevoTemp = map.get(voTemp.getContentcateid());
//				if(catevoTemp!=null){
//				   voTemp.setContentcatename(catevoTemp.getContentcatename());
//				}
//				activityVO = activityMap.get(voTemp.getContentid());
//				if(activityVO!=null){
//				   voTemp.setLastaccesstime(activityVO.getStarttime());
//				}
//			}
//		}
		
		return new Object[]{catelist_return, topcatevo_return, useCatevo_return};
	}
	
	/**
	 * 自动学习按钮，自动计算用户该学习的章节和学习资料或练习
	 * 根据用户上次学习的章节内容记录来计算
	 * 或者用户选择某个章节来学习
	 * 如果用户直接点击了某个learncontent或exercise，则使用原来的学习链接，暂时不走该api
	 */
	private ActionForward autolearn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LearncontentForm theForm = (LearncontentForm) form;		
		LearncontentQuery queryVO = theForm.getQueryVO();
		Long productid = queryVO.getProductbaseid();
		Long contcateid = queryVO.getContentcateid();
		Long objectid = theForm.getObjectid();
		String objecttype = theForm.getObjecttype();		
		AssertUtil.assertNotNull(productid, null);
		Long userid = getLoginInfo().getUserid();
		
		// 如果objectid不为空，说明是在具体的exercise或者learncontent上点击"继续学习"
		// 如果objectid为空，并且contcateid不为空，说明是在目录上点击了"在本节学习"
		// 如果objectid为空，并且contcateid为空，说明用户直接点击了"继续上次学习"
		BaseEmptyEntity nextvo = null;
		LearncontentLogic logic = BOFactory.getLearncontentLogic();
		ContentcateLogic catelogic = BOFactory.getContentcateLogic();
		Learncontent learncontvo = null;
		Exercise exervo = null;
		boolean needLoadNextChapter = false;
		if(objectid!=null && objecttype!=null) {
			if(Learncontent.ObjectType.equals(objecttype)){
				// 如果用户在学习资料上点击"继续学习"，则需要send message将当前的学习资料设置成“已经学习”状态
				logic.sendLearnActivity(objectid, userid, 
			             Learnactivity.Learnstatus_Finished, Learnactivity.Actiontype_Learn);
				// get learn content category
				learncontvo = BOFactory.getLearncontentDao().selectPlainByPK(objectid);
				contcateid = learncontvo.getContentcateid();
			}else if(Exercise.ObjectType.equals(objecttype)){
				exervo = BOFactory.getExerciseDao().selectByPK(objectid);
				contcateid = exervo.getContentcateid();
			}
			
		}else if(contcateid==null){
			// 如果传入的contcateid是空，则查询用户最近的学习内容记录
			Learnactivity activityVO = BOFactory.getLearnactivityDao()
	                					 .selectLearnLastest(userid, productid);
			if(activityVO!=null && activityVO.getContentcateid()!=null){
			   contcateid = activityVO.getContentcateid();
			   objectid = activityVO.getObjectid();
			   objecttype = activityVO.getObjecttype();
			   if(!Learnactivity.Learnstatus_Finished.equals(activityVO.getLearnstatus())){
				   nextvo = new BaseEmptyEntity(objectid, objecttype);
			   }
			}
			needLoadNextChapter = true;
		}
		
		if(nextvo==null){
			List<Contentcate> catelist_return = catelogic.getProdCatetory(productid, true, true);
			Contentcate curcatevo = null;
			if(contcateid!=null){
			   curcatevo = catelogic.getCateWithLearncont(catelist_return, contcateid, productid);	
			   boolean needLoadFirst = !needLoadNextChapter;
			   nextvo = catelogic.getNextLearnInCurrentContcate(objectid, objecttype, contcateid,
					 					 		   productid, curcatevo, userid, needLoadFirst);
			}
			if(nextvo==null && needLoadNextChapter){
				for(Contentcate cate_tempvo : catelist_return){
					curcatevo = catelogic.getNextContentcateFromList(catelist_return, cate_tempvo.getContentcateid());
					nextvo = catelogic.getNextLearnInCurrentContcate(objectid, objecttype, contcateid,
					 		   						       productid, curcatevo, userid, false);
					if(curcatevo!=null){
						contcateid = curcatevo.getContentcateid();
					}
				    if(nextvo!=null){
				    	break;
				    }
				}
			}
		}
		
		String url = "toUrl";
		if(nextvo==null){
			String actionUrl = "learn_main.jsp?productid="+productid+"&contentcateid="+contcateid
			           +"&finishall=1";
			super.setMessUrlPage(request, actionUrl, "", "1", "BizKey");
		}else if(Learncontent.ObjectType.equals(nextvo.getName())
			   || Exercise.ObjectType.equals(nextvo.getName()))
		{
			url = "toUrl";
			String actionUrl = "learn_main.jsp?productid="+productid+"&contentcateid="+contcateid
					           +"&objecttype="+nextvo.getName()+"&objectid="+nextvo.getId();
			super.setMessUrlPage(request, actionUrl, "", "1", "BizKey");
		}else {
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		}
		return mapping.findForward(url);
	} 

	
	/**
	 * list free try course
	 */
	private ActionForward listtry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LearncontentForm theForm = (LearncontentForm) form;		
		LearncontentQuery queryVO = theForm.getQueryVO();
		AssertUtil.assertNotNull(queryVO.getProductbaseid(), null);

		// 如果是列出学习内容，而且又没有选择课程，则不需要查询
		if(queryVO.getProductbaseid()!=null){
			LearncontentDao dao = BOFactory.getLearncontentDao();
			List<Learncontent> list = dao.selectTryProduct(queryVO.getProductbaseid());
            theForm.setDatalist(list);
		}
		return mapping.findForward("list");
	}
	
	/** 
	 * Method save
	 */
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String messCode = KeyInMemoryConstant.modifySuccess;	
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Long userid = loginfo.getUserid();
		LearncontentForm theForm = (LearncontentForm) form;
		Learncontent vo = theForm.getVo();	
		
        if(vo.getContentid()==null||vo.getContentid()==0){
            vo.setShopid(shopid);
            vo.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
            vo.setCreator(userid);
        	messCode = KeyInMemoryConstant.AddSuccess;
        }
        // 检查能不能上传到本地server, 目前仅系统商店可以
        if(theForm.getUploadFile()!=null && theForm.getUploadFile().getFileSize()>0){
        	boolean ableuploadlocal = BOFactory_Platform.getShopLogic().ableUploadFileToLocalServer(shopid);
        	if(!ableuploadlocal){
        		throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
        	}
        }
        
        // 处理file link source
		if(SocialoathtokenConstant.supportService(vo.getLinktype())){
			if(vo.getLinkfilesource()!=null && vo.getLinkfilesource().trim().length()>0){
				vo.setLinkfilesource(SocialoathtokenLogicImpl.
						filterFileSource(vo.getLinkfilesource().trim(), vo.getLinktype()));
			}else if(vo.getLinkfileid()!=null && vo.getLinkuserid()!=null){
				vo.setLinkfilesource(SocialoathtokenLogicImpl.getInstance().
						getFileSource(vo.getShopid(), SocialoathtokenConstant.IdentityType_ShopID, 
								vo.getLinktype(), vo.getLinkuserid(), vo.getLinkfileid()));
			}
			
		}
        vo.setStatus(LearncontentConstant.Status_Valide);
        
        LearncontentLogic logic = BOFactory.getLearncontentLogic();
        logic.save(vo, theForm.getUploadFile());
		// set messag page parameters.
        theForm.setListType(1);
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		// goto list page
		theForm.setProductbaseid(vo.getProductbaseid());
		theForm.getQueryVO().setContentcateid(vo.getContentcateid());
		return listmag(mapping, theForm, request, response);
	}
	
	/** 
	 * Method edit
	 */
	private ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		UserinfoSession loginfo = getLoginInfo(true);
		Long localeid = loginfo.getLocaleid(request);
		Long userid = loginfo.getUserid();
		LearncontentForm theForm = (LearncontentForm) form;
		Long pk = theForm.getVo().getContentid();
		
		Learncontent vo = BOFactory.getLearncontentLogic().getLearncontent(pk);
		AssertUtil.assertNotNull(vo, null);
		Productbase productVO = ProductLogicImpl.getInstance().selectVO(vo.getProductbaseid(), localeid);
		if(productVO!=null){
			vo.setProductname(productVO.getProductname());
		}
		theForm.setVo(vo);
        String tourl = "";
		if(theForm.getEditType()==WebConstant.editType_edit){
			// 防止重复提交
			saveToken(request);
			if(LearncontentConstant.ContentType_HTML.equals(vo.getContenttype())){
				tourl = "editPage_html";
			}else {
				// 
				tourl = "editPage_file";
			}
			// 设置上传文件类型
			// 设置商店是否是系统商店
			ShopLogic shoplogic = BOFactory_Platform.getShopLogic();
			boolean ableuploadlocal = shoplogic.ableUploadFileToLocalServer(vo.getShopid());
			theForm.setAbleuploadlocal(ableuploadlocal);
			boolean ableuploadfileserver = shoplogic.ableUploadFileToFileServer(vo.getShopid());
			theForm.setAbleuploadfileserver(ableuploadfileserver);
		}else{
			if(LearncontentConstant.ContentType_HTML.equals(vo.getContenttype())){
				tourl = "viewPage_html";
			}else {
				// encode url
				//vo.setLinkfilesource(StringUtil.encodeString(vo.getLinkfilesource(), null));
				tourl = "viewPage_file";
			}
			Learnactivity activityvo = BOFactory.getLearnactivityDao().selectByPK(userid, pk, Learncontent.ObjectType, Learnactivity.Actiontype_Learn);
		    theForm.setActivityvo(activityvo);
		}
		// 学习该课程的用户每次浏览产品都会更新用户learn activity
		if(!loginfo.isAnonymous() && userid!=null) {
			Short learnactivity_actiontype = null;
			if(theForm.getEditType()==WebConstant.editType_edit){
				learnactivity_actiontype = Learnactivity.Actiontype_Manage;
			}else {
				learnactivity_actiontype = Learnactivity.Actiontype_Learn;
			}
			BOFactory.getLearncontentLogic().sendLearnActivity(pk, userid, 
					             Learnactivity.Learnstatus_Progressing, learnactivity_actiontype);
		}
		return mapping.findForward(tourl);
	}
		
	/** 
	 * Method add
	 */
	private ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LearncontentForm theForm = (LearncontentForm) form;
		Learncontent vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getProductbaseid(), null);
		AssertUtil.assertNotNull(vo.getContenttype(), null);
		
		saveToken(request);	
		theForm.setEditType(WebConstant.editType_add);
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Long localeid = loginfo.getLocaleid();
		String tourl = "";
		Productbase productVO = ProductLogicImpl.getInstance().selectVO(vo.getProductbaseid(), localeid);
		if(productVO!=null){
			vo.setProductname(productVO.getProductname());
		}
		if(vo.getContentcateid()!=null && 
				!CommonConstant.TreeTopnodePid.equals(vo.getContentcateid()))
		{
			Contentcate catevo = BOFactory.getContentcateDao().selectByPK(vo.getContentcateid());
			vo.setContentcatename(catevo.getContentcatename());
		}
		
		if(LearncontentConstant.ContentType_HTML.equals(vo.getContenttype())){
			tourl = "addPage_html";
			// if add new html page, insert the html page first then get PK, then
			// if upload picture in the page, the upload path need use this PK
			Long userid = loginfo.getUserid();
	        vo.setShopid(shopid);
	        vo.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
	        vo.setCreator(userid);
	        // 因为是新增页面, 先设置为autoSave
	        vo.setStatus(LearncontentConstant.Status_AutoSave);
	        
	        LearncontentLogic logic = BOFactory.getLearncontentLogic();
	        vo = logic.save(vo, null);
			theForm.setVo(vo);
		}else {
			tourl = "addPage_file";
		}
		
		//vo.setLinktype(LearncontentConstant.Linktype_SkydirveLINK);
		// 设置商店是否是系统商店
		ShopLogic shoplogic = BOFactory_Platform.getShopLogic();
		boolean ableuploadlocal = shoplogic.ableUploadFileToLocalServer(shopid);
		theForm.setAbleuploadlocal(ableuploadlocal);
		boolean ableuploadfileserver = shoplogic.ableUploadFileToFileServer(shopid);
		theForm.setAbleuploadfileserver(ableuploadfileserver);
		
		return mapping.findForward(tourl);
	}
	
	/** 
	 * Method delete
	 */
	private ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LearncontentForm theForm = (LearncontentForm) form;
		Long pk = theForm.getVo().getContentid();
		
		String result = String.valueOf(CommonConstant.success);
		String info = getResources(request, "BizKey").getMessage(KeyInMemoryConstant.deleteSuccess, "1");
		try {
			LearncontentLogic logic = BOFactory.getLearncontentLogic();
			logic.delete(pk);
		}catch (HasReferenceException e) {
			result = String.valueOf(CommonConstant.fail);
			if(e instanceof HasReferenceException){
				info = e.getMessage();
			}else {
				info = ExceptionConstant.Error_System;
			}
			
		}
		
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	/** 
	 * user marked the content has completed
	 */
	private ActionForward markFinishLearn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LearncontentForm theForm = (LearncontentForm) form;
		Learncontent vo = theForm.getVo();
		if(vo.getContentid()==null){
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		}
		LoginInfoEdu loginfo = getLoginInfo();
		boolean result_boolean = BOFactory.getLearncontentLogic().sendLearnActivity(vo.getContentid(), loginfo.getUserid(), Learnactivity.Learnstatus_Finished, Learnactivity.Actiontype_Learn);
		
		String result = String.valueOf(CommonConstant.success);
		String info = null;
		if(!result_boolean){
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	private ActionForward openUploadFilePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LearncontentForm theForm = (LearncontentForm) form;
		Learncontent vo = theForm.getVo();
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Long userid = loginfo.getUserid();
		
		boolean ableuploadfileserver = BOFactory_Platform.getShopLogic().ableUploadFileToFileServer(shopid);
		if(!ableuploadfileserver){
			throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
		}
		
		if(vo!=null && vo.getContentid()==null && 
				(LearncontentConstant.linktype_QiniuLink.equals(vo.getLinktype())
				 || LearncontentConstant.Linktype_Local.equals(vo.getLinktype())) )
		{
	        vo.setShopid(shopid);
	        vo.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
	        vo.setCreator(userid);
	        // 先设置为autoSave
	        vo.setStatus(LearncontentConstant.Status_AutoSave);
	        
	        LearncontentLogic logic = BOFactory.getLearncontentLogic();
	        vo = logic.save(vo, null);
			theForm.setVo(vo);
		}
		// 
		String url = "";
		if(LearncontentConstant.linktype_QiniuLink.equals(vo.getLinktype())){
			url = "uploadpage_qiniu";
			//
//			String[] tokenArr = QiniuFileUtil.getInstance().geneUploadTokenWithCBUrl(Learncontent.ObjectType, vo.getContentid());
//			theForm.setUploadtoken(tokenArr[0]);
//			theForm.setVerifykey(tokenArr[1]);
			String uploadtoken = QiniuFileUtil.getInstance().geneUploadToken();
			theForm.setUploadtoken(uploadtoken);
		}
		return mapping.findForward(url);
	}
	
	// 页面调用或者qiniu回调
	private ActionForward afterUploadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// name is uploaded file name with prefix
		String name = request.getParameter("name");
		String size = request.getParameter("size");
		String hash = request.getParameter("hash");
		String idstr = request.getParameter("id");
		// 暂时没有用verifykey, 没有用到qiniu的callback
		String verifykey = request.getParameter("verifykey");
		
		String result = String.valueOf(CommonConstant.success);
		String info = null;
		try{
			AssertUtil.assertNotEmpty(idstr, ExceptionConstant.Error_Need_Paramter);
			AssertUtil.assertNotEmpty(name, ExceptionConstant.Error_Need_Paramter);
			//AssertUtil.assertNotEmpty(verifykey, ExceptionConstant.Error_Need_Paramter);
			long id = Long.parseLong(idstr.trim());
			LearncontentDao dao = BOFactory.getLearncontentDao();
			Learncontent dbvo = dao.selectPlainByPK(id);
			AssertUtil.assertNotNull(dbvo, null);
			
			Long filesize = null;
			if(!StringUtil.isEmpty(size)){
			    filesize = Long.parseLong(size);
			}else if(LearncontentConstant.linktype_QiniuLink.equals(dbvo.getLinktype())){
				filesize = QiniuFileUtil.getInstance().getFileSize(name);
			}
			
			
			// 如果linkfileid或filesize为空，需要继续更新，如果两者都不为空，说明已经更新过了，不需要再次更新
			if(StringUtil.isEmpty(dbvo.getLinkfileid())||(dbvo.getFilesize()==null)){
				Learncontent vo = new Learncontent();
				vo.setContentid(id);
				vo.setLinkfilename(QiniuFileUtil.getOriginalFilename(name));
				vo.setLinkfileid(name);
				vo.setFilesize(filesize);
				String downloadurl = QiniuFileUtil.getInstance().getDownloadUrl(name);
				vo.setLinkfilesource(downloadurl);
				vo.setLastupdatetime(DateUtil.getInstance().getNowtime_GLNZ());
				dao.updateByPK(vo);
				
				if(filesize!=null){
				   // 统计文件增减
				   UploadFileUtilNettest.satisFileStorage(Shop.ObjectType, dbvo.getShopid(), filesize);
				}
			}
		}catch (Exception e){
			log.error("error, name:"+name+"|size:"+size+"|id:"+idstr
					  +"|hash:"+hash+"|verifykey:"+verifykey
					  , e);
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	/**
	 * 用于在页面删除文件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteExistFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LearncontentForm theForm = (LearncontentForm) form;
		Learncontent vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getContentid(), null);
		
		String result = String.valueOf(CommonConstant.success);
		String info = null;
		try{
			LearncontentDao dao = BOFactory.getLearncontentDao();
			Learncontent dbvo = dao.selectPlainByPK(vo.getContentid());
			AssertUtil.assertNotNull(dbvo, null);
			
			if(LearncontentConstant.Linktype_Local.equals(dbvo.getLinktype())
				&& !StringUtil.isEmpty(dbvo.getLinkfilesource())){
				UploadFileUtilNettest.delFile(null, dbvo.getLinkfilesource(), Shop.ObjectType, dbvo.getShopid(), dbvo.getFilesize());
			}else if(LearncontentConstant.linktype_QiniuLink.equals(dbvo.getLinktype()) && 
					 !StringUtil.isEmpty(dbvo.getLinkfileid())){
				QiniuFileUtil.getInstance().removeSingleFile(dbvo.getLinkfileid(), Shop.ObjectType, dbvo.getShopid(), dbvo.getFilesize());
			}
			// update db information
			dao.removeLinkFileByPK(dbvo.getContentid());
		}catch (Throwable e){
			log.error("error, learncontentid:"+vo.getContentid(), e);
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
}

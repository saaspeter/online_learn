package netTest.product.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import platform.logicImpl.BOFactory_Platform;
import platform.vo.Shop;
import netTest.bean.BOFactory;
import netTest.bean.BeanFactory;
import netTest.event.EventHandlerNetTest;
import netTest.exam.constant.TestuserConstant;
import netTest.exam.vo.Testinfo;
import netTest.product.dao.CoursepostDao;
import netTest.product.vo.Coursepost;
import netTest.util.ResourceBundleUtil;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.constant.CommonConstant;
import commonTool.event.Event;
import commonTool.event.EventHandle;
import commonTool.util.DateUtil;

public class CoursepostLogicImpl implements EventHandle {

	private static final String Key_DefaultProductPost_NewTestinfo = "NetTest.DefaultProductPost_NewTestinfo";
	
	private CoursepostDao coursepostDao;
	
	// 当新的testinfo 创建的时候会自动生成course post
	@Override
	public void onEvent(Event event) {
		String eventType = event.getEventType();
		Map paraMap = event.getParaMap();
		
		if(EventHandlerNetTest.EventType_TestInfo_Added.equals(eventType)){
			// 设想的feature是：当新增考试时候，会自动生成一个 course post
			Long testid = new Long(paraMap.get("testid").toString());
			// get testinfo
			Testinfo testvo = BOFactory.getTestinfoDao().selectByPK(testid);
			List<String> paraList = new ArrayList<String>();
			Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(testvo.getShopid(), null);
			Long localeid = shopvo.getLocaleid();
			Locale locale = I18nLogicImpl.getLocale(localeid);
			paraList.add(testvo.getTestname()); 
			paraList.add(DateUtil.printDate(testvo.getTeststartdate(), localeid, DateUtil.FormatType_DateTime)+" -- "
					     +DateUtil.printDate(testvo.getTestenddate(), localeid, DateUtil.FormatType_DateTime));
			String openurl = "/"+CommonConstant.WebContext_Education+"/exam/listTestinfouser.do?productbaseid="+testvo.getProductbaseid()+"&queryVO.roughteststatus="+TestuserConstant.RoughTestStatus_upcoming;
			paraList.add(openurl);
			// get post content template
			String content = ResourceBundleUtil.getInstance()
			  .getValue(Key_DefaultProductPost_NewTestinfo, locale, paraList);
			//
			Coursepost vo = new Coursepost();
			vo.setContent(content);
			Date curdate = DateUtil.getInstance().getNowtime_GLNZ();
			vo.setCreatetime(curdate);
			vo.setGeneratetype(Coursepost.GenerateType_AutoCreate);
			vo.setOpenurl(openurl);
			vo.setRefobjecttype(Testinfo.ObjectType);
			vo.setRefobjectid(testid);
			vo.setProductbaseid(testvo.getProductbaseid());
			vo.setShopid(testvo.getShopid());
			vo.setUpdatetime(curdate);
			BOFactory.getCoursepostDao().insert(vo);
		}
	}
	
	public static CoursepostLogicImpl getInstance() {
		CoursepostLogicImpl logic = (CoursepostLogicImpl)BeanFactory.getBeanFactory().getBean("coursepostLogic");
        return logic;
    }

	public CoursepostDao getCoursepostDao() {
		return coursepostDao;
	}

	public void setCoursepostDao(CoursepostDao coursepostDao) {
		this.coursepostDao = coursepostDao;
	}

}

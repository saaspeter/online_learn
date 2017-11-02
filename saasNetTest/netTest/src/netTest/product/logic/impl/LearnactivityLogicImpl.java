package netTest.product.logic.impl;

import java.util.Map;

import netTest.bean.BeanFactory;
import netTest.event.EventHandlerNetTest;
import netTest.product.dao.LearnactivityDao;
import netTest.product.logic.LearnactivityLogic;
import netTest.product.vo.Learnactivity;

import org.apache.log4j.Logger;

import commonTool.event.Event;
import commonTool.event.EventHandle;

public class LearnactivityLogicImpl implements LearnactivityLogic, EventHandle{

	private static Logger log = Logger.getLogger(LearnactivityLogicImpl.class.getName());
	
	private LearnactivityDao learnactivityDao;
	

	public void onEvent(Event event) {
		Map paraMap = event.getParaMap();
		Learnactivity vo = (Learnactivity)paraMap.get("vo");
		
		String eventType = event.getEventType();
		if(EventHandlerNetTest.EventType_Product_LearnActivity.equals(eventType)){
			learnactivityDao.save(vo);
		}
		
	}

    /**
     * static spring getMethod
     */
    public static LearnactivityLogic getInstance() {
    	LearnactivityLogic logic = (LearnactivityLogic)BeanFactory.getBeanFactory().getBean("learnactivityLogic");
        return logic;
    }

	public LearnactivityDao getLearnactivityDao() {
		return learnactivityDao;
	}

	public void setLearnactivityDao(LearnactivityDao learnactivityDao) {
		this.learnactivityDao = learnactivityDao;
	}
	
}

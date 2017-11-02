package netTest.wareques.logic.impl;

import netTest.bean.BeanFactory;
import netTest.wareques.dao.QuestypeshopDao;
import netTest.wareques.logic.QuestypeshopLogic;

public class QuestypeshopLogicImpl implements QuestypeshopLogic {

	private QuestypeshopDao dao;
	
	
		
	
	/**
	 * static spring getMethod
	 */
	 public static QuestypeshopLogic getInstance(){
		 QuestypeshopLogic logic = (QuestypeshopLogic)BeanFactory.getBeanFactory().getBean("questypeshopLogic");
	     return logic;
	 }


	
}

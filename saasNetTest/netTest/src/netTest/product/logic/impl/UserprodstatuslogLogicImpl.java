package netTest.product.logic.impl;

import netTest.bean.BeanFactory;
import netTest.product.dao.UserprodstatuslogDao;
import netTest.product.logic.UserprodstatuslogLogic;

import org.apache.log4j.Logger;

public class UserprodstatuslogLogicImpl implements UserprodstatuslogLogic{
    
	static Logger log = Logger.getLogger(UserprodstatuslogLogicImpl.class.getName());
	
	private UserprodstatuslogDao dao;
	
 	
    /**
     * static spring getMethod
     */
    public static UserprodstatuslogLogic getInstance() throws Exception {
      try{
    	  UserprodstatuslogLogic logic = (UserprodstatuslogLogic)BeanFactory.getBeanFactory().getBean("userprodstatuslogLogic");
           return logic;
      }catch(Exception e) {
      	 log.error("UserprodstatuslogLogicImpl方法getInstance()时出错误!", e);
            throw new Exception("Errors occur while getInstance() in UserprodstatuslogLogicImpl!") ;
      }
   }

	public UserprodstatuslogDao getDao() {
		return dao;
	}

	public void setDao(UserprodstatuslogDao dao) {
		this.dao = dao;
	}
	
}

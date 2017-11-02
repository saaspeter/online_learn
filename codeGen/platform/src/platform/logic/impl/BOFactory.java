package platform.logic.impl;

import platform.dao.AccountvalidatetaskDao;
import platform.daoImpl.AccountvalidatetaskDaoImpl;
import platform.dao.UseractivityDao;
import platform.daoImpl.UseractivityDaoImpl;


public class BOFactory {

  public static AccountvalidatetaskDao getAccountvalidatetaskDao() throws Exception{
    return AccountvalidatetaskDaoImpl.getInstance();
  }

  public static UseractivityDao getUseractivityDao() throws Exception{
    return UseractivityDaoImpl.getInstance();
  }



}

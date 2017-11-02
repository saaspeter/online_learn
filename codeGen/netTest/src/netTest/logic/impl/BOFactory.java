package netTest.logic.impl;

import netTest.dao.ShopopenactivitymemberDao;
import netTest.daoImpl.ShopopenactivitymemberDaoImpl;
import netTest.dao.ShopopenactivityDao;
import netTest.daoImpl.ShopopenactivityDaoImpl;


public class BOFactory {

  public static ShopopenactivitymemberDao getShopopenactivitymemberDao() throws Exception{
    return ShopopenactivitymemberDaoImpl.getInstance();
  }

  public static ShopopenactivityDao getShopopenactivityDao() throws Exception{
    return ShopopenactivityDaoImpl.getInstance();
  }



}

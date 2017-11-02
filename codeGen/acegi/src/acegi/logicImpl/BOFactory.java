package acegi.logicImpl;

import acegi.dao.ResourcesDao;
import acegi.daoImpl.ResourcesDaoImpl;
import acegi.dao.RolesDao;
import acegi.daoImpl.RolesDaoImpl;
import acegi.dao.UsersDao;
import acegi.daoImpl.UsersDaoImpl;
import acegi.dao.Role_rescDao;
import acegi.daoImpl.Role_rescDaoImpl;
import acegi.dao.User_roleDao;
import acegi.daoImpl.User_roleDaoImpl;
import acegi.dao.CustorderDao;
import acegi.daoImpl.CustorderDaoImpl;
import acegi.dao.RolesvalueDao;
import acegi.daoImpl.RolesvalueDaoImpl;
import acegi.dao.MenusDao;
import acegi.daoImpl.MenusDaoImpl;
import acegi.dao.ResourcesvalueDao;
import acegi.daoImpl.ResourcesvalueDaoImpl;
import acegi.dao.MenusvalueDao;
import acegi.daoImpl.MenusvalueDaoImpl;
import acegi.dao.ResclinkDao;
import acegi.daoImpl.ResclinkDaoImpl;


public class BOFactory {

  public static ResourcesDao getResourcesDao() throws Exception{
    return ResourcesDaoImpl.getInstance();
  }

  public static RolesDao getRolesDao() throws Exception{
    return RolesDaoImpl.getInstance();
  }

  public static UsersDao getUsersDao() throws Exception{
    return UsersDaoImpl.getInstance();
  }

  public static Role_rescDao getRole_rescDao() throws Exception{
    return Role_rescDaoImpl.getInstance();
  }

  public static User_roleDao getUser_roleDao() throws Exception{
    return User_roleDaoImpl.getInstance();
  }

  public static CustorderDao getCustorderDao() throws Exception{
    return CustorderDaoImpl.getInstance();
  }

  public static RolesvalueDao getRolesvalueDao() throws Exception{
    return RolesvalueDaoImpl.getInstance();
  }

  public static MenusDao getMenusDao() throws Exception{
    return MenusDaoImpl.getInstance();
  }

  public static ResourcesvalueDao getResourcesvalueDao() throws Exception{
    return ResourcesvalueDaoImpl.getInstance();
  }

  public static MenusvalueDao getMenusvalueDao() throws Exception{
    return MenusvalueDaoImpl.getInstance();
  }

  public static ResclinkDao getResclinkDao() throws Exception{
    return ResclinkDaoImpl.getInstance();
  }



}

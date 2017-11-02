package commonWeb.security.logic;

import commonWeb.security.dao.MenusDao;
import commonWeb.security.dao.MenusvalueDao;
import commonWeb.security.dao.ResourcesDao;
import commonWeb.security.dao.ResourcesvalueDao;
import commonWeb.security.dao.RoleRescDao;
import commonWeb.security.dao.RolesDao;
import commonWeb.security.dao.RolesvalueDao;
import commonWeb.security.dao.SuperuserDao;
import commonWeb.security.dao.UserRoleDao;
import commonWeb.security.dao.impl.MenusDaoImpl;
import commonWeb.security.dao.impl.MenusvalueDaoImpl;
import commonWeb.security.dao.impl.ResourcesDaoImpl;
import commonWeb.security.dao.impl.ResourcesvalueDaoImpl;
import commonWeb.security.dao.impl.RoleRescDaoImpl;
import commonWeb.security.dao.impl.RolesDaoImpl;
import commonWeb.security.dao.impl.RolesvalueDaoImpl;
import commonWeb.security.dao.impl.SuperuserDaoImpl;
import commonWeb.security.dao.impl.UserRoleDaoImpl;

public class BOFactory {

  public static ResourcesDao getResourcesDao(){
    return ResourcesDaoImpl.getInstance();
  }

  public static RolesDao getRolesDao(){
    return RolesDaoImpl.getInstance();
  }

  public static RoleRescDao getRoleRescDao(){
    return RoleRescDaoImpl.getInstance();
  }

  public static UserRoleDao getUserRoleDao(){
    return UserRoleDaoImpl.getInstance();
  }

  public static MenusDao getMenusDao(){
	return MenusDaoImpl.getInstance();
  }
  
  public static MenusvalueDao getMenusvalueDao(){
	  return MenusvalueDaoImpl.getInstance();
  }
  
  public static ResourcesvalueDao getResourcesvalueDao(){
	  return ResourcesvalueDaoImpl.getInstance();
  }
  
  public static RolesvalueDao getRolesvalueDao(){
	  return RolesvalueDaoImpl.getInstance();
  }
  
  public static SuperuserDao getSuperuserDao(){
	  return SuperuserDaoImpl.getInstance();
  }
  
  public static SecurityEventHandle getSecurityEventHandle(){
	  return SecurityEventHandle.getInstance();
  }

}

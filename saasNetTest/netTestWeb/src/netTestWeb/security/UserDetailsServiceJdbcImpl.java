package netTestWeb.security;

import java.util.List;

import netTest.common.session.LoginInfoEdu;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import platform.constant.CustomerConstant;
import platform.dao.UserDao;
import platform.vo.User;
import commonTool.constant.CommonConstant;
import commonWeb.security.constant.RolesConstant;
import commonWeb.security.constant.UserRolesConstant;
import commonWeb.security.dao.RolesDao;
import commonWeb.security.dao.UserRoleDao;
import commonWeb.security.dao.impl.UserRoleDaoImpl;
import commonWeb.security.vo.Roles;


public class UserDetailsServiceJdbcImpl implements UserDetailsService{
	
	static Logger log = Logger.getLogger(UserDetailsServiceJdbcImpl.class.getName());
	
	private UserDao userDao = null;
	private UserRoleDao userroleDao = null;
	

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		if(username==null||username.trim().length()<1){
			throw new UsernameNotFoundException("error.user.lackofUsername");
		}
		userDao=this.getUserDao();
		userroleDao=this.getUserRoleDao();

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean accountNonLocked = true;
		boolean credentialsNonExpired = true;
		String password = null;
		GrantedAuthority[] authArr = new GrantedAuthority[0];
		String syscode = CommonConstant.SysCode_Education;
		
		User vo = userDao.selectByLogin(username);
		// 如果根据用户名查询不到，则用email来查询该用户
		if(vo==null){
			vo = userDao.selectByEmail(username);
		}
		
		if(vo!=null&&vo.getUserid()!=null){
			// 在此只查询用户在系统中的角色，而不查询用户在商店中的角色将不会被查出来
			List<Long> roleidlist = userroleDao.selectUserRole(vo.getUserid(), 
					                           UserRolesConstant.NonShopID, syscode);
			authArr = transToAuthFromRoles(roleidlist);
			password = vo.getPass();
			// 验证用户的状态是否正常
            if(!CustomerConstant.CustomerStatus_active.equals(vo.getStatus())){
            	enabled = false;
            }
		}else{
			throw new UsernameNotFoundException("error.user.nosuchuser");
		}
		
		LoginInfoEdu userinfo = new LoginInfoEdu(username, password, enabled,
				accountNonExpired, accountNonLocked, credentialsNonExpired,
				authArr);
		if(vo!=null && vo.getUserid()!=null){
			userinfo.populateFromUser(vo);
		}
		// 如果用户设置了localeid，则在此加载信息。否则在登录后的LoginAction中设置
		if(userinfo.getLocale()!=null){
			String jsSuffix = "_"+userinfo.getLocale().getLanguage()+"_"+userinfo.getLocale().getCountry();
			userinfo.setJsSuffix(jsSuffix);
		}
		
		userinfo.setAnonymous(false);
		userinfo.setSyscode(syscode);
		return userinfo;
	}
	
	/**
	 * 将用户的role转成GrantedAuthorityImpl,并且带上默认注册用户的Role
	 * @param list
	 * @return
	 */
	private GrantedAuthority[] transToAuthFromRoles(List<Long> idlist){
		if(idlist==null||idlist.size()<1)
			return new GrantedAuthorityImpl[]{new GrantedAuthorityImpl(RolesConstant.ROLE_RegisterMember),
											  new GrantedAuthorityImpl(RolesConstant.ROLE_ANONYMOUS)}; 
		Roles rolevo = null;
		Long roleid = null;
		RolesDao roledao = commonWeb.security.logic.BOFactory.getRolesDao();
		int listsize = idlist.size();
		GrantedAuthorityImpl[] auths = new GrantedAuthorityImpl[listsize+2];
		for(int i=0;i<listsize;i++){
			roleid = idlist.get(i);
			rolevo = roledao.selectByPK(roleid);
			auths[i] = new GrantedAuthorityImpl(rolevo.getCode());
		}
		auths[listsize] = new GrantedAuthorityImpl(RolesConstant.ROLE_RegisterMember);
		auths[listsize+1] = new GrantedAuthorityImpl(RolesConstant.ROLE_ANONYMOUS);
		return auths;
	}

	public UserDao getUserDao(){
		if(userDao==null){
			try {
				userDao = platform.logicImpl.BOFactory_Platform.getUserDao();
			} catch (Exception e) {
				log.error("error in UserDetailsServiceJdbcImpl", e);
			}
		}
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public UserRoleDao getUserRoleDao() {
		if(userroleDao==null){
			try {
				userroleDao = UserRoleDaoImpl.getInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userroleDao;
	}

	public void setUserRoleDao(UserRoleDao userroleDao) {
		this.userroleDao = userroleDao;
	}
	
}

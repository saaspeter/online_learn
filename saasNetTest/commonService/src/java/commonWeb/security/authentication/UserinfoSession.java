package commonWeb.security.authentication;

import java.io.Serializable;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;
import platform.vo.User;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonWeb.base.KeyInMemoryConstant;


/**
 * 该类为放在内存中的用户信息的基类，如果子系统有特殊要求，必须继承该类，因为系统个别地方直接转换客户信息为该类
 * @author fan
 *
 */

public class UserinfoSession extends User implements UserDetails, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1740074279029969511L;
	
	protected GrantedAuthority[] authorities;
	protected GrantedAuthority[] shopauthorities;
	private GrantedAuthority[] totalauthorities;
	
	protected boolean accountNonExpired;
	protected boolean accountNonLocked;
	protected boolean credentialsNonExpired;
	protected boolean enabled;
	
	/** 是不是root登录，如果是，则不进行权限验证 **/
	protected boolean rootLogin = false;
    	
    /** 是否是匿名登录，默认是匿名登录 **/
	protected boolean isAnonymous = true;
	
    /** 用户正在使用的localeid，在第一次访问系统时初始化，在SaasAnonymousProcessingFilter中也设置
     * userLocaleid用户可能会在主界面上改变，主要用于显示主界面上site的内容，用户登录后个人的界面则使用自己设置的locale设置，
     * 而localeid是数据库中的记录. 如果用户的localeid不是系统支持的，则用系统默认的locale **/
//	protected Long useLocaleid;
//    /** 用户正在使用的locale，在第一次访问系统时初始化 **/
//	protected Locale useLocale;
    /** 记录用户本次登录要操作的系统编码 **/
	protected String syscode;
	
    /** 进入商店的id **/
	protected Long shopid;
    /** 进入商店的代码 **/
	protected String shopcode;
	protected String shopname;
	/** 目前记录用户有学习产品的商店列表 **/
//	protected List<ShopMini> sessShopList;
	/** 记录用户在各个商店中的authorities，以便于来回切换 **/
	//protected Map<Long, GrantedAuthority[]> shopauthMap = new HashMap<Long, GrantedAuthority[]>();
	
    /** 记录用户用到的js后缀名，格式为：_language_country **/
	protected String jsSuffix = "_default";
	
	/**
	 * 登录的方式，ref to: CommonConstant.LoginType
	 */
	protected Short logintype;
	/** 如果是social登录，则记录下access token **/
	protected String accesstoken;
	

	public UserinfoSession(String loginname, String password, boolean enabled,
			boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired,
			GrantedAuthority[] authorities)
	{
	   	this.loginname = loginname;
	   	this.pass = password;
	   	this.enabled = enabled;
	   	this.accountNonExpired = accountNonExpired;
	   	this.accountNonLocked = accountNonLocked;
	   	this.credentialsNonExpired = credentialsNonExpired;
	   	this.authorities = authorities;
	}
	
	public GrantedAuthority[] getBaseAuthorities() {
		return authorities;
    }

	public GrantedAuthority[] getAuthorities() {
//		if(authorities==null){
//			authorities = new GrantedAuthority[]{};
//		}
//        return authorities;
		// 目前没有需要单独得到authorities的情况，一般都是得到baisc + shop authorities
		return getTotalauthorities();
    }
	
	public GrantedAuthority[] getTotalauthorities() {
		if(totalauthorities!=null){
			return totalauthorities;
		}
		if(authorities!=null&&authorities.length>0
				&&shopauthorities!=null&&shopauthorities.length>0){
			totalauthorities = new GrantedAuthority[authorities.length+shopauthorities.length];
			for(int i=0;i<authorities.length;i++){
				totalauthorities[i] = authorities[i];
			}
			for(int j=0;j<shopauthorities.length;j++){
				totalauthorities[authorities.length+j] = shopauthorities[j];
			}
		}else if(shopauthorities==null||shopauthorities.length==0){
			totalauthorities = authorities;
		}else {
			totalauthorities = shopauthorities;
		}
		if(totalauthorities==null){
			totalauthorities = new GrantedAuthority[]{};
		}
		return totalauthorities;
	}

	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean equals(Object object) {
		if (object instanceof UserinfoSession) {
			if (this.userid!=null && this.userid.equals(((UserinfoSession) object).getUserid()))
				return true;
		}
		return false;
	}
	
//    public int hashCode(){     
//        return this.userid.hashCode();     
//    }

	public String getPassword() {
		return pass;
	}

	public String getUsername() {
		return loginname;
	}   
	
	public void setUsername(String loginname){
		this.loginname = loginname;
	}

	public boolean isAnonymous() {
		return isAnonymous;
	}

	public void setAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public String getJsSuffix() {
		return jsSuffix;
	}

	public void setJsSuffix(String jsSuffix) {
		this.jsSuffix = jsSuffix;
	}

	public boolean isRootLogin() {
		return rootLogin;
	}

	public void setRootLogin(boolean rootLogin) {
		this.rootLogin = rootLogin;
	}

	public String getShopcode() {
		return shopcode;
	}

	public void setShopcode(String shopcode) {
		this.shopcode = shopcode;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public Locale getUseLocale(HttpServletRequest request) {
		return (Locale)request.getSession().getAttribute(KeyInMemoryConstant.SessionKey_LocaleUserSelect);
	}

	public void setUseLocale(Locale useLocale, HttpServletRequest request) {
		request.getSession(true).setAttribute(KeyInMemoryConstant.SessionKey_LocaleUserSelect, useLocale);
	}

	public Long getUseLocaleid(HttpServletRequest request) {
		return (Long)request.getSession().getAttribute(KeyInMemoryConstant.SessionKey_LocaleidUserSelect);
	}
	
	public void setUseLocaleid(Long uselocaleid, HttpServletRequest request) {
		request.getSession(true).setAttribute(KeyInMemoryConstant.SessionKey_LocaleidUserSelect, uselocaleid);
	}
	
	public Long getLocaleid(HttpServletRequest request) {
		if(this.getLocaleid()!=null){
			return this.getLocaleid();
		}else {
			return getUseLocaleid(request);
		}
	}
	
	public Locale getLocale(HttpServletRequest request) {
		if(this.getLocale()!=null){
			return super.getLocale();
		}else {
			return getUseLocale(request);
		}
	}
	
	public String getUseJsSuffix(HttpServletRequest request) {
		return (String)request.getSession().getAttribute(KeyInMemoryConstant.SessionKey_UserJsSuffix);
	}
	
	public void setUseJsSuffix(String jsSuffix, HttpServletRequest request) {
		request.getSession(true).setAttribute(KeyInMemoryConstant.SessionKey_UserJsSuffix, jsSuffix);
	}
	
	public GrantedAuthority[] getShopauthorities() {
		return shopauthorities;
	}

	public void setShopauthorities(GrantedAuthority[] shopauthorities) {
		this.shopauthorities = shopauthorities;
		this.totalauthorities = null;
	}

	public void populateFromUser(User vo){
    	if(vo==null)
    		return;
    	this.setLocaleid(vo.getLocaleid());
    	this.setLocale(I18nLogicImpl.getLocale(vo.getLocaleid()));
    	this.setLoginname(vo.getLoginname());
    	this.setUserid(vo.getUserid());
    	this.setUsertype(vo.getUsertype());
    	this.setTimezone(vo.getTimezone());
    	this.setEmail(vo.getEmail());
    	this.setGender(vo.getGender());
    	
    	this.setDisplayname(vo.getDisplayname());
    	this.setStatus(vo.getStatus());
    	this.setStagestatus(vo.getStagestatus());
    	return;
    }

	public Short getLogintype() {
		return logintype;
	}

	public void setLogintype(Short logintype) {
		this.logintype = logintype;
	}

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	
//	public GrantedAuthority[] getShopAuthority(Long shopid){
//		return shopauthMap.get(shopid);
//	}
//	
//	public void putShopAuthority(Long shopid, GrantedAuthority[] shopauthorities){
//		shopauthMap.put(shopid, shopauthorities);
//	}
	
}

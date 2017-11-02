package commonWeb.security.voter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Element;
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.vote.AccessDecisionVoter;
import platform.logic.Container;
import commonTool.condexec.ContextObj;
import commonTool.util.StringUtil;
import commonWeb.security.authentication.UserinfoSession;
import commonWeb.security.authorized.ProbeAccess;
import commonWeb.security.resource.ConfigAttributeDefinitionEx;


/**
* 改造RoleVoter, 如果Authention中的userDetail中有权限的值，则用之，否则用Authention中的权限。
* 用户登录系统后会分配用户平台的权限，只有当进入商店后会加载用户在商店中的权限。因此userDetail中的权限最全面。Authention中的权限不能修改(private变量)。
* @author peter
*/
public abstract class RoleVoterEx implements AccessDecisionVoter {

    //~ Instance fields ================================================================================================

    private String rolePrefix = "ROLE_";
    
    private ProbeAccess probeaccess;

    //~ Methods ========================================================================================================

    public String getRolePrefix() {
        return rolePrefix;
    }

    /**
     * Allows the default role prefix of <code>ROLE_</code> to be overridden.
     * May be set to an empty value, although this is usually not desirable.
     *
     * @param rolePrefix the new prefix
     */
    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    public boolean supports(ConfigAttribute attribute) {
        if ((attribute.getAttribute() != null) && attribute.getAttribute().startsWith(getRolePrefix())) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This implementation supports any type of class, because it does not query
     * the presented secure object.
     *
     * @param clazz the secure object
     *
     * @return always <code>true</code>
     */
    public boolean supports(Class clazz) {
        return true;
    }

    //@override
    public int vote(Authentication authentication, Object object, ConfigAttributeDefinition config) {
    	FilterInvocation filterObj = (FilterInvocation)object;
    	// paramap的key是参数name, value是string数组
    	Map<String, String[]> paramap = filterObj.getHttpRequest().getParameterMap();  
        return vote(authentication, paramap, config);
    }
    
    // this method must be public, because other class called it, e.g: ResAuthorizeTag.doStartTag()
    public int vote(Authentication authentication, Map<String, String[]> paramap, ConfigAttributeDefinition config) {
    	if(isRootLogin(authentication)){
        	return ACCESS_GRANTED;
        }
    	int result = ACCESS_ABSTAIN;
    	
        List<Element> policyruleList = null;
        String requestobjectidname = null;
        String requestobjecttype = null;
        String requestcontaineridname = null;
		String requestcontainertype = null;
        if(config instanceof ConfigAttributeDefinitionEx){
        	ConfigAttributeDefinitionEx configEx = (ConfigAttributeDefinitionEx)config;
        	policyruleList = configEx.getPolicyruleList();
        	requestobjectidname = configEx.getRequestObjectidName();
        	requestobjecttype = configEx.getRequestObjecttype();
        	requestcontaineridname = configEx.getRequestContaineridname();
        	requestcontainertype = configEx.getRequestContainertype();
        }
        // load target object
        Object targetObject = null;
        Container container = null;
        if(paramap!=null){
        	if(requestobjectidname!=null&&!"".equals(requestobjectidname)
            	&&requestobjecttype!=null&&!"".equals(requestobjecttype)
            	&& !StringUtil.isEmpty(paramap.get(requestobjectidname)))
            {
            	String[] objectidArr = paramap.get(requestobjectidname);
            	if(objectidArr!=null&&objectidArr.length>0){
                	targetObject = loadTargetObject(getObjectType(requestobjecttype,paramap), objectidArr[0]);
            	}
            	container = getContainer(targetObject, paramap);
            }else if(requestcontaineridname!=null&&!"".equals(requestcontaineridname)
            		&&requestcontainertype!=null&&!"".equals(requestcontainertype)
            		&& !StringUtil.isEmpty(paramap.get(requestcontaineridname))){
            	String[] objectidArr = paramap.get(requestcontaineridname);
            	if(objectidArr!=null&&objectidArr.length>0){
            		Object reqContainObj = loadTargetObject(getObjectType(requestcontainertype,paramap), objectidArr[0]);
            		if(reqContainObj!=null && (reqContainObj instanceof Container)){
            			container = (Container)reqContainObj;
            		}
            		//container = ContainerFactory.getInstance().createContainer(objectidArr[0], getObjectType(requestcontainertype,paramap));
            	}
            }
        }
        
        UserinfoSession sessinfo = null;
        if(authentication.getPrincipal() instanceof UserinfoSession){
        	sessinfo = (UserinfoSession)authentication.getPrincipal();
        }
        // 默认用户和container在同一个shop,这样当用户访问一些UI元素的link时可以加载上shop上的权限
        boolean isInSameShop = true;
        GrantedAuthority[] containerAuth = null;
        GrantedAuthority[] objectAuth = null;
        // load container roles
        if(container!=null){
            containerAuth = loadContainerAuth(sessinfo, container);
            if(sessinfo!=null){
            	isInSameShop = container.isSameShop(sessinfo.getShopid());
            }
        }
        // load target object roles
        if(targetObject!=null){
        	objectAuth = loadObjectAuth(sessinfo, targetObject);
        }
        
        GrantedAuthority[] authorities = extractAuthorities(isInSameShop, authentication, sessinfo, containerAuth, objectAuth);    
        
        // 检查Role权限
        Iterator iter = config.getConfigAttributes().iterator();
        while (iter.hasNext()) {
            ConfigAttribute attribute = (ConfigAttribute) iter.next();
            if (attribute!=null&&this.supports(attribute)) {
                result = ACCESS_DENIED;
                // Attempt to find a matching granted authority
                for (int i = 0; i < authorities.length; i++) {
                    if (attribute.getAttribute().equals(authorities[i].getAuthority())) {
                    	result = ACCESS_GRANTED;
                    	break;
                    }
                }
                if(result == ACCESS_GRANTED){
                	break;
                }
            }
        }
        if(result==ACCESS_GRANTED && policyruleList!=null && policyruleList.size()>0){
            // 开始进行xml自定义表达式检查
            ContextObj contObj = new ContextObj(targetObject, paramap, authorities);
            boolean datacheck = true;
            for(Element policyrule : policyruleList){
            	datacheck = probeaccess.check(policyrule, contObj);
            	if(!datacheck){
            		break;
            	}
            }
            if(datacheck){
            	result = ACCESS_GRANTED;
            }else {
            	result = ACCESS_DENIED;
            }
        }
        
        return result;
    }
    
    /**
     * get authorities
     */
    private GrantedAuthority[] extractAuthorities(boolean isInSameShop, Authentication authentication, 
    		       UserinfoSession sessinfo, GrantedAuthority[] containerAuth, GrantedAuthority[] objectAuth) 
    {
    	GrantedAuthority[] rtnAuth = null;
    	if(sessinfo!=null){
    		if(isInSameShop){
    			rtnAuth = sessinfo.getAuthorities();
    		}else {
    			rtnAuth = sessinfo.getBaseAuthorities();
    		}
	    }else {
	    	rtnAuth = authentication.getAuthorities();
	    }
    	if(containerAuth!=null&&containerAuth.length>0){
    		if(rtnAuth==null || rtnAuth.length<1){
    			rtnAuth = containerAuth;
    		}else {
    			rtnAuth = (GrantedAuthority[])ArrayUtils.addAll(rtnAuth, containerAuth);
    		}
    	}
    	if(objectAuth!=null&&objectAuth.length>0){
    		if(rtnAuth==null || rtnAuth.length<1){
    			rtnAuth = objectAuth;
    		}else {
    			rtnAuth = (GrantedAuthority[])ArrayUtils.addAll(rtnAuth, objectAuth);
    		}
    	}
    	return rtnAuth;
    }
    
    private boolean isRootLogin(Authentication authentication){
    	boolean ret = false;
    	if(authentication!=null){
    		Object obj = authentication.getPrincipal();
    		if(obj!=null && (obj instanceof UserinfoSession) 
    				&& ((UserinfoSession)obj).isRootLogin()){
    			ret = true;
    		}
    	}
    	return ret;
    }
    
    public abstract Object loadTargetObject(String objecttype, String objectid);
    
    /** 从target object 或者 request url中获得container, 
     * 如果target object中不包含container,则从paramMap中获取 **/
    public abstract Container getContainer(Object targetObject, Map<String, String[]> paramap);
    
    /** 加载在container中的权限 **/
    public abstract GrantedAuthority[] loadContainerAuth(UserinfoSession sessinfo, Container container);
    
    /** 加载对target object的权限 **/
    public abstract GrantedAuthority[] loadObjectAuth(UserinfoSession sessinfo, Object targetObject);

	public ProbeAccess getProbeaccess() {
		return probeaccess;
	}

	public void setProbeaccess(ProbeAccess probeaccess) {
		this.probeaccess = probeaccess;
	}
	
	private String getObjectType(String objecttypeStr, Map<String, String[]> paramap){
		String objecttype = objecttypeStr;
		if(objecttypeStr!=null){
			if(objecttypeStr.startsWith("$")){
				String[] objectidArr = paramap.get(objecttypeStr.substring(1));
				if(objectidArr!=null && objectidArr.length>0){
					objecttype = objectidArr[0];
				}
			}
		}
		return objecttype;
	}
	
}


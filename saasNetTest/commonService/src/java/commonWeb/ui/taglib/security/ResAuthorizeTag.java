package commonWeb.ui.taglib.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.ExpressionEvaluationUtils;

import commonWeb.base.BaseActionBase;
import commonWeb.security.authentication.UserinfoSession;
import commonWeb.security.resource.AbstractFilterInvocationDefinitionSource;
import commonWeb.security.voter.RoleVoterEx;

/**
 * 对用户授权进行认证的Tag, 如果用户没相应授权，则Tag中的Html代码全部不显示
 * @author cac
 */
public class ResAuthorizeTag extends TagSupport {

	private static final long serialVersionUID = 0L;

	private String res = "";

	// ~ Methods
	// ========================================================================================================

	public int doStartTag() throws JspException {
		BaseActionBase baseAction = new BaseActionBase();
		UserinfoSession sessioninfo = baseAction.getLoginInfo(true);
		if(sessioninfo==null || sessioninfo.isAnonymous()){
			return Tag.SKIP_BODY;
		}
	    // 如果验证结果是超级用户，则跳过正常的检验
	    if(sessioninfo.isRootLogin()){
	    	return Tag.EVAL_BODY_INCLUDE;
	    }
		ServletContext sc = pageContext.getServletContext();
		WebApplicationContext webAppCtx = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		AbstractFilterInvocationDefinitionSource ObjectDefinitionSource = (AbstractFilterInvocationDefinitionSource) webAppCtx.getBean("objectDefinitionSource");
		
		//取出 tag 的名称
		final String tag = ExpressionEvaluationUtils.evaluateString("res", res,	pageContext);
		
		//找出相应的资源
		ConfigAttributeDefinition confattrdef = ObjectDefinitionSource.lookupAttributes(tag);
		if (confattrdef == null || confattrdef.getConfigAttributes()==null) {
			return Tag.EVAL_BODY_INCLUDE;
		}
		
		Map<String, String[]> paramap = getParamFromUrl(tag);
		
		RoleVoterEx rolevoter = (RoleVoterEx)webAppCtx.getBean("roleVoter");
		int result = rolevoter.vote(SecurityContextHolder.getContext().getAuthentication(), paramap, confattrdef);
        if(RoleVoterEx.ACCESS_GRANTED!=result){
        	return Tag.SKIP_BODY;
        }
        
		return Tag.EVAL_BODY_INCLUDE;
	}
	
	private Map<String, String[]> getParamFromUrl(String url){
		Map<String, String[]> paramap = null;
		int firstQuestionMarkIndex = url.lastIndexOf("?");
		String paramStr = url.substring(firstQuestionMarkIndex+1);
		if(paramStr!=null){
			paramap = new HashMap<String, String[]>();
			String[] paramArr = paramStr.split("&");
			if(paramArr!=null && paramArr.length>0){
				String[] eachparamArr = null;
				for(String paraStr : paramArr){
					eachparamArr = paraStr.split("=");
					if(eachparamArr!=null && eachparamArr.length==2){
						paramap.put(eachparamArr[0], new String[]{eachparamArr[1]});
					}
				}
			}
		}
		return paramap;
	}

	/**
	 * 获取当前用户授权
	 * @return
	 */
	private Collection getPrincipalAuthorities() {
		Authentication currentUser = SecurityContextHolder.getContext()
				.getAuthentication();
		if (null == currentUser) {
			return Collections.EMPTY_LIST;
		}
		GrantedAuthority[] authorities = currentUser.getAuthorities();
		if(currentUser.getPrincipal() instanceof UserDetails){
			UserDetails userdetail = (UserDetails)currentUser.getPrincipal();
	        if(userdetail!=null && userdetail.getAuthorities()!=null){
	     	   authorities = userdetail.getAuthorities();
	        }
		}
		if ((null == authorities)
				|| (authorities.length < 1)) {
			return Collections.EMPTY_LIST;
		}
		return Arrays.asList(authorities);
	}

	private Set copy(Collection c) {
		Set target = new HashSet();
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			target.add(iterator.next());
		}
		return target;
	}

	public void setRes(String res) {
		this.res = res;
	}

}

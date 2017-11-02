package commonWeb.security.resource;

import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.dom4j.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import commonWeb.security.vo.Resources;

/**
 * @author 
 *
 */
public class DataBaseFilterInvocationDefinitionSource extends
		AbstractFilterInvocationDefinitionSource implements InitializingBean {

	static Logger log = Logger.getLogger(DataBaseFilterInvocationDefinitionSource.class.getName());
	
	private boolean convertUrlToLowercaseBeforeComprison = false;
	private boolean useAntPath = false;
	private PathMatcher pathMatcher = new AntPathMatcher();
	private PatternMatcher matcher = new Perl5Matcher();
	private ResourceLoader resourceLoader;


	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		resourceLoader.initData();
		Assert.notNull(resourceLoader, "ResourceLoader Object in DataBaseFilterInvocationDefinitionSource is required");
	}

	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/* (non-Javadoc)
	 * @see org.security.intercept.web.AbstractFilterInvocationDefinitionSource#lookupAttributes(java.lang.String)
	 */
	@Override
	public ConfigAttributeDefinition lookupAttributes(String url) {
		//初始化数据，从数据库读取
		//cacheManager.initResourceInCache();
		if (isUseAntPath()) {
			int firstQuestionMarkIndex = url.lastIndexOf("?");
			if (firstQuestionMarkIndex != -1) {
				url = url.substring(0, firstQuestionMarkIndex);
			}
		}
		//将URL在比较前都转换为小写
		if (isConvertUrlToLowercaseBeforeComprison()) {
			url = url.toLowerCase();
		}
		
		GrantedAuthority[] authorities = new GrantedAuthority[0];
		Element policyrule = null;
		String requestobjectidname = null;
		String requestobjecttype = null;
		String requestcontaineridname = null;
		String requestcontainertype = null;
		
		//获取所有的URL
		//List<String> urls = cacheManager.getUrlResources();
        List<String> urls = resourceLoader.getRestringByType(Resources.ResType_URL);
		
        if(urls!=null&&urls.size()>0){
    	   //倒叙排序--如果不进行排序，如果用户使用浏览器的导航工具访问页面可能出现问题
    	   //例如：访问被拒绝后用户刷新页面
        	// TODO 暂时不加这个功能，因为不理解这个case，如果需要做，也应该在初始化的时候做，
        	// 因为这里会每次访问都做一次，显然有性能问题
//		   Collections.sort(urls);
//		   Collections.reverse(urls);
		   
		   //将请求的URL与配置的URL资源进行匹配，并将正确匹配的URL资源对应的权限
		   //取出
		   String match_resourceName_url = null;
		   for(String resourceName_url : urls) {
			   if (isConvertUrlToLowercaseBeforeComprison()) {
				   match_resourceName_url = resourceName_url.toLowerCase();
			   }else {
				   match_resourceName_url = resourceName_url;
			   }
			   boolean matched = false;
			   //使用ant匹配URL
			   if (isUseAntPath()) {
				   matched = pathMatcher.match(match_resourceName_url, url);
			   } else {//perl5编译URL
				   Pattern compliedPattern = null;
				   Perl5Compiler compiler = new Perl5Compiler();
				   try {
					  compliedPattern = compiler.compile(match_resourceName_url, Perl5Compiler.READ_ONLY_MASK);
				   } catch (MalformedPatternException e) {
					   log.error("error in:DataBaseFilterInvocationDefinitionSource"+e);
				   }
				   matched = matcher.matches(url, compliedPattern);
			   }
			   //匹配正确,获取响应权限
			   if (matched) {
				   //获取正确匹配URL资源对应的权限
				   //ResourcDetail detail = cacheManager.getResourcDetailFromCache(resourceName_url);
				   ResourceInf resc = resourceLoader.get(resourceName_url);
				   authorities = resc.getAuthorities();
				   policyrule = resc.getPolicyRule();
				   requestobjectidname = resc.getRequestObjectidName();
				   requestobjecttype = resc.getRequestObjecttype();
				   requestcontaineridname = resc.getRequestContaineridname();
				   requestcontainertype = resc.getRequestContainertype();
				   break;
			   }
			}
        }

		//将权限封装成ConfigAttributeDefinition对象返回（使用ConfigAttributeEditor）
		if (authorities!=null && authorities.length > 0) {
			String authTemp = "";
			for (GrantedAuthority grantedAuthority : authorities) {
				authTemp += grantedAuthority.getAuthority() + ",";
			}
			String authority = authTemp.substring(0, (authTemp.length() - 1));
			ConfigAttributeEditorEx attributeEditor = new ConfigAttributeEditorEx();
			attributeEditor.setAsText(authority.trim(), policyrule, requestobjectidname, requestobjecttype, requestcontaineridname, requestcontainertype);
			return (ConfigAttributeDefinitionEx)attributeEditor.getValue();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.security.intercept.web.AbstractFilterInvocationDefinitionSource#getConfigAttributeDefinitions()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection getConfigAttributeDefinitions() {
		return null;
	}

	/**
	 * @return the convertUrlToLowercaseBeforeComprison
	 */
	public boolean isConvertUrlToLowercaseBeforeComprison() {
		return convertUrlToLowercaseBeforeComprison;
	}

	/**
	 * @param convertUrlToLowercaseBeforeComprison the convertUrlToLowercaseBeforeComprison to set
	 */
	public void setConvertUrlToLowercaseBeforeComprison(
			boolean convertUrlToLowercaseBeforeComprison) {
		this.convertUrlToLowercaseBeforeComprison = convertUrlToLowercaseBeforeComprison;
	}

	/**
	 * @return the useAntPath
	 */
	public boolean isUseAntPath() {
		return useAntPath;
	}

	/**
	 * @param useAntPath the useAntPath to set
	 */
	public void setUseAntPath(boolean useAntPath) {
		this.useAntPath = useAntPath;
	}

	/**
	 * @return the pathMatcher
	 */
	public PathMatcher getPathMatcher() {
		return pathMatcher;
	}

	/**
	 * @param pathMatcher the pathMatcher to set
	 */
	public void setPathMatcher(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	/**
	 * @return the matcher
	 */
	public PatternMatcher getMatcher() {
		return matcher;
	}

	/**
	 * @param matcher the matcher to set
	 */
	public void setMatcher(PatternMatcher matcher) {
		this.matcher = matcher;
	}

}

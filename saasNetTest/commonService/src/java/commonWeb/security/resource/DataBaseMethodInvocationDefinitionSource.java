package commonWeb.security.resource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.springframework.util.Assert;

import commonWeb.security.vo.Resources;


public class DataBaseMethodInvocationDefinitionSource extends
		AbstractMethodDefinitionSource implements InitializingBean{

	//private SecurityCacheManager securityCacheManager;
	private ResourceLoader resourceLoader;

	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void afterPropertiesSet() throws Exception {
		resourceLoader.initData();
		Assert.notNull(resourceLoader, "ResourceLoader Object in DataBaseMethodInvocationDefinitionSource is required");
	}

	@SuppressWarnings("unchecked")
	public ConfigAttributeDefinition lookupAttributes(Method method, Class targetClass) {
		//获取所有方法资源
		List<String> methods = resourceLoader.getRestringByType(Resources.ResType_MENU);

		//权限集合
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		List<Element> policyruleList = null;
		String requestobjectidname = null;
		String requestobjecttype = null;
		String requestcontaineridname = null;
		String requestcontainertype = null;

		//遍历方法资源，并获取匹配的资源名称，然后从缓存中获取匹配正确
		//的资源对应的权限（ResourcDetail对象的GrantedAuthority[]对象数据）
		if(methods!=null && methods.size()>0){
			policyruleList = new ArrayList<Element>();
			for (String resourceName_method : methods) {
				if (isMatch(targetClass, method, resourceName_method)) {
					ResourceInf resc = resourceLoader.get(resourceName_method);
					if (resc == null) {
						continue;
					}
					GrantedAuthority[] authorities = resc.getAuthorities();
					if(authorities!=null&&authorities.length>0){
					    authSet.addAll(Arrays.asList(authorities));
					}
					if(resc.getPolicyRule()!=null){
						policyruleList.add(resc.getPolicyRule());
					}
					requestobjectidname = resc.getRequestObjectidName();
					requestobjecttype = resc.getRequestObjecttype();
					requestcontaineridname = resc.getRequestContaineridname();
					requestcontainertype = resc.getRequestContainertype();
				}
			}
		}
		if (authSet.size() > 0) {
			String authString = "";
			for (GrantedAuthority grantedAuthority : authSet) {
				authString += grantedAuthority.getAuthority() + ",";
			}
			String authority = authString.substring(0, (authString.length() - 1));
			ConfigAttributeEditorEx attributeEditor = new ConfigAttributeEditorEx();
			attributeEditor.setAsText(authority.trim(), policyruleList, requestobjectidname, requestobjecttype, requestcontaineridname, requestcontainertype);
			return (ConfigAttributeDefinitionEx)attributeEditor.getValue();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static boolean isMatch(Class clszz, Method method, String resourceMethod) {
		boolean isMatch = true;
		try {
			int lastDotIndex = resourceMethod.lastIndexOf('.');
			String className = resourceMethod.substring(0, lastDotIndex);
			String methodName = resourceMethod.substring(lastDotIndex + 1);

			// 判断类是否相等
			if (!clszz.getName().equals(className))
				isMatch = false;

			// 判断接口是否相等
			Class[] interfaces = clszz.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				Class inf = interfaces[i];
				if (inf.getName().equals(className)) {
					isMatch = true;
				}
			}

			// 判断方法是否相等
			if (isMatch
					&& !(method.getName().equals(methodName)
					|| (methodName.endsWith("*") && method.getName().startsWith(
					methodName.substring(0, methodName.length() - 1))) || (methodName.startsWith("*") && method
					.getName().endsWith(methodName.substring(1, methodName.length())))))
				isMatch = false;

		} catch (Exception e) {
			isMatch = false;
		}
		return isMatch;
	}

	@SuppressWarnings("unchecked")
	public Collection getConfigAttributeDefinitions() {
		return null;
	}
	
}

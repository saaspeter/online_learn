package netTest.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import commonTool.util.AbstractResourceBundleUtil;

public class ResourceBundleUtil extends AbstractResourceBundleUtil{
	
	public static final String bundleName_java = "netTest.resource.ConstantResources";
	
	public static final String bundleName_struts_PageKey = "netTestWeb.resource.PagetextResources";
	
	public static final String bundleName_struts_BizKey = "netTestWeb.resource.BizMessResources";
	
	private static ResourceBundleUtil instance;
	
	@Override
	protected List<String> getBundleNameByType(String type) {
		List<String> list = new ArrayList<String>();
		if(bundleName_java.equals(type) || bundleName_struts_PageKey.equals(type)
				|| bundleName_struts_BizKey.equals(type)){
			list.add(type);
			return list;
		}else {
			list.add(bundleName_java);
			list.add(bundleName_struts_PageKey);
			list.add(bundleName_struts_BizKey);
			return list;
		}
	}

	/**
	 * 根据key和用户的Locale得到该key对应的值
	 * @param key:资源标识，由："Constant类型.variableName"构成
	 * @param locale
	 * @return
	 */
	public String getValue(String key,Locale locale){
		return getValue(key, locale, null, null);
	}
	
	public String getValue(String key, Locale locale, List<String> paraList){
		String rtnStr = getValue(key, locale, paraList, null);
	    return rtnStr;
	}
	
	public static ResourceBundleUtil getInstance(){
		if(instance == null){
			instance = new ResourceBundleUtil();
		}
		return instance;
	}

}

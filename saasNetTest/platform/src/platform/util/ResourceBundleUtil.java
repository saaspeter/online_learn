package platform.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import commonTool.util.AbstractResourceBundleUtil;

public class ResourceBundleUtil extends AbstractResourceBundleUtil{
	
	public static final String bundleName_java = "platform.resource.ConstantResources";
		
	public static final String bundleName_struts_default = "platformWeb.resource.PagetextResources";
	
	public static final String bundleName_struts_BaseKey = "platformWeb.resource.LogicMessResources";
	
	private static ResourceBundleUtil instance;
	
	
	@Override
	protected List<String> getBundleNameByType(String type) {
		List<String> list = new ArrayList<String>();
		if(bundleName_java.equals(type) || bundleName_struts_default.equals(type)
				|| bundleName_struts_BaseKey.equals(type)){
			list.add(type);
			return list;
		}else {
			list.add(bundleName_java);
			list.add(bundleName_struts_default);
			list.add(bundleName_struts_BaseKey);
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        //Locale locale = new Locale("zh","CN");
        String name1 = ResourceBundleUtil.getInstance().getValue("ProductCategoryConstant.leve", null);
        System.out.println("顶级目录为：：："+name1);
	}

}

package commonWeb.ui.taglib.struts.tool;

import org.apache.struts.taglib.TagUtils;

public class HtmlFilter {
	
	public HtmlFilter(){}
	
	/**
	 * 过滤最后写到jsp的值
	 * @param value
	 * @return
	 */
	public String filter(String value){
		value = TagUtils.getInstance().filter(value);
		if(value!=null){
			value = value.replaceAll("\r\n", "<br>");
		}
		return value;
	}
	
}

package commonWeb.security.resource;

import java.util.List;

import org.dom4j.Element;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.util.StringUtils;

public class ConfigAttributeEditorEx extends ConfigAttributeEditor {

	public void setAsText(String s, Element policyrule, String requestobjectidname, String requestobjecttype, String requestcontaineridname, String requestcontainertype) throws IllegalArgumentException {
        if (StringUtils.hasText(s)) {
            setValue(new ConfigAttributeDefinitionEx(
            		 StringUtils.commaDelimitedListToStringArray(s), policyrule, requestobjectidname, requestobjecttype, requestcontaineridname, requestcontainertype));
        } else if(policyrule!=null){
        	setValue(new ConfigAttributeDefinitionEx(null, policyrule, requestobjectidname, requestobjecttype, requestcontaineridname, requestcontainertype));
        } else {
            setValue(null);
        }
    }
	
	public void setAsText(String s, List<Element> policyruleList, String requestobjectidname, String requestobjecttype, String requestcontaineridname, String requestcontainertype) throws IllegalArgumentException {
        if (StringUtils.hasText(s)) {
            setValue(new ConfigAttributeDefinitionEx(
            		 StringUtils.commaDelimitedListToStringArray(s), policyruleList, requestobjectidname, requestobjecttype, requestcontaineridname, requestcontainertype));
        } else if(policyruleList!=null && policyruleList.size()>0){
        	setValue(new ConfigAttributeDefinitionEx(null, policyruleList, requestobjectidname, requestobjecttype, requestcontaineridname, requestcontainertype));
        } else {
            setValue(null);
        }
    }
	
}

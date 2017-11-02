package commonWeb.security.resource;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Element;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;

public class ConfigAttributeDefinitionEx extends ConfigAttributeDefinition {

	private String requestobjectidname;
	
	private String requestobjecttype;
	
	/** request请求参数中可能直接包含containeridname和containertype **/
	private String requestcontaineridname;
	
	private String requestcontainertype;
	
	private List<Element> policyruleList;
	
	
	public ConfigAttributeDefinitionEx(ConfigAttribute attribute) {
		super(attribute);
	}
	
    public ConfigAttributeDefinitionEx(String[] attributeTokens, Element policyrule, 
    								   String requestobjectidname, String requestobjecttype, 
    								   String requestcontaineridname, String requestcontainertype) 
    {
        super(attributeTokens);
        // set policy rule element
        if(policyrule!=null){
            policyruleList = new ArrayList<Element>();
            policyruleList.add(policyrule);
        }
        this.requestobjectidname = requestobjectidname;
        this.requestobjecttype = requestobjecttype;
        this.requestcontaineridname = requestcontaineridname;
        this.requestcontainertype = requestcontainertype;
    }
    
    public ConfigAttributeDefinitionEx(String[] attributeTokens, List<Element> policyruleList,
    									 String requestobjectidname, String requestobjecttype, 
    									 String requestcontaineridname, String requestcontainertype) 
    {
        super(attributeTokens);
        // set policy rule element
        this.policyruleList = policyruleList;
        this.requestobjectidname = requestobjectidname;
        this.requestobjecttype = requestobjecttype;
        this.requestcontaineridname = requestcontaineridname;
        this.requestcontainertype = requestcontainertype;
    }
    
    public String getRequestObjectidName() {
    	return requestobjectidname;
    }
    
    public String getRequestObjecttype() {
    	return requestobjecttype;
    }

	public List<Element> getPolicyruleList() {
		return policyruleList;
	}

	public String getRequestContaineridname() {
		return requestcontaineridname;
	}

	public String getRequestContainertype() {
		return requestcontainertype;
	}

}

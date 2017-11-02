/*
 * $Id: CheckboxTag.java 54929 2004-10-16 16:38:42Z germuska $ 
 *
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package commonWeb.ui.taglib.struts;

import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

import commonTool.util.ObjUtil;
import commonTool.util.StringUtil;

/**
 * Tag for input fields of type "checkbox".
 * modify by peter -- 2008.12.30 based struts 1.2.7
 * @version $Rev: 54929 $ $Date: 2004-10-16 17:38:42 +0100 (Sat, 16 Oct 2004) $
 */
public class CheckboxTag extends BaseHandlerTag {

    // ----------------------------------------------------- Instance Variables

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

    /**
     * The name of the bean containing our underlying property.
     */
    protected String name;

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The property name for this field.
     */
    protected String property = null;
    
    /** for the name in the <input type="checkbox" name=""> tag **/
    protected String tagName = null;

    /**
     * The body content of this tag (if any).
     */
    protected String text = null;

    /**
     * The server value for this option.
     */
    protected String value = null;
    
    /** get the value from nameVal and propertyVal,if there isnot a value been set. **/
    protected String nameVal = null;
    
    /** get the value from nameVal and propertyVal,if there isnot a value been set. **/
    protected String propertyVal = null;
    
    /** check if the value is in the checkArr,if it is,then check it. separate by comma **/
    protected String checkStr = null;

    // ------------------------------------------------------------- Properties

    /**
     * Return the property name.
     */
    public String getProperty() {

        return (this.property);

    }

    /**
     * Set the property name.
     *
     * @param property The new property name
     */
    public void setProperty(String property) {

        this.property = property;

    }

    /**
     * Return the server value.
     */
    public String getValue() throws JspException{
        //return value == null ? "on" : value;
        if(this.value==null||this.value.trim().length()<1){
        	this.value = getComplexValue(nameVal,propertyVal);
        }
        return this.value == null ? "" : this.value;
    }

    /**
     * Set the server value.
     *
     * @param value The new server value
     */
    public void setValue(String value) {

        this.value = value;

    }

    // --------------------------------------------------------- Public Methods

    /**
     * Generate the required input tag.
     * <p>
     * Support for indexed property since Struts 1.1
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

        // Create an appropriate "input" element based on our parameters
        StringBuffer results = new StringBuffer("<input type=\"checkbox\"");
        prepareAttribute(results, "name", prepareName());
        prepareAttribute(results, "accesskey", getAccesskey());
        prepareAttribute(results, "tabindex", getTabindex());
        
        prepareAttribute(results, "value", getValue());
        if (isChecked()) {
            results.append(" checked=\"checked\"");
        }

        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        prepareOtherAttributes(results);
        results.append(getElementClose());

        // Print this field to our output writer
        TagUtils.getInstance().write(pageContext, results.toString());

        // Continue processing this page
        this.text = null;
        return (EVAL_BODY_TAG);

    }
    
    /**
     * Determines if the checkbox should be checked.
     * @return true if checked="checked" should be rendered.
     * @throws JspException
     * @since Struts 1.2
     */
	protected boolean isChecked() throws JspException {
		boolean rtn = false;
		// check checkArr,if checkArr is empty,then remain the original logic
		if(checkStr==null||checkStr.trim().length()<1){
			Object result = null;
			if(name!=null&&name.trim().length()>0)
			   result = TagUtils.getInstance().lookup(pageContext, name, property, null);

			if (result == null) {
				result = "";
			}
			result = result.toString();
			String checked = (String) result;
			rtn = (	checked.equalsIgnoreCase(this.value)
						|| checked.equalsIgnoreCase("true")
						|| checked.equalsIgnoreCase("yes")
						|| checked.equalsIgnoreCase("on"));
		}else{
			checkStr = checkStr.trim();
			if(StringUtil.includeStr(checkStr, this.value, null)){ 
				rtn = true;
			}
		}
		
		return rtn;
	}

    /**
     * Save the associated label from the body content.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doAfterBody() throws JspException {

        if (bodyContent != null) {
            String value = bodyContent.getString().trim();
            if (value.length() > 0) {
                text = value;
            }
        }
        return (SKIP_BODY);

    }

    /**
     * Process the remainder of this page normally.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {

        // Render any description for this checkbox
        if (text != null) {
            TagUtils.getInstance().write(pageContext, text);
        }
        // add by peter -- 2009-05-24,因为用logic:iterator循环显示checkbox时，其值总是一样的，不知道struts的checkbox是不是单例的？？
        this.release();
        // Evaluate the remainder of this page
        return (EVAL_PAGE);

    }


    /**
     * Prepare the name element
     * @return The element name.
     */
    protected String prepareName() throws JspException {

        if (property == null&&tagName == null) {
            return null;
        }
        if(tagName!=null&&tagName.trim().length()>0){
        	return tagName;
        }
        // * @since Struts 1.1
        if(indexed) {
            StringBuffer results = new StringBuffer();
            prepareIndex(results, name);
            results.append(property);
            return results.toString();
        }

        return property;

    }

    /**
     * Release any acquired resources.
     */
    public void release() {

        super.release();
        name = null;
        property = null;
        text = null;
        value = null;
        nameVal = null;
        propertyVal = null;
        checkStr = null;
        
    }

	public String getNameVal() {
		return nameVal;
	}

	public void setNameVal(String nameVal) {
		this.nameVal = nameVal;
	}

	public String getPropertyVal() {
		return propertyVal;
	}

	public void setPropertyVal(String propertyVal) {
		this.propertyVal = propertyVal;
	}

	// 
	private String getComplexValue(String nameParam,String propertyParam) throws JspException{
		String retVal = null;
		if(nameParam!=null&&nameParam.trim().length()>0){
			String[] arrs = StringUtil.splitForTag(propertyParam);
	    	if(arrs!=null&&arrs.length>0){
	    		Object resultMem = TagUtils.getInstance().lookup(pageContext, nameParam, arrs[0], null);
	    		if(resultMem!=null&&arrs[1]!=null&&Integer.parseInt(arrs[1])>=0){
					resultMem = ((List)resultMem).get(Integer.parseInt(arrs[1]));
					if(arrs[2]!=null&&arrs[2].trim().length()>0&&resultMem!=null){
					   resultMem = ObjUtil.getProperty(resultMem, arrs[2]);
					}
	    		}
	    		retVal = String.valueOf(resultMem);
	    	}
		}
    	return retVal;
	}

	public String getCheckStr() {
		return checkStr;
	}

	public void setCheckStr(String checkStr) {
		this.checkStr = checkStr;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
}

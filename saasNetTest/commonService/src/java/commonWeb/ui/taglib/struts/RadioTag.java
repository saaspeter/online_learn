/*
 * $Id: RadioTag.java 54929 2004-10-16 16:38:42Z germuska $ 
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
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;
import org.apache.struts.taglib.html.BaseHandlerTag;

import commonTool.util.ObjUtil;
import commonTool.util.StringUtil;

/**
 * Tag for input fields of type "radio".
 *
 * @version $Rev: 54929 $ $Date: 2004-10-16 17:38:42 +0100 (Sat, 16 Oct 2004) $
 */
public class RadioTag extends BaseHandlerTag {


    // ----------------------------------------------------- Instance Variables


    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
     MessageResources.getMessageResources(Constants.Package + ".LocalStrings");


    /**
     * The name of the bean containing our underlying property.
     */
    protected String name = Constants.BEAN_KEY;

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

    /** for the name in the <input type="radio" name=""> tag **/
    protected String tagName = null;

    /**
     * The body content of this tag (if any).
     */
    protected String text = null;


    /**
     * The server value for this option.
     */
    protected String value = null;


    /**
     * Name of the bean (in some scope) that will return the
     * value of the radio tag.
     * <p>
     * If an iterator is used to render a series of radio tags,
     * this field may be used to specify the name of the bean
     * exposed by the iterator. In this case, the value attribute is
     * used as the name of a property on the <code>idName</code> bean
     * that returns the value of the radio tag in this iteration.
     */
    protected String idName = null;

    /** get the value from nameVal and propertyVal,if there isnot a value been set. **/
    protected String nameVal = null;
    
    /** get the value from nameVal and propertyVal,if there isnot a value been set. **/
    protected String propertyVal = null;

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
    public String getValue() {

        return (this.value);

    }

    /**
     * Set the server value.
     *
     * @param value The new server value
     */
    public void setValue(String value) {

        this.value = value;

    }

    /**
     * Return the idName.
     * @since Struts 1.1
     */
    public String getIdName() {

        return (this.idName);

    }

    /**
     * Set the idName.
     * @since Struts 1.1
     *
     * @param idName The new idName
     */
    public void setIdName(String idName) {

        this.idName=idName;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Generate the required input tag.
     * [Indexed property since Struts 1.1]
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

        String radioTag = renderRadioElement(serverValue(), currentValue());

        TagUtils.getInstance().write(pageContext, radioTag);

        this.text = null;
        return (EVAL_BODY_TAG);
    }

    /**
     * Return the String to be used in the radio tag's <code>value</code> attribute 
     * that gets sent to the server on form submission.
     * @throws JspException
     */
    private String serverValue() throws JspException {

    	String serverValue = null;
        // Not using indexed radio buttons
        if (this.idName == null) {
        	serverValue = this.value;
        }else{
        	serverValue = this.lookupProperty(this.idName, this.value);
        }
        if(serverValue==null||serverValue.trim().length()<1){
        	serverValue = getComplexValue(nameVal,propertyVal);
        }
        return (serverValue == null) ? "" : serverValue;
    }

    /**
     * Acquire the current value of the bean specified by the <code>name</code> 
     * attribute and the property specified by the <code>property</code> attribute.
     * This radio button with this value will be checked.
     * @throws JspException
     */
    private String currentValue() throws JspException {
        //String current = this.lookupProperty(this.name, this.property);
    	String current = getComplexValue(name,property);
        return (current == null) ? "" : current;
    }
    
    /**
     * Renders an HTML &lt;input type="radio"&gt; element.
     * @param serverValue The data to be used in the tag's <code>value</code> 
     * attribute and sent to the server when the form is submitted.
     * @param checkedValue If the serverValue equals this value the radio button 
     * will be checked.
     * @return A radio input element.
     * @throws JspException
     * @since Struts 1.1
     */
    protected String renderRadioElement(String serverValue, String checkedValue)
        throws JspException {
            
        StringBuffer results = new StringBuffer("<input type=\"radio\"");

        prepareAttribute(results, "name", prepareName());
        prepareAttribute(results, "accesskey", getAccesskey());
        prepareAttribute(results, "tabindex", getTabindex());
        prepareAttribute(results, "value", serverValue);
        if (serverValue.equals(checkedValue)) {
            results.append(" checked=\"checked\"");
        }
        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        prepareOtherAttributes(results);
        results.append(getElementClose());
        return results.toString();
    }

    /**
     * Save the associated label from the body content.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doAfterBody() throws JspException {

        if (this.bodyContent != null) {
            String value = this.bodyContent.getString().trim();
            if (value.length() > 0) {
                this.text = value;
            }
        }
        
        return (SKIP_BODY);
    }

    /**
     * Optionally render the associated label from the body content.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {

        // Render any description for this radio button
        if (this.text != null) {
            TagUtils.getInstance().write(pageContext, text);
        }
        // add by peter -- 2009-05-28
        this.release();
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
        idName = null;
        name = Constants.BEAN_KEY;
        property = null;
        text = null;
        value = null;
        nameVal = null;
        propertyVal = null;
        
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
	private String getComplexValue(String nameVal,String propertyVal) throws JspException{
		String retVal = null;
		if(nameVal!=null&&nameVal.trim().length()>0){
			String[] arrs = StringUtil.splitForTag(propertyVal);
	    	if(arrs!=null&&arrs.length>0){
	    		Object resultMem = TagUtils.getInstance().lookup(pageContext, nameVal, arrs[0], null);
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

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}

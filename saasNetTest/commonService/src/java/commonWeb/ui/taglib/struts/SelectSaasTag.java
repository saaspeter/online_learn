/*
 * $Id: SelectTag.java 54929 2004-10-16 16:38:42Z germuska $ 
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

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.SelectTag;

/**
 * Custom tag that represents an HTML select element, associated with a
 * bean property specified by our attributes.  This tag must be nested
 * inside a form tag.
 *
 * @version $Rev: 54929 $ $Date: 2004-10-16 17:38:42 +0100 (Sat, 16 Oct 2004) $
 */
public class SelectSaasTag extends SelectTag {


    // ----------------------------------------------------- Instance Variables
    
    /** for the name in the <select name=""> tag **/
    protected String tagName = null;
    
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
        match = null;
        multiple = null;
        name = Constants.BEAN_KEY;
        property = null;
        saveBody = null;
        size = null;
        value = null;
        tagName = null;
    }

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}

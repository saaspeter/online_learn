/*
 * $Id: OptionsCollectionTag.java 376841 2006-02-10 21:01:28Z husted $
 *
 * Copyright 2002-2004 The Apache Software Foundation.
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

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.SelectTag;
import org.apache.struts.util.IteratorAdapter;
import org.apache.struts.util.MessageResources;
import org.springframework.security.context.SecurityContextHolder;

import commonTool.biz.logic.ConstantInf;
import commonTool.biz.logic.I18nLogic;
import commonTool.biz.logicImpl.ConstantLogicImpl;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.util.ObjUtil;
import commonWeb.base.KeyInMemConstBase;

/**
 * Tag for creating multiple &lt;select&gt; options from a collection. The
 * collection may be part of the enclosing form, or may be independent of the
 * form. Each element of the collection must expose a 'label' and a 'value',
 * the property names of which are configurable by attributes of this tag. <p>
 * The collection may be an array of objects, a Collection, an Enumeration, an
 * Iterator, or a Map. <p> <b>NOTE</b> - This tag requires a Java2 (JDK 1.2 or
 * later) platform.
 *
 * @version $Rev: 376841 $ $Date: 2004-11-03 14:20:47 -0500 (Wed, 03 Nov 2004)
 *          $
 * @since Struts 1.1
 */
public class OptionsTag extends TagSupport {
    // ----------------------------------------------------- Instance Variables

	static Logger log = Logger.getLogger(OptionsTag.class.getName());
	
    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources(Constants.Package
            + ".LocalStrings");

    // ------------------------------------------------------------- Properties

    /**
     * Should the label values be filtered for HTML sensitive characters?
     */
    protected boolean filter = true;

    /**
     * The name of the bean property containing the label.
     */
    protected String label = "label";

    /**
     * The name of the bean containing the values collection.
     */
    protected String name = null;

    /**
     * The name of the property to use to build the values collection.
     */
    protected String property = null;

    /**
     * The style associated with this tag.
     */
    private String style = null;

    /**
     * The named style class associated with this tag.
     */
    private String styleClass = null;

    /**
     * The name of the bean property containing the value.
     */
    protected String value = "value";
    
    /**
     * The const code for label,add by peter 2008.09.02
     */
    protected String consCode = null;
    
    /**
     * param1 to filter lableList,add by peter 2008.09.02
     */
    protected String param1 = null;
    
    /**
     * param2 to filter lableList,add by peter 2008.09.02
     */
    protected String param2 = null;
    
    /**
     * removeStr to remove the designate Items using value,it use "," to split the values
     */
    protected String removeStr = null;
    
    /**
     * whether it is the localeList setting
     */
    protected boolean localeListSet = false;
    /** 'selectlocale': means user selected locale, 
     * 'userlocale': means user defined locale in user setting, 
     * 'inputlocale': means use input localeid, see localeid parameter
     * default is 'userlocale' **/
    protected String localetype = null;
    
    protected String localeid = null;
    

    public boolean getFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Process the start of this tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        // Acquire the select tag we are associated with
        SelectTag selectTag =
            (SelectTag) pageContext.getAttribute(Constants.SELECT_KEY);

        if (selectTag == null) {
            JspException e =
                new JspException(messages.getMessage(
                        "optionsCollectionTag.select"));

            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        // Acquire the collection containing our options
        Object collection = null;
        Long localeidLong = null;
        if(localeid!=null&&localeid.trim().length()>0){
        	localeidLong = new Long(localeid);
        }
        if(localeListSet){ // if it is the localList then fetch it directly
        	Locale locale = null;
        	if(localeidLong==null){
        		if("selectlocale".equals(localetype)){
            		locale = (Locale)pageContext.getSession().getAttribute(KeyInMemConstBase.SessionKey_LocaleUserSelect);
            	}else {
            	    Object obj1 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                	if(obj1!=null){
                		locale = (Locale)ObjUtil.getProperty(obj1, "locale");
                	}
            	}
        	}else {
        		locale = I18nLogicImpl.getLocale(localeidLong);
        	}
        	
        	try {
				I18nLogic logic = I18nLogicImpl.getInstance();
				collection = logic.getLabelListFilter(locale,removeStr);
			} catch (Exception e) {
				log.error("Error in Class:OptionsTag||method:doStartTag||", e);
			}
        }else if(consCode!=null&&consCode.trim().length()>0){
        	try {
        		if(localeidLong==null){
        			if("selectlocale".equals(localetype)){
            			localeidLong = (Long)pageContext.getSession().getAttribute(KeyInMemConstBase.SessionKey_LocaleidUserSelect);
                	}else {
                	    Object obj2 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    	if(obj2!=null){
                    		localeidLong = (Long)ObjUtil.getProperty(obj2, "localeid");
                    	}
                	}
        		}
        		
				if(localeidLong!=null)
				{
					ConstantInf inf = ConstantLogicImpl.getInstance();
					collection = inf.getLabelList(consCode,localeidLong,removeStr,param1,param2);
				}
			} catch (Exception e) {
				log.error("Error in Class:OptionsTag||method:doStartTag||", e);
			}	
        }else if(name!=null&&name.trim().length()>0){
            collection = TagUtils.getInstance().lookup(pageContext, name, property, null);
        }
        
        if (collection == null) {
            JspException e =
                new JspException(messages.getMessage(
                        "optionsCollectionTag.collection"));

            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        // Acquire an iterator over the options collection
        Iterator iter = getIterator(collection);

        StringBuffer sb = new StringBuffer();

        // Render the options
        while (iter.hasNext()) {
            Object bean = iter.next();
            Object beanLabel = null;
            Object beanValue = null;

            // Get the label for this option
            try {
                beanLabel = PropertyUtils.getProperty(bean, label);

                if (beanLabel == null) {
                    beanLabel = "";
                }
            } catch (IllegalAccessException e) {
                JspException jspe =
                    new JspException(messages.getMessage("getter.access",
                            label, bean));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                JspException jspe =
                    new JspException(messages.getMessage("getter.result",
                            label, t.toString()));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            } catch (NoSuchMethodException e) {
                JspException jspe =
                    new JspException(messages.getMessage("getter.method",
                            label, bean));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            }

            // Get the value for this option
            try {
                beanValue = PropertyUtils.getProperty(bean, value);

                if (beanValue == null) {
                    beanValue = "";
                }
            } catch (IllegalAccessException e) {
                JspException jspe =
                    new JspException(messages.getMessage("getter.access",
                            value, bean));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                JspException jspe =
                    new JspException(messages.getMessage("getter.result",
                            value, t.toString()));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            } catch (NoSuchMethodException e) {
                JspException jspe =
                    new JspException(messages.getMessage("getter.method",
                            value, bean));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            }

            String stringLabel = beanLabel.toString();
            String stringValue = beanValue.toString();

            // Render this option
            addOption(sb, stringLabel, stringValue,
                selectTag.isMatched(stringValue));
        }

        TagUtils.getInstance().write(pageContext, sb.toString());

        return SKIP_BODY;
    }

    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
        filter = true;
        label = "label";
        name = Constants.BEAN_KEY;
        property = null;
        style = null;
        styleClass = null;
        value = "value";
        consCode = null;
        param1 = null;
        param2 = null;
        removeStr = null;
        localeListSet = false;
        localetype = null;
        localeid = null;
    }

    // ------------------------------------------------------ Protected Methods

    /**
     * Add an option element to the specified StringBuffer based on the
     * specified parameters. <p> Note that this tag specifically does not
     * support the <code>styleId</code> tag attribute, which causes the HTML
     * <code>id</code> attribute to be emitted.  This is because the HTML
     * specification states that all "id" attributes in a document have to be
     * unique.  This tag will likely generate more than one
     * <code>option</code> element element, but it cannot use the same
     * <code>id</code> value.  It's conceivable some sort of mechanism to
     * supply an array of <code>id</code> values could be devised, but that
     * doesn't seem to be worth the trouble.
     *
     * @param sb      StringBuffer accumulating our results
     * @param value   Value to be returned to the server for this option
     * @param label   Value to be shown to the user for this option
     * @param matched Should this value be marked as selected?
     */
    protected void addOption(StringBuffer sb, String label, String value,
        boolean matched) {
        sb.append("<option value=\"");

        if (filter) {
            sb.append(TagUtils.getInstance().filter(value));
        } else {
            sb.append(value);
        }

        sb.append("\"");

        if (matched) {
            sb.append(" selected=\"selected\"");
        }

        if (style != null) {
            sb.append(" style=\"");
            sb.append(style);
            sb.append("\"");
        }

        if (styleClass != null) {
            sb.append(" class=\"");
            sb.append(styleClass);
            sb.append("\"");
        }

        sb.append(">");

        if (filter) {
            sb.append(TagUtils.getInstance().filter(label));
        } else {
            sb.append(label);
        }

        sb.append("</option>\r\n");
    }

    /**
     * Return an iterator for the options collection.
     *
     * @param collection Collection to be iterated over
     * @throws JspException if an error occurs
     */
    protected Iterator getIterator(Object collection)
        throws JspException {
        if (collection.getClass().isArray()) {
            collection = Arrays.asList((Object[]) collection);
        }

        if (collection instanceof Collection) {
            return (((Collection) collection).iterator());
        } else if (collection instanceof Iterator) {
            return ((Iterator) collection);
        } else if (collection instanceof Map) {
            return (((Map) collection).entrySet().iterator());
        } else if (collection instanceof Enumeration) {
            return new IteratorAdapter((Enumeration) collection);
        } else {
            throw new JspException(messages.getMessage(
                    "optionsCollectionTag.iterator", collection.toString()));
        }
    }

	public String getConsCode() {
		return consCode;
	}

	public void setConsCode(String consCode) {
		this.consCode = consCode;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getRemoveStr() {
		return removeStr;
	}

	public void setRemoveStr(String removeStr) {
		this.removeStr = removeStr;
	}

	public boolean isLocaleListSet() {
		return localeListSet;
	}

	public void setLocaleListSet(boolean localeListSet) {
		this.localeListSet = localeListSet;
	}

	public String getLocaletype() {
		return localetype;
	}

	public void setLocaletype(String localetype) {
		this.localetype = localetype;
	}

}

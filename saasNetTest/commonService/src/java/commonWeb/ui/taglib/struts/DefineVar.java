package commonWeb.ui.taglib.struts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 修改struts中bean:define标签，改动的为207行，使得define标签如果查不到指定的值，则可以默认为null，原来的逻辑是抛出exception
 * @author fan
 *
 */
public class DefineVar extends BodyTagSupport{

    /**
     * Commons logging instance.
     */
    private static final Log log = LogFactory.getLog(DefineVar.class);

    // ---------------------------------------------------- Protected variables

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources(
            "org.apache.struts.taglib.bean.LocalStrings");

    /**
     * The body content of this tag (if any).
     */
    protected String body = null;

    // ------------------------------------------------------------- Properties

    /**
     * The name of the scripting variable that will be exposed as a page scope
     * attribute.
     */
    protected String id = null;

    /**
     * The name of the bean owning the property to be exposed.
     */
    protected String name = null;

    /**
     * The name of the property to be retrieved.
     */
    protected String property = null;

    /**
     * The scope within which to search for the specified bean.
     */
    protected String scope = null;

    /**
     * The scope within which the newly defined bean will be creatd.
     */
    protected String toScope = null;

    /**
     * The fully qualified Java class name of the value to be exposed.
     */
    protected String type = null;

    /**
     * The (String) value to which the defined bean will be set.
     */
    protected String value = null;

    public String getId() {
        return (this.id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return (this.property);
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getScope() {
        return (this.scope);
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToScope() {
        return (this.toScope);
    }

    public void setToScope(String toScope) {
        this.toScope = toScope;
    }

    public String getType() {
        return (this.type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return (this.value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Check if we need to evaluate the body of the tag
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        return (EVAL_BODY_TAG);
    }

    /**
     * Save the body content of this tag (if any), or throw a JspException if
     * the value was already defined.
     *
     * @throws JspException if value was defined by an attribute
     */
    public int doAfterBody() throws JspException {
        if (bodyContent != null) {
            body = bodyContent.getString();

            if (body != null) {
                body = body.trim();
            }

            if (body.length() < 1) {
                body = null;
            }
        }

        return (SKIP_BODY);
    }

    /**
     * Retrieve the required property and expose it as a scripting variable.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
        // Enforce restriction on ways to declare the new value
        int n = 0;

        if (this.body != null) {
            n++;
        }

        if (this.name != null) {
            n++;
        }

        if (this.value != null) {
            n++;
        }

        if (n > 1) {
            JspException e =
                new JspException(messages.getMessage("define.value", id));

            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        // Retrieve the required property value
        Object value = this.value;

        if ((value == null) && (name != null)) {
            value =
                TagUtils.getInstance().lookup(pageContext, name, property, scope);
        }

        if ((value == null) && (body != null)) {
            value = body;
        }

        // if value is null,then let it be null
//        if (value == null) {
//            JspException e =
//                new JspException(messages.getMessage("define.null", id));
//
//            TagUtils.getInstance().saveException(pageContext, e);
//            throw e;
//        }

        // Expose this value as a scripting variable
        int inScope = PageContext.PAGE_SCOPE;

        try {
            if (toScope != null) {
                inScope = TagUtils.getInstance().getScope(toScope);
            }
        } catch (JspException e) {
            log.warn("toScope was invalid name so we default to PAGE_SCOPE", e);
        }

        pageContext.setAttribute(id, value, inScope);

        // Continue processing this page
        return (EVAL_PAGE);
    }

    /**
     * Release all allocated resources.
     */
    public void release() {
        super.release();
        body = null;
        id = null;
        name = null;
        property = null;
        scope = null;
        toScope = "page";
        type = null;
        value = null;
    }
	
}

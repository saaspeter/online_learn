/*
 * 改动自struts1.2.7中的IterateTag
 */
package netTestWeb.ui.taglib.ques;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;
import commonTool.util.StringUtil;

/**
 * 实现从用户答案useranswer中取值itemorderstr，分解后得到每个随机数。
 * 然后再根据随机数到题目答案itemList中取相应的答案选项，实现随机。
 * 如果不要求随机，即itemorderstr为空，或者用户答案vo为空，则不随机，按顺序取选项。
 * @version  $Date: 2009-8-30  $
 */

public class IteQuesitemTag extends BodyTagSupport {

    public IteQuesitemTag(){
    	super();
    }
	
	// ----------------------------------------------------- Instance Variables

	protected Iterator usrite = null;
	
	/**
     * Iterator of the elements of this collection, while we are actually
     * running.
     */
    protected String[] itemordArr = null;

    /**
     * The number of elements we have already rendered.
     */
    protected int lengthCount = 0;

    /**
     * The actual length value (calculated in the start tag).
     */
    protected int lengthValue = 0;

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources("org.apache.struts.taglib.logic.LocalStrings");

    /**
     * Has this tag instance been started?
     */
    protected boolean started = false;

    // ------------------------------------------------------------- Properties

    /**
     * The collection over which we will be iterating.
     */
    protected List itemlist = null;

    /** 问题答案vo引用的名字 */
    protected String itemvoid = null;

    /** 答案选项字符串的属性的默认值，对应Useranswer类中的属性 **/
    private final static String ordprop_default = "itemorderstr";

    /**
     * The name of the scripting variable to be exposed as the current index.
     */
    protected String indexId = null;

    /**
     * The length value or attribute name (<=0 means no limit).
     */
    protected String length = null;

    /**
     * 用户题目选项顺序数组的name，一般是指向对象Useranswer。如果不需要乱序答案选项，则此属性为null即可
     */
    protected String ordname = null;
    
    /**
     * 用户题目选项顺序数组的property，默认为Useranswer中的属性itemorderstr
     */
    protected String ordprop = null;
    
    /**
     * 问题答案选项list的name
     */
    protected String listname = null;
    
    /**
     * 问题答案选项list的property
     */
    protected String listprop = null;

    /**
     * The scope of the bean specified by the name property, if any.
     */
    protected String scope = null;

    /**
     * The Java class of each exposed element of the collection.
     */
    protected String type = null;
    
    /**
     * <p>Return the zero-relative index of the current iteration through the
     * loop.  If you specify an <code>offset</code>, the first iteration
     * through the loop will have that value; otherwise, the first iteration
     * will return zero.</p>
     *
     * <p>This property is read-only, and gives nested custom tags access to
     * this information.  Therefore, it is <strong>only</strong> valid in
     * between calls to <code>doStartTag()</code> and <code>doEndTag()</code>.
     * </p>
     */
    public int getIndex() {
        if (started)
            return (lengthCount - 1);
        else
            return (0);
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Construct an iterator for the specified collection, and begin
     * looping through the body once per element.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

    	lengthCount = 0;
    	// Acquire the collection we are going to iterate over
        String itemorderstr = null;
        if (itemlist == null) {
        	itemlist = (List)TagUtils.getInstance().lookup(pageContext, listname, listprop, scope);
        }

        if (itemordArr==null&&ordname!=null&&ordname.trim().length()>0
        		&&TagUtils.getInstance().lookup(pageContext, ordname, getOrdprop(), scope)!=null) 
        {
        	itemorderstr = (String)TagUtils.getInstance().lookup(pageContext, ordname, getOrdprop(), scope);
        	itemordArr = StringUtil.splitString(itemorderstr, ",");
        }

        if (itemlist==null) {
            JspException e = new JspException("question items is null ");
            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }
        
        // 如果得不到用户设置的题目顺序设置，则不需要随机排序答案选项，此时按顺序输出题目答案
        if(itemordArr==null||itemordArr.length<1){
        	itemordArr = new String[itemlist.size()];
        	for(int j = 0;j<itemordArr.length;j++){
        		itemordArr[j] = String.valueOf(j);
        	}
        }
        
        usrite = Arrays.asList((Object[]) itemordArr).iterator();
        // Store the first value and evaluate, or skip the body if none
        if (usrite.hasNext()) {
            int itemord = Integer.parseInt((String)usrite.next()); // 得到用户答案选项的排序数
            Object itemelement = null;
            
            itemelement = itemlist.get(itemord);
        	// 题目选项itemvo
        	if(itemelement!=null){
        		pageContext.removeAttribute(itemvoid);
        		pageContext.setAttribute(itemvoid, itemelement);
        	}else{
        		// 抛异常
        		JspException e = new JspException("the question item is null according to the useranswer");
                TagUtils.getInstance().saveException(pageContext, e);
                throw e;
        	}
            
            lengthCount++;
            started = true;
            if (indexId != null) {
                pageContext.setAttribute(indexId, new Integer(getIndex()));
            }
            return (EVAL_BODY_TAG);
        } else {
            return (SKIP_BODY);
        }

    }

    /**
     * Make the next collection element available and loop, or
     * finish the iterations if there are no more elements.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doAfterBody() throws JspException {

        // Render the output from this iteration to the output stream
        if (bodyContent != null) {
            TagUtils.getInstance().writePrevious(pageContext, bodyContent.getString());
            bodyContent.clearBody();
        }

        // Decide whether to iterate or quit
        if ((lengthValue > 0) && (lengthCount >= lengthValue)) {
            return (SKIP_BODY);
        }

        if (usrite.hasNext()) {
            int itemord = Integer.parseInt((String)usrite.next()); // 得到用户答案选项的排序数
            Object itemelement = null;
            
            itemelement = itemlist.get(itemord);
        	// 题目选项itemvo
        	if(itemelement!=null){
        		pageContext.removeAttribute(itemvoid);
        		pageContext.setAttribute(itemvoid, itemelement);
        	}else{
        		// 抛异常
        		JspException e = new JspException("the question item is null according to the useranswer");
                TagUtils.getInstance().saveException(pageContext, e);
                throw e;
        	}
            
            lengthCount++;
            if (indexId != null) {
                pageContext.setAttribute(indexId, new Integer(getIndex()));
            }
            return (EVAL_BODY_TAG);
        } else {
            return (SKIP_BODY);
        }

    }

    /**
     * Clean up after processing this enumeration.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {

        // Clean up our started state
        started = false;
        usrite = null;
        itemlist = null;
        itemordArr = null;
        // Continue processing this page
        return (EVAL_PAGE);

    }

    /**
     * Release all allocated resources.
     */
    public void release() {
        super.release();
        usrite = null;
        itemlist = null;
        lengthCount = 0;
        lengthValue = 0;
        itemordArr = null;

        id = null;
        length = null;
        ordname = null;
        ordprop = null;
        listname = null;
        scope = null;
        started = false;
        listprop = null;
    }

    public String getLength() {
        return (this.length);
    }

    public void setLength(String length) {
        this.length = length;
    }
    
    public String getIndexId() {
        return (this.indexId);
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }
    
    public String getType() {
        return (this.type);
    }

    public void setType(String type) {
        this.type = type;
    }
    

    public String getScope() {
        return (this.scope);
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

	public List getItemlist() {
		return itemlist;
	}

	public void setItemlist(List itemlist) {
		this.itemlist = itemlist;
	}

	public String[] getItemordArr() {
		return itemordArr;
	}

	public void setItemordArr(String[] itemordArr) {
		this.itemordArr = itemordArr;
	}

	public String getItemvoid() {
		return itemvoid;
	}

	public void setItemvoid(String itemvoid) {
		this.itemvoid = itemvoid;
	}

	public String getOrdname() {
		return ordname;
	}

	public void setOrdname(String ordname) {
		this.ordname = ordname;
	}

	public String getOrdprop() {
		if(ordprop==null||ordprop.trim().length()<1)
			ordprop = ordprop_default;
		return ordprop;
	}

	public void setOrdprop(String ordprop) {
		this.ordprop = ordprop;
	}

	public String getListname() {
		return listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}

	public String getListprop() {
		return listprop;
	}

	public void setListprop(String listprop) {
		this.listprop = listprop;
	}
	
}


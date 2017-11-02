/*
 * 改动自struts1.2.7中的IterateTag
 */
package netTestWeb.ui.taglib.ques;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import netTest.exam.vo.Useranswer;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

import commonTool.util.ObjUtil;

/**
 * 实现根据用户随机问题列表，从题目列表中取题目。
 * queslist中放置的是题目列表。usrList中放置的是useranswer，即题目的随机列表。
 * 根据usrList中的useranswer中的属性quesorder从题目列表quesList中取题目
 * 如果不要求随机取题目，设置usrname为空即可，按顺序选取题目。
 * @version  $Date: 2009-8-30  $
 */

public class IterateQuesTag extends BodyTagSupport {

    // ----------------------------------------------------- Instance Variables

    /**
	 * 
	 */
	private static final long serialVersionUID = -6111820876288719492L;

	/**
     * Iterator of the elements of this collection, while we are actually
     * running.
     */
    protected Iterator usrite = null;

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
     * The actual offset value (calculated in the start tag).
     */
    protected int offsetValue = 0;

    /**
     * Has this tag instance been started?
     */
    protected boolean started = false;

    // ------------------------------------------------------------- Properties

    /**
     * The collection over which we will be iterating.
     */
    protected List usrList = null;

    protected List queslist = null;

    /** 问题vo引用的名字 */
    protected String quesvoid = null;
    /** 用户答案vo引用的名字 **/
    protected String usrvoid = null;
    /** 用户答案vo中的表示题目顺序的字段名，现在是quesorder **/
    protected String quesordname = null;
    
    private final static String quesordname_default = "quesorder";

    /**
     * The name of the scripting variable to be exposed as the current index.
     */
    protected String indexId = null;
    /** 用户useranswer中的题目顺序 **/
    protected String quesordId = null;

    /**
     * The length value or attribute name (<=0 means no limit).
     */
    protected String length = null;

    /**
     * 用户题目顺序集合的name，如为空则顺序取题目列表中的题目，不需要排序
     */
    protected String usrname = null;
    
    /**
     * 用户答案集合的property
     */
    protected String usrprop = null;
    
    /**
     * 试卷问题集合的name
     */
    protected String quesname = null;
    
    /**
     * 试卷问题集合的property
     */
    protected String quesprop = null;

    /**
     * The starting offset (zero relative).
     */
    protected String offset = null;

    /**
     * The scope of the bean specified by the name property, if any.
     */
    protected String scope = null;

    /**
     * quesvoid对应vo的类型，默认为netTest.paper.vo.Paperques
     */
    protected String quetype = null;
    /**
     * usrvoid对应vo的类型，默认为netTest.exam.vo.Useranswer
     */
    protected String usrtype = null;
    /** 是否对用户的题目进行乱序处理，默认为true **/
    protected boolean random = true;

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
        List usrList = this.usrList;
        Object usrObj = null;
        if (usrList==null&&usrname!=null&&usrname.trim().length()>0) {
        	usrObj = TagUtils.getInstance().lookup(pageContext, usrname, usrprop, scope);
        	if(usrObj!=null)
        		usrList = (List)usrObj;
        }
        if (queslist == null) {
        	queslist = (List)TagUtils.getInstance().lookup(pageContext, quesname, quesprop, scope);
        }

        if (queslist==null) {
            JspException e = new JspException(messages.getMessage("iterate.collection"));
            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }
        
        // 如果得不到用户设置的题目顺序，则不需要随机排序答案选项，此时按顺序输出题目答案
        if(usrList==null||usrList.size()<1){
        	usrList = new ArrayList();
        	Useranswer answetTemp = null;
        	for(int j = 0;j<queslist.size();j++){
        		answetTemp = new Useranswer();
        		answetTemp.setQuesorder(new Integer(j));
        		usrList.add(answetTemp);
        	}
        	random = false;
        }
        
        usrite = usrList.iterator();
        // Store the first value and evaluate, or skip the body if none
        if (usrite.hasNext()) {
            Object element = usrite.next(); // 得到用户答案vo
            Object queselement = null;
            Integer quesorder = null;
            if (element != null) {
            	if(quesordname==null||quesordname.trim().length()<1){
            		quesordname = quesordname_default;
            	}
            	if(usrname==null||usrname.trim().length()<1){
            		random = false;
            	}
            	quesorder = (Integer)ObjUtil.getProperty(element,quesordname);
            	queselement = queslist.get(quesorder.intValue());
            	// 试卷题目vo
            	if(queselement!=null){
            		pageContext.removeAttribute(quesvoid);
            		pageContext.setAttribute(quesvoid, queselement);
            	}else{
            		// 抛异常
            		JspException e = new JspException(messages.getMessage("the question is null according to the useranswer"));
                    TagUtils.getInstance().saveException(pageContext, e);
                    throw e;
            	}
            	// 用户答案vo
            	pageContext.removeAttribute(usrvoid);
        		pageContext.setAttribute(usrvoid, element);
            } else {
            	// 抛异常
            	JspException e = new JspException(messages.getMessage("the useranswer in the usranwlist is null"));
                TagUtils.getInstance().saveException(pageContext, e);
                throw e;
            }
            lengthCount++;
            started = true;
            if (indexId != null) {
                pageContext.setAttribute(indexId, new Integer(getIndex()));
            }
            if (quesorder != null) {
                pageContext.setAttribute(quesordId, quesorder);
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
            Object element = usrite.next(); // 得到用户答案vo
            Object queselement = null;
            Integer quesorder = null;
            if (element != null) {
            	if(quesordname==null||quesordname.trim().length()<1){
            		quesordname = quesordname_default;
            	}           	
            	quesorder = (Integer)ObjUtil.getProperty(element,quesordname);
            	queselement = queslist.get(quesorder.intValue());
            	// 试卷题目vo
            	if(queselement!=null){
            		pageContext.removeAttribute(quesvoid);
            		pageContext.setAttribute(quesvoid, queselement);
            	}else{
            		// 抛异常
            		JspException e = new JspException(messages.getMessage("the question is null according to the useranswer"));
                    TagUtils.getInstance().saveException(pageContext, e);
                    throw e;
            	}
            	// 用户答案vo
            	pageContext.removeAttribute(usrvoid);
        		pageContext.setAttribute(usrvoid, element);
            } else {
            	// 抛异常
            	JspException e = new JspException(messages.getMessage("the useranswer in the usranwlist is null"));
                TagUtils.getInstance().saveException(pageContext, e);
                throw e;
            }
            lengthCount++;
            if (indexId != null) {
                pageContext.setAttribute(indexId, new Integer(getIndex()));
            }
            if (quesordId!=null&&quesorder!=null) {
                pageContext.setAttribute(quesordId, quesorder);
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
        usrList = null;
        queslist = null;
        usrite = null;
        
        quesvoid = null;
        usrvoid = null;
        indexId = null;
        quesordId = null;
        // Continue processing this page
        return (EVAL_PAGE);

    }

    /**
     * Release all allocated resources.
     */
    public void release() {

        super.release();
        usrList = null;
        queslist = null;
        lengthCount = 0;
        lengthValue = 0;
        offsetValue = 0;

        id = null;
        length = null;
        usrname = null;
        offset = null;
        usrprop = null;
        scope = null;
        started = false;
        quesname = null;
        quesprop = null;
        quesordname = null;
        
        quesvoid = null;
        usrvoid = null;
        indexId = null;
        quesordId = null;
    }

	public String getQuesvoid() {
		return quesvoid;
	}

	public void setQuesvoid(String quesvoid) {
		this.quesvoid = quesvoid;
	}

	public String getUsrvoid() {
		return usrvoid;
	}

	public void setUsrvoid(String usrvoid) {
		this.usrvoid = usrvoid;
	}

	public String getQuesname() {
		return quesname;
	}

	public void setQuesname(String quesname) {
		this.quesname = quesname;
	}

	public String getQuesprop() {
		return quesprop;
	}

	public void setQuesprop(String quesprop) {
		this.quesprop = quesprop;
	}

	public String getUsrname() {
		return usrname;
	}

	public void setUsrname(String usrname) {
		this.usrname = usrname;
	}

	public String getUsrprop() {
		return usrprop;
	}

	public void setUsrprop(String usrprop) {
		this.usrprop = usrprop;
	}

	public List getQueslist() {
		return queslist;
	}

	public void setQueslist(List queslist) {
		this.queslist = queslist;
	}

	public String getQuesordname() {
		return quesordname;
	}

	public void setQuesordname(String quesordname) {
		this.quesordname = quesordname;
	}

	public List getUsrList() {
		return usrList;
	}

	public void setUsrList(List usrList) {
		this.usrList = usrList;
	}
	

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
            return (offsetValue + lengthCount - 1);
        else
            return (0);
    }
    
    public String getIndexId() {
        return (this.indexId);
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }
    
    public String getLength() {
        return (this.length);
    }

    public void setLength(String length) {
        this.length = length;
    }
    

    public String getOffset() {
        return (this.offset);
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
    
    public String getScope() {
        return (this.scope);
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

	public String getQuesordId() {
		return quesordId;
	}

	public void setQuesordId(String quesordId) {
		this.quesordId = quesordId;
	}

	public String getQuetype() {
		return quetype;
	}

	public void setQuetype(String quetype) {
		this.quetype = quetype;
	}

	public String getUsrtype() {
		return usrtype;
	}

	public void setUsrtype(String usrtype) {
		this.usrtype = usrtype;
	}

	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}
    
}


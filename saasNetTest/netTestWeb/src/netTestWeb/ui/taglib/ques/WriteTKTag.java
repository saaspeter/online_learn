package netTestWeb.ui.taglib.ques;

import javax.servlet.jsp.JspException;

import netTest.util.QueshowUtil;
import netTest.wareques.constant.QuestionConstant;

import org.apache.struts.taglib.TagUtils;

import commonWeb.ui.taglib.struts.WriteTag;


public class WriteTKTag extends WriteTag {
	
//	public WriteTKTag(){
//		super.setFilterClass(new TKHtmlFilter());
//	}

    /** 是否对题目题干进行转换，主要是对填空题 **/
    protected boolean transfer = false;
    
    /** 显示填空时的显示格式。0代表直接输出〖和〗之间内容（去掉〖和〗），
     * 1用下划线_代替〖和〗之间的内容，不显示〖和〗间的内容
     * 2代表显示〖和〗之间的内容，但是内容下有下划线(_)
     * 3代表用input代替〖和〗之间的内容
     *  只有transfer为true时才起作用 **/
    protected int showIndentWay = 0;
    
    /** 对于填空，当输出中包括input填空标签时，input标签的name取值，当然会自动添加上序号，
     * 输出形式为:inputName[1] 只有当transfer为true和showIndentWay=2时才起作用 **/
    protected String inputName = "";
    
    /** 对应vo中题目类型属性，如果为空或填空题类型，都用输出填空题的方式输出；否则正常输出题目 **/
    protected String questypeProp = null;
    
    private Object questype = QuestionConstant.QuesType_TianKong;
    
    /** 填空题的答案，当不为空时，用该值取代问题中的标准答案。 **/
    private String answerTK;
    
    
	public boolean isTransfer() {
		return transfer;
	}

	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public int getShowIndentWay() {
		return showIndentWay;
	}

	public void setShowIndentWay(int showIndentWay) {
		this.showIndentWay = showIndentWay;
	}

	private String filterStr = QuestionConstant.SplitChar_begin+"(.*?)(?<!"+QuestionConstant.SplitChar_transfer+")"+
            QuestionConstant.SplitChar_end; 
	
	// 先执行formatValue然后再执行以下的TKHtmlFilter类的format
    protected String formatValue(Object valueToFormat)
       throws JspException {
    	if(questypeProp!=null&&questypeProp.trim().length()>0){
    	   questype = TagUtils.getInstance().lookup(pageContext, name, questypeProp, scope);
    	}
//    	if(questype!=null&&(QuestionConstant.QuesType_TianKong.toString().equals(questype.toString())
//    			            ||QuestionConstant.QuesType_WanXingTianKong.toString().equals(questype.toString())))
//    	{
//        	// 如果该元素是数组，则把数组中每个元素组成字符串 -- 支持数组型元素是为了什么作用？？不记得了
//        	if(valueToFormat!=null&&(valueToFormat instanceof String[])){
//        		transfer = true;
//        		showIndentWay = 2;
//        		// 把每个数组元素用/{和/}包裹起来，中间用2个空格隔开-- 这个起什么作用？
//        		String[] arr = (String[])valueToFormat;
//        		if(arr.length>0){
//        		   StringBuffer buffer = new StringBuffer();
//        		   for(int i=0;i<arr.length;i++){
//        			   buffer.append(QuestionConstant.SplitChar_begin).append("/{"+(String)arr[i]+"/}  ");
//        		   }
//        		   valueToFormat = buffer.toString();
//        		}
//        	}
//    	}
    	String rtnStr = super.formatValue(valueToFormat);
    	if(transfer){
    		rtnStr = QueshowUtil.tiankongFilter(rtnStr, questype.toString(), showIndentWay, filterStr, inputName, answerTK);
    	}
    	return rtnStr;
    }
	
    // 负责生成填空题不同的题目题干形式字符串, 不用了，在formatValue()函数中处理
//	class TKHtmlFilter extends HtmlFilter{
//		// 正则表达式字符串，〖(.*?)(?<!/)〗
//		// I'd like u,〖124/〗5〗,but u dont like me at all,〖ppp〗llll
//		// 就不能正确的匹配
//		private String filterStr = QuestionConstant.SplitChar_begin+"(.*?)(?<!"+QuestionConstant.SplitChar_transfer+")"+
//		                           QuestionConstant.SplitChar_end; 
//		
//		public String filter(String value){
//			//value = super.filter(value);
//            // 只有当题目类型是填空时才执行以下替换逻辑
//			// 因为先调用方法formatValue，所以在此不需要再次获取questype的值
//			value = super.filter(value);
//			if(transfer){
//			   value = QueshowUtil.tiankongFilter(value, questype.toString(), showIndentWay, filterStr, inputName, answerTK);
//			}	    
//			return value;
//		}
//		
//	}

	public String getQuestypeProp() {
		return questypeProp;
	}

	public void setQuestypeProp(String questypeProp) {
		this.questypeProp = questypeProp;
	}

	public Object getQuestype() {
		return questype;
	}

	public String getAnswerTK() {
		return answerTK;
	}

	public void setAnswerTK(String answerTK) {
		this.answerTK = answerTK;
	}
	
}

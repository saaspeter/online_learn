
package netTest.wareques.constant;

import java.util.HashMap;
import java.util.Map;

import netTest.util.ResourceBundleUtil;

public final class QuestionImportConstant {

    private static QuestionImportConstant instance;
    
    private QuestionImportConstant(){}
    
    /**
     * 用户存放: 属性描述文字和对应字段的关系，例如:
     *          问题类型 questypename
     *          每题分数 paperquesscore
     */
    private static Map<String, Object> propertyNameResourceMap = new HashMap<String, Object>();
    
    public static QuestionImportConstant getInstance(){
    	if(instance==null){
    		instance = new QuestionImportConstant();
    		//
    		String questypeName_value = ResourceBundleUtil.getInstance().getValue(questypeName_ResourceKey, null);
    		String paperquesscore_value = ResourceBundleUtil.getInstance().getValue(paperquesscore_ResourceKey, null);
    		String ques_right_value = ResourceBundleUtil.getInstance().getValue(ques_right_ResourceKey, null);
    		String ques_wrong_value = ResourceBundleUtil.getInstance().getValue(ques_wrong_ResourceKey, null);
    		propertyNameResourceMap.put(questypeName_value, QuestionProperty_questypename);
    		propertyNameResourceMap.put(paperquesscore_value, PaperQuesProperty_paperquesscore);
    		propertyNameResourceMap.put(ques_right_value, QuestionConstant.IsRight_right);
    		propertyNameResourceMap.put(ques_wrong_value, QuestionConstant.IsRight_wrong);
    		
    	}
    	return instance;
    }
    
	/** Question类型问题 **/
	public static final int QuestionType_Question = 1;
	/** Paperques类型问题 **/
	public static final int QuestionType_PaperQues = 2;
	
    /** 
     * 导入题库时，在excel文件中的各列代表的字段的顺序，这些字段和VO的字段对应
     * questypename: 题目类型
     * question: 题目主干
     * answer: 正确答案
     * A,B,C: 选择题等的子选项的答案选项
     **/
    public static final String[] questionExcelColumn = 
       new String[]{"questypename","question","answer",
    		"$A","$B","$C","$D","$E","$F","$G","$H","$I","$J"};
    
    /**
     * 导入试卷时，在excel文件中的各列代表的字段的顺序，这些字段和VO的字段对应
     **/
    public static final String[] paperQuesExcelColumn = 
       new String[]{"quesTypeName","question","quesDiffName","quesLevelName","quesAnswer","paperQuesScore",
    		"$A","$B","$C","$D","$E","$F","$G"};
    
//////-----------------------   解析txt文件需要用到的正则表达式   -----------------------------------
    // 注释行，跳过，不解析
    public static final String commentsStart = "$$comment$$";
    
    // 属性描述行，用于设题题目属性
    public static final String propertyStart = "$$property$$";
    
    // 是否是问题开端的标识
	public static final String quesStart = "\\s*\\d+[、.．].*";
	public static final String quesStartDeli = "\\d+[、.．]";
	// 用于是否是题目选项的标识
	public static final String itemStart = "\\s*[A-Z][、.．].*";
	public static final String itemStartDeli = "[A-Z][、.．]";
	public static final String itemStartFlagDeli = "[、.．]";
	
	// 用于是否是答案开始的标识
	/** 用于标识问题答案开始的字符串 **/
	public static String questionAnswerFlagReg = ResourceBundleUtil.getInstance().getValue("QuestionImportConstant.answerName.start", null);
	public static final String answerStart = "\\s*("+questionAnswerFlagReg+")[：:].*";
	public static final String answerStartDeli = "("+questionAnswerFlagReg+")[：:]";
	
	// 选择题答案中间的分隔符，例如ABCD或A B C或A,B,C或A，B，C，D
	public static final String qnswerSplitReg = "[ |,|，]";
    
	public static final String QuestionProperty_questype = "questype";
	public static final String QuestionProperty_questypename = "questypename";
	public static final String QuestionProperty_comptype = "comptype";
	public static final String QuestionProperty_queschecktype = "queschecktype";
	public static final String QuestionProperty_quesoptnum = "quesoptnum";
	public static final String PaperQuesProperty_paperquesscore = "paperquesscore";
	
	
	private static final String questypeName_ResourceKey = "QuestionImportConstant.questypeName";
	private static final String paperquesscore_ResourceKey = "QuestionImportConstant.paperquesscore";
	private static final String ques_right_ResourceKey = "QuestionImportConstant.panduan.answerName.right";
	private static final String ques_wrong_ResourceKey = "QuestionImportConstant.panduan.answerName.wrong";
	
	
	/**
	 * @return 把常用的题目属性计入其中,并返回 propertyMap
	 */
	public Object getImportProperty(String inputStr) {
		Object returnValue = null;
		inputStr = inputStr.trim().toLowerCase();
		for(Map.Entry<String, Object> entry : propertyNameResourceMap.entrySet()){
			if(entry.getKey().indexOf(inputStr)!=-1){
				returnValue = entry.getValue();
				break;
			}
		}
		return returnValue;
	}
    
}

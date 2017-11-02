
package netTest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import netTest.exception.ExceptionConstant;
import netTest.paper.vo.Paperques;
import netTest.paper.vo.Paperquestype;
import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.constant.QuestionImportConstant;
import netTest.wareques.vo.Quesanswer;
import netTest.wareques.vo.Question;
import netTest.wareques.vo.Questionitem;

import org.apache.log4j.Logger;

import commonTool.biz.logic.ConstantInf;
import commonTool.biz.logicImpl.ConstantLogicImpl;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;
import commonTool.util.ObjUtil;
import commonTool.util.StringUtil;

/**
 * @author fanxl
 *
 * 导入txt类型的题目文件或试卷文件，解析这些文件并产生ArrayList
 */
public class ImportQuesFromTxtHelper {
	
	private Logger logger =Logger.getLogger(ImportQuesFromTxtHelper.class);

	
//	private int questionType;
//		
//	private BufferedReader in;
	
	public static final String propertySeparator = "=";
	
	// 上一行内容的类型
	private static final int PreviousLineType_question = 1;
	//private static final int PreviousLineType_quesitem = 2;
	private static final int PreviousLineType_answer = 3;
	
	private static ImportQuesFromTxtHelper helper = null;
	
	private ImportQuesFromTxtHelper(){}
	
	private Question geneNewQuestion(int questionType, Map paraMap){
		Question quesVO = null;
		if(questionType==QuestionImportConstant.QuestionType_Question){
			quesVO = new Question();
		}else if(questionType==QuestionImportConstant.QuestionType_PaperQues){
			quesVO = new Paperques();
		} else {
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		}
		if(paraMap!=null){
			ObjUtil.copyProperties(quesVO, paraMap);
		}
		return quesVO;
	}
	
	private Questionitem geneQuestionItem(int questionType){
		return new Questionitem();
	}
	
	private Quesanswer geneQuesanswer(int questionType){
		return new Quesanswer();
	}
	
	
	
	/**
	 * 读取txt文件，把解析出来的问题放入list中返回
	 * @return 返回结果数组, 第一个元素是questionList, 第二个元素是message list, 第三个元素是试卷题目题型List
	 * @throws IOException
	 */
	public List[] readFile(InputStream input, int questionType, Map paraMap) {
		if(input==null||(questionType!=QuestionImportConstant.QuestionType_Question 
				         && questionType!=QuestionImportConstant.QuestionType_PaperQues))
			return null;
		List<Question> quesList = new ArrayList<Question>();
		// 记录属性地方: 如题目类型，题目分数等
		Map<String, Object> quesPropertyMap = new HashMap<String, Object>();
		Map<Integer, Paperquestype> paperquesPatternMap = null;
		List<Paperquestype> patternList = null;
		if(questionType==QuestionImportConstant.QuestionType_PaperQues){
			paperquesPatternMap = new HashMap<Integer, Paperquestype>();
			patternList = new ArrayList<Paperquestype>();
		}
		// 记录错误信息的地方
		List<String> messageList = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new InputStreamReader(input));
		// 返回结果数组
		List[] rtnArr = new List[]{quesList, messageList, patternList};
		String lineStr = null;
		// 代表问题的VO
		Question quesVO = null;
		// 上一题的VO
		Question previousQuesVO = null;
		// itemList:代表题目选项的集合
		List<Questionitem> itemList = null;
		String quesRightWrong = null;
		
		int previousLineType = 0;
		try {
			while((lineStr=in.readLine())!=null){
				lineStr = lineStr.trim();
				if("".equals(lineStr)){
					continue;
				}
				if(lineStr.startsWith(QuestionImportConstant.propertyStart)){
					// 设置题目相关的属性Map
					readQuesPropertySetting(lineStr, quesPropertyMap);
				}else if(isQuesStart(lineStr)){ 
					if(quesVO!=null){ // 如果quesVO不为null, 题目没有设置完整，废弃
						messageList.add(quesVO.getQuestion());
					}
					// 设置题目问题和类型等属性
					quesVO = geneNewQuestion(questionType, paraMap);
					initQuesProperty(lineStr, quesVO, quesPropertyMap);
				} else if(isQuesItemStart(lineStr) && quesVO!=null){
					// 如果是答案选项，把答案选项内容和标识放在itemList中
					Questionitem itemVO = geneQuestionItem(questionType);
					if(itemList==null){
						itemList = new ArrayList<Questionitem>();
					}
					String item = getQuesItem(lineStr);
					String itemFlag = getQuesItemFlag(lineStr);
					itemVO.setQuesitemcontent(item);
					// 本来选择题是不需要这个设置的,但是为了设置正确性设置了flag
					itemVO.setQuesitemflag(itemFlag);
					itemVO.setShopid(quesVO.getShopid());
					itemVO.setFiletype(QuestionConstant.FileType_None);
					itemList.add(itemVO);
				}else if(isQuesAnswerStart(lineStr)){
					// 如果是问题答案，把答案选项放入问题的选项集合中，并把这个问题放入问题集合list中
					String answerValue = getQuesAnswer(lineStr);
					if(quesVO!=null && answerValue!=null && answerValue.length()>0){
						Integer questype = quesVO.getQuestype();
						if(questype!=null){
							if(QuestionConstant.QuesType_DanXuan.equals(questype)
								|| QuestionConstant.QuesType_DuoXuan.equals(questype))
							{
							   if(itemList!=null&&itemList.size()>0){
							       quesVO.setItemList(itemList);
							// 如果解析文本得到的题目类型是：单选题，而题目答案确是多个，则设置该题为多选题，
							// 并且把答案设置成多选格式，付给obj对象，否则直接设置答案。但不更改系统题目默认属性
							       setXuanzeQuesItemAnswer(quesVO, answerValue, itemList);
							   }else { // 没有答案选项，废弃
							   	   messageList.add(quesVO.getQuestion());
							   	   quesVO = null;
							   }
							}else if(QuestionConstant.QuesType_PanDuan.equals(questype)){
								quesRightWrong = (QuestionImportConstant.getInstance().getImportProperty(answerValue)).toString();
								if(quesRightWrong!=null){
									quesVO.setAnswersim(quesRightWrong);
								}else {
									// 判断题没有答案，废弃该题目
									messageList.add(quesVO.getQuestion());
									quesVO = null; 
								}
							}else if(QuestionConstant.QuesType_WenDa.equals(questype)){
								Quesanswer answerVO = geneQuesanswer(questionType);
								answerVO.setAnswertext(answerValue);
								quesVO.setAnswerVO(answerVO);
							}
						}
					}
					// 碰到答案行，说明这个题目要结束，做处理
					if(quesVO!=null){
						// 把map中的属性付给问题Object，主要是InventoryID和InventoryName
						if((QuestionConstant.QuesType_DanXuan.equals(quesVO.getQuestype())
							||QuestionConstant.QuesType_DuoXuan.equals(quesVO.getQuestype())))
						{
							if(quesVO.getItemList()!=null&&quesVO.getItemList().size()>0){
							    quesList.add(quesVO);
							    // 如果是试卷题目，则更新试卷题型Map
							    updatePaperquestype(quesVO, paperquesPatternMap, questionType, quesPropertyMap);
							}else {
								quesVO = null;
							}
						}else {
							quesList.add(quesVO);
							updatePaperquestype(quesVO, paperquesPatternMap, questionType, quesPropertyMap);
						}
					}
					// 清空quesVO
					previousQuesVO = quesVO;
					quesVO = null;
					itemList = null;
				}else if(lineStr.startsWith(QuestionImportConstant.commentsStart)){
					continue;
				}else {
					// 如果本行有内容，并且上一行是 问题题干 或 答案内容，则把本行也算作题干或答案的一部分
					if(previousLineType==PreviousLineType_question && quesVO!=null){
						quesVO.setQuestion(quesVO.getQuestion()+lineStr);
					}else if(previousLineType==PreviousLineType_answer 
							&& previousQuesVO!=null && previousQuesVO.getAnswerVO()!=null)
					{
						previousQuesVO.getAnswerVO().setAnswertext(lineStr);
					}
				}
			}
			// 如果文件读完，quesVO还不为null，说明最后一题异常
			if(quesVO!=null){
				messageList.add(quesVO.getQuestion());
			}
		} catch (IOException e) {
			logger.error("open txt file occurs errors!", e);
			throw new LogicException(ExceptionConstantBase.Error_Invalid_Parameter, e);
		} 
		if(paperquesPatternMap!=null){
			for(Map.Entry<Integer, Paperquestype> entry : paperquesPatternMap.entrySet()){
				patternList.add(entry.getValue());
			}
		}
		return rtnArr;
	}
	
	/**
	 * 识别是否是问题的开端
	 * @param lineStr
	 * @param express
	 * @return
	 */
	private boolean isQuesStart(String lineStr){
		if(lineStr==null||lineStr.trim().length()<=0)
			return false;
		Pattern pattern = Pattern.compile(QuestionImportConstant.quesStart);
		Matcher matcher = pattern.matcher(lineStr);
		return matcher.matches();
	}
	
	/**
	 * 用问题分隔符分割问题，返回得到的题干
	 * @param lineStr
	 * @return
	 */
	private String getQuestion(String lineStr){
		if(lineStr==null||lineStr.trim().length()<=0)
			return null;
		Pattern pattern = Pattern.compile(QuestionImportConstant.quesStartDeli);
		String[] arrs = pattern.split(lineStr,2);
		if(arrs!=null&&arrs.length>1)
		   return arrs[1].trim();
		else return null;
	}
	
	/**
	 * 识别是否是问题答案选项的开端
	 * @param lineStr
	 * @param express
	 * @return
	 */
	private boolean isQuesItemStart(String lineStr){
		if(lineStr==null||lineStr.trim().length()<=0)
			return false;
		Pattern pattern = Pattern.compile(QuestionImportConstant.itemStart);
		Matcher matcher = pattern.matcher(lineStr);
		return matcher.matches();
	}
	
	/**
	 * 用问题分隔符分割问题答案选项，返回得到的答案选项
	 * @param lineStr
	 * @return
	 */
	private String getQuesItem(String lineStr){
		if(lineStr==null||lineStr.trim().length()<=0)
			return null;
		Pattern pattern = Pattern.compile(QuestionImportConstant.itemStartDeli);
		String[] arrs = pattern.split(lineStr,2);
		if(arrs!=null&&arrs.length>1)
		   return arrs[1].trim();
		else return null;
	}
	
	/**
	 * 用问题分隔符分割问题答案选项，返回得到的答案选项的标识符
	 * @param lineStr
	 * @return
	 */
	private String getQuesItemFlag(String lineStr){
		if(lineStr==null||lineStr.trim().length()<=0)
			return null;
		Pattern pattern = Pattern.compile(QuestionImportConstant.itemStartFlagDeli);
		String[] arrs = pattern.split(lineStr,2);
		if(arrs!=null&&arrs.length>1)
		   return arrs[0].trim();
		else return null;
	}
	
	/**
	 * 识别是否是问题答案开端
	 * @param lineStr
	 * @param express
	 * @return
	 */
	private boolean isQuesAnswerStart(String lineStr){
		if(lineStr==null||lineStr.trim().length()<=0)
			return false;
		Pattern pattern = Pattern.compile(QuestionImportConstant.answerStart);
		Matcher matcher = pattern.matcher(lineStr);
		return matcher.matches();
	}
	
	/**
	 * 用问题分隔符分割问题答案，返回得到的答案,此处得到的是原始答案，多选题答案间没有用","隔开
	 * @param lineStr
	 * @return
	 */
	private String getQuesAnswer(String lineStr){
		if(lineStr==null||lineStr.trim().length()<=0)
			return null;
		Pattern pattern = Pattern.compile(QuestionImportConstant.answerStartDeli);
		String[] arrs = pattern.split(lineStr,2);
		if(arrs!=null&&arrs.length>1)
		   return arrs[1].trim();
		else return null;
	}
	
	/**
	 * 如果改行是属性行，则调用该函数设置题目属性
	 * @param lineStr
	 * @return
	 */
	private Map<String, Object> readQuesPropertySetting(String lineStr, Map<String, Object> quesPropertyMap)
	{
		lineStr = lineStr.trim();
		if(lineStr.startsWith(QuestionImportConstant.propertyStart)){
			lineStr = lineStr.substring(QuestionImportConstant.propertyStart.length());
			String[] propertyStrArr = StringUtil.splitString(lineStr, "|");
			
			for(String propertyStr : propertyStrArr){
				String propertyName = null;
				String propertyValue = null;
				// 如果该属性没有=号，则判断是否只有一个属性设置，如果只有一个，则认为该属性是题目类型
				// 如果有过个属性，则跳过这个属性设置
				if(propertyStr.indexOf(propertySeparator)==-1){
					if(propertyStrArr.length==1){
						propertyName = QuestionImportConstant.QuestionProperty_questypename;
						propertyValue = propertyStr.trim();
					}else {
						continue;
					}
				}else {
					// 用户输入的属性得到 属性对应的列名称
					int positionNO = propertyStr.indexOf(propertySeparator);
					Object propertyObj = QuestionImportConstant.getInstance().getImportProperty(propertyStr.substring(0,positionNO).trim());
					if(propertyObj!=null){
						propertyName = propertyObj.toString();
					}
					propertyValue = propertyStr.substring(positionNO+1).trim();
				}
					
				
				if(propertyName!=null&&propertyValue!=null&&!("".equals(propertyValue))){
					if(QuestionImportConstant.QuestionProperty_questypename.equals(propertyName)){
						ConstantInf inf = ConstantLogicImpl.getInstance();
						String questypeStr = inf.getValue(QuestionConstant.QuesType_ConsCode, propertyValue);
						if(questypeStr==null || questypeStr.trim().length()<1){
							continue;
						}
						Integer questype = new Integer(questypeStr);
						quesPropertyMap.put(QuestionImportConstant.QuestionProperty_questype, questype);
						quesPropertyMap.put(QuestionImportConstant.QuestionProperty_questypename, propertyValue);
						// 设置其他的属性
						Short queschecktype = QuestionConstant.getQueschecktype(questype);
						quesPropertyMap.put(QuestionImportConstant.QuestionProperty_queschecktype, queschecktype);
						//
						if(!QuestionConstant.QuesType_TianKong.equals(questype)){
						   quesPropertyMap.put(QuestionImportConstant.QuestionProperty_quesoptnum, new Integer(1));
						}
						// set compType
						if(QuestionConstant.QuesType_WanXingTianKong.equals(questype)
							||QuestionConstant.QuesType_YueDuLiJie.equals(questype)){
							quesPropertyMap.put(QuestionImportConstant.QuestionProperty_comptype, QuestionConstant.Comptype_compWhole);
						}else {
							quesPropertyMap.put(QuestionImportConstant.QuestionProperty_comptype, QuestionConstant.Comptype_whole);
						}
					}else if(QuestionImportConstant.PaperQuesProperty_paperquesscore.equals(propertyName)){
						Float score = null; 
						try {
							score = new Float(propertyValue);
							quesPropertyMap.put(QuestionImportConstant.PaperQuesProperty_paperquesscore, score);
						}catch(Exception e) {
							logger.error(e);
						}
					}
				}
			}
		}
		return quesPropertyMap;
	}
	
	/**
	 * 分析lineStr，如果是设置属性的行，则返回的属性放在Object对象中，
	 * $property$ questypename=选择题 | 
	 * 该Object对象可能是QuestionVO或PaperQuesVO
	 * @param lineStr
	 * @param obj:需要将题目属性记录在内的对象
	 * @param type:是录入题库题目还是试卷题目,1为题库题目;2为试卷题目,需要有分数信息
	 * @return
	 */
	private Question initQuesProperty(String lineStr, Object obj, Map<String, Object> quesPropertyMap){
		if(lineStr==null||lineStr.trim().length()<=0||obj==null)
			return null;
		Question quesVO = (Question)obj;
		String question = getQuestion(lineStr);
		quesVO.setQuestion(question);
		// 属性匹配
		Integer questype = (Integer)quesPropertyMap.get(QuestionImportConstant.QuestionProperty_questype);
		Short queschecktype = (Short)quesPropertyMap.get(QuestionImportConstant.QuestionProperty_queschecktype);
		Integer quesoptnum = (Integer)quesPropertyMap.get(QuestionImportConstant.QuestionProperty_quesoptnum);
		Short comptype = (Short)quesPropertyMap.get(QuestionImportConstant.QuestionProperty_comptype);
		Float score = (Float)quesPropertyMap.get(QuestionImportConstant.PaperQuesProperty_paperquesscore);
		// 设置questype
		quesVO.setQuestype(questype);
		quesVO.setQuestypeid(Paperquestype.getQuestypeID(questype));
        //   设置是否是主观或客观题
		quesVO.setQueschecktype(queschecktype);
		// 如果不是主观题，则把答案要写入db
		if(!QuestionConstant.Queschecktype_manual.equals(queschecktype)) {
		   quesVO.setAnswersim(quesVO.getAnswer());
		}
		//
		quesVO.setQuesoptnum(quesoptnum);
		// set compType
		quesVO.setComptype(comptype);
		quesVO.setStatus(QuestionConstant.Status_valide);
		//
		if(quesVO instanceof Paperques){
			((Paperques)quesVO).setPaperquesscore(score);
		}
	    return quesVO;
			
	}
	
	
	/**
	 * 从问题的答案判断是否是多选题，如果答案由多个字符，则把题目属性设为多选题，
	 * 并且把问题答案处理成多选格式，并付给obj对象，否则直接把答案付给obj对象
	 * @param obj
	 * @param answer
	 * @return
	 */
	private Object setXuanzeQuesItemAnswer(Question quesVO, String answer, List itemList){
		if(quesVO==null||answer==null||answer.trim().length()<=0||itemList==null||itemList.size()<1)
			return null;
		answer = answer.replaceAll(" ", "");
		String[] answerArr = null;
		if(answer.indexOf(",")!=-1){
			answerArr = answer.split(",");
		}else if(answer.indexOf("，")!=-1){
			answerArr = answer.split("，");
		}else if(answer.indexOf(" ")!=-1){
			answerArr = answer.split(" ");
		}else {
			answerArr = answer.split("");
		}
		List<String> answerItemList = new ArrayList<String>();
		for(int j=0;j<answerArr.length;j++){
			if(answerArr[j]!=null && !"".equals(answerArr[j])){
				answerItemList.add(answerArr[j]);
			}
		}
		// 如果题目答案确是多个，则设置该题为多选题，但不更改系统题目默认属性
		if(answerItemList.size()>1){
			quesVO.setQuestype(QuestionConstant.QuesType_DuoXuan);
		}
        if(itemList.size()>0){
        	Questionitem itemVO = null;
        	for(int i=0;i<itemList.size();i++){
        		itemVO = (Questionitem)itemList.get(i);
        		if(answerItemList.contains(itemVO.getQuesitemflag())){
        			itemVO.setIsright(QuestionConstant.IsRight_right);
        		}else {
        			itemVO.setIsright(QuestionConstant.IsRight_wrong);
        		}
        		itemVO.setQuesitemflag(null);
        	}
        }
		return quesVO;
	}
	
	//如果是试卷试题，则需要更新试卷题型Map
	private void updatePaperquestype(Question quesVO, Map<Integer, Paperquestype> paperquesPattern, 
									 int questionType, Map<String, Object> quesPropertyMap)
	{
		if(questionType==QuestionImportConstant.QuestionType_PaperQues 
			&& quesVO!=null&& paperquesPattern!=null){
			Paperques paperquesVO = (Paperques)quesVO;
			Paperquestype patternVO = null;
			if(paperquesPattern.get(paperquesVO.getQuestype())==null){
        		patternVO = new Paperquestype();
        		patternVO.setPaperid(paperquesVO.getPaperid());
        		patternVO.setShopid(paperquesVO.getShopid());
        		patternVO.setQuestype(quesVO.getQuestype());
        		patternVO.setQuestypeid(quesVO.getQuestypeid());
        		patternVO.setQuestypename((String)quesPropertyMap.get(QuestionImportConstant.QuestionProperty_questypename));
        		patternVO.setPatternquesnum(1);
        		patternVO.setPatternscore(paperquesVO.getPaperquesscore());
        		patternVO.setQuesscore(paperquesVO.getPaperquesscore());
        		// set pagesize
        		if(QuestionConstant.QuesType_WenDa.equals(paperquesVO.getQuestype())){
        			patternVO.setPagesize(1);
        		}else {
        			patternVO.setPagesize(10);
        		}
        		paperquesPattern.put(quesVO.getQuestype(), patternVO);
    		}else {
    			patternVO = paperquesPattern.get(paperquesVO.getQuestype());
    			patternVO.setPatternscore(patternVO.getPatternscore()
    					+((paperquesVO.getPaperquesscore()==null)?0f:paperquesVO.getPaperquesscore()));
    			patternVO.setPatternquesnum(patternVO.getPatternquesnum()+1);
    		}
		}
	}
	
	public static void main(String[] args){
		String testStr = "A.只有被告人供述" +
				"B、只有被告人供述"+
				"sdkfdk11、测试测试"+"答案:ABCD"+"被告人供"+"答案：BCD";

		 Pattern pattern1 = Pattern.compile(QuestionImportConstant.itemStart);
		 Pattern pattern2 = Pattern.compile(QuestionImportConstant.itemStartFlagDeli);
		 Matcher matcher1 = pattern1.matcher(testStr);
		 Matcher matcher2 = pattern2.matcher(testStr);
		 
		 boolean b = matcher1.matches();
		 System.out.println("matchers:"+matcher1.pattern().pattern()+"###"+matcher1.matches());
		 String[] arrs = pattern2.split(testStr,2);
		 System.out.println("匹配答案的开始："+arrs.length+arrs[0]);
		 		 
	
	}
	
	/**
	 * 初始化TxtHelper，并返回实例
	 * @param input:文件流
	 * @param className:要生成的问题类型种类，是QuestionVO还是PaperQuesVO
	 * @return
	 */
	public static ImportQuesFromTxtHelper getInstance() {
		if(helper==null){
			helper = new ImportQuesFromTxtHelper();
		}
		return helper;
	}
	
}

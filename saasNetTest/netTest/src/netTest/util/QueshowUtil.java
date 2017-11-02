package netTest.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts.taglib.TagUtils;

import commonTool.util.StringUtil;

import netTest.wareques.constant.QuestionConstant;

/**
 * 支持考试学习系统间题型显示的帮助类，主要是帮助显示不同题目类型的
 * 
 * @author fan
 * 
 */
public class QueshowUtil {
	
	/** 默认的分割〖(.*?)(?<!/)〗 任意字符，但不能有/符号 **/
	public final static String FilterStr_Default = QuestionConstant.SplitChar_begin+"(.*?)(?<!"+QuestionConstant.SplitChar_transfer+")"
	                                               +QuestionConstant.SplitChar_end; 
//	/** 填空题默认的分割符，要保证用户的输入值中不能有〖和〗 **/
//	public final static String FilterStr_TK_Default = QuestionConstant.SplitChar_begin+"(.*?)"
//    												  +QuestionConstant.SplitChar_end; 
	//public final static String TianKongTag_Reg = "<span name=\"spantiankong\" style=\"border-bottom:2px solid blue;\">(.*?)</span>";
	
	public final static String TianKong_SpanStyle = "border-bottom-color:blue; border-bottom-style:solid; border-bottom-width:2px";
	
	public final static String TianKongTag_Output1 = "<span name=\"spantiankong\" style=\""+TianKong_SpanStyle+"\">_____</span>";
	
	public final static String TianKongTag_Output2 = "<span name=\"spantiankong\" style=\""+TianKong_SpanStyle+"\">$1</span>";
	
	/**
	 * 把默认的分割符包含的信息分离出来成为数组，为填空题服务
	 * @param value
	 * @return
	 */
	public static String[] getQuesAnswerTK(String value){
		String[] arr = null;
		if(value==null||value.trim().length()<1)
			return arr;
		List<String> list = new ArrayList<String>();
		Pattern patt = Pattern.compile(FilterStr_Default);
		Matcher matcher = patt.matcher(value);
		while(matcher.find()){
			list.add(matcher.group(1));
		}
		if(list.size()>0){
		   arr = new String[list.size()];
		   list.toArray(arr);
		}else{
			arr = new String[]{value};
		}
        return arr;
	}
	
	/**
	 * 得到填空题中题干需要填写的空格数目
	 * @param value
	 * @return
	 */
	public static int getQuesAnswerCountTK(String value){
		int count = 0;
		if(value==null||value.trim().length()<1)
			return count;
		List<String> list = new ArrayList<String>();
		Pattern patt = Pattern.compile(FilterStr_Default);
		Matcher matcher = patt.matcher(value);
		while(matcher.find()){
			count++;
		}
        return count;
	}
	
	/**
	 * 把用户填写的答案数组变成用〖〗分割的字符串，如果用户输的字符中有〖，则替换为/〖
	 * @param value
	 * @return
	 */
	public static String wrapUserAnswerTK(String[] ansarr){
		if(ansarr==null||ansarr.length<1)
			return "";
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<ansarr.length;i++){
			if(ansarr[i]!=null){
			   // 如果用户的答案中有〖的需要转换为/〖
			   ansarr[i] = ansarr[i].replaceAll(QuestionConstant.SplitChar_begin,"/"+QuestionConstant.SplitChar_begin);
			   ansarr[i] = ansarr[i].replaceAll(QuestionConstant.SplitChar_end,"/"+QuestionConstant.SplitChar_end);
			   buffer.append(QuestionConstant.SplitChar_begin+ansarr[i]+QuestionConstant.SplitChar_end);
			}
		}
		return buffer.toString();
	}

	/**
	 * 
	 * @param value : 需要转换的字符串
	 * @param questype ：题目类型，字符串型
	 * @param showIndentWay ：转换类型，显示填空时的显示格式。0代表直接输出〖和〗之间内容（去掉〖和〗），
     * 						  1 用下划线_代替〖和〗之间的内容，不显示〖和〗间的内容
     * 						  2 代表显示〖和〗之间的内容，但是内容要用特殊的样式代替，用于在ckeditor中显示
     * 						  3 代表用input代替〖和〗之间的内容
	 * @param filterStr ：转换规则的正则表达式，即匹配规则
	 * @param inputName ：当showIndentWay=3时，代表要用input标签来显示要填写的空，inputName为这个填空标签的name属性
	 * @return ：转换后的值
	 */
	public static String tiankongFilter(String value, String questype,
			int showIndentWay, String filterStr, String inputName, String answerTK) {
		if(filterStr==null||filterStr.trim().length()<1){
			filterStr = FilterStr_Default; 
		}
		if(questype==null||(!QuestionConstant.QuesType_TianKong.toString().equals(questype.toString())
				            &&!QuestionConstant.QuesType_WanXingTianKong.toString().equals(questype.toString())))
		{
			return value;
		}

		if(QuestionConstant.QuesType_TianKong.toString().equals(questype.toString())){
			if (showIndentWay == 1) {
				value = value.replaceAll(filterStr, TianKongTag_Output1);
			} else if (showIndentWay == 2) {
				value = value.replaceAll(filterStr, TianKongTag_Output2);
			} else if (showIndentWay == 3) {
				String[] ansarr = null;
				if(answerTK!=null&&answerTK.trim().length()>0){
					ansarr = getQuesAnswerTK(answerTK);
				}
				if(ansarr!=null&&ansarr.length>0){ // 如果用户填写的有答案，则要保证用户的答案也被写到input标签中
					String[] quesArr = value.split(filterStr); // 用分割符把问题分割成数组
					StringBuffer buffer = new StringBuffer();
					String tempStr = "";
					for(int j=0;j<quesArr.length;j++){
						tempStr = "";
						buffer.append(quesArr[j]);
						if(j<ansarr.length&&ansarr[j]!=null){
							tempStr = ansarr[j];
						}
						if(!((j==quesArr.length-1)&&!value.endsWith(QuestionConstant.SplitChar_end))){
							// 排除掉这种情况：如果现在处理的是分割的题目的最后一段，并且题目不是以〗结尾。其他情况都加input标签
							// 如果要在input中输出内容，则需要用过滤input的方法过滤内容
							tempStr = TagUtils.getInstance().filter(tempStr);
							buffer.append("<input type=\"text\" name=\""+inputName+"\" value=\""+tempStr+"\"/>");
						}
					}
					value = buffer.toString();
				}else{
				    value = value.replaceAll(filterStr,"<input type=\"text\" name=\""+inputName+"\" value=\"\"/>");
				}
			}
		}else if(QuestionConstant.QuesType_WanXingTianKong.toString().equals(questype.toString())){
			if (showIndentWay == 2) {
				value = value.replaceAll(filterStr, "<u>$1</u>");
			}
		}
		// 把文本中/〖和/〗分别转换为〖和〗，即用户输入的〖被转换为/〖在数据表中保存。
		value = value.replaceAll("/" + QuestionConstant.SplitChar_begin,QuestionConstant.SplitChar_begin);
		value = value.replaceAll("/" + QuestionConstant.SplitChar_end,QuestionConstant.SplitChar_end);
		
        return value;
	}

	/**
	 * 得到用户答案的flag，例如用户答案：ABC
	 * @param itemFlagMap: 题目itemVO的id和itemFlag的对应关系
	 * @param itemanswer：用户答案itemid，其中用','分割
	 * @return
	 */
	public static String getUseranswerFlag(Map<String,String> itemFlagMap,String itemanswer){
		if(itemFlagMap==null||itemanswer==null)
			return "";
		StringBuffer buffer = new StringBuffer();
		String[] itemanswerArr = StringUtil.splitString(itemanswer, ",");
		for(int i=0;i<itemanswerArr.length;i++){
			if(itemFlagMap.get(itemanswerArr[i])!=null){
				buffer.append(itemFlagMap.get(itemanswerArr[i]));
			}
		}
		return buffer.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "123测试，填空题，用了〖ckeditor〗，我写的字〖真/〖ppp/〗丑〗，测试〖5.4〗&，<u>5.4〖五四青年节〗特色汤4,/〖/〗";
		//str = "〖v/〗〖我看破〗";
//		String[] arr = QueshowUtil.getQuesAnswerTK(str);
//		arr = str.split(FilterStr_TK_Default);
//		if(arr!=null&&arr.length>0){
//			System.out.println("arr.length:"+arr.length);
//			for(int i=0;i<arr.length;i++){
//			    System.out.println(arr[i]);
//			}
//		}
		String yyy = "123测试，填空题，用了〖ckeditor〗，我写的字〖真/〖ppp/〗丑〗，测试〖5.4〗&，<u>5.4〖五四青年节〗特色汤4,/〖/〗";
		yyy = yyy.replaceAll(FilterStr_Default, "<u>_____</u>");
		System.out.println("----:"+yyy);
		System.out.println("---size:"+getQuesAnswerCountTK(str));
		System.out.println("????:"+str);
		String uuu = tiankongFilter(str, "4",1, null, null, null);
		System.out.println("####:"+uuu);
	}

}

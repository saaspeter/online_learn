
package netTest.wareques.logic.impl;

import java.util.List;
import java.util.Locale;

import netTest.util.ResourceBundleUtil;
import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.vo.Quesanswer;
import netTest.wareques.vo.Question;
import netTest.wareques.vo.Questionitem;

import org.apache.log4j.Logger;

import commonTool.biz.logic.ConstantInf;
import commonTool.biz.logicImpl.ConstantLogicImpl;
import commonTool.exception.LogicException;
import commonTool.iface.IDoExcelRow;
import commonTool.util.ObjUtil;

/**
 * @author 
 */
public class QuestionImportRow implements IDoExcelRow {

	static Logger log = Logger.getLogger(QuestionImportRow.class.getName());


	/**
	 * 
	 */
	private QuestionImportRow() {  }
	
	/**
	 * 处理有子集合类的情况，例如：QuestionVO中包含一个QuestionItemVO的类的集合，且QuestionVO的属性和
	 * QuestionItemVO的属性防在excel中的同一行，在此方法中把属于QuestionItemVO的类的属性组装成一个类。
	 * 把QuestionItemVO的类加入list中。
	 * @param obj: 主体Object对象
	 * @param list: 返回的子类集合，即QuestionVO中的一个集合属性
	 * @param columnName：要付给QuestionItemVO中的属性的名称
	 * @param cellValue：excel中的一个cell的值
	 * @return 一行处理完毕后，子类的集合
	 */
	public List doChildColumn(Object obj, List list, String columnName, Object cellValue) {
		if(cellValue.toString()!=null && cellValue.toString().trim().length()>0){
			Question quesVO = (Question)obj;
			Questionitem itemVO = new Questionitem();
	       	itemVO.setQuesitemcontent(cellValue.toString());
	       	if(columnName.equals(quesVO.getAnswer())){
	       		itemVO.setIsright(QuestionConstant.IsRight_right);
	       	}else {
	       		itemVO.setIsright(QuestionConstant.IsRight_wrong);
	       	}
	       	itemVO.setShopid(quesVO.getShopid());
	        list.add(itemVO);
		}
        return list;
	}

	/**
	 * 把一行处理完得到的list子集合付给父类，在实现时须指定父类中的那个属性是集合属性
	 * 组装QuestionItemVO的list集合放入QuestionVO中的quesItems属性中
	 * @param obj：父类
	 * @param list：子集合
	 */
	public void setChildProperty(Object obj, List list) {
		if(list!=null&&list.size()>0)
		   ObjUtil.setProperty(obj,"itemList",list);
	}

	/**
	 * 导入一行excel行后，处理每一行，在此处理：将问题的难度由汉字匹配成ID号，
	 * 完成题目类型、难度、级别的转换成ID号的工作
	 * 完成将判断题的对错在此转换成1或2
	 * @param obj：行对象
	 * @return：处理完毕的行对象
	 */
	public Object transRow(Object obj) {
    	if(obj==null)
    		return null;
    	Question quesVO = (Question)obj;
		String quesTypeName = quesVO.getQuestypename();
		ConstantInf inf = ConstantLogicImpl.getInstance();
		String questypeStr = inf.getValue(QuestionConstant.QuesType_ConsCode, quesTypeName);
		if(questypeStr==null || questypeStr.trim().length()<1){
			return null;
		}else {
			Integer questype = Integer.parseInt(questypeStr);
			quesVO.setQuestype(questype);
		    // 设置是否是主观或客观题
			Short queschecktype = QuestionConstant.getQueschecktype(questype);
			quesVO.setQueschecktype(queschecktype);
			// 如果不是主观题，则把答案要写入db
			if(!QuestionConstant.Queschecktype_manual.equals(queschecktype)) {
			   quesVO.setAnswersim(quesVO.getAnswer());
			}else {
				Quesanswer answerVO = new Quesanswer();
				answerVO.setAnswertext(quesVO.getAnswer());
				quesVO.setAnswerVO(answerVO);
			}
			//
			if(!QuestionConstant.QuesType_TianKong.equals(questype))
			   quesVO.setQuesoptnum(1);
			// set compType
			if(QuestionConstant.QuesType_WanXingTianKong.equals(questype)
				||QuestionConstant.QuesType_YueDuLiJie.equals(questype)){
				quesVO.setComptype(QuestionConstant.Comptype_compWhole);
			}else {
				quesVO.setComptype(QuestionConstant.Comptype_whole);
			}
			// set status
			quesVO.setStatus(QuestionConstant.Status_valide);
		}
		
    	return obj;
	}

	
	public static QuestionImportRow getInstance(){
		QuestionImportRow vo = new QuestionImportRow();
		return vo;
	}

	@Override
	public String showRowErrorInfo(Object obj, Exception e, Locale locale) {
		String mess = "";
		if(obj!=null){
			mess = ((Question)obj).getQuestion();
			if(e instanceof LogicException){
				mess += " -- "+ ResourceBundleUtil.getInstance().getValue(e.getMessage(), locale);
			}else {
				mess += " -- "+e.getMessage();
			}
		}
		return mess;
	}
	
}

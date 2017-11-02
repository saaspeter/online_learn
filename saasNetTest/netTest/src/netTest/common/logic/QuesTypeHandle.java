package netTest.common.logic;

import netTest.wareques.constant.QuestionConstant;
import commonTool.exception.NeedParamsException;

/**
 * 为了对不同题型做统一处理，在此定义该虚类，所有需要对不同题型做不同处理的类都继承该类
 * @author fan
 *
 */
public abstract class QuesTypeHandle {
		
	public Object handleQuesType(Object paraObj1,Object paraObj2,Integer questype){
		if(questype==null)
			throw new NeedParamsException("Lack Of Parameters:Class:QuesTypeHandle#Method:doQuesType");
		Object rtnMap = null;
		if(QuestionConstant.QuesType_DanXuan.equals(questype))
			rtnMap = this.doDanXuan(paraObj1,paraObj2,questype);
		else if(QuestionConstant.QuesType_DuoXuan.equals(questype))
			rtnMap = this.doDuoXuan(paraObj1,paraObj2,questype);
		else if(QuestionConstant.QuesType_PanDuan.equals(questype))
			rtnMap = this.doPanDuan(paraObj1,paraObj2,questype);
		else if(QuestionConstant.QuesType_TianKong.equals(questype))
			rtnMap = this.doTianKong(paraObj1,paraObj2,questype);
		else if(QuestionConstant.QuesType_WanXingTianKong.equals(questype))
			rtnMap = this.doWanXingTianKong(paraObj1,paraObj2,questype);
//		else if(QuestionConstant.QuesType_PeiDui.equals(questype))
//			rtnMap = this.doPeiDui(paraObj1,paraObj2,questype);
		else if(QuestionConstant.QuesType_YueDuLiJie.equals(questype))
			rtnMap = this.doYueDuLiJie(paraObj1,paraObj2,questype);
		else if(QuestionConstant.QuesType_WenDa.equals(questype))
			rtnMap = this.doWenDa(paraObj1,paraObj2,questype);
//		else if(QuestionConstant.QuesType_CaiLiao.equals(questype))
//			rtnMap = this.doCaiLiao(paraObj1,paraObj2,questype);
		else if(QuestionConstant.QuesType_XZ_NoTrunk.equals(questype))
			rtnMap = this.doDanXuan(paraObj1,paraObj2,questype);
		 
	    return rtnMap;
	}
	
	/** 处理单选题 **/
	protected abstract Object doDanXuan(Object paraObj1,Object paraObj2,Integer questype);
	/** 处理多选题 **/
	protected abstract Object doDuoXuan(Object paraObj1,Object paraObj2,Integer questype);
	/** 处理判断题 **/
	protected abstract Object doPanDuan(Object paraObj1,Object paraObj2,Integer questype);
	/** 处理填空题 **/
	protected abstract Object doTianKong(Object paraObj1,Object paraObj2,Integer questype);
	/** 处理完形填空题 **/
	protected abstract Object doWanXingTianKong(Object paraObj1,Object paraObj2,Integer questype);
	/** 处理配对题 **/
	//protected abstract Object doPeiDui(Object paraObj1,Object paraObj2,Integer questype);
	/** 处理阅读理解题 **/
	protected abstract Object doYueDuLiJie(Object paraObj1,Object paraObj2,Integer questype);
	/** 处理问答题 **/
	protected abstract Object doWenDa(Object paraObj1,Object paraObj2,Integer questype);
	/** 处理材料题 **/
	//protected abstract Object doCaiLiao(Object paraObj1,Object paraObj2,Integer questype);
	/** 处理noTrunk类型的单选题，主要是完形填空题的子题目 **/
	protected abstract Object doDanXuanNoTrunk(Object paraObj1,Object paraObj2,Integer questype);
	
}

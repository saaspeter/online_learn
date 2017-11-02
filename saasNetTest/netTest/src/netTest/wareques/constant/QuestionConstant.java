package netTest.wareques.constant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import netTest.util.ResourceBundleUtil;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.constant.LabelValueVO;

public class QuestionConstant {
	
	/** 题库题目 **/
	public static final int Question_ware = 1;
	/** 试卷题目 **/
	public static final int Question_paper = 2;
	
	
	/** 题型：客观题 **/
	public static final Short QuesPattern_object = 1;
	/** 题型：主观题 **/
	public static final Short QuesPattern_subject = 2;
	
	/** 题目类型的静态数据的常量编码 **/
	public static final String QuesType_ConsCode = "Question.QuesType";
	
	/** 题目类型：单选题 **/
	public static final Integer QuesType_DanXuan = 1;
	/** 题目类型：多选题 **/
	public static final Integer QuesType_DuoXuan = 2;
	/** 题目类型：判断题 **/
	public static final Integer QuesType_PanDuan = 3;
	/** 题目类型：填空题 **/
	public static final Integer QuesType_TianKong = 4;
	/** 题目类型：完形填空题 **/
	public static final Integer QuesType_WanXingTianKong = 5;
	/** 题目类型：配对题 , 暂时不做，没有时间 **/
	//public static final Integer QuesType_PeiDui = 6;
	/** 题目类型：阅读理解题 **/
	public static final Integer QuesType_YueDuLiJie = 7;
	/** 题目类型：问答题 **/
	public static final Integer QuesType_WenDa = 8;
	/** 题目类型：材料题 , 暂时不做，没有时间 **/
	//public static final Integer QuesType_CaiLiao = 9;
	/** 题目类型：选择题，没有题干，只有答案选项，例如完形填空的子题目 **/
	public static final Integer QuesType_XZ_NoTrunk = 10;
	
	/** 子题目类型：完形填空题的子题目类型，和单选题相同 **/
	public static final Integer QuesType_WanXingTianKong_Subques = 1;
	/** 子题目类型：阅读理解题的子题目类型，现在和多选题相同 **/
	public static final Integer QuesType_YueDuLiJie_Subques = 2;
	
	private static final String QuesType_DanXuan_code = "QuestionConstant.QuesType.DanXuan";
	private static final String QuesType_DuoXuan_code = "QuestionConstant.QuesType.DuoXuan";
	private static final String QuesType_PanDuan_code = "QuestionConstant.QuesType.PanDuan";
	private static final String QuesType_TianKong_code = "QuestionConstant.QuesType.TianKong";
	private static final String QuesType_WanXingTianKong_code = "QuestionConstant.QuesType.WanXingTianKong";
	private static final String QuesType_PeiDui_code = "QuestionConstant.QuesType.PeiDui";
	private static final String QuesType_YueDuLiJie_code = "QuestionConstant.QuesType.YueDuLiJie";
	private static final String QuesType_WenDa_code = "QuestionConstant.QuesType.WenDa";
	private static final String QuesType_CaiLiao_code = "QuestionConstant.QuesType.CaiLiao";
	
	
	/** 题目类型的静态数据的常量编码 **/
	public static final String Difficult_ConsCode = "Question.Difficult";
	
	/** 题目难度：难 **/
	public static final Integer DifficultID_Hard = 7;
	/** 题目难度：中 **/
	public static final Integer DifficultID_Normal = 4;
	/** 题目难度：易 **/
	public static final Integer DifficultID_Easy = 1;
	
	private static final String DifficultID_Hard_code = "QuestionConstant.DifficultID.Hard";
	private static final String DifficultID_Normal_code = "QuestionConstant.DifficultID.Normal";
	private static final String DifficultID_Easy_code = "QuestionConstant.DifficultID.Easy";
	
	
	/** 题目附件类型：0没有附件，1为图片，2为声音，3为视频 **/
	public static final Short FileType_None = 0;
	public static final Short FileType_pict = 1;
	public static final Short FileType_voice = 2;
	// 题目暂时不支持视频
	//public static final Short FileType_video = 3;
	
	/** 题目选项是否正确，1为正确，2为错误 **/
	public static final Short IsRight_right = 1;
	public static final Short IsRight_wrong = 2;
	
	/** 题目判题方式，1为自动判题，2为手动判题，3为两者皆可(例如填空题) **/
	public static final Short Queschecktype_auto = 1;
	public static final Short Queschecktype_manual = 2;
	public static final Short Queschecktype_both = 3;
	
	/** 复合题目类型：0代表独立的题目，没有子题目；1代表独立题目，有子题目，阅读理解或完形填空。
	 * 2代表非独立题目，是复合题目的子题目，阅读理解、完形填空的下的子问题 **/
	public static final Short Comptype_whole = 0;
	public static final Short Comptype_compWhole = 1;
	public static final Short Comptype_compPart = 2;
	
	/** 题目状态，1代表有效，2为失效 **/
	public static final Short Status_valide = 1;
	public static final Short Status_invalide = 2;
		
	
	/** 填空题目中需要填空的开始和结束标记符 **/
	public static final String SplitChar_begin = "〖";
	public static final String SplitChar_end = "〗";
	/** 填空题目中的转义符，当用户输入的字符串中有〖时将被转为/〖，〗被转为/〗 **/
	public static final String SplitChar_transfer = "/";
	
	// 暂时不支持视频，也不支持在题目中直接输入url链接
	/** 对于输入的video文件url, 直接显示该连接地址视频 DirectLink **/
	public static final String FileUrl_DirectLink_Prefix = "$DL$";
	/** 对于输入的video文件url, 使用swf player显示该视频 Use Player **/
	public static final String FileUrl_UsePlayer_Prefix = "$UP$";
	
	/** 选择题题目选项每行显示几个选项: 1 means一行放一个选项 **/
	public static final Short Rowitems_One = 1;
	/** 选择题题目选项每行显示几个选项: -1 means一行放所有选项 **/
	public static final Short Rowitems_AllInOneRow = -1;
	
	/**
	 * 得到题目类型列表，包括多种国家语言版本
	 * @return
	 */
	public static List getQuesTypeList(){
		String danXuan_name = "";
		String duoXuan_name = "";
		String panDuan_name = "";
		String tianKong_name = "";
		String wanXingTianKong_name = "";
		String peiDui_name = "";
		String yueDuLiJie_name = "";
		String wenDa_name = "";
		String caiLiao_name = "";
		
		List<LabelValueVO> listRtn = new ArrayList<LabelValueVO>();
		Iterator ite = I18nLogicImpl.localeMap.keySet().iterator();
		Locale locale = null;
		Long localeid = null;
		while(ite.hasNext()){
			localeid = (Long)ite.next();
			locale = I18nLogicImpl.localeMap.get(localeid).getEqualLocale();
			danXuan_name = ResourceBundleUtil.getInstance().getValue(QuesType_DanXuan_code, locale);
			duoXuan_name = ResourceBundleUtil.getInstance().getValue(QuesType_DuoXuan_code, locale);
			panDuan_name = ResourceBundleUtil.getInstance().getValue(QuesType_PanDuan_code, locale);
			tianKong_name = ResourceBundleUtil.getInstance().getValue(QuesType_TianKong_code, locale);
			wanXingTianKong_name = ResourceBundleUtil.getInstance().getValue(QuesType_WanXingTianKong_code, locale);
			//peiDui_name = ResourceBundleUtil.getInstance().getValue(QuesType_PeiDui_code, locale);
			yueDuLiJie_name = ResourceBundleUtil.getInstance().getValue(QuesType_YueDuLiJie_code, locale);
			wenDa_name = ResourceBundleUtil.getInstance().getValue(QuesType_WenDa_code, locale);
			//caiLiao_name = ResourceBundleUtil.getInstance().getValue(QuesType_CaiLiao_code, locale);
			
			LabelValueVO vo1 = new LabelValueVO(danXuan_name,String.valueOf(QuesType_DanXuan),localeid,String.valueOf(getQueschecktype(QuesType_DanXuan)),null);
			LabelValueVO vo2 = new LabelValueVO(duoXuan_name,String.valueOf(QuesType_DuoXuan),localeid,String.valueOf(getQueschecktype(QuesType_DuoXuan)),null);
			LabelValueVO vo3 = new LabelValueVO(panDuan_name,String.valueOf(QuesType_PanDuan),localeid,String.valueOf(getQueschecktype(QuesType_PanDuan)),null);
			LabelValueVO vo4 = new LabelValueVO(tianKong_name,String.valueOf(QuesType_TianKong),localeid,String.valueOf(getQueschecktype(QuesType_TianKong)),null);
			LabelValueVO vo5 = new LabelValueVO(wanXingTianKong_name,String.valueOf(QuesType_WanXingTianKong),localeid,String.valueOf(getQueschecktype(QuesType_WanXingTianKong)),null);
			//LabelValueVO vo6 = new LabelValueVO(peiDui_name,String.valueOf(QuesType_PeiDui),localeid,String.valueOf(getQueschecktype(QuesType_PeiDui)),null);
			LabelValueVO vo7 = new LabelValueVO(yueDuLiJie_name,String.valueOf(QuesType_YueDuLiJie),localeid,String.valueOf(getQueschecktype(QuesType_YueDuLiJie)),null);
			LabelValueVO vo8 = new LabelValueVO(wenDa_name,String.valueOf(QuesType_WenDa),localeid,String.valueOf(getQueschecktype(QuesType_WenDa)),null);
			//LabelValueVO vo9 = new LabelValueVO(caiLiao_name,String.valueOf(QuesType_CaiLiao),localeid,String.valueOf(getQueschecktype(QuesType_CaiLiao)),null);
			listRtn.add(vo1);
			listRtn.add(vo2);
			listRtn.add(vo3);
			listRtn.add(vo4);
			listRtn.add(vo8);
			listRtn.add(vo5);
			//listRtn.add(vo6);
			listRtn.add(vo7);
			
			//listRtn.add(vo9);
		}
		return listRtn;
	}
	
	public static String getQuestypename(Integer questype, Locale locale){
		String questypename = "";
		if(questype!=null && locale!=null){
			String questyepCode = null;
			if(QuesType_DanXuan.equals(questype)){
				questyepCode = QuesType_DanXuan_code;
			}else if(QuesType_DuoXuan.equals(questype)){
				questyepCode = QuesType_DuoXuan_code;
			}else if(QuesType_PanDuan.equals(questype)){
				questyepCode = QuesType_PanDuan_code;
			}else if(QuesType_TianKong.equals(questype)){
				questyepCode = QuesType_TianKong_code;
			}else if(QuesType_WanXingTianKong.equals(questype)){
				questyepCode = QuesType_WanXingTianKong_code;
			}else if(QuesType_YueDuLiJie.equals(questype)){
				questyepCode = QuesType_YueDuLiJie_code;
			}else if(QuesType_WenDa.equals(questype)){
				questyepCode = QuesType_WenDa_code;
			}
			
			questypename = ResourceBundleUtil.getInstance().getValue(questyepCode, locale);
		}
		return questypename;
	}
	
	
	/**
	 * 得到题目难度列表
	 * @return
	 */
	public static List getDifficultList(){
		String hard_name = "";
		String normal_name = "";
		String easy_name = "";
		String none_name = "";
		
		List<LabelValueVO> listRtn = new ArrayList<LabelValueVO>();
		Iterator ite = I18nLogicImpl.localeMap.keySet().iterator();
		Locale locale = null;
		Long localeid = null;
		while(ite.hasNext()){
			localeid = (Long)ite.next();
			locale = I18nLogicImpl.localeMap.get(localeid).getEqualLocale();
			hard_name = ResourceBundleUtil.getInstance().getValue(DifficultID_Hard_code, locale);
			normal_name = ResourceBundleUtil.getInstance().getValue(DifficultID_Normal_code, locale);
			easy_name = ResourceBundleUtil.getInstance().getValue(DifficultID_Easy_code, locale);
			
			LabelValueVO vo2 = new LabelValueVO(easy_name,String.valueOf(DifficultID_Easy),localeid,"1",null); // param1为1代表有难度取值
			LabelValueVO vo3 = new LabelValueVO(normal_name,String.valueOf(DifficultID_Normal),localeid,"1",null);
			LabelValueVO vo4 = new LabelValueVO(hard_name,String.valueOf(DifficultID_Hard),localeid,"1",null);		
	
			listRtn.add(vo2);
			listRtn.add(vo3);
			listRtn.add(vo4);
		}
		return listRtn;
	}
	
	/**
	 * 根据复合型的父题目的类型决定子题目的类型
	 * @param questype_parent
	 * @return
	 */
	public static Integer getSubquestype(Integer questype_parent){
		if(questype_parent==null)
			return null;
		Integer subquestype = null;
		if(QuestionConstant.QuesType_WanXingTianKong.equals(questype_parent))
			subquestype = QuestionConstant.QuesType_XZ_NoTrunk;
		if(QuestionConstant.QuesType_YueDuLiJie.equals(questype_parent))
			subquestype = QuestionConstant.QuesType_DuoXuan;
//		if(QuestionConstant.QuesType_CaiLiao.equals(questype_parent))
//			subquestype = QuestionConstant.QuesType_WenDa;
		return subquestype;
	}
	
	/**
	 * 根据是哪一种题库得到这种题目的存放题目文件的文件夹
	 * @param quesKind
	 * @return
	 */
//	public static String getQuesFileDir(int quesKind){
//		if(quesKind==Question_ware)
//			return UploadFileUtil.WarequesFileDir;
//		else if(quesKind==Question_paper)
//			return UploadFileUtil.PaperquesFileDir;
//		else
//			return "";
//	}
	
	/**
	 * 根据题目类型决定题目的阅卷方式
	 * @param questype
	 * @return
	 */
	public static Short getQueschecktype(Integer questype){
		if(questype==null)
			return null;
		if(QuesType_DanXuan.equals(questype)||QuesType_DuoXuan.equals(questype)
			||QuesType_PanDuan.equals(questype)||QuesType_WanXingTianKong.equals(questype)
			//||QuesType_PeiDui.equals(questype)
			||QuesType_YueDuLiJie.equals(questype)){
			return Queschecktype_auto;
		}else if(QuesType_WenDa.equals(questype)
				//||QuesType_CaiLiao.equals(questype)
				){
			return Queschecktype_manual;
		}else if(QuesType_TianKong.equals(questype)){
			return Queschecktype_both;
		}else
			return null;
	}
	
}

package netTest.exam.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import netTest.util.ResourceBundleUtil;

import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.constant.LabelValueVO;

public class TestinfoConstant {

	/** 记录考试的状态:未开始考试1 **/
	public static final Short Teststatus_unStart = 1;
	/** 记录考试的状态:考试正在进行3 **/
	public static final Short Teststatus_started = 3;
	/** 记录考试的状态:考试结束5 **/
	public static final Short Teststatus_ended = 5;
	/** 记录考试的状态:客观题评阅中9，只出现在异步处理自动评阅试卷中，用户点击了自动评阅试卷，
	 * 考试状态变为此状态，当自动阅卷完毕后，状态自动变为别的状态 **/
	public static final Short Teststatus_autoChecking = 9;
	/** 记录考试的状态:主观题评阅中11 **/
	public static final Short Teststatus_manualChecking = 11;
	/** 记录考试状态:正在统计考试状态中 **/
	public static final Short Teststatus_statisticTest = 12;
	/** 记录考试的状态:试卷已批阅完毕13 **/
	public static final Short Teststatus_allChecked = 13;
	/** 记录考试的状态:开放考试结果15 **/
	public static final Short Teststatus_openExam = 15;
	
	//////---- 考试状态的控制状态，即仅仅用于显示 控制动作的状态，不对应真正的考试状态 ----
	//////---- 例如在考试监视界面 显示处理考试的动作 ----///////
	/** 记录考试的状态:开始阅卷7，只是一个动作，不显示在考试状态列表中 **/
	public static final Short Teststatus_beginCheck = 7;
	
	//////---- 下拉菜单中显示 考试状态，这些状态比较概括，用来查询考试列表 ----///////
	
	/** 记录考试的状态:考试准备进行中，包括：Teststatus_unStart, Teststatus_started **/
	public static final Short Teststatus_testPrepareOrStart = 6;
	
	/** 记录考试的状态:试卷评阅中10，该状态是为了对外显示考试评阅状态，因为不需要显示很细的考试状态
	 * 包括:ended, autoChecking, manaualChecking, statisticTest, allChecked **/
	public static final Short Teststatus_checkPaper = 10;
	
	
	/** 查看考试结果类型, 1 考试结束后, 管理员控制是否开放考试结果(分数和详细答卷分析) **/
	public static final Short ViewResultType_AdministorControl = 1;
	/** 查看考试结果类型, 2:  学员考完试立即可以查看考试分数 **/
	public static final Short ViewResultType_ViewScoreInstant = 2;
	/** 查看考试结果类型, 3: 学员考试结束后立即可以查看考试分数及详细答卷分析 **/
	public static final Short ViewResultType_ViewScorePaperInstant = 3;
	
	
	public static Map<Short, String> StatusRescMap;
	public static List<Short> RawstatusList;
	
	private static final String Teststatus_unStart_code = "TestinfoConstant.Teststatus.unStart";
	private static final String Teststatus_started_code = "TestinfoConstant.Teststatus.started";
	private static final String Teststatus_testPrepareOrStart_code = "TestinfoConstant.Teststatus.testPrepareOrStart";
	private static final String Teststatus_ended_code = "TestinfoConstant.Teststatus.ended";
	private static final String Teststatus_beginCheck_code = "TestinfoConstant.Teststatus.beginCheck";
	private static final String Teststatus_checkPaper_code = "TestinfoConstant.Teststatus.checkPaper";
	private static final String Teststatus_autoChecking_code = "TestinfoConstant.Teststatus.autoChecking";
	private static final String Teststatus_manualChecking_code = "TestinfoConstant.Teststatus.manualChecking";
	private static final String Teststatus_statisticTest_code = "TestinfoConstant.Teststatus.statisticTest";
	private static final String Teststatus_allChecked_code = "TestinfoConstant.Teststatus.allChecked";
	private static final String Teststatus_openExam_code = "TestinfoConstant.Teststatus.openExam";
	
	static{
		RawstatusList = new ArrayList<Short>();
		RawstatusList.add(Teststatus_unStart);
		RawstatusList.add(Teststatus_started);
		RawstatusList.add(Teststatus_ended);
		RawstatusList.add(Teststatus_beginCheck);
		RawstatusList.add(Teststatus_autoChecking);
		RawstatusList.add(Teststatus_manualChecking);
		RawstatusList.add(Teststatus_statisticTest);
		RawstatusList.add(Teststatus_allChecked);
		RawstatusList.add(Teststatus_openExam);
		
		StatusRescMap = new HashMap<Short, String>();
		StatusRescMap.put(Teststatus_unStart, Teststatus_unStart_code);
		StatusRescMap.put(Teststatus_started, Teststatus_started_code);
		StatusRescMap.put(Teststatus_ended, Teststatus_ended_code);
		StatusRescMap.put(Teststatus_beginCheck, Teststatus_beginCheck_code);
		StatusRescMap.put(Teststatus_checkPaper, Teststatus_checkPaper_code);
		StatusRescMap.put(Teststatus_autoChecking, Teststatus_autoChecking_code);
		StatusRescMap.put(Teststatus_manualChecking, Teststatus_manualChecking_code);
		StatusRescMap.put(Teststatus_statisticTest, Teststatus_statisticTest_code);
		StatusRescMap.put(Teststatus_allChecked, Teststatus_allChecked_code);
		StatusRescMap.put(Teststatus_openExam, Teststatus_openExam_code);
	}
	
	/** 考试过程中是否允许暂停考试:1 允许暂停 **/
	public static final Short Testcanstop_yes = 1;
	/** 考试过程中是否允许暂停考试:2 不允许暂停考试 **/
	public static final Short Testcanstop_no = 2;
	
	/** 考试开放类型，1: 课程所有学员都可参加 **/
	public static final Short OpenType_ProductUser = 1;
	/** 考试开放类型，2: 管理员选择参与考试的学员 **/
	public static final Short OpenType_TestUser = 2;
	/** 考试开放类型，3: 对整个互联网开放， 暂时没有用到公开考试功能 **/
	//public static final Short OpenType_AllUser_Internet = 3;
	
	
	/**
	 * 根据目前的teststatus,返回用于查询的status string，包含当前状态
	 * @param teststatus
	 * @param includebeforestatus 是否包括该状态之前的状态
	 * @return
	 */
	public static String getTeststatusquerystring(Short teststatus, boolean includebeforestatus) {
		if(teststatus==null){
			return null;
		}
		String teststatusquerystring = "";
		if(includebeforestatus){ // 包含之前的状态
			for(Short status : RawstatusList){
				if(status.shortValue() <= teststatus.shortValue()){
					teststatusquerystring += status.toString()+",";
				}
			}
		}else {
			if(Teststatus_testPrepareOrStart.equals(teststatus)){
				teststatusquerystring += Teststatus_unStart.toString()+","+Teststatus_started.toString();
			}else if(Teststatus_checkPaper.equals(teststatus)){
				teststatusquerystring += Teststatus_ended.toString()+","+Teststatus_autoChecking.toString()+","+Teststatus_manualChecking.toString()
				                         +","+ Teststatus_statisticTest.toString()+","+Teststatus_allChecked.toString();
			}else {
			    teststatusquerystring = teststatus.toString();
			}
		}
		if(teststatusquerystring.endsWith(",")){
			teststatusquerystring = teststatusquerystring.substring(0, teststatusquerystring.length()-1);
		}
		return teststatusquerystring;
	}

	
	/**
	 * 全部考试状态列表，主要用来显示状态名称，不用于显示下拉菜单
	 * @return
	 */
	public static List getTeststatusList(){
		String unStart_name = "";
		String started_name = "";
		String ended_name = "";
		String autoChecking_name = "";
		String manualChecking_name = "";
		String satisticTest_name = "";
		String allChecked_name = "";
		String openExam_name = "";
		
		List<LabelValueVO> listRtn = new ArrayList<LabelValueVO>();
		Iterator ite = I18nLogicImpl.localeMap.keySet().iterator();
		Locale locale = null;
		Long localeid = null;
		while(ite.hasNext()){
			localeid = (Long)ite.next();
			locale = I18nLogicImpl.localeMap.get(localeid).getEqualLocale();
			unStart_name = ResourceBundleUtil.getInstance().getValue(Teststatus_unStart_code, locale);
			started_name = ResourceBundleUtil.getInstance().getValue(Teststatus_started_code, locale);
			ended_name = ResourceBundleUtil.getInstance().getValue(Teststatus_ended_code, locale);
			autoChecking_name = ResourceBundleUtil.getInstance().getValue(Teststatus_autoChecking_code, locale);
			manualChecking_name = ResourceBundleUtil.getInstance().getValue(Teststatus_manualChecking_code, locale);
			satisticTest_name = ResourceBundleUtil.getInstance().getValue(Teststatus_statisticTest_code, locale);
			allChecked_name = ResourceBundleUtil.getInstance().getValue(Teststatus_allChecked_code, locale);
			openExam_name = ResourceBundleUtil.getInstance().getValue(Teststatus_openExam_code, locale);
			
			LabelValueVO vo1 = new LabelValueVO(unStart_name,String.valueOf(Teststatus_unStart),localeid,null,null);
			LabelValueVO vo2 = new LabelValueVO(started_name,String.valueOf(Teststatus_started),localeid,null,null);
			LabelValueVO vo3 = new LabelValueVO(ended_name,String.valueOf(Teststatus_ended),localeid,null,null);
			LabelValueVO vo4 = new LabelValueVO(autoChecking_name,String.valueOf(Teststatus_autoChecking),localeid,null,null);
			LabelValueVO vo5 = new LabelValueVO(manualChecking_name,String.valueOf(Teststatus_manualChecking),localeid,null,null);
			LabelValueVO vo6 = new LabelValueVO(satisticTest_name,String.valueOf(Teststatus_statisticTest),localeid,null,null);
			LabelValueVO vo7 = new LabelValueVO(allChecked_name,String.valueOf(Teststatus_allChecked),localeid,null,null);
			LabelValueVO vo8 = new LabelValueVO(openExam_name,String.valueOf(Teststatus_openExam),localeid,null,null);
						
			listRtn.add(vo1);
			listRtn.add(vo2);
			listRtn.add(vo3);
			listRtn.add(vo4);
			listRtn.add(vo5);
			listRtn.add(vo6);
			listRtn.add(vo7);
			listRtn.add(vo8);
		}
		return listRtn;
	}
	
	/**
	 * 得到对外显示的考试状态列表，用于对外显示考试状态，这里只显3种状态，
	 * 这三种状态对应的真正的考试状态见静态描述
	 * @return
	 */
	public static List getTeststatusshowList(){
		String testPrepareOrStart_name = "";
		String checkpaper_name = "";
		String openExam_name = "";
		
		List<LabelValueVO> listRtn = new ArrayList<LabelValueVO>();
		Iterator ite = I18nLogicImpl.localeMap.keySet().iterator();
		Locale locale = null;
		Long localeid = null;
		while(ite.hasNext()){
			localeid = (Long)ite.next();
			locale = I18nLogicImpl.localeMap.get(localeid).getEqualLocale();
			testPrepareOrStart_name = ResourceBundleUtil.getInstance().getValue(Teststatus_testPrepareOrStart_code, locale);
			checkpaper_name = ResourceBundleUtil.getInstance().getValue(Teststatus_checkPaper_code, locale);
			openExam_name = ResourceBundleUtil.getInstance().getValue(Teststatus_openExam_code, locale);
			
			LabelValueVO vo1 = new LabelValueVO(testPrepareOrStart_name,String.valueOf(Teststatus_testPrepareOrStart),localeid,null,null);
			LabelValueVO vo2 = new LabelValueVO(checkpaper_name,String.valueOf(Teststatus_checkPaper),localeid,null,null);
			LabelValueVO vo3 = new LabelValueVO(openExam_name,String.valueOf(Teststatus_openExam),localeid,null,null);
						
			listRtn.add(vo1);
			listRtn.add(vo2);
			listRtn.add(vo3);
		}
		return listRtn;
	}
	
	/**
	 * 得到控制考试的命令。例如: 当前状态如果是: Teststatus_started，则显示:开始考试
	 * 用于在考试监控页面来改变考试状态
	 * @return
	 */
	public static List getControlStatusList(){
		String unStart_name = "";
		String started_name = "";
		String ended_name = "";
		String beginCheck_name = "";
		//String autoChecking_name = "";
		String manualChecking_name = "";
		String allChecked_name = "";
		String openExam_name = "";
		
		List<LabelValueVO> listRtn = new ArrayList<LabelValueVO>();
		Iterator ite = I18nLogicImpl.localeMap.keySet().iterator();
		Locale locale = null;
		Long localeid = null;
		while(ite.hasNext()){
			localeid = (Long)ite.next();
			locale = I18nLogicImpl.localeMap.get(localeid).getEqualLocale();
			unStart_name = ResourceBundleUtil.getInstance().getValue(Teststatus_unStart_code, locale);
			started_name = ResourceBundleUtil.getInstance().getValue("TestinfoConstant.Teststatus.Control.StartTest", locale);
			ended_name = ResourceBundleUtil.getInstance().getValue("TestinfoConstant.Teststatus.Control.EndTest", locale);
			beginCheck_name = ResourceBundleUtil.getInstance().getValue("TestinfoConstant.Teststatus.Control.AutoCheckTest", locale);
			//autoChecking_name = ResourceBundleUtil.getInstance().getValue(Teststatus_autoChecking_code, locale);
			manualChecking_name = ResourceBundleUtil.getInstance().getValue("TestinfoConstant.Teststatus.Control.ManaualCheckTest", locale);
			allChecked_name = ResourceBundleUtil.getInstance().getValue(Teststatus_allChecked_code, locale);
			openExam_name = ResourceBundleUtil.getInstance().getValue("TestinfoConstant.Teststatus.Control.OpenTestResult", locale);
			
			LabelValueVO vo1 = new LabelValueVO(unStart_name,String.valueOf(Teststatus_unStart),localeid,null,null);
			LabelValueVO vo2 = new LabelValueVO(started_name,String.valueOf(Teststatus_started),localeid,null,null);
			LabelValueVO vo3 = new LabelValueVO(ended_name,String.valueOf(Teststatus_ended),localeid,null,null);
			LabelValueVO vo4 = new LabelValueVO(beginCheck_name,String.valueOf(Teststatus_beginCheck),localeid,null,null);
			//LabelValueVO vo5 = new LabelValueVO(autoChecking_name,String.valueOf(Teststatus_autoChecking),localeid,null,null);
			LabelValueVO vo6 = new LabelValueVO(manualChecking_name,String.valueOf(Teststatus_manualChecking),localeid,null,null);
			LabelValueVO vo7 = new LabelValueVO(allChecked_name,String.valueOf(Teststatus_allChecked),localeid,null,null);
			LabelValueVO vo8 = new LabelValueVO(openExam_name,String.valueOf(Teststatus_openExam),localeid,null,null);
						
			listRtn.add(vo1);
			listRtn.add(vo2);
			listRtn.add(vo3);
			listRtn.add(vo4);
			//listRtn.add(vo5);
			listRtn.add(vo6);
			listRtn.add(vo7);
			listRtn.add(vo8);
		}
		return listRtn;
	}
	
	/**
	 * 得到考试状态的下一个状态
	 * @param teststatus
	 * @return
	 */
	public static Short getNextTeststatus(Short teststatus){
		if(teststatus==null)
			return null;
		Short nextteststatus = null;
		if(TestinfoConstant.Teststatus_unStart.equals(teststatus)){
			nextteststatus = TestinfoConstant.Teststatus_started;
		}else if(TestinfoConstant.Teststatus_started.equals(teststatus)){
			nextteststatus = TestinfoConstant.Teststatus_ended;
		}else if(TestinfoConstant.Teststatus_ended.equals(teststatus)){
			nextteststatus = TestinfoConstant.Teststatus_beginCheck;
		}
		// 不可能有这两个状态下去显示下一个状态的业务流程
//		else if(TestinfoConstant.Teststatus_beginCheck.equals(teststatus)){
//			nextteststatus = TestinfoConstant.Teststatus_autoChecking;
//		}else if(TestinfoConstant.Teststatus_autoChecking.equals(teststatus)){
//			nextteststatus = TestinfoConstant.Teststatus_manualChecking;
//		}
		else if(TestinfoConstant.Teststatus_manualChecking.equals(teststatus)){
			nextteststatus = TestinfoConstant.Teststatus_allChecked;
		}else if(TestinfoConstant.Teststatus_allChecked.equals(teststatus)){
			nextteststatus = TestinfoConstant.Teststatus_openExam;
		}
		return nextteststatus;
	}
	
	/**
	 * 得到考试状态的下一个状态名称
	 * @param teststatus
	 * @return
	 */
	public static String getNextTeststatusName(Short teststatus, Long localeid){
		if(teststatus==null)
			return "";
		Short nextteststatus = getNextTeststatus(teststatus);
		String resCode = StatusRescMap.get(nextteststatus);
		Locale locale = I18nLogicImpl.localeMap.get(localeid).getEqualLocale();
		String resc_name = ResourceBundleUtil.getInstance().getValue(resCode, locale);
		return resc_name;
	}
	
	public List getConstItems(){
		return null;
	}
	
}

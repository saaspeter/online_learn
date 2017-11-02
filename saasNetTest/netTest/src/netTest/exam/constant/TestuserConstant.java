package netTest.exam.constant;

import java.util.List;

public class TestuserConstant {


	/** 考生的状态:未开始考试1 **/
	public static final Short Status_unStart = 1;
	/** 考生的状态:正在考试3 **/
	public static final Short Status_examing = 3;
//	/** 考生的状态:暂停考试5 **/
//	public static final Short Status_suspend = 5;
	/** 考生的状态:提交答卷7 **/
	public static final Short Status_endExam = 7;
	
	/** 是否几个，1为及格，0为不及格 **/
	public static final Short IsQualify_passed = 1;
	public static final Short IsQualify_notpassed = 0;

	/** 粗略的考试状态, 为了在用户页面查询考试使用, 1代表unStart和started, 2我的已经结束的考试 **/
	public static final int RoughTestStatus_upcoming = 1;
	public static final int RoughTestStatus_finish = 2;
	
	
	public List getConstItems(){
		return null;
	}
	
}

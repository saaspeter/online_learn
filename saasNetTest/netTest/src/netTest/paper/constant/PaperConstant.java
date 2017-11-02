package netTest.paper.constant;

public class PaperConstant {

	/** 试卷状态：有效 **/
	public static final Short PaperStatus_valide = 1;
	/** 试卷状态：失效 **/
	public static final Short PaperStatus_notValide = 2;
	
	/** 试卷生成阶段：等待生成 **/
	public static final Short PaperPhase_toMake = 1;
	/** 试卷生成阶段：正在生成 **/
	public static final Short PaperPhase_inMaking = 3;
	/** 试卷生成阶段：已经生成 **/
	public static final Short PaperPhase_finished = 5;
	
	/** 试卷生成方式，1为普通的固定试题生成，2为生成的动态试卷 **/
	public static final Short GeneType_fixed = 1;
	public static final Short GeneType_dynamic = 2;
	
	/** 暂时不需要乱序，以后如果需要，需要在记录选项在paper或exercise中? 或者记录在每次的考试或练习表中 **/
	public static final int Ques_Random = 0;
		
}

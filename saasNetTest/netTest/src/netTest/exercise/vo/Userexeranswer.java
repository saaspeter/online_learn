package netTest.exercise.vo;

import java.util.List;

import netTest.common.vo.NettestBaseVO;
import netTest.util.QueshowUtil;
import netTest.wareques.constant.QuestionConstant;

public class Userexeranswer extends NettestBaseVO{

    private Long useranswerid;

    private Long exerid;

    private Long userid;
    
    private Long exerquesid;

    private Long quesid;

    private String quesitemflag;

    private Integer questype;

    private Long pid;

    private Short queschecktype;

    private Float answerscore;

    private String isright;

    private Short examinestatus;

    private String answer;
    
    /** 用来收集多项选择题的答案，因为是数组 **/
    private String[] answerArr;
    
    /** 复合题目的子题目顺序，和问题中的字问题集合对应 **/
    private List<Userexeranswer> subanswList;
    
    public final static String ObjectType = "userexeranswer";
    
    
	/**
	 * 根据题目类型组装并返回用户的答案。如果是填空则使用answerArr数组，否则直接用字符串
	 * @param questype:题目类型
	 */ 
    public String getAnswerByType(Integer questype) {
    	if(questype!=null&&QuestionConstant.QuesType_TianKong.equals(questype)){
    		this.answer = QueshowUtil.wrapUserAnswerTK(this.answerArr);
    	}else if(answerArr!=null&&answerArr.length>0){
            StringBuffer buff = new StringBuffer(); 
            for(int i=0;i<this.answerArr.length;i++){
                buff.append(this.answerArr[i]).append(",");
            }
            this.answer = buff.substring(0,buff.length()-1);
    	}
        return this.answer;
    }

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Float getAnswerscore() {
		return answerscore;
	}

	public void setAnswerscore(Float answerscore) {
		this.answerscore = answerscore;
	}

	public Short getExaminestatus() {
		return examinestatus;
	}

	public void setExaminestatus(Short examinestatus) {
		this.examinestatus = examinestatus;
	}

	public Long getExerid() {
		return exerid;
	}

	public void setExerid(Long exerid) {
		this.exerid = exerid;
	}

	public String getIsright() {
		return isright;
	}

	public void setIsright(String isright) {
		this.isright = isright;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Short getQueschecktype() {
		return queschecktype;
	}

	public void setQueschecktype(Short queschecktype) {
		this.queschecktype = queschecktype;
	}

	public Long getQuesid() {
		return quesid;
	}

	public void setQuesid(Long quesid) {
		this.quesid = quesid;
	}

	public String getQuesitemflag() {
		return quesitemflag;
	}

	public void setQuesitemflag(String quesitemflag) {
		this.quesitemflag = quesitemflag;
	}

	public Integer getQuestype() {
		return questype;
	}

	public void setQuestype(Integer questype) {
		this.questype = questype;
	}

	public Long getUseranswerid() {
		return useranswerid;
	}

	public void setUseranswerid(Long useranswerid) {
		this.useranswerid = useranswerid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getExerquesid() {
		return exerquesid;
	}

	public void setExerquesid(Long exerquesid) {
		this.exerquesid = exerquesid;
	}

	public List<Userexeranswer> getSubanswList() {
		return subanswList;
	}

	public void setSubanswList(List<Userexeranswer> subanswList) {
		this.subanswList = subanswList;
	}

	public String[] getAnswerArr() {
		return answerArr;
	}

	public void setAnswerArr(String[] answerArr) {
		this.answerArr = answerArr;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}

}
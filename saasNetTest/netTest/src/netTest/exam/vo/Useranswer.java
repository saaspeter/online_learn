package netTest.exam.vo;

import java.util.List;

import netTest.common.vo.NettestBaseVO;
import netTest.util.QueshowUtil;
import netTest.wareques.constant.QuestionConstant;


public class Useranswer extends NettestBaseVO{

	private static final long serialVersionUID = 1L;

	private Long useranswerid;
	
    private Long testid;

    private Long userid;

    private Long paperid;

    private Long quesid;

    private String quesitemflag;
    
    private Long questypeid;
    
    private Integer questype;
    
    private Long pid;
    
    private Short queschecktype;

    private Float answerscore;
    /** 是否正确。1正确，2错误。对于填空题有多个空，此处填写多个1或2，表明填空题中的每个空的正确性 **/
    private String isright;

    private Short examinestatus;

    /** 代表在选出的题目中的顺序号，用于乱序取题目 **/
    private Integer quesorder;

    /** 题目选项序号串，如：2,4,5,3,1，代表存放在list中的题目答案选项取出的顺序 **/
    private String itemorderstr;

    private String answer;
    /** 用来收集多项选择题的答案，因为是数组 **/
    private String[] answerArr;
    
    /** 复合题目的子题目顺序，和问题中的字问题集合对应 **/
    private List<Useranswer> subanswList;
    /** 记录所有子题目的quesid的字符串，中间用逗号(,)隔开。
     * 当为用户组建试卷题目时，会把所有子题目的id记录在主题目中 **/
    //private String subquesidStr;
    
    public final static String ObjectType = "useranswer";
      

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public Long getTestid() {
        return testid;
    }

    public void setTestid(Long testid) {
        this.testid = testid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getPaperid() {
        return paperid;
    }

    public void setPaperid(Long paperid) {
        this.paperid = paperid;
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

    public Integer getQuesorder() {
        return quesorder;
    }

    public void setQuesorder(Integer quesorder) {
        this.quesorder = quesorder;
    }

    public String getItemorderstr() {
        return itemorderstr;
    }

    public void setItemorderstr(String itemorderstr) {
        this.itemorderstr = itemorderstr;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

	public Integer getQuestype() {
		return questype;
	}

	public void setQuestype(Integer questype) {
		this.questype = questype;
	}

	public List<Useranswer> getSubanswList() {
		return subanswList;
	}

	public void setSubanswList(List<Useranswer> subanswList) {
		this.subanswList = subanswList;
	}

	public String[] getAnswerArr() {
		return answerArr;
	}

	// 将数组型多选题答案转换到answer属性中，收集多选题或填空题时调用
	public void setAnswerArr(String[] answerArr) {
		this.answerArr = answerArr;
	}

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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getUseranswerid() {
		return useranswerid;
	}

	public void setUseranswerid(Long useranswerid) {
		this.useranswerid = useranswerid;
	}

	public String getIsright() {
		return isright;
	}

	public void setIsright(String isright) {
		this.isright = isright;
	}

	public Long getQuestypeid() {
		return questypeid;
	}

	public void setQuestypeid(Long questypeid) {
		this.questypeid = questypeid;
	}

	public Short getQueschecktype() {
		return queschecktype;
	}

	public void setQueschecktype(Short queschecktype) {
		this.queschecktype = queschecktype;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}
    
}
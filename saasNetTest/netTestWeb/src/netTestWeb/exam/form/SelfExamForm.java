
package netTestWeb.exam.form;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import netTest.exam.vo.Answerquestype;
import netTest.exam.vo.Testuser;
import netTest.exam.vo.Useranswer;
import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperquestype;
import netTestWeb.base.BaseForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SelfExamForm extends BaseForm {
	
	private Long testid;
	private Long testuserid;
	private Long userid;
	
	private Testuser testuserVO;
    private Paper paperVO;
    /** 用户答案列表，是某一种题型的用户答案列表。
     * 在显示页面时可以用来展现用户的题目，用户提交页面时也可以用来提交用户答案
     *  **/
    private List<Useranswer> usranwlist;
    
    /** 正在使用的题目题型VO，包含的是题目的信息 **/
    private Paperquestype typeUseVO;
    /** 用户答卷题目类型vo,其中包含该题型用户答案的分数和用户每题的特定信息 **/
    private Answerquestype answertypeVO;
    
    /** 正在使用的题目类型(展现编辑的题型) **/
    private Integer questypeUse;
    private Long questypeidUse;
    
    /** 上一页要展现的题型，-3代表是第一种题型的第一页，没有上一页了。-2代表要跳转至上一种题型 **/
    private int pagenumPrv;
    /** 下一页要展现的题型，-3代表是最后一种题型的最后一页。-1代表需要跳转至下一种题型 **/
    private int pagenumNxt;
    /** 考生点击的即将显示的页数 **/
    private int pagenumShw;
    
    /** 用户点击的将要显示的题型 **/
    private Long questypeidShw;
    
    /** 考试试卷剩余的时间 **/
    private int remainTime;
    /** 用户做试卷得到的试卷总分 **/
    private Float totalscore;
    /** 试卷的版本号，用于判断试卷是否有效 **/
    private int paperversion;
    
    /** 考试的查看类型 **/
    private Short testviewresulttype;
    /** 显示在exam paper上的名字，自测时显示试卷名，考试时显示考试名称 **/
    private String examnameshow;

	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		paperVO = new Paper();
		usranwlist = null;
		answertypeVO = null;
		questypeUse = null;
		pagenumPrv = 0;
		pagenumNxt = 0;
		pagenumShw = 0;
		questypeidShw = null;
		remainTime = 0;
		paperversion = 0;
		questypeidUse = null;
		testid = null;
		testuserid = null;
		testuserVO = new Testuser();
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	public Paper getPaperVO() {
		return paperVO;
	}

	public void setPaperVO(Paper paperVO) {
		this.paperVO = paperVO;
	}

	public List<Useranswer> getUsranwlist() {
		if(usranwlist==null)
			usranwlist = new ArrayList<Useranswer>();
		return usranwlist;
	}

	public void setUsranwlist(List<Useranswer> usranwlist) {
		this.usranwlist = usranwlist;
	}
	
    /**
     * 收集用户答案
     */
    public Object getUseranswer(int key) {
    	usranwlist = getUsranwlist();
        if(usranwlist.size()<=key){
            int iCount = key-usranwlist.size()+1;
            for(int i=0;i<iCount;i++)
            {
            	Useranswer voTemp = new Useranswer();
            	usranwlist.add(voTemp);
            }        	
        	return usranwlist.get(key);
        }
    	return usranwlist.get(key);
    }
    
	public Integer getQuestypeUse() {
		return questypeUse;
	}

	public void setQuestypeUse(Integer questypeUse) {
		this.questypeUse = questypeUse;
	}

	public Paperquestype getTypeUseVO() {
		return typeUseVO;
	}

	public void setTypeUseVO(Paperquestype typeUseVO) {
		this.typeUseVO = typeUseVO;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}

	public Answerquestype getAnswertypeVO() {
		return answertypeVO;
	}

	public void setAnswertypeVO(Answerquestype answertypeVO) {
		this.answertypeVO = answertypeVO;
	}

	public Float getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(Float totalscore) {
		this.totalscore = totalscore;
	}

	public int getPaperversion() {
		return paperversion;
	}

	public void setPaperversion(int paperversion) {
		this.paperversion = paperversion;
	}

	public Long getQuestypeidUse() {
		return questypeidUse;
	}

	public void setQuestypeidUse(Long questypeidUse) {
		this.questypeidUse = questypeidUse;
	}

	public int getPagenumNxt() {
		return pagenumNxt;
	}

	public void setPagenumNxt(int pagenumNxt) {
		this.pagenumNxt = pagenumNxt;
	}

	public Long getQuestypeidShw() {
		return questypeidShw;
	}

	public void setQuestypeidShw(Long questypeidShw) {
		this.questypeidShw = questypeidShw;
	}

	public int getPagenumPrv() {
		return pagenumPrv;
	}

	public void setPagenumPrv(int pagenumPrv) {
		this.pagenumPrv = pagenumPrv;
	}

	public int getPagenumShw() {
		return pagenumShw;
	}

	public void setPagenumShw(int pagenumShw) {
		this.pagenumShw = pagenumShw;
	}

	public Long getTestid() {
		return testid;
	}

	public void setTestid(Long testid) {
		this.testid = testid;
	}

	public Long getTestuserid() {
		return testuserid;
	}

	public void setTestuserid(Long testuserid) {
		this.testuserid = testuserid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Testuser getTestuserVO() {
		return testuserVO;
	}

	public void setTestuserVO(Testuser testuserVO) {
		this.testuserVO = testuserVO;
	}

	public Short getTestviewresulttype() {
		return testviewresulttype;
	}

	public void setTestviewresulttype(Short testviewresulttype) {
		this.testviewresulttype = testviewresulttype;
	}

	public String getExamnameshow() {
		return examnameshow;
	}

	public void setExamnameshow(String examnameshow) {
		this.examnameshow = examnameshow;
	}


		
}

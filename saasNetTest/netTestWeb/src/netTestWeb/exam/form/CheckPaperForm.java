
package netTestWeb.exam.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import netTest.exam.dto.TestcheckerQuery;
import netTest.exam.dto.UseranswerQuery;
import netTest.exam.vo.Testinfo;
import netTest.exam.vo.Useranswer;
import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperques;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class CheckPaperForm extends BaseForm {
	
	private TestcheckerQuery queryVO;
	
    private Testinfo testVO;
	private Paper paperVO;
	/** 记录每题阅卷情况 **/
	private Map<Long,UseranswerQuery> quescheckMap;
	/**  **/
	private Long quesid;
	/** 父问题的id **/
	private Long pid;
	
	private Paperques quesVO;
	
	private List<Useranswer> useranswerList;
	/** 考试信息阅卷列表 **/
	private List checktestList;
	/** 待评阅试题数目 **/
	private long uncheckNum;
	
	private String questypename;
	/** 每一题的分值，对于填空而言是每个空的分值 **/
	private float quesscore;
	
	/** 1继续判卷，0提交后返回 **/
	private int tourltype;
	

	public int getTourltype() {
		return tourltype;
	}

	public void setTourltype(int tourltype) {
		this.tourltype = tourltype;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		queryVO = new TestcheckerQuery();
		paperVO = new Paper();
		testVO = new Testinfo();
		quesVO = new Paperques();
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

	public Testinfo getTestVO() {
		return testVO;
	}

	public void setTestVO(Testinfo testVO) {
		this.testVO = testVO;
	}

	public Map<Long,UseranswerQuery> getQuescheckMap() {
		return quescheckMap;
	}

	public void setQuescheckMap(Map<Long,UseranswerQuery> quescheckMap) {
		this.quescheckMap = quescheckMap;
	}

	public Long getQuesid() {
		return quesid;
	}

	public void setQuesid(Long quesid) {
		this.quesid = quesid;
	}

	public Paperques getQuesVO() {
		return quesVO;
	}

	public void setQuesVO(Paperques quesVO) {
		this.quesVO = quesVO;
	}

	public List<Useranswer> getUseranswerList() {
		if(useranswerList==null)
			useranswerList = new ArrayList<Useranswer>();
		return useranswerList;
	}

	public void setUseranswerList(List<Useranswer> useranswerList) {
		this.useranswerList = useranswerList;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public TestcheckerQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(TestcheckerQuery queryVO) {
		this.queryVO = queryVO;
	}

	public List getChecktestList() {
		return checktestList;
	}

	public void setChecktestList(List checktestList) {
		this.checktestList = checktestList;
	}

	public long getUncheckNum() {
		return uncheckNum;
	}

	public void setUncheckNum(long uncheckNum) {
		this.uncheckNum = uncheckNum;
	}

	public String getQuestypename() {
		return questypename;
	}

	public void setQuestypename(String questypename) {
		this.questypename = questypename;
	}

	/**
	 * 收集考生的分数
	 * @param key
	 * @return
	 */
    public Object getUserscore(int key) {
    	useranswerList = getUseranswerList();
        if(useranswerList.size()<=key){
            int iCount = key-useranswerList.size()+1;
            for(int i=0;i<iCount;i++)
            {
            	Useranswer voTemp = new Useranswer();
            	useranswerList.add(voTemp);
            }        	
        	return useranswerList.get(key);
        }
    	return useranswerList.get(key);
    }

	public float getQuesscore() {
		return quesscore;
	}

	public void setQuesscore(float quesscore) {
		this.quesscore = quesscore;
	}
		
}

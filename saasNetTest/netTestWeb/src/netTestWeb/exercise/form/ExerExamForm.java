
package netTestWeb.exercise.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.exercise.vo.Exercise;
import netTest.exercise.vo.Exerques;
import netTest.exercise.vo.Exerquestype;
import netTest.exercise.vo.Exeruser;
import netTest.exercise.vo.Userexeranswer;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


public class ExerExamForm extends BaseForm {
	
	private static final long serialVersionUID = 6216612127795464751L;

	private Long exerid;
	//private Long exeruserid;
	private Long userid;
	private String exername;
	
	private Exercise exerVO;
	private Exeruser exeruserVO;
    
	private Exerques quesVO;
    /** 用户答案 **/
    private Userexeranswer useranswerVO;
    
    /** 正在使用的题目题型VO，包含的是题目的信息 **/
    private Exerquestype typeUseVO;
    /** 用户答卷题目类型vo,其中包含该题型用户答案的分数和用户每题的特定信息 **/
    //private Answerquestype answertypeVO;
    
    /** 正在使用的题目类型(展现编辑的题型) **/
    //private Integer questypeUse;
    //private Long questypeidUse;
    
    /** 每种题型中的第多少题 **/
    private int quesIndex;
    /** 1代表下一题，-1代表上一题 **/
    private int direction;
    /** 共有多少道题 **/
    private int quesnum;
    
    /** 做练习方式，1为管理员测试练习使用，2为学员正式做练习 **/
	private int testway;
    
	/** 标识练习是否有题目的，1有题目，0没有题目，不能学习该练习 **/
	private int hasques;
	
	private Short exeruserStatus;
    
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		useranswerVO = new Userexeranswer();
		hasques=1;
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
	
    /**
     * 收集用户答案
     */
    public Object getUseranswer(int key) {
    	Userexeranswer useranswerVO = this.getUseranswerVO();
    	List<Userexeranswer> subanwlist = useranswerVO.getSubanswList();
    	if(subanwlist==null){
    		subanwlist = new ArrayList<Userexeranswer>();
    		useranswerVO.setSubanswList(subanwlist);
    	}
    	if(subanwlist.size()<=key){
            int iCount = key-subanwlist.size()+1;
            for(int i=0;i<iCount;i++)
            {
            	Userexeranswer voTemp = new Userexeranswer();
            	subanwlist.add(voTemp);
            }        	
        }
    	return subanwlist.get(key);
    }

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Exeruser getExeruserVO() {
		return exeruserVO;
	}

	public void setExeruserVO(Exeruser exeruserVO) {
		this.exeruserVO = exeruserVO;
	}

	public Exercise getExerVO() {
		return exerVO;
	}

	public void setExerVO(Exercise exerVO) {
		this.exerVO = exerVO;
	}

	public Exerquestype getTypeUseVO() {
		return typeUseVO;
	}

	public void setTypeUseVO(Exerquestype typeUseVO) {
		this.typeUseVO = typeUseVO;
	}

	public Userexeranswer getUseranswerVO() {
		return useranswerVO;
	}

	public void setUseranswerVO(Userexeranswer useranswerVO) {
		this.useranswerVO = useranswerVO;
	}

	public int getQuesIndex() {
		return quesIndex;
	}

	public void setQuesIndex(int quesIndex) {
		this.quesIndex = quesIndex;
	}

	public Exerques getQuesVO() {
		return quesVO;
	}

	public void setQuesVO(Exerques quesVO) {
		this.quesVO = quesVO;
	}

	public Long getExerid() {
		return exerid;
	}

	public void setExerid(Long exerid) {
		this.exerid = exerid;
	}

	public String getExername() {
		return exername;
	}

	public void setExername(String exername) {
		this.exername = exername;
	}

	public int getTestway() {
		return testway;
	}

	public void setTestway(int testway) {
		this.testway = testway;
	}

	public int getDirection() {
		if(direction>1)
			direction = 1;
		else if(direction<-1){
			direction = -1;
		}
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getHasques() {
		return hasques;
	}

	public void setHasques(int hasques) {
		this.hasques = hasques;
	}

	public int getQuesnum() {
		return quesnum;
	}

	public void setQuesnum(int quesnum) {
		this.quesnum = quesnum;
	}

	public Short getExeruserStatus() {
		return exeruserStatus;
	}

	public void setExeruserStatus(Short exeruserStatus) {
		this.exeruserStatus = exeruserStatus;
	}

		
}

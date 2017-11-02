package netTest.wareques.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import netTest.common.vo.NettestBaseVO;
import netTest.product.vo.ProductMini;
import netTest.wareques.constant.QuestionConstant;

import org.apache.struts.upload.FormFile;

public class Question extends NettestBaseVO implements QuesBaseVOInf{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Long quesid;

    protected Long wareid;
    
    protected String warename;
        
    /** 题目类型，包括系统自带的题目类型和用户自定义的题目类型。
     * 如果是系统自带的题型则为负值，由sql手动插入。用户自定义的题型的id由数据库生成 **/
    protected Long questypeid;
    
    protected Integer questype;
    
    protected String questypename;

    protected Integer quesoptnum;

    protected Date quescreatetime;

    protected Short queschecktype;
    
    /** 知识点 **/
    private Long contentcateid;

    private String contentcatename;
    
    protected Integer difficultid;

    protected String fileurl;
    // 暂时不用fileurlprefix和fileurloriginal，暂时不支持video
    /** 当filetype是video, 如何使用文件url, 直接显示该连接地址视频 DirectLink
     * 或者使用swf player显示该视频 Use Player. 区别在fileurl的prefix，见QuestionConstant **/
    protected String fileurlprefix;
    /** 真是的fileurl,去除前缀的fileurl **/
    protected String fileurloriginal;

    protected Short filetype;
    
    protected String filetypeStr;
    
    /** 数据库中数据的文件类型，从页面传过来，不用再次查询数据库即可知道修改前的状态 **/
    protected Short filetypeOrigin;

    protected String answersim;
    /** 0代表独立的题目，没有子题目；1代表独立题目，有子题目。2代表非独立题目，是复合题目的子题目 **/
    protected Short comptype;
    /** how many ques items in one row,  default 1: one item in one row,  -1 means: all items in one row **/
    protected Short rowitems;
    
    /** 仅仅为方便查询使用，当设置该为true时，comptype自动设置为null **/
    protected Boolean qrywholeques;

    protected Long pid;

    protected Integer refnum;

    protected Long creatorid;

    protected Short status;

    protected String question;

    protected String difficultname;
    
    /** 显示顺序；并不对应数据库字段 **/
    protected Integer disorder;
    
    /** 题目多媒体文件 **/
    protected FormFile quesFile;
    /** 题目选项列表 **/
    protected List itemList;
    /** 问题答案VO **/
    protected Quesanswer answerVO;
    /** 问题答案的数组，例如填空题答案数组 **/
    protected String[] answerArr;
    /** 问题的答案，把各种类型题目的答案转换到这个字段 **/
    protected String answer;

    
    /** 该复合题目的子题目列表，list内也是Question的类或其子类 **/
    protected List<Question> subquesList;
    /** 复合题目存放子题目的Map集合 **/
    protected Map<Long,Question> subquesMap;
    
    public static final String ObjectType = "question";
    
    
    public Long getQuesid() {
    	return quesid;
    }

    public void setQuesid(Long quesid) {
    	this.quesid = quesid;
    }

    public Integer getQuestype() {
    	return questype;
    }

    public void setQuestype(Integer questype) {
    	this.questype = questype;
    }

    public Integer getQuesoptnum() {
    	return quesoptnum;
    }

    public void setQuesoptnum(Integer quesoptnum) {
    	this.quesoptnum = quesoptnum;
    }

    public Date getQuescreatetime() {
    	return quescreatetime;
    }

    public void setQuescreatetime(Date quescreatetime) {
    	this.quescreatetime = quescreatetime;
    }

    public Short getQueschecktype() {
    	return queschecktype;
    }

    public void setQueschecktype(Short queschecktype) {
    	this.queschecktype = queschecktype;
    }

    public Integer getDifficultid() {
    	return difficultid;
    }

    public void setDifficultid(Integer difficultid) {
    	this.difficultid = difficultid;
    }

    public String getFileurl() {
    	return fileurl;
    }

    public void setFileurl(String fileurl) {
    	this.fileurl = fileurl;
    }

    public Short getFiletype() {
    	return filetype;
    }

    public void setFiletype(Short filetype) {
    	this.filetype = filetype;
    }

    public String getAnswersim() {
    	return answersim;
    }

    public void setAnswersim(String answersim) {
    	this.answersim = answersim;
    }

    public Long getPid() {
    	return pid;
    }

    public void setPid(Long pid) {
    	this.pid = pid;
    }

    public Integer getRefnum() {
    	return refnum;
    }

    public void setRefnum(Integer refnum) {
    	this.refnum = refnum;
    }

    public Long getCreatorid() {
    	return creatorid;
    }

    public void setCreatorid(Long creatorid) {
    	this.creatorid = creatorid;
    }

    public Short getStatus() {
    	return status;
    }

    public void setStatus(Short status) {
    	this.status = status;
    }

    public String getQuestion() {
    	return question;
    }

    public void setQuestion(String question) {
    	this.question = question;
    }

	public String getDifficultname() {
		return difficultname;
	}

	public void setDifficultname(String difficultname) {
		this.difficultname = difficultname;
	}

	public FormFile getQuesFile() {
		return quesFile;
	}

	public void setQuesFile(FormFile quesFile) {
		this.quesFile = quesFile;
	}

	public List getItemList() {
		if(itemList==null)
			itemList = new ArrayList();
		return itemList;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	public Quesanswer getAnswerVO() {
		return answerVO;
	}

	public void setAnswerVO(Quesanswer answerVO) {
		this.answerVO = answerVO;
	}

	public Short getFiletypeOrigin() {
		return filetypeOrigin;
	}

	public void setFiletypeOrigin(Short filetypeOrigin) {
		this.filetypeOrigin = filetypeOrigin;
	}

	public Short getComptype() {
		return comptype;
	}

	public void setComptype(Short comptype) {
		this.comptype = comptype;
	}

	public void setQrywholeques(Boolean qrywholeques) {
		// 为了在ibatis查询中当设置了该条件时compType不起作用
		if(qrywholeques.equals(Boolean.TRUE))
		   comptype = null;
		this.qrywholeques = qrywholeques;
	}

	public Boolean getQrywholeques() {
		return qrywholeques;
	}
	
	public boolean isEmptyUpdate(){
		if(this.questype!=null||this.quesoptnum!=null||this.quescreatetime!=null||this.queschecktype!=null||this.productbaseid!=null
				||this.difficultid!=null||this.fileurl!=null||this.filetype!=null||this.answersim!=null||this.comptype!=null
				||this.pid!=null||this.refnum!=null||this.creatorid!=null||this.status!=null||this.question!=null)
			return false;
		else 
			return true;
	}
	
//	public void filterQues(){
//		// 过滤填空题的答案
//		if(questype!=null&&QuestionConstant.QuesType_TianKong.equals(questype)){ 
//			if(question!=null&&question.trim().length()>0){
//				String pattStr = "";
//			}
//		}
//	}

	public String[] getAnswerArr() {
		return answerArr;
	}

	public void setAnswerArr(String[] answerArr) {
		this.answerArr = answerArr;
	}

	public List<Question> getSubquesList() {
		if(subquesList==null)
			subquesList = new ArrayList<Question>();
		return subquesList;
	}

	public void setSubquesList(List<Question> subquesList) {
		this.subquesList = subquesList;
	}

	public Integer getDisorder() {
		return disorder;
	}

	public void setDisorder(Integer disorder) {
		this.disorder = disorder;
	}

	public Long getContentcateid() {
		return contentcateid;
	}

	public void setContentcateid(Long contentcateid) {
		this.contentcateid = contentcateid;
	}

	public String getContentcatename() {
		return contentcatename;
	}

	public void setContentcatename(String contentcatename) {
		this.contentcatename = contentcatename;
	}

	public Long getWareid() {
		return wareid;
	}

	public void setWareid(Long wareid) {
		this.wareid = wareid;
	}

	public String getWarename() {
		return warename;
	}

	public void setWarename(String warename) {
		this.warename = warename;
	}
	
	/** 得到问题的种类：题库题目 **/
	public int getQuesKind(){
		return QuestionConstant.Question_ware;
	}

	public String getFiletypeStr() {
		return filetypeStr;
	}

	public void setFiletypeStr(String filetypeStr) {
		this.filetypeStr = filetypeStr;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Map<Long, Question> getSubquesMap() {
		return subquesMap;
	}

	public void setSubquesMap(Map<Long, Question> subquesMap) {
		this.subquesMap = subquesMap;
	}

	public Long getQuestypeid() {
		return questypeid;
	}

	public void setQuestypeid(Long questypeid) {
		this.questypeid = questypeid;
	}

	public String getQuestypename() {
		return questypename;
	}

	public void setQuestypename(String questypename) {
		this.questypename = questypename;
	}

	@Override
	protected String getObjectType() {
		return "question";
	}

	public String getFileurloriginal() {
		if(fileurloriginal==null && fileurl!=null&&fileurl.trim().length()>0){
			if(fileurl.startsWith(QuestionConstant.FileUrl_DirectLink_Prefix)){
    			this.fileurlprefix = QuestionConstant.FileUrl_DirectLink_Prefix;
    			this.fileurloriginal = fileurl.substring(QuestionConstant.FileUrl_DirectLink_Prefix.length());
    		}else if(fileurl.startsWith(QuestionConstant.FileUrl_UsePlayer_Prefix)){
    			this.fileurlprefix = QuestionConstant.FileUrl_UsePlayer_Prefix;
    			this.fileurloriginal = fileurl.substring(QuestionConstant.FileUrl_UsePlayer_Prefix.length());
    		}else {
    			this.fileurloriginal = fileurl;
    		}
		}else {
			this.fileurloriginal = fileurl;
		}
		return fileurloriginal;
	}

	public String getFileurlprefix() {
		getFileurloriginal();
		return fileurlprefix;
	}

//	public String getQuesanswer(){
//		if(this.answer!=null&&this.answer.trim().length()>0)
//			return this.answer;
//		if(this.questype==null||QuestionConstant.Comptype_compWhole.equals(this.comptype)){
//			return "";
//		}
//		if(QuestionConstant.QuesType_DanXuan.equals(this.questype)
//		   ||QuestionConstant.QuesType_DuoXuan.equals(this.questype)){
//			
//		}else if(QuestionConstant.QuesType_PanDuan.equals(this.questype)
//		   ||QuestionConstant.QuesType_WenDa.equals(this.questype)){
//			
//		}else if(QuestionConstant.QuesType_TianKong.equals(this.questype)){
//			
//		}else if(QuestionConstant.QuesType_WanXingTianKong.equals(this.questype)
//		   ||QuestionConstant.QuesType_YueDuLiJie.equals(this.questype)){
//			
//		}
//		
//	}
	
	public Short getRowitems() {
		if(rowitems==null){
			return QuestionConstant.Rowitems_One;
		}else {
		    return rowitems;
		}
	}

	public void setRowitems(Short rowitems) {
		this.rowitems = rowitems;
	}
	
	public Long getContainerID() {
		return productbaseid;
	}

	public String getContainerType() {
		return ProductMini.ObjectType;
	}

}
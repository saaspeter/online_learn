package netTest.paper.vo;

import java.util.List;
import java.util.Set;

import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.vo.QuesBaseVOInf;
import netTest.wareques.vo.Question;
import commonTool.util.ObjUtil;

public class Paperques extends Question implements QuesBaseVOInf{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long paperquesid;
	
	private Long paperid;

    private Integer paperquesorder;

    private Float paperquesscore;

    private Short genetype;
    
    private List<Long> quesidlist;
    
    public static final String ObjectType = "paperques";


    public Long getPaperid() {
        return paperid;
    }

    public void setPaperid(Long paperid) {
        this.paperid = paperid;
    }

    public Integer getPaperquesorder() {
        return paperquesorder;
    }

    public void setPaperquesorder(Integer paperquesorder) {
        this.paperquesorder = paperquesorder;
    }

    public Float getPaperquesscore() {
        return paperquesscore;
    }

    public void setPaperquesscore(Float paperquesscore) {
        this.paperquesscore = paperquesscore;
    }

	public Long getPaperquesid() {
		return paperquesid;
	}

	public void setPaperquesid(Long paperquesid) {
		this.paperquesid = paperquesid;
	}

	/** 得到问题的种类：试卷题目 **/
	public int getQuesKind(){
		return QuestionConstant.Question_paper;
	}

	public Short getGenetype() {
		return genetype;
	}

	public void setGenetype(Short genetype) {
		this.genetype = genetype;
	}
	
	public void copyQuestion(Question quesVO){
		if(quesVO!=null){
			Long questypeid_old = this.questypeid;
		    ObjUtil.copyProperties(this, quesVO);
		    if(quesVO.getQuestypeid()==null){
				this.questypeid = questypeid_old;
			}
		}
	}

	public List<Long> getQuesidlist() {
		return quesidlist;
	}

	public void setQuesidlist(List<Long> quesidlist) {
		this.quesidlist = quesidlist;
	}
	
}
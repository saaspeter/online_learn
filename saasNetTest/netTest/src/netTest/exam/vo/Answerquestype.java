package netTest.exam.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 用于某种题型用户答题的情况，包括该题型的分数和题型中用户答题的vo集合
 * @author peter
 *
 */
public class Answerquestype implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long paperid;
    
    private Long userid;
    private Long questypeid;
    private Integer questype;

    private Long shopid;
    /** 该题型用户的总分数 **/
    private Float answerscore = 0f;
    
    private List<Useranswer> answerList;
    
    /** 每页题目数 **/
    private int pagesize;
	/** 总共的页数*/
	private int totalpage;
	/** 当前页数 */
	private int currentpage;
	private int startnum; // 从1开始
	private int endnum; // 包换endnum的元素
	/** 每页用户的题目答案list **/
	private List<Useranswer> pageList;
        

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

	public Long getPaperid() {
		return paperid;
	}

	public void setPaperid(Long paperid) {
		this.paperid = paperid;
	}

	public Integer getQuestype() {
		return questype;
	}

	public void setQuestype(Integer questype) {
		this.questype = questype;
	}

	public List<Useranswer> getAnswerList() {
		return answerList;
	}
	
	/**
	 * 得到从startnum到endnum之间的元素集合
	 * @return
	 */
	public List<Useranswer> getAnswerpageList() {
		if(answerList!=null){
			return answerList.subList(startnum-1, endnum);
		}else{
			return answerList;
		}
	}

	public void setAnswerList(List<Useranswer> answerList) {
		this.answerList = answerList;
	}

	public Float getAnswerscore() {
		return answerscore;
	}

	public void setAnswerscore(Float answerscore) {
		this.answerscore = answerscore;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		int totalNumber = answerList==null?0:answerList.size();
		this.currentpage = currentpage;
		this.startnum = (this.currentpage - 1)*pagesize+1;
		if(currentpage==totalpage){
			this.endnum = totalNumber;	
		}else{
		    this.endnum = this.startnum + this.pagesize - 1;
		}
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public List<Useranswer> getPageList() {
		return pageList;
	}

	public void setPageList(List<Useranswer> pageList) {
		this.pageList = pageList;
	}
	
	public void initPageInfo(Integer pagesizeObj){
		int totalNumber = 1;
		if(answerList!=null&&answerList.size()>0){
			totalNumber = answerList.size();
		}
		if(pagesizeObj==null||pagesizeObj.intValue()==0){
			this.pagesize = totalNumber; // 如果为空，则所有题目在一页中显示
		}else{
			this.pagesize = pagesizeObj.intValue();
		}
		this.totalpage = (totalNumber - 1) / pagesize + 1;
		setCurrentpage(1);
	}

	public int getEndnum() {
		return endnum;
	}

	public void setEndnum(int endnum) {
		this.endnum = endnum;
	}

	public int getStartnum() {
		return startnum;
	}

	public void setStartnum(int startnum) {
		this.startnum = startnum;
	}

	public Long getQuestypeid() {
		return questypeid;
	}

	public void setQuestypeid(Long questypeid) {
		this.questypeid = questypeid;
	}

}
package netTest.paper.vo;

import netTest.common.vo.NettestBaseVO;

public class RefQuizQues extends NettestBaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long quizid;

    private Long shopid;

    private Long quesid;
    
    private Integer quesorder;
    
    private Float quesscore;
    
    private Long wareid;
    
    private Short status;

	public Long getQuesid() {
		return quesid;
	}

	public void setQuesid(Long quesid) {
		this.quesid = quesid;
	}

	public Integer getQuesorder() {
		return quesorder;
	}

	public void setQuesorder(Integer quesorder) {
		this.quesorder = quesorder;
	}

	public Float getQuesscore() {
		return quesscore;
	}

	public void setQuesscore(Float quesscore) {
		this.quesscore = quesscore;
	}

	public Long getQuizid() {
		return quizid;
	}

	public void setQuizid(Long quizid) {
		this.quizid = quizid;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Long getWareid() {
		return wareid;
	}

	public void setWareid(Long wareid) {
		this.wareid = wareid;
	}

	@Override
	protected String getObjectType() {
		return "ref1uizques";
	}



}
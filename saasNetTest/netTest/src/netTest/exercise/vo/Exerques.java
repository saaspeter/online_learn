package netTest.exercise.vo;

import netTest.wareques.vo.QuesBaseVOInf;
import netTest.wareques.vo.Question;

public class Exerques extends Question implements QuesBaseVOInf {
	
	private Long exerquesid;

    private Long exerid;

    private Integer quesorder;

    private Float quesscore;

    
    
	public Long getExerid() {
		return exerid;
	}

	public void setExerid(Long exerid) {
		this.exerid = exerid;
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

	public Long getExerquesid() {
		return exerquesid;
	}

	public void setExerquesid(Long exerquesid) {
		this.exerquesid = exerquesid;
	}
    
}
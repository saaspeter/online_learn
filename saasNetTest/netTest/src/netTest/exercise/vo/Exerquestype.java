package netTest.exercise.vo;

import java.util.List;

import netTest.bean.BOFactory;
import netTest.common.vo.NettestBaseVO;
import netTest.product.vo.ProductMini;
import netTest.product.vo.Productbase;

public class Exerquestype extends NettestBaseVO{

    private Long patternid;

    private Long exerid;

//    private Long questypeid;

    private Integer questype;

    private String questypename;

    private String questypenote;

    private Integer patternquesnum;

    private Float quesscore;

    private Float patternscore;

    private Integer pagesize;

    private Integer disorder;
    
    private List<Exerques> quesList;
    
    public final static String ObjectType = "exerquestype";
    

	public Integer getDisorder() {
		return disorder;
	}

	public void setDisorder(Integer disorder) {
		this.disorder = disorder;
	}

	public Long getExerid() {
		return exerid;
	}

	public void setExerid(Long exerid) {
		this.exerid = exerid;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Long getPatternid() {
		return patternid;
	}

	public void setPatternid(Long patternid) {
		this.patternid = patternid;
	}

	public Integer getPatternquesnum() {
		return patternquesnum;
	}

	public void setPatternquesnum(Integer patternquesnum) {
		this.patternquesnum = patternquesnum;
	}

	public Float getPatternscore() {
		return patternscore;
	}

	public void setPatternscore(Float patternscore) {
		this.patternscore = patternscore;
	}

	public Float getQuesscore() {
		return quesscore;
	}

	public void setQuesscore(Float quesscore) {
		this.quesscore = quesscore;
	}

	public Integer getQuestype() {
		return questype;
	}

	public void setQuestype(Integer questype) {
		this.questype = questype;
	}

//	public Long getQuestypeid() {
//		return questypeid;
//	}
//
//	public void setQuestypeid(Long questypeid) {
//		this.questypeid = questypeid;
//	}

	public String getQuestypename() {
		return questypename;
	}

	public void setQuestypename(String questypename) {
		this.questypename = questypename;
	}

	public String getQuestypenote() {
		return questypenote;
	}

	public void setQuestypenote(String questypenote) {
		this.questypenote = questypenote;
	}

	public List<Exerques> getQuesList() {
		return quesList;
	}

	public void setQuesList(List<Exerques> quesList) {
		this.quesList = quesList;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}
	
	@Override
	public Long getContainerID() {
		if(productbaseid==null){
			Exercise exervo = BOFactory.getExerciseDao().selectByPK(exerid);
			if(exervo!=null){
				productbaseid = exervo.getProductbaseid();
			}
		}
		return productbaseid;
	}

	@Override
	public String getContainerType() {
		return ProductMini.ObjectType;
	}

}
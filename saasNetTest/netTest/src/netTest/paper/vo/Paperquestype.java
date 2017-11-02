package netTest.paper.vo;

import java.util.List;

import netTest.bean.BOFactory;
import netTest.common.vo.NettestBaseVO;
import netTest.product.vo.ProductMini;
import netTest.product.vo.Productbase;

public class Paperquestype extends NettestBaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long patternid;
	
	private Long paperid;

	private Long questypeid;
	
    private Integer questype;

    private String questypename;
    
    private String questypenote;

    private Integer patternquesnum;
    /** 每题的分值 **/
    private Float quesscore;
    /** 试卷上每种题型实际的分数值 **/
    private Float patternscore;
    /** 生成试卷时配置的题型分数 **/
    private Float pattconfigscore;
    
    /** 起辅助作用，不对应数据库字段，用于判断修改时分值是否有发生变化 **/
    private Float quesscore_old;
    
    private Integer pagesize;

    private Integer disorder;
    
    private List<Paperques> paperquesList;
        
    public final static String ObjectType = "paperquestype";
    

    public String getQuestypename() {
        return questypename;
    }

    public void setQuestypename(String questypename) {
        this.questypename = questypename;
    }

    public Integer getPatternquesnum() {
        return patternquesnum;
    }

    public void setPatternquesnum(Integer patternquesnum) {
        this.patternquesnum = patternquesnum;
    }

    public Float getQuesscore() {
        return quesscore;
    }

    public void setQuesscore(Float quesscore) {
        this.quesscore = quesscore;
    }

    public Float getPatternscore() {
        return patternscore;
    }

    public void setPatternscore(Float patternscore) {
        this.patternscore = patternscore;
    }

    public Integer getDisorder() {
        return disorder;
    }

    public void setDisorder(Integer disorder) {
        this.disorder = disorder;
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

	public List<Paperques> getPaperquesList() {
		return paperquesList;
	}

	public void setPaperquesList(List<Paperques> paperquesList) {
		this.paperquesList = paperquesList;
	}

	public Float getPattconfigscore() {
		return pattconfigscore;
	}

	public void setPattconfigscore(Float pattconfigscore) {
		this.pattconfigscore = pattconfigscore;
	}

	public Long getQuestypeid() {
		return questypeid;
	}

	public void setQuestypeid(Long questypeid) {
		this.questypeid = questypeid;
	}

	public Long getPatternid() {
		return patternid;
	}

	public void setPatternid(Long patternid) {
		this.patternid = patternid;
	}

	public String getQuestypenote() {
		return questypenote;
	}

	public void setQuestypenote(String questypenote) {
		this.questypenote = questypenote;
	}

	public Float getQuesscore_old() {
		return quesscore_old;
	}

	public void setQuesscore_old(Float quesscore_old) {
		this.quesscore_old = quesscore_old;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public static Long getQuestypeID(Integer questype){
		Long idTemp = null;
		if(questype!=null){
			idTemp = new Long(questype.toString()); 
		}
		return idTemp;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}
	
	public Long getContainerID() {
		if(productbaseid==null){
			Paper papervo = BOFactory.getPaperDao().selectByPK(paperid);
			if(papervo!=null){
				productbaseid = papervo.getProductbaseid();
			}
		}
		return productbaseid;
	}

	public String getContainerType() {
		return ProductMini.ObjectType;
	}

}
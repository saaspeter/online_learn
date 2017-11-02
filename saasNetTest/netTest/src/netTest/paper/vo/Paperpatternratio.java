package netTest.paper.vo;

import netTest.common.vo.NettestBaseVO;

public class Paperpatternratio extends NettestBaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long elementid;

    private Short elementtype;

    private Long paperid;

    private Long questypeid;
    
    private Integer questype;
	
    private Long shopid;

    private String elementname;

    private Integer elementnum;

    private Float elementratio;

    /** 组卷元素类型：1题库类型 **/
    public static final Short Elementtype_Ware = 1;
    /** 组卷元素类型：2难度类型 **/
    public static final Short Elementtype_Diff = 2;
    /** 组卷元素类型：3知识点类型 **/
    public static final Short Elementtype_Cont = 3;
    

    public Long getPaperid() {
        return paperid;
    }

    public void setPaperid(Long paperid) {
        this.paperid = paperid;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public Long getElementid() {
        return elementid;
    }

    public void setElementid(Long elementid) {
        this.elementid = elementid;
    }

    public String getElementname() {
        return elementname;
    }

    public void setElementname(String elementname) {
        this.elementname = elementname;
    }

    public Float getElementratio() {
        return elementratio;
    }

    public void setElementratio(Float elementratio) {
        this.elementratio = elementratio;
    }

    public Short getElementtype() {
        return elementtype;
    }

    public void setElementtype(Short elementtype) {
        this.elementtype = elementtype;
    }

	public Integer getElementnum() {
		return elementnum;
	}

	public void setElementnum(Integer elementnum) {
		this.elementnum = elementnum;
	}

	public Integer getQuestype() {
		return questype;
	}

	public void setQuestype(Integer questype) {
		this.questype = questype;
	}

	public Long getQuestypeid() {
		return questypeid;
	}

	public void setQuestypeid(Long questypeid) {
		this.questypeid = questypeid;
	}

	@Override
	protected String getObjectType() {
		return "paperpatternratio";
	}
    
}
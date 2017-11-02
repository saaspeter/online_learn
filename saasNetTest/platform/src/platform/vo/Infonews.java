package platform.vo;

import java.util.Date;

public class Infonews {

	private Long id;

    private Long shopid;

    private String syscode;

    private String caption;

    private Date createtime;

    private Long creator;
    
    private String content = "";
    /** 1: 系统消息  2: 新闻资讯 **/
    private Short type;
    
    private String logourl;
    
    private String openurl;
    
    private Long refid;
    
    private Short status;
    
    private Integer orderno;
    
    private Long categoryid;
    
    
    public static final Short TYPE_System_Message = 1;
    public static final Short TYPE_NewsCategory = 2;
    
    public static final String ObjectType = "infonews";
    

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogourl() {
		return logourl;
	}

	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}

	public String getOpenurl() {
		return openurl;
	}

	public void setOpenurl(String openurl) {
		this.openurl = openurl;
	}

	public Long getRefid() {
		return refid;
	}

	public void setRefid(Long refid) {
		this.refid = refid;
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

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}
	
}
package platform.vo;

public class Newscategory {

    private Long id;

    private String name;

    private Long localeid;

    private Long categoryid;
    
    private Short type;
    
    private Integer orderno;
    
    // pk of table: NewsCategoryValue
    private Long valueid;
    
    public static final String ObjectType = "newscategory";
    

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLocaleid() {
		return localeid;
	}

	public void setLocaleid(Long localeid) {
		this.localeid = localeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Long getValueid() {
		return valueid;
	}

	public void setValueid(Long valueid) {
		this.valueid = valueid;
	}

}
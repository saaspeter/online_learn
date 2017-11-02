package platform.vo;

public class Productcategoryvalue {
    
    protected Long categoryvalueid;
    protected Long categoryid;
    protected Long localeid;
    protected String categoryname;
    protected String categorydesc;
    
    /**  显示顺序  **/
    protected Integer disOrder;


    public Long getCategoryvalueid() {
        return categoryvalueid;
    }


    public void setCategoryvalueid(Long categoryvalueid) {
        this.categoryvalueid = categoryvalueid;
    }


    public Long getCategoryid() {
        return categoryid;
    }


    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }


    public Long getLocaleid() {
        return localeid;
    }


    public void setLocaleid(Long localeid) {
        this.localeid = localeid;
    }


    public String getCategoryname() {
        return categoryname;
    }


    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }


    public String getCategorydesc() {
        return categorydesc;
    }

    
    public void setCategorydesc(String categorydesc) {
        this.categorydesc = categorydesc;
    }

	public Integer getDisOrder() {
		return disOrder;
	}

	public void setDisOrder(Integer disOrder) {
		this.disOrder = disOrder;
	}
	
}
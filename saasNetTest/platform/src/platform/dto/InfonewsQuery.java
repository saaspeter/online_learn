package platform.dto;

import platform.vo.Infonews;

public class InfonewsQuery extends Infonews{

	private Long localeid;

    private Long categoryid;
    
    private String searchcontent;
	
    private String order_By_Clause;
    
   public InfonewsQuery() {
       super();
   }
   
   //public ShoppostQuery(Shoppost vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
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

	public String getSearchcontent() {
		return searchcontent;
	}

	public void setSearchcontent(String searchcontent) {
		this.searchcontent = searchcontent;
	}

   //public void copyProperty(Shoppost vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}

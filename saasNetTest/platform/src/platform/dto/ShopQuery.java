package platform.dto;

import platform.vo.Shop;

public class ShopQuery extends Shop{

   private Long categoryid;
   
   private String regioncode;
      
   private String searchtext;

    private String order_By_Clause;

   public ShopQuery() {
       super();
   }

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
	
    public String getRegioncode() {
	    return regioncode;
    }
	
    public void setRegioncode(String regioncode) {
	    this.regioncode = regioncode;
    }

	public String getSearchtext() {
		return searchtext;
	}

	public void setSearchtext(String searchtext) {
		this.searchtext = searchtext;
	}
}

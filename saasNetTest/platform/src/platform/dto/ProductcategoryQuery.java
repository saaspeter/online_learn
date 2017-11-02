package platform.dto;

import platform.vo.Productcategory;

public class ProductcategoryQuery extends Productcategory{

//  /**
//   * This field corresponds to the database column productcategoryrl.rlID
//   */
//   private Long rlid;
//
//  /**
//   * This field corresponds to the database column productcategoryrl.parentID
//   */
//   private Long parentid;
//
//   /**
//    * This field corresponds to the database column productcategoryrl.childID
//    */
//   private Long childid;
   
	// 查询时是否包含下级目录，1为包含，2为不包含下级目录。-1代表不设置
    private int isIncludeChild = -1;
	
   private String order_By_Clause;
   
   public ProductcategoryQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

	public int getIsIncludeChild() {
		return isIncludeChild;
	}
	
	public void setIsIncludeChild(int isIncludeChild) {
		this.isIncludeChild = isIncludeChild;
	}

//	public Long getChildid() {
//		return childid;
//	}
	
//	public Integer getChildnum() {
//		return childnum;
//	}
	
//	public Long getParentid() {
//		return parentid;
//	}
//	
//	public Long getRlid() {
//		return rlid;
//	}
//	
//	public void setChildid(Long childid) {
//		this.childid = childid;
//	}
	
//	public void setChildnum(Integer childnum) {
//		this.childnum = childnum;
//	}
	
//	public void setParentid(Long parentid) {
//		this.parentid = parentid;
//	}
//	
//	public void setRlid(Long rlid) {
//		this.rlid = rlid;
//	}
	
//	public String getParentName() {
//		return parentName;
//	}
//	
//	public void setParentName(String parentName) {
//		this.parentName = parentName;
//	}

}

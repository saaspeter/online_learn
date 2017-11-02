package netTestWeb.base;

import commonWeb.base.KeyInMemConstBase;

public class KeyInMemoryConstant extends KeyInMemConstBase{
	
     public final static String TestUseranswerKey = "TestUseranswerKey";	
     
     public final static String ExerUseranswerKey = "ExerUseranswerKey";	
     
     public final static String ValideEnterTestKey = "ValideEnterTestKey";
     
     /** session中存储categoryID的key **/
     public final static String CategoryID_Key = "CategoryIDKeyInSession";
     /** session中存储categoryName的key **/
     public final static String CategoryName_Key = "CategoryNameKeyInSession";
     
     /** 记录用户sessionForm在session中的key值，这个值一般在struts配置文件中配置 **/
     //public final static String UserSessionFormKey = "prodUseForm";
     
 	/////////////////购物车相关/////////////////////////
 	/** 放在购物车中的订单key，用来获取放在session中的购物车信息 **/
 	public static final String CustorderKey = "ShoppingCart.Custorder";
	
}

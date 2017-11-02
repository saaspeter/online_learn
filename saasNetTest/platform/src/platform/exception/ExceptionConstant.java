package platform.exception;

import commonTool.exception.ExceptionConstantBase;

public class ExceptionConstant extends ExceptionConstantBase{

	public static final String Error_UserLoginname_alreadyExist = "exception.userRegister.loginname.alreadyExists";
	
	public static final String Error_Email_alreadyExist = "exception.userRegister.email.alreadyExists";
	
	public static final String Error_ShopStatus_Abnormal = "exception.shop.status.abnormal";
	
	public static final String Error_Shopname_alreadyExist = "exception.applyshop.shopname.alreadyExists";
	
	public static final String Error_Shopcode_alreadyExist = "exception.applyshop.shopcode.alreadyExists";
	
	public static final String Error_Shopapp_appstatusNotValidate = "exception.shopapp.approve.appstatusNotValidate";
	
	public static final String Error_UpdateUserPassword_OldPasswordNotRight = "exception.user.updateUserPassword.oldPasswordNotRight";
	
	public static final String Error_Shop_NotAuthentiated = "exception.shop.NotAuthentiated";
}

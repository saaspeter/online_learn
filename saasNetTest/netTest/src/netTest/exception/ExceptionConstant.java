package netTest.exception;

import commonTool.exception.ExceptionConstantBase;

public class ExceptionConstant extends ExceptionConstantBase{
	
	public static final String Error_Product_notExist = "exception.product.notexist";
	
	public static final String Error_Course_NoEnoughLearnContent = "error.course.noEnoughLearnContent";

	public static final String Error_Testinfo_notExist = "exception.testinfo.notexist";
	
	public static final String Error_Paper_notExist = "exception.paper.notexist";
	
	public static final String Error_SelfExamAction_notInTest = "exception.selfExamAction.notInTest";
	
	public static final String Error_ExamAction_TestNotStart = "exception.ExamAction.TestNotStart";
	
	public static final String Error_ExamAction_TestAlreadyEnded = "exception.ExamAction.TestAlreadyEnded";
	
	public static final String Error_SelfExamAction_InvalideEnterTest = "exception.selfExamAction.invalideEnterTest";
	
	public static final String Error_User_HasProduct = "exception.user.hasProduct";
	
	public static final String Error_Exercise_NoQues = "exception.exercise.noQuestion";
	
	public static final String Error_RegionCode_AlreadyExist = "exception.regionCode.alreadyexist";
	
	public static final String Error_UserProduct_Delete_ActiveError = "exception.userProduct.delete.activeError";
	
	public static final String Error_Product_Delete_HasUserProduct = "exception.product.delete.hasUserProduct";
	
	public static final String Error_Product_Delete_HasActiveTestinfo = "exception.product.delete.hasActiveTest";
	
	public static final String Error_Paper_Delete_HasTestinfoRef = "exception.paper.delete.hasTestinfoRef";
	
	public static final String Error_ResourceLimit_MaxShopProduct = "commonWeb.errors.ResourceLimit.MaxShopProduct";
	
	public static final String Error_ResourceLimit_MaxProductUser = "commonWeb.errors.ResourceLimit.MaxProductUser";
	
	public static final String Error_ResourceLimit_MaxExamUser = "commonWeb.errors.ResourceLimit.MaxExamUser";
	
	public static final String Error_ProdUseComment_CannotComment = "exception.ProdUseComment.CannotComment";

	public static final String Error_Product_NotOpen = "exception.Product.NotOpen";
}

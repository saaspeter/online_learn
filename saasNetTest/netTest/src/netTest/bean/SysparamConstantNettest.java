package netTest.bean;

import org.springframework.beans.factory.InitializingBean;
import commonTool.constant.SysparamConstant;

/**
 * 记录保存平台系统相关的常量的系统编码，这些编码代表的值记录在数据库中
 * 这个应该可以和SysparamConstant.java合并，但暂时先这么做
 * 编码的长度不超过32位
 * @author fan
 */
public class SysparamConstantNettest extends SysparamConstant implements InitializingBean{
   

	/** 阅卷时每次取出考生答案题目的数量 **/
	public static Integer checkPaperAnswer_PageSize = 1;
	public static final String Key_CheckPaperAnswer_Size = "exampaper.checkpaperanswer.size";
	
	/** 文件导入用户时的最大导入量，目前主要是Excel导入, 保存在db中 **/
	public static Integer FileImportUser_maxNumLimit = 100;
	public static final String Key_FileImportUser_maxNumLimit = "FileImportUser.maxNumLimit";
	
	/** 文件导入题库题目时的最大导入量，目前主要是Excel导入 **/
	public static Integer FileImportQuestion_maxNumLimit = 100;
	public static final String Key_FileImportQuestion_maxNumLimit = "FileImportQuestion.maxNumLimit";
	
	/*************** 能上传的文件 File Size  **************/
	/** 文章中使用的上传图片，默认大小，单位是kb **/
	public static Float singleFileSize_article = -1f;
	/** 考试系统中试题中的上传文件大小，默认大小，单位是kb **/
	public static Float singleFileSize_exam = -1f;
	/** 考试系统中学习内容中的上传文件大小，默认大小，单位是kb **/
	public static Float singleFileSize_learncontent = -1f;
	
	/** 键：文章中的上传文件大小 **/
	private static final String Key_SingleFileSize_Article = "singlefile.article.size";
	/** 键：考试系统中试题中的上传文件大小 **/
	private static final String Key_SingleFileSize_Exam = "singlefile.exam.size";
	/** 键：学习内容中的上传文件大小 **/
	private static final String Key_SingleFileSize_LearnContent = "singlefile.learncontent.size";
	
	// 单个上传文件部分的moduleName
	public static final String ModuleName_SingleFileSize_Exam = "modulename.singlefile.exam";
	public static final String ModuleName_SingleFileSize_Article = "modulename.singlefile.article";
	public static final String ModuleName_SingleFileSize_LearnContent = "modulename.singlefile.learncontent";
	
	/***************  不同收费类型商店产品使用限制  *************/
	
	/** free shop最多能创建的产品数目 **/
	public static Integer maxProduct_freeShop_num = 0;
	/** paid shop最多能创建的产品数目 **/
	public static Integer maxProduct_paidShop_num = 0;
	/** free product产品允许选修人员的个数 **/
	public static Integer maxProductUser_freeProduct_num = 0;
	/** free product产品的考试允许参加人员的个数 **/
	public static Integer maxExamUser_freeProduct_num = 0;
	/** paid product产品允许选修人员的个数 **/
	public static Integer maxProductUser_paidProduct_num = 0;
	/** paid product产品的考试允许参加人员的个数 **/
	public static Integer maxExamUser_paidProduct_num = 0;
	
		
	private static final String Key_maxProduct_freeShop_num = "maxProduct.freeShop.num";
	private static final String Key_maxProduct_paidShop_num = "maxProduct.paidShop.num";
	private static final String Key_maxProductUser_freeProduct_num = "maxProductUser.freeProduct.num";
	private static final String Key_maxExamUser_freeProduct_num = "maxExamUser.freeProduct.num";
	private static final String Key_maxProductUser_paidProduct_num = "maxProductUser.paidProduct.num";
	private static final String Key_maxExamUser_paidProduct_num = "maxExamUser.paidProduct.num";
	
	
	
	public void afterPropertiesSet() throws Exception {
		baseLoad();
		// do local load
		if(getProperty(Key_CheckPaperAnswer_Size)!=null){
			String numStr = getProperty(Key_CheckPaperAnswer_Size);
			if(numStr!=null){
				checkPaperAnswer_PageSize = Integer.parseInt(numStr);
			}
		}
		if(getProperty(Key_SingleFileSize_Exam)!=null){
			String numStr1 = getProperty(Key_SingleFileSize_Exam);
			if(numStr1!=null){
				singleFileSize_exam = Float.parseFloat(numStr1);
			}
		}
		if(getProperty(Key_SingleFileSize_Article)!=null){
			String numStr2 = getProperty(Key_SingleFileSize_Article);
			if(numStr2!=null){
				singleFileSize_article = Float.parseFloat(numStr2);
			}
		}
		if(getProperty(Key_SingleFileSize_LearnContent)!=null){
			String numStr3 = getProperty(Key_SingleFileSize_LearnContent);
			if(numStr3!=null){
				singleFileSize_learncontent = Float.parseFloat(numStr3);
			}
		}
		if(getProperty(Key_maxProduct_freeShop_num)!=null){
			String numStr4 = getProperty(Key_maxProduct_freeShop_num);
			if(numStr4!=null){
				maxProduct_freeShop_num = Integer.parseInt(numStr4);
			}
		}
		if(getProperty(Key_maxProduct_paidShop_num)!=null){
			String numStr5 = getProperty(Key_maxProduct_paidShop_num);
			if(numStr5!=null){
				maxProduct_paidShop_num = Integer.parseInt(numStr5);
			}
		}
		if(getProperty(Key_maxProductUser_freeProduct_num)!=null){
			String numStr6 = getProperty(Key_maxProductUser_freeProduct_num);
			if(numStr6!=null){
				maxProductUser_freeProduct_num = Integer.parseInt(numStr6);
			}
		}
		if(getProperty(Key_maxExamUser_freeProduct_num)!=null){
			String numStr7 = getProperty(Key_maxExamUser_freeProduct_num);
			if(numStr7!=null){
				maxExamUser_freeProduct_num = Integer.parseInt(numStr7);
			}
		}
		if(getProperty(Key_maxProductUser_paidProduct_num)!=null){
			String numStr8 = getProperty(Key_maxProductUser_paidProduct_num);
			if(numStr8!=null){
				maxProductUser_paidProduct_num = Integer.parseInt(numStr8);
			}
		}
		if(getProperty(Key_maxExamUser_paidProduct_num)!=null){
			String numStr9 = getProperty(Key_maxExamUser_paidProduct_num);
			if(numStr9!=null){
				maxExamUser_paidProduct_num = Integer.parseInt(numStr9);
			}
		}
		if(getProperty(Key_FileImportUser_maxNumLimit)!=null){
			String numStr = getProperty(Key_FileImportUser_maxNumLimit);
			if(numStr!=null){
				FileImportUser_maxNumLimit = Integer.parseInt(numStr);
			}
		}
		if(getProperty(Key_FileImportQuestion_maxNumLimit)!=null){
			String numStr = getProperty(Key_FileImportQuestion_maxNumLimit);
			if(numStr!=null){
				FileImportQuestion_maxNumLimit = Integer.parseInt(numStr);
			}
		}
		
	}
	
	/**
	 * 根据所处的module返回此处的单个文件大小限制
	 * 例如: 在题目中的单个文件大小和文章中的单个文件大小是不相同的
	 * @param moduleName
	 * @return
	 */
	public static Float getSingleFileSizeLimit(String moduleName){
		Float rtnNum = -1f;
		if(ModuleName_SingleFileSize_Exam.endsWith(moduleName)){
			rtnNum = singleFileSize_exam;
		}else if(ModuleName_SingleFileSize_Article.endsWith(moduleName)){
			rtnNum = singleFileSize_article;
		}else if(ModuleName_SingleFileSize_LearnContent.endsWith(moduleName)){
			rtnNum = singleFileSize_learncontent;
		}
		return rtnNum;
	}
	
}

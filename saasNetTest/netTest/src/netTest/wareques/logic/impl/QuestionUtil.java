package netTest.wareques.logic.impl;

import netTest.bean.SysparamConstantNettest;
import netTest.paper.vo.Paperques;
import netTest.product.vo.Productbase;
import netTest.util.UploadFileUtilNettest;
import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.vo.Question;

import org.apache.struts.upload.FormFile;

import commonTool.util.UploadFileUtil;

import platform.vo.Shop;

public class QuestionUtil {

	/**
	 * 保存问题的文件，保存到总上传文件目录下的问题文件夹中
	 * @param quesid
	 * @param quesKind 问题的种类：题库题目还是试卷题目
	 * @param oldFile
	 * @return
	 */
	public static String saveQuesFile(Long quesid,Long shopid,Long productid,
			                          int quesKind,FormFile oldFile){
		String fileurl = null;
		if(quesid!=null){
		   String fileDir = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, shopid,  
														Productbase.ObjectType, productid, 
														Question.ObjectType, quesid);
		   // 暂时不处理storage问题
		   fileurl = UploadFileUtilNettest.saveFileFromStruts(getQuesFileName(quesid.toString()),oldFile,fileDir,
				             SysparamConstantNettest.ModuleName_SingleFileSize_Exam, null, null);
		}
		return fileurl;
	}
	
	/**
	 * 保存问题选项的文件，保存到总上传文件目录下的问题文件夹中
	 * @param quesid
	 * @param quesKind 问题的种类：题库题目还是试卷题目
	 * @param oldFile
	 * @return
	 */
	public static String saveQuesItemFile(Long quesid,String quesitemid,Long shopid,
									Long productid,	int quesKind,FormFile oldFile)
	{
		String fileurl = null;
		if(quesid!=null&&quesitemid!=null&&quesitemid.trim().length()>0){
		   String fileDir = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, shopid,  
												  Productbase.ObjectType, productid, 
												  Question.ObjectType, quesid);
		   // 上传文件，暂时不处理storage问题
		   fileurl = UploadFileUtilNettest.saveFileFromStruts(getItemFileName(quesitemid),oldFile,fileDir,
				   SysparamConstantNettest.ModuleName_SingleFileSize_Exam, null, null);
		}
		return fileurl;
	}
	
	/**
	 * 把题库题目中的文件拷贝到试卷题目文件夹中，可以拷贝题目的或题目选项的文件
	 * @param quesid: 试卷问题id
	 * @param quesitemid ：题目选项id
	 * @param quesfileurl: 源文件地址
	 * @return 新建文件的路径(fileurl格式)
	 */
	public static String copyQuesFileToPaper(Long quesid,String quesitemid,Long shopid,
											 Long productid,String quesfileurl){
		String paperfileurl = null;
		if(quesid!=null){
		   String filename = null;
		   if(quesitemid!=null&&quesitemid.trim().length()>0){
			   filename = getItemFileName(quesitemid);
		   }else{
			   filename = getQuesFileName(quesid.toString());
		   }
		   String paperquesFileDir = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, shopid,  
					  								Productbase.ObjectType, productid, 
					  									Paperques.ObjectType, quesid);
		   paperfileurl = UploadFileUtilNettest.copyFile(quesfileurl, filename, paperquesFileDir);
		}
		return paperfileurl;
	}
	
	/**
	 * 删除问题下面的所有文件
	 * @param quesid
	 * @param quesKind 问题的种类：题库题目还是试卷题目
	 * @return
	 */
	public static boolean delWholeQuesFile(Long quesid, Long shopid, Long productid)
	{
		if(quesid==null)
			return false;
		String quesDir = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, shopid,  
				  								Productbase.ObjectType, productid, 
				  								Question.ObjectType, quesid);
		UploadFileUtilNettest.delFile(null, quesDir, null, null, null);
		return true;
	}
	
	/**
	 * 删除问题quesid下的文件开头为pk的文件
	 * @param quesid:问题的id
	 * @param pk：要删除文件的名的主键
	 * @param quesKind 问题的种类：题库题目还是试卷题目
	 * @param deltype：1：删除题干文件，2：删除题目选项文件
	 * @return
	 */
	public static boolean delQuesPerfixFile(Long quesid, String pk,
										 Long shopid, Long productid, 
										  	int quesKind, int deltype)
	{
		if(quesid==null||pk==null||pk.trim().length()<1)
			return false;
		String quesFileDir = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, shopid,  
													Productbase.ObjectType, productid, 
													Question.ObjectType, quesid);
		String prefixName = null;
		if(deltype==1){
			prefixName = QuestionUtil.getQuesFileName(pk);
		}else if(deltype==2){
			prefixName = QuestionUtil.getItemFileName(pk);
		}
		if(prefixName!=null)
		   UploadFileUtilNettest.delFileByFileName(prefixName, quesFileDir);
		return true;
	}
	
	/**
	 * 默认的问题文件的名字，根据问题的主键
	 * @param pk
	 * @return
	 */
	private static String getQuesFileName(String pk){
		if(pk==null||pk.trim().length()<1)
			return "";
		return "q"+pk;
	}
	
	/**
	 * 计算填空题的分值
	 * @param quesscore
	 * @param answer
	 * @return
	 */
	public static float calScoreTianKong(float quesscore,String answer){
		float ret = 0f;
		if(quesscore<=0||answer==null||answer.trim().length()<1)
			return ret;
		char[] ansArr = answer.toCharArray();
		int rightNum = 0;
		for(int i=0;i<ansArr.length;i++){
			if(QuestionConstant.IsRight_right.toString().equals(String.valueOf(ansArr[i]))){
				rightNum++;
			}
		}
		ret = quesscore*rightNum;
		return ret;
	}
	
	/**
	 * 默认问题答案选项文件的名字，根据问题选项的主键
	 * @param pk
	 * @return
	 */
	private static String getItemFileName(String pk){
		if(pk==null||pk.trim().length()<1)
			return "";
		return "i"+pk;
	}
	
}

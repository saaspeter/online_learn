package platform.logic;

import java.util.List;
import java.util.Locale;

import org.apache.struts.upload.FormFile;

import platform.shop.vo.Shopext;
import platform.vo.Shop;
import platform.vo.ShopMini;
import platform.vo.Shopapp;
import platform.vo.Shopstatuslog;
import platform.vo.Shopstyleconfig;
import commonTool.exception.DuplicateException;
import commonTool.exception.NoLocaleValueException;

public interface ShopLogic {
	
    /**
     * 判断商店的名字或商店code是否重复
     * @param shopname
     * @param shopcode
     * @return 1:都不重复，2为商店code重复，3为商店名重复
     */
    public String checkDepulicateShop(String shopname, String shopcode);

	/**
	 * 申请商店，如果是新增商店则保存商店的功能，并在netLearn中增加一个顶级单位，给商店添加一个商店帐户
	 * 此处以后可能要改，因为可能要根据子系统不同而分别在子系统中执行不同的操作
	 * @param vo：商店vo
	 * @param funcArr：功能id的数组
	 */
	public Shopapp saveApplyNewShop(Shopapp vo, String prodcates) 
	       throws DuplicateException,Exception;
	
	/**
	 * 批量审批商店
	 * @param arrs
	 * @param appvo
	 * @return
	 * @throws Exception
	 */
	public int approveShopBatch(String[] arrs, Shopapp appvo) throws Exception;
	
	/**
	 * 管理员审批商店，根据审批状态结果分别处理
	 * 审核通过时： 新建商店帐户；更新商店管理员改变商店的信息；更新商店状态为正常使用状态；在学习系统中添加一个顶级单位
	 * 审核商店不通过且要删除商店的操作：删除商店信息；删除用户商店信息
	 * 审核商店不通过且需要用户修改商店的信息：修改商店信息
	 * @param vo
	 * @param appvo
	 * @return
	 * @throws Exception
	 */
	public boolean approveShop(Shopapp appvo) throws Exception;
	
	/**
	 * 更新用户商店基本信息，不涉及商店其他信息
	 * @param vo：商店vo
	 */
	public Shop updateShop(Shop vo) throws Exception;
	
	/**
	 * 根据商店id，删除商店信息，包括商店的多种语言设置信息
	 * TODO　在删除之前有很多东西要做
	 * @param shopid
	 * @return
	 * @throws Exception
	 */
	public abstract int delShop(Long shopid) throws Exception;

	/**
	 * 删除商店的某个语言设置,如果只剩一个语言设置了，则抛出异常
	 * @param shopid
	 * @param localeid
	 * @return
	 * @throws Exception
	 */
//	public abstract int delShopValue(Long shopid, Long localeid)
//			throws NoLocaleValueException, Exception;
	
	   /**
	    * 根据用户userid和用户使用的Locale得到该用户使用的用于设置商店的Locale集合的Lable。显示的是某个用户用于设置商店的Locale集合
	    * 用于在下拉菜单中显示用户使用过那些Locale设置商店
	    * @param userid
	    * @param locale
	    * @return Locale的Label集合
	    * @throws Exception
	    */
	   public List qryLabelUserSetLocales(Long userid,Locale locale)
	        throws Exception;
	   
	   /**
	    * 根据商店id得到该商店已经设置语言的列表，并且以用户指定的Locale显示。显示的是某个商店的Locale设置列表
	    * @param shopid
	    * @param locale：要显示的语言，一般为客户使用的语言
	    * @return
	    */
	   public List qryLabelShopSetLocales(Long shopid,Locale locale)
	        throws Exception;

		/**
		 * 处理商店状态的改变，暂时做了在商店状态记录表里插入一条商店状态改变的记录。
		 * 把商店基础表里的用户状态改变。以后还要加入别的判断逻辑
		 * @param vo
		 * @return
		 * @throws Exception
		 */
		public boolean changeStatus(Shop vo,Shopstatuslog logVO) throws Exception;
	    
	    /**
	     * select single shop mini object
	     */
	    public ShopMini getShopMini(Long shopid,Long localeid);
	    
	    /**
	     * 判断用户是否可以创建商店
	     * @param userid
	     * @return
	     */
	    public boolean canCreateShop(Long userid);
	    
	    /**
	     * 保存更新shop authen信息
	     * @param extvo
	     * @param isApprove, false表示是申请的时候保存，true表示是系统管理员审批
	     */
	    public void saveShopauthen(Shopext extvo, Long userid, boolean isApprove);
	    
	    public void saveShopstyle(Shopstyleconfig vo, FormFile logofile);
	    
	    /**
	     * 本商店是否支持上传文件到local server, 目前只有系统商店才可以
	     * @param shopid
	     * @return
	     */
	    public boolean ableUploadFileToLocalServer(Long shopid);
	    
	    /**
	     * 本商店是否支持上传文件到file server, 目前只有付费商店和系统商店才可以
	     * @param shopid
	     * @return
	     */
	    public boolean ableUploadFileToFileServer(Long shopid);
	    
	    /**
		 * 给dwr调用，是否可以在producateCategory上创建课程
		 * @param cateid
		 * @return
		 */
		public boolean checkAddProdInCate(Long cateid);
	    
}
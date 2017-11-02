package platform.logic;

import platform.vo.Productcategory;

public interface ProductcategoryLogic {
  
	/**
	 * 创建用于显示目录树的xml文件
	 * @param pid
	 * @param action
	 * @param url
	 * @param target
	 * @return
	 * @throws Exception
	 */
//	public String createTreeXml(Long pid, String syscode,String action, String url,
//			String target,Long localeid) throws Exception;
	
	
	/**
	 * 查询目录名称，主要是要为dwr页面调用，需要有cache
	 */
	public String qryCategoryname(Long pk, Long localeid);
	
	    
    /**
     * 根据商品目录id选择其平级目录的列表，用于向上一级查询中
     * @param ProductcategoryQuery：需要查询的平级目录的id,需要包括的参数有pid,localeid
     * @return Page
     */
//    public Page qryUpperCate(ProductcategoryQuery queryVO,int pageIndex,int pageSize,Integer total)
//                throws Exception;
    
    /**
     * 根据提交的产品目录ViewVO，将其中信息分解分别存入对应的数据表中。
     * 主要是为了区分不同语言对目录的不同描述。
     * @param viewVO
     * @return
     * @throws Exception
     */
    public boolean save(Productcategory vo);
    
//    /**
//     * 根据categoryid和localeid得到一条ViewproductcategoryQuery记录，
//     * 其数据是来源于查询得到的Viewproductcategory
//     * @param categoryid
//     * @param localeid
//     * @return
//     * @throws Exception
//     */
//    public ViewproductcategoryQuery selectViewQueryVO(Long categoryid,Long localeid) throws Exception;

    
    /**
     * 根据syscode得到该系统top的category
     * @param syscode
     * @param localeid
     * @return
     */
    public Productcategory getSystemTopCategory(String syscode, Long localeid);
    
}
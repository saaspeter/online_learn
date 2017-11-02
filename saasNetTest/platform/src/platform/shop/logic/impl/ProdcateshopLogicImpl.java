package platform.shop.logic.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import platform.bean.BeanFactory;
import platform.logic.ProductcategoryLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.logicImpl.ProductcategoryLogicImpl;
import platform.shop.dao.ProdcateshopDao;
import platform.shop.logic.ProdcateshopLogic;
import platform.shop.vo.Prodcateshop;
import platform.vo.Productcategory;
import commonTool.constant.CommonConstant;
import commonTool.util.StringUtil;

public class ProdcateshopLogicImpl implements ProdcateshopLogic{

	static Logger log = Logger.getLogger(ProdcateshopLogicImpl.class.getName());
	private ProdcateshopDao dao;

	
	/**
	 * 得到商店目录列表，如果没有指定pid则用各个子系统自己的默认pid
	 */
	public List getShopCateChildNodes(Long pid, Long shopid, Long localeid, String syscode) {
		if(pid==null || CommonConstant.TreeTopnodePid.equals(pid)){
			Productcategory cateVO = getCateLogic().getSystemTopCategory(syscode, localeid);
			pid = cateVO.getCategoryid();
		}
		List list = dao.getChildNodes(pid, shopid, localeid, syscode);
		return list;
	}
	
	/**
	 * 返回商店所涉及的产品目录名称字符串
	 * @param shopStr: shopid组成的字符串，其中以逗号隔开
	 * @param localeid
	 * @return
	 */
	public String[] getShopCategoryNames(String shopStr, Long localeid){
		if(shopStr==null||shopStr.trim().length()<1||localeid==null)
			return null;
		String[] shopArr = StringUtil.splitString(shopStr, ",");
		String[] rtnArr = new String[shopArr.length];
		for(int i=0; i<shopArr.length; i++){
			if(shopArr[i]==null||shopArr[i].trim().length()<1){
				continue;
			}
			StringBuffer buffer = new StringBuffer();
			List list = dao.selectByShop(new Long(shopArr[i]), localeid);
			if(list!=null&&list.size()>0){
				Prodcateshop tempVO = null;
				Productcategory cateVO = null;
				for(int j=0; j<list.size(); j++){
					tempVO = (Prodcateshop)list.get(j);
					cateVO = BOFactory_Platform.getProductcategoryDao().selectByLogicPK(tempVO.getCategoryid(), localeid);
					if(cateVO!=null){
						buffer.append(cateVO.getCategoryname()).append(", ");
					}
				}
			}
			rtnArr[i] = StringUtil.trimComma(buffer.toString());
		}
		return rtnArr;
	}
	
	/**
	 * 商店新增产品目录，过滤掉已经存在的目录
	 * @param categoryIdStr
	 * @param shopid
	 * @param syscode
	 * @return
	 */
    public int addCategory(String categoryIdStr, Long shopid, String syscode){
    	if(categoryIdStr==null||categoryIdStr.trim().length()<1||shopid==null
    			||syscode==null||syscode.trim().length()<1)
    		return 0;
    	String[] idArr = StringUtil.splitString(categoryIdStr, ",");
    	List<Prodcateshop> list = new ArrayList<Prodcateshop>();
    	int result = 0;
    	if(idArr!=null&&idArr.length>0){
    		Long categoryid = null;
    		boolean exist = false;
    		for(int i=0;i<idArr.length;i++){
    			categoryid = new Long(idArr[i]);
    			exist = dao.existsCate(categoryid, shopid, syscode);
    			if(!exist){
    				Prodcateshop vo = new Prodcateshop();
    	    		vo.setCategoryid(categoryid);
    	    		vo.setSyscode(syscode);
    	    		vo.setShopid(shopid);
    	    		list.add(vo);
    			}
    		}
    		result = dao.insertBatch(list, shopid);
    	}
    	return result;
    }
    
    /**
     * 删除shop的产品目录。先判断是否有子目录，然后判断目录下是否有产品
     * @return 1:成功删除 -1:缺少参数 -2:目录下有子目录 -3:该目录下还有产品
     */
    public int delCate(Long categoryid, Long shopid, String syscode){
    	if(categoryid==null||shopid==null||syscode==null||syscode.trim().length()<1)
    		return -1;
    	// 查询该目录下是否还有子目录。(商店选择的)
    	boolean exist = dao.existChildNode(categoryid, shopid, syscode);
    	if(exist){
    		return -2;
    	}
    	// 查询该目录下是否还有本商店的产品
    	exist = dao.cateExistProd(categoryid, shopid, syscode);
    	if(exist){
    		return -3;
    	}
    	// 删除
    	dao.deleteByPK(shopid, categoryid, syscode);
    	return 1;
    }
		
	/**
     * static spring getMethod
     */
     public static ProdcateshopLogic getInstance(){
    	 ProdcateshopLogic logic = (ProdcateshopLogic)BeanFactory.getBeanFactory().getBean("prodcateshopLogic");
         return logic;
     }
	

	public ProdcateshopDao getDao() {
		return dao;
	}

	public void setDao(ProdcateshopDao dao) {
		this.dao = dao;
	}

	public ProductcategoryLogic getCateLogic() {
		return ProductcategoryLogicImpl.getInstance();
	}

}

package netTest.product.logic;

import java.util.List;

import org.springframework.security.GrantedAuthority;

import commonTool.base.Page;
import commonTool.exception.LogicException;
import netTest.product.dto.ProductbaseQuery;
import netTest.product.vo.Productbase;


public interface ProductLogic {

	/**
	 * 查询目前学习人数最多的课程, cache不会自动更新，时间到期后自动失去cache
	 * @param categoryid
	 * @return
	 */
	public List<Productbase> selectMostLearned(Long categoryid);
	
	/**
	 * 查询系统推荐的产品
	 * @param cateid
	 * @param localeid
	 * @return
	 */
	public List<Productbase> selectRecommendProd(Long cateid, Long localeid);
	
	/**
	 * 查询目录下包含了哪些产品及所属商店. 互联网用户用此方法来搜索产品，因此必须是开放的产品
	 * @param vo
	 * @param needCommendProduct  是否需要加入推荐产品，如果需要则会在搜索结果的第一页加入推荐产品
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page qryCateProductsPage(ProductbaseQuery queryVO, boolean needCommendProduct, int pageIndex, int pageSize,Integer total);
	
	/**
	 * 查询目录下包含了哪些产品及所属商店，为了管理产品使用
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page qryCateProdForMag(ProductbaseQuery queryVO,boolean loadshop,int pageIndex,int pageSize,Integer total);
	
	
	/* 
	 * 根据主键查询产品数据
	 */
	public Productbase selectVO(Long id, Long localeid);
	
    /**
     * 根据产品主键字符串查询产品
     * @param pkStr
     * @param localeid
     * @return
     */
	public List<Productbase> selectByPkStr(String pkStr, Long localeid);
	
	/**
	 * 检查shop还可以创建的产品数目
	 * @param shopid
	 * @return
	 */
	public int checkShopProductLimit(Long shopid);
	
	/**
	 * 检查课程内容是否满足发布要求
	 * 课程的任一课程章节必须要有学习资料，如果课程没有章节目录，则必须要有学习资料
	 * @param productid
	 * @return
	 */
	public boolean checkCourseForPublish(Long productid);
	
	public Productbase save(Productbase vo, Long userid);
	
	/**
	 * 删除产品
	 * @param productid
	 * @param sessUserid
	 * @param sessOrgid
	 */
	public void deleteProduct(Long productid, Long sessUserid, Long sessOrgid) throws LogicException;

	public GrantedAuthority[] loadContainerAuthority(Long userid, Long productid, Long userShopid, 
			 GrantedAuthority[] sessionAuthArr);
	
}
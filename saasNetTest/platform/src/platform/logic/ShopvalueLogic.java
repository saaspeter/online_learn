package platform.logic;

import java.util.List;
import java.util.Locale;

import platform.dto.ShopvalueQuery;
import platform.vo.Shopvalue;

import commonTool.constant.LabelValueVO;
import commonTool.exception.DuplicateException;

public interface ShopvalueLogic {

	/**
	 * 查询商店语言地区设置的集合，其中localename为语言地区的名字
	 * @param queryVO
	 * @param locale
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List qryVoWithoutBLOBs(ShopvalueQuery queryVO,Locale locale)
	   throws Exception;
	
	/**
	 * 显示该商店已经设置开通的国家列表
	 * @param shopid
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public List<LabelValueVO> qrySelectedLocales(Long shopid, Locale locale) throws Exception;
	
	/**
	 * 保存Shopvalue对象，如果有shopid和localeid的重复记录，则抛出异常
	 * @param vo
	 * @return
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public Long addShopvalue(Shopvalue vo) throws DuplicateException,Exception;
	
	/**
	 * 根据常量id和所要显示的locale得到需要显示的语言国家列表。该列表式过滤掉已设置的语言国家后的列表
	 * @param sysconstid
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public List<LabelValueVO> getUnaddLocales(Long shopid,Locale locale) throws Exception;

}
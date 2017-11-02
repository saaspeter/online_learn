package platform.logicImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import platform.bean.BeanFactory;
import platform.dao.ShopvalueDao;
import platform.dto.ShopvalueQuery;
import platform.exception.ExceptionConstant;
import platform.logic.ShopvalueLogic;
import platform.vo.Shopvalue;

import commonTool.biz.logic.I18nLogic;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.biz.vo.I18n;
import commonTool.constant.LabelValueVO;
import commonTool.exception.DuplicateException;
import commonTool.exception.LogicException;

public class ShopvalueLogicImpl implements ShopvalueLogic{

	static Logger log = Logger.getLogger(ShopvalueLogicImpl.class.getName());
	
	private ShopvalueDao dao;

	/**
	 * 查询商店语言地区设置的集合，其中localename为语言地区的名字
	 * @param queryVO
	 * @param locale
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List qryVoWithoutBLOBs(ShopvalueQuery queryVO,Locale locale) throws Exception{
		List list = dao.selectByVOWithoutBLOBs(queryVO);
		Map map = I18nLogicImpl.getInstance().getLocaleMap();
		if(list!=null&&list.size()>0){
		   Shopvalue vo = null;
		   for(int i=0;i<list.size();i++){
			   vo = (Shopvalue)list.get(i);
			   Long localeid = vo.getLocaleid();
			   I18n i18n = (I18n)map.get(localeid);
			   if(i18n!=null){
				   Locale valueLocale = new Locale(i18n.getLanguage(),i18n.getCountry());
				   vo.setLocaleName(valueLocale.getDisplayLanguage(locale)+"("+valueLocale.getDisplayCountry(locale)+")");
			   }
		   }
		}
		return list;
	}
	
	/**
	 * 显示该商店已经设置开通的国家列表
	 * @param shopid
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public List<LabelValueVO> qrySelectedLocales(Long shopid, Locale locale) throws Exception{
		ShopvalueQuery queryVO = new ShopvalueQuery();
		queryVO.setShopid(shopid);
		List list = this.qryVoWithoutBLOBs(queryVO, locale);
		List<LabelValueVO> valueList = new ArrayList<LabelValueVO>();
		if(list!=null && list.size()>0){
			Shopvalue votemp = null;
			LabelValueVO labelVO = new LabelValueVO();
		    for(int i=0;i<list.size();i++){
				votemp = (Shopvalue)list.get(i);
				labelVO = new LabelValueVO(votemp.getLocaleName(),String.valueOf(votemp.getLocaleid()),
						                   votemp.getLocaleid(),null,null);
				valueList.add(labelVO);
		    }
		}
		return valueList;
	}
	
	/**
	 * 保存Shopvalue对象，如果有shopid和localeid的重复记录，则抛出异常
	 * @param vo
	 * @return
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public Long addShopvalue(Shopvalue vo) throws DuplicateException,Exception{
        if(vo==null)
        	throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		if(vo.getShopvalueid()==null){ // 如果是新增操作，则判断是否为重复插入
		   ShopvalueQuery queryVO = new ShopvalueQuery();
		   queryVO.setLocaleid(vo.getLocaleid());
		   queryVO.setShopid(vo.getShopid());
		   List list = dao.selectByVOWithoutBLOBs(queryVO);
		   if(list!=null&&list.size()>0)
			  throw new DuplicateException("duplicated Shopvalue at the same shopid and localeid");
		}
		return dao.insert(vo);		
	}
	
	/**
	 * 根据常量id和所要显示的locale得到需要显示的语言国家列表。该列表式过滤掉已设置的语言国家后的列表
	 * @param sysconstid
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public List<LabelValueVO> getUnaddLocales(Long shopid,Locale locale) throws Exception{
		if(shopid==null||locale==null)
			return new ArrayList<LabelValueVO>();
		List<LabelValueVO> listRtn = new ArrayList<LabelValueVO>();
		I18nLogic logic = I18nLogicImpl.getInstance();
		ShopvalueQuery queryVO = new ShopvalueQuery();
		queryVO.setShopid(shopid);
		List list = dao.selectByVO(queryVO);
		Map<Long, Long> param = null;
		if(list!=null&&list.size()>0){
			param = new HashMap<Long, Long>();
			Shopvalue voTemp = null;
			for(int i=0;i<list.size();i++){
				voTemp = (Shopvalue)list.get(i);
			    if(voTemp!=null&&voTemp.getLocaleid()!=null)
			    	param.put(voTemp.getLocaleid(), voTemp.getLocaleid());
			}
		}
		listRtn = logic.getLabelListFilter(locale, param);
		return listRtn;
	}
	
    /**
     * static spring getMethod
     */
     public static ShopvalueLogic getInstance() {
       	 ShopvalueLogic logic = (ShopvalueLogic)BeanFactory.getBeanFactory().getBean("shopvalueLogic");
         return logic;
     }

	public void setDao(ShopvalueDao dao) {
		this.dao = dao;
	}
     
	public ShopvalueDao getDao() {
		return dao;
	}

	
}

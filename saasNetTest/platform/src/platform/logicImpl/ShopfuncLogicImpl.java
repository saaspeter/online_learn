package platform.logicImpl;

import java.util.List;

import org.apache.log4j.Logger;

import platform.bean.BeanFactory;
import platform.constant.ShopfuncConstant;
import platform.dao.ShopfuncDao;
import platform.dao.SysfunctionitemDao;
import platform.dto.ShopfuncQuery;
import platform.logic.ShopfuncLogic;
import platform.vo.Shopfunc;
import platform.vo.Sysfunctionitem;

import commonTool.exception.DuplicateException;
import commonTool.util.DateUtil;

public class ShopfuncLogicImpl implements ShopfuncLogic{

	static Logger log = Logger.getLogger(ShopfuncLogicImpl.class.getName());
	
	private ShopfuncDao dao;
	
	private SysfunctionitemDao funcDao;
	
	/**
	 * 查询商店已经设置了哪些功能
	 * @param shopid
	 * @return 功能functioncode的数组
	 */
	public String[] qryShopfuncArr(Long shopid) {
		String[] arr = new String[0];
		if(shopid==null)
			return arr;
		ShopfuncQuery queryVO = new ShopfuncQuery();
		queryVO.setShopid(shopid);
		List list = dao.selectByVO(queryVO);
		if(list!=null&&list.size()>0){
			arr = new String[list.size()];
		    for(int i=0;i<list.size();i++){
		    	arr[i] = String.valueOf(((Shopfunc)list.get(i)).getFunctioncode());
		    }
		}
		return arr;
	}
	
	
	/**
	 * 添加商店shopid选择的商店功能
	 * @param shopid
	 * @param funcArr: functioncode数组
	 * @return
	 * @throws Exception
	 */
	public int insertFuncs(Long shopid,String[] funcArr) throws DuplicateException{
		if(shopid==null||funcArr==null||funcArr.length<1)
			return 0;
		try {
			for(int i=0;i<funcArr.length;i++){
            //	检查该商店是否已经添加了该功能
				ShopfuncQuery queryVO = new ShopfuncQuery();
				queryVO.setShopid(shopid);
				queryVO.setFunctioncode(funcArr[i]);
				List list = dao.selectByVO(queryVO);
				if(list!=null&&list.size()>0)
				  throw new DuplicateException("the shop has already chosen this function!");
				
				Sysfunctionitem funcVO = funcDao.selectByPK(funcArr[i]);
				if(funcVO!=null){
					Shopfunc vo = new Shopfunc();
					vo.setCost(funcVO.getCost());
					vo.setFunctioncode(funcVO.getFunctioncode());
					vo.setIspay(ShopfuncConstant.IsPay_no);
					vo.setPaytype(funcVO.getPaytype());
					vo.setShopid(shopid);
					vo.setSyscode(funcVO.getSyscode());
					vo.setStartdate(DateUtil.getInstance().getNowtime_GLNZ());
					vo.setStatus(ShopfuncConstant.Status_valide);
				    dao.insert(vo);
				}
			}
		}catch (DuplicateException e) {
			throw e;
		}
		return funcArr.length;
	}
	
    /**
     * static spring getMethod
     */
     public static ShopfuncLogic getInstance() {
       	 ShopfuncLogic logic = (ShopfuncLogic)BeanFactory.getBeanFactory().getBean("shopfuncLogic");
         return logic;
     }

	public void setDao(ShopfuncDao dao) {
		this.dao = dao;
	}
     
	public ShopfuncDao getDao() {
		return dao;
	}

	public SysfunctionitemDao getFuncDao() {
		return funcDao;
	}

	public void setFuncDao(SysfunctionitemDao funcDao) {
		this.funcDao = funcDao;
	}

	
}

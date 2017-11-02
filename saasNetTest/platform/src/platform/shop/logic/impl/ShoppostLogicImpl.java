package platform.shop.logic.impl;

import java.util.Date;
import java.util.List;
import platform.bean.BeanFactory;
import platform.shop.dao.ShoppostDao;
import platform.shop.logic.ShoppostLogic;
import platform.shop.vo.Shoppost;
import platform.util.UploadFileUtilPlatform;
import platform.vo.Shop;
import commonTool.util.DateUtil;
import commonTool.util.UploadFileUtil;

public class ShoppostLogicImpl implements ShoppostLogic{

	private ShoppostDao shoppostDao;
	
	/**
	 * 删除shoppost，并且包括其中的文件
	 */
	public int delete(Long pk) {
		Shoppost vo = shoppostDao.selectByPK(pk);
		String fileDir = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, vo.getShopid(),  
														 Shoppost.ObjectType, pk, null, null);
		UploadFileUtilPlatform.delFile(null, fileDir);
		shoppostDao.deleteByPK(pk);
		
		return 1;
	}
	
	/**
	 * 删除每天自动产生的shoppost数据，目前是进入新增页面而没有保存产生的垃圾数据
	 * 供CleanDataJob调用
	 * @return
	 */
	public void deleteDirtyAutoSaveData(){
		Date todate = DateUtil.getInstance().getNowtime_GLNZ();
		// 删除生成10小时后的垃圾数据
		todate = DateUtil.dateAddHours(todate, 10);
		
		List<Shoppost> dataList = null;
		boolean keeprun = true;
		while(keeprun){
			dataList = shoppostDao.selectLongTimeData(todate, Shoppost.Status_AutoSave);
			if(dataList!=null && dataList.size()>0)
			{   
				for(Shoppost tempVO : dataList){
					if(tempVO!=null){
						String fileDir = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, tempVO.getShopid(),  
																Shoppost.ObjectType, tempVO.getId(), 
																null, null);
						UploadFileUtilPlatform.delFile(null, fileDir);
					}
				}
			}else {
				keeprun = false;
			}
		}
		// 删除DB中的数据
		shoppostDao.deleteLongTimeData(todate, Shoppost.Status_AutoSave);
	}
	
	public static ShoppostLogic getInstance() {
		ShoppostLogic logic = (ShoppostLogic)BeanFactory.getBeanFactory().getBean("shoppostLogic");
        return logic;
    }

	public ShoppostDao getShoppostDao() {
		return shoppostDao;
	}

	public void setShoppostDao(ShoppostDao shoppostDao) {
		this.shoppostDao = shoppostDao;
	}

}

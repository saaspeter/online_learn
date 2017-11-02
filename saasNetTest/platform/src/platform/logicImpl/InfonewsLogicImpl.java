package platform.logicImpl;

import java.util.Date;
import java.util.List;
import platform.bean.BeanFactory;
import platform.constant.InfonewsConstant;
import platform.dao.InfonewsDao;
import platform.logic.InfonewsLogic;
import platform.util.UploadFileUtilPlatform;
import platform.vo.Infonews;
import commonTool.constant.CommonConstant;
import commonTool.util.DateUtil;
import commonTool.util.UploadFileUtil;

public class InfonewsLogicImpl implements InfonewsLogic{

	private InfonewsDao infonewsDao;
	
	/**
	 * 删除每天自动产生的Infonews数据，目前是进入新增页面而没有保存产生的垃圾数据
	 * 供CleanDataJob调用
	 * no need to delete cache
	 * @return
	 */
	public void deleteDirtyAutoSaveData(){
		Date todate = DateUtil.getInstance().getNowtime_GLNZ();
		// 删除生成10小时后的垃圾数据
		todate = DateUtil.dateAddHours(todate, 10);
		
		List<Long> dataList = null;
		Long[] pkArr = null;
		boolean keeprun = true;
		while(keeprun){
			dataList = infonewsDao.selectLongTimeData(todate, InfonewsConstant.Status_AutoSave);
			if(dataList!=null && dataList.size()>0)
			{   
				pkArr = new Long[dataList.size()];
				for(int i=0;i<dataList.size();i++){
					String fileDir = UploadFileUtil.getUploadFileDir(CommonConstant.FilePathPrefix_PlatformSystem, 
																	 Infonews.ObjectType, dataList.get(i),  
																	 null, null, null, null);
					UploadFileUtilPlatform.delFile(null, fileDir);
					pkArr[i] = dataList.get(i);
				}
				// 删除DB中的数据
				infonewsDao.deleteBatchByPK(pkArr);
			}else {
				keeprun = false;
			}
		}
	}
	
	/**
	 * 删除记录和文件
	 * @param pk
	 * @return
	 */
	public int delete(Long pk){
		if(pk==null)
			return 0;
		Infonews vo = infonewsDao.selectByPK(pk);
		if(vo!=null)
		{   
			String fileDir = UploadFileUtil.getUploadFileDir(CommonConstant.FilePathPrefix_PlatformSystem, 
															 Infonews.ObjectType, pk,  
															 null, null, null, null);
			UploadFileUtilPlatform.delFile(null, fileDir);
			infonewsDao.deleteByPK(pk);
		}
		return 1;
	}

	public InfonewsDao getInfonewsDao() {
		return infonewsDao;
	}

	public void setInfonewsDao(InfonewsDao infonewsDao) {
		this.infonewsDao = infonewsDao;
	}
	

    public static InfonewsLogic getInstance(){
    	InfonewsLogic logic = (InfonewsLogic)BeanFactory.getBeanFactory().getBean("infonewsLogic");
        return logic;
    }
	
}

package platform.social.logic.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import platform.bean.BeanFactory;
import platform.social.dao.LeavemessageDao;
import platform.social.dto.LeavemessageQuery;
import platform.social.logic.LeavemessageLogic;
import platform.social.vo.Leavemessage;

import commonTool.base.Page;
import commonTool.cache.CacheSynchronizer;
import commonTool.util.AssertUtil;


public class LeavemessageLogicImpl implements LeavemessageLogic {

	private LeavemessageDao leavemessageDao;
	
	
	/**
	 * 查询leavemessage, 同时包括下级子记录
	 * @param queryVO
	 * @return
	 */
	@Cacheable(value="platform.socialContentCache", 
    		key="'LeavemessageLogic.qryMessagePage~leavemessage-'+#objecttype+':'+#objectid+'~'+#pageIndex+'~'+#pageSize")
	public Page qryMessagePage(String objectid, String objecttype, int pageIndex, int pageSize,Integer total){
		AssertUtil.assertNotEmpty(objecttype, null);
		// first get all top level message, set parent is -1 means: parent is null
		LeavemessageQuery queryVO = new LeavemessageQuery();
		queryVO.setParent(new Long("-1"));
		queryVO.setObjectid(objectid);
		queryVO.setObjecttype(objecttype);
		Page page = leavemessageDao.selectByVOPage(queryVO, pageIndex, pageSize, total);
		List messlist = page.getList();
		StringBuffer buffer = new StringBuffer();
		if(messlist!=null&&messlist.size()>0){
			Leavemessage tempVO = null;
			Map<Long, Leavemessage> map = new HashMap<Long, Leavemessage>();
			for(int i=0; i<messlist.size(); i++){
				tempVO = (Leavemessage)messlist.get(i);
				map.put(tempVO.getMessid(), tempVO);
				buffer.append(tempVO.getMessid()).append(",");
			}
			String pkStr = buffer.toString();
			pkStr = pkStr.substring(0, pkStr.length()-1);
			// 查询子一级记录
			LeavemessageQuery subQuery = new LeavemessageQuery();
			subQuery.setPkStr(pkStr);
			List subList = leavemessageDao.selectByVO(subQuery);
			if(subList!=null && subList.size()>0){
				for(int i=0; i<subList.size(); i++){
					tempVO = (Leavemessage)subList.get(i);
					if(tempVO.getParent()!=null){
						tempVO = map.get(tempVO.getParent());
						if(tempVO!=null){
							tempVO.addSubVO(tempVO);
						}
					}
				}
			}
		}
		CacheSynchronizer.getInstance().buildAssoc("platform.socialContentCache", 
				"LeavemessageLogic.qryMessagePage~"+Leavemessage.ObjectType+"-"+objecttype+":"+objectid+'~'+pageIndex+'~'+pageSize,
				new String[]{Leavemessage.ObjectType+"-"+objecttype+":"+objectid});
		return page;
	}

	/**
	 * delete comments, and update its parent's sub comment number
	 * @param pk
	 */
	public void delMessageByPK(Long pk){
		Leavemessage vo = leavemessageDao.selectByPK(pk);
		//TODO do delete authentation
		if(vo!=null){
			leavemessageDao.deleteChildByPK(pk);
			leavemessageDao.deleteByPK(pk);
		}
	}
	
	public static LeavemessageLogic getInstance() {
		LeavemessageLogic logic = (LeavemessageLogic)BeanFactory.getBeanFactory().getBean("leavemessageLogic");
        return logic;
    }

	public LeavemessageDao getLeavemessageDao() {
		return leavemessageDao;
	}

	public void setLeavemessageDao(LeavemessageDao leavemessageDao) {
		this.leavemessageDao = leavemessageDao;
	}

}

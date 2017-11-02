package platform.logicImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import platform.dao.ReversesearchDao;
import platform.event.EventHandlerPlatform;
import platform.vo.Reversesearch;
import commonTool.event.Event;
import commonTool.event.EventHandle;
import commonTool.util.DateUtil;

public class SearchIndexImpl implements EventHandle {

	static Logger log = Logger.getLogger(SearchIndexImpl.class.getName());
	
	private static SearchIndexImpl instance;
	
	public static final String HandleType_AddIndex = "AddIndex";
	public static final String HandleType_UpdateIndex = "UpdateIndex";
	public static final String HandleType_DeleteIndex = "DeleteIndex";
		
	private static ReversesearchDao reversesearchDao;
	

	/**
	 * add index, parameters are: indexed text, entityID and entityType
	 */
	private int addIndex(String[] indextextArr, Long entityid, String entitytype) {
		int result = 0;
		if(indextextArr!=null&&indextextArr.length>0&&entityid!=null
				&&entitytype!=null&&entitytype.trim().length()>0){
		   List<Reversesearch> list = new ArrayList<Reversesearch>();
		   Date curDate = DateUtil.getInstance().getNowtime_GLNZ();
		   for(String indextext : indextextArr){
			   Reversesearch vo = new Reversesearch();
			   vo.setSearchtext(indextext);
			   vo.setEntityid(entityid);
			   vo.setEntitytype(entitytype);
			   vo.setCreatetime(curDate);
			   list.add(vo);
		   }
		   reversesearchDao.insertBatch(list);
		   result = 1;
		}
		return result;
	}

	/**
	 * delete index, parameters are: indexed text, entityType
	 */
	private int deleteIndex(String indexText, String entitytype) {
		reversesearchDao.deleteByText(indexText, entitytype);
		return 1;
	}

	/**
	 * update index, current implementation is: delete the old index and rebuild new index
	 */
	private int updateIndex(String indexText, String newIndexText,
			Long entityid, String entitytype) {
		if(indexText==null||indexText.trim().length()<1||entityid==null
				||entitytype==null||entitytype.trim().length()<1){
			return 0;
		}
		reversesearchDao.deleteByText(indexText, entitytype);
		Reversesearch vo = new Reversesearch();
		vo.setSearchtext(newIndexText);
		vo.setEntityid(entityid);
		vo.setEntitytype(entitytype);
		vo.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
		reversesearchDao.insert(vo);
		return 0;
	}

	// 处理反向索引的数据
	public void onEvent(Event event) {
		Map paraMap = event.getParaMap();
		String eventType = event.getEventType();
		try {
			if(EventHandlerPlatform.EventType_SearchIndex_Handle.equals(eventType)){ 
				String handleType = (String)paraMap.get("handleType");
				Long entityid = (Long)paraMap.get("entityid");
				String entitytype = (String)paraMap.get("entitytype");
				
				if(handleType!=null&&handleType.trim().length()>0){
					if(HandleType_AddIndex.endsWith(handleType)){
						String[] indextextArr = (String[])paraMap.get("indextextArr");
						addIndex(indextextArr, entityid, entitytype);
					}else if(HandleType_UpdateIndex.endsWith(handleType)){
						String indexText = (String)paraMap.get("indexText");
						String newIndexText = (String)paraMap.get("newIndexText");
						updateIndex(indexText, newIndexText, entityid, entitytype);
					}else if(HandleType_DeleteIndex.endsWith(handleType)){
						String indexText = (String)paraMap.get("indexText");
						deleteIndex(indexText, entitytype);
					}
				}
			}
		} catch (Exception e) {
			log.error("SearchIndexImpl方法onEvent时出错误, eventType:"+eventType+"|paraMap:"+paraMap, e);
		}
	}
	
	public static SearchIndexImpl getInstance(){
		if(instance==null){
			instance = new SearchIndexImpl();
			reversesearchDao = BOFactory_Platform.getReversesearchDao();
		}
		return instance;
	}

}

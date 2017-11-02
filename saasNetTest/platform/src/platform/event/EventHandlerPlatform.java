package platform.event;

import platform.concurrent.ParallelExecutor;
import commonTool.concurrent.AbstractParallelExecutor;
import commonTool.event.EventControl;

public class EventHandlerPlatform extends EventControl {
	
	public static final String EventType_Shop_ApprovePass = "EventType.Shop.ApprovePass";
	
	public static final String EventType_SearchIndex_Handle = "EventType.SearchIndex.Handle";
	
	public static final String EventType_UserShop_Handle = "EventType.UserShop.Handle";
	
	static{
    	eventMapping.put(EventType_Shop_ApprovePass, 
    			             new String[]{"netTest.bean.BOFactory#getOrgLogic",
    									  "commonWeb.security.logic.BOFactory#getSecurityEventHandle"});
    	eventMapping.put(EventType_SearchIndex_Handle, 
	             			 new String[]{"platform.logicImpl.BOFactory_Platform#getSearchIndexImpl"});
    	eventMapping.put(EventType_UserShop_Handle, 
    			 			 new String[]{"platform.logicImpl.BOFactory_Platform#getUsershopLogic"});
    }
    
    private static EventHandlerPlatform instance;
    
    private EventHandlerPlatform(){ }
    
    public static EventHandlerPlatform getInstance(){
    	if(instance==null){
    		instance = new EventHandlerPlatform();
    	}
    	return instance;
    }
    
    @Override
	public AbstractParallelExecutor getThreadExecutor() {
		return ParallelExecutor.getInstance();
	}

}

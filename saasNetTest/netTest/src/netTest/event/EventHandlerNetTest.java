package netTest.event;

import netTest.concurrent.ParallelExecutor;
import commonTool.concurrent.AbstractParallelExecutor;
import commonTool.event.EventControl;

public class EventHandlerNetTest extends EventControl {
	
	public static final String EventType_WareQuestion_DelQuesBatch = "EventType.WareQuestion.DelQuesBatch";
	
	public static final String EventType_Question_ImportQues = "EventType.Question.ImportQues";
		
	public static final String EventType_LearnContent_AddDelete = "EventType.LearnContent.AddDelete";
	
	public static final String EventType_TestInfo_Added = "EventType.TestInfo.Added";
	
	public static final String EventType_TestInfo_Finished = "EventType.TestInfo.Finished";
	
	public static final String EventType_Exercise_AddDelete = "EventType.Exercise.AddDelete";
	
	public static final String EventType_Product_LearnActivity = "EventType.Product.LearnActivity";
	
	//public static final String EventType_Question_Change_UpdatePaper = "EventType.Question.Change.UpdatePaper";
	
	//public static final String EventType_Shop_ApprovePass = "EventType.Shop.ApprovePass";
	

    static{
    	eventMapping.put(EventType_WareQuestion_DelQuesBatch, 
    			             new String[]{"netTest.bean.BOFactory#getExerquesLogic"});
    	eventMapping.put(EventType_Question_ImportQues, 
    			             new String[]{"netTest.bean.BOFactory#getWareLogic"});
    	eventMapping.put(EventType_Product_LearnActivity, 
    			             new String[]{"netTest.bean.BOFactory#getLearnactivityLogic","netTest.bean.BOFactory#getUserproductLogic"});
    	eventMapping.put(EventType_LearnContent_AddDelete, 
    			             new String[]{"netTest.bean.BOFactory#getProductLogic"});
    	eventMapping.put(EventType_TestInfo_Added, 
    						 new String[]{"netTest.bean.BOFactory#getCoursepostLogic","netTest.bean.BOFactory#getTestuserLogic"});
    	eventMapping.put(EventType_TestInfo_Finished, 
				 			 new String[]{"netTest.bean.BOFactory#getProductLogic"});
    	eventMapping.put(EventType_Exercise_AddDelete, 
				 		     new String[]{"netTest.bean.BOFactory#getProductLogic"});
    }
    
    private static EventHandlerNetTest instance = new EventHandlerNetTest();
    
    private EventHandlerNetTest(){ }
    
    public static EventHandlerNetTest getInstance(){
    	return instance;
    }

	@Override
	public AbstractParallelExecutor getThreadExecutor() {
		return ParallelExecutor.getInstance();
	}

}

package netTest.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import commonTool.concurrent.AbstractParallelExecutor;
import commonTool.constant.SysparamConstant;

public class ParallelExecutor extends AbstractParallelExecutor {

	
	public static final ParallelExecutor instance = new ParallelExecutor();
	
	private ParallelExecutor(){}
	
	public static ParallelExecutor getInstance(){
		return instance;
	}
	
	protected ExecutorService loadDefaultExecutor(String module) {
//		String eventNumStr = SysparamConstantNettest.getProperty(SysparamConstant.Key_ThreadPoolNumber);
//		Integer eventNum = null;
//        if(eventNumStr!=null && eventNumStr.trim().length()>0){
//        	eventNum = new Integer(eventNumStr.trim());
//        }
//        if(eventNum==null){
//        	throw new LogicException("--error in netTest.concurrent.ParallelExecutor, module:"+module+" has not define number");
//        }
		ExecutorService rtnService =  Executors.newFixedThreadPool(SysparamConstant.threadPoolNumberEnent);
		return rtnService;
	}

}

package platform.concurrent;

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
		ExecutorService rtnService =  Executors.newFixedThreadPool(SysparamConstant.threadPoolNumberEnent);
		return rtnService;
	}

}

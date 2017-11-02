package netTest.jobTask;

import netTest.product.logic.UserproductLogic;
import commonTool.jobTask.JobBizInf;

/**
 * 处理用户产品相关的job, 每天执行一次
 * @author peter
 *
 */
public class UserProductJob implements JobBizInf {
	
	private UserproductLogic userproductLogic;
	
//	public UserProductJob(){
//		if(userproductLogic==null){
//			userproductLogic = UserproductLogicImpl.getInstance();
//		}
//	}

	public void executeJob() {
		// 查找快到期的用户产品记录，并生成userNotification
		// 查找已经过期的用户产品记录，并删除userproduct，并且记录日志
		userproductLogic.notifyDueUserProduct();
	}

	public UserproductLogic getUserproductLogic() {
		return userproductLogic;
	}

	public void setUserproductLogic(UserproductLogic userproductLogic) {
		this.userproductLogic = userproductLogic;
	}

}

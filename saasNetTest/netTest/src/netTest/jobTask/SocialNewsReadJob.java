package netTest.jobTask;

import platform.logicImpl.BOFactory_Platform;
import platform.social.logic.SocialNewsLogic;
import commonTool.jobTask.JobBizInf;

/**
 * 处理用户产品相关的job, 每天执行一次
 * @author peter
 *
 */
public class SocialNewsReadJob implements JobBizInf {
	
	public void executeJob() {
		getSocialNewsLogic().autoReadFromServiceAndSave();
	}

	private SocialNewsLogic getSocialNewsLogic() {
		return BOFactory_Platform.getSocialNewsLogic();
	}

}

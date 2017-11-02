package netTest.jobTask;

import platform.logic.InfonewsLogic;
import platform.shop.logic.ShoppostLogic;
import netTest.exam.logic.TestuserLogic;
import netTest.learncont.logic.LearncontentLogic;
import commonTool.jobTask.JobBizInf;

/**
 * 处理系统每天产生的垃圾数据, 每天执行一次
 * @author peter
 *
 */
public class CleanDataJob implements JobBizInf {
	
	private LearncontentLogic learncontentLogic;
	private TestuserLogic testuserLogic;
    private ShoppostLogic shoppostLogic;
    private InfonewsLogic infonewsLogic;
    

	public LearncontentLogic getLearncontentLogic() {
		return learncontentLogic;
	}

	public void setLearncontentLogic(LearncontentLogic learncontentLogic) {
		this.learncontentLogic = learncontentLogic;
	}

	public void executeJob() {
		// 删除每天自动产生的learncontent数据，目前是进入新增页面而没有保存产生的垃圾数据
		// (对于网页型learncontent,在刚进入新增页面，系统就会自动插入一个learncontent数据，
		//  这样做的目的是得到learncontentid，如果页面有插入图片会用到主键)
		learncontentLogic.deleteDirtyAutoSaveData();
		// 和learncontent一样的处理方式
		infonewsLogic.deleteDirtyAutoSaveData();
		shoppostLogic.deleteDirtyAutoSaveData();
		// 删除useranswer表中需要自动删除的数据
		// 删除 openTest结束后，开放考试结果15天后的useranswer记录
	    // 删除openTest, 查看结果是 '用户考完试就可以查看详细结果', 
	    // 并且testuser结束考试的时间超过15天用户的useranser记录被删除
		testuserLogic.cleanUseranswerJob();
	}

	public TestuserLogic getTestuserLogic() {
		return testuserLogic;
	}

	public void setTestuserLogic(TestuserLogic testuserLogic) {
		this.testuserLogic = testuserLogic;
	}

	public ShoppostLogic getShoppostLogic() {
		return shoppostLogic;
	}

	public void setShoppostLogic(ShoppostLogic shoppostLogic) {
		this.shoppostLogic = shoppostLogic;
	}

	public InfonewsLogic getInfonewsLogic() {
		return infonewsLogic;
	}

	public void setInfonewsLogic(InfonewsLogic infonewsLogic) {
		this.infonewsLogic = infonewsLogic;
	}


}

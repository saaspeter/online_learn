package platform.logic;

public interface InfonewsLogic {

	/**
	 * 删除每天自动产生的Infonews数据，目前是进入新增页面而没有保存产生的垃圾数据
	 * 供CleanDataJob调用
	 * @return
	 */
	public void deleteDirtyAutoSaveData();
	
	/**
	 * 删除记录和文件
	 * @param pk
	 * @return
	 */
	public int delete(Long pk);
	
}

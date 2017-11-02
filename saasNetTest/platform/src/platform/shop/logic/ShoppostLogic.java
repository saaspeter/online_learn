package platform.shop.logic;

public interface ShoppostLogic {

	public int delete(Long pk);
	
	/**
	 * 删除每天自动产生的shoppost数据，目前是进入新增页面而没有保存产生的垃圾数据
	 * 供CleanDataJob调用
	 * @return
	 */
	public void deleteDirtyAutoSaveData();
	
}

package netTest.learncont.logic;

import org.apache.struts.upload.FormFile;
import org.springframework.security.GrantedAuthority;

import platform.logic.Container;
import netTest.learncont.vo.Learncontent;


public interface LearncontentLogic {

	public Learncontent getLearncontent(Long contentid);
	
	public abstract Learncontent save(Learncontent vo, FormFile uploadFile) throws Exception;

	public abstract int delete(Long pk);
	
	/**
	 * 删除每天自动产生的learncontent数据，目前是进入新增页面而没有保存产生的垃圾数据
	 * 供CleanDataJob调用
	 * @return
	 */
	public void deleteDirtyAutoSaveData();
	
	/**
	 * 根据product删除学习内容，不删除包含的文件file,由删除product的函数统一删除
	 * @param productid
	 * @param shopid
	 * @return
	 */
	public int deleteByProd(Long productid, Long shopid);
	
	public GrantedAuthority[] loadContainerAuthority(Long userid, Long objectid, Long sessShopid, 
			 GrantedAuthority[] sessionAuthArr);
	
//	 load对learncontent的权限，如果是可以try的，就付给权限
	public GrantedAuthority[] loadObjectAuthority(Long userid, Long objectid, Object targetObject);
	
	public boolean sendLearnActivity(Long objectid, Long userid, Short learnstatus, Short actiontype);
}

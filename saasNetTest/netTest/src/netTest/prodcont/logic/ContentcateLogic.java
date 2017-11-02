package netTest.prodcont.logic;

import java.util.List;
import java.util.Map;

import commonTool.base.BaseEmptyEntity;
import netTest.prodcont.vo.Contentcate;

public interface ContentcateLogic {

	/**
	 * get content category, may include its learncontent list or exercise list
	 * @param productid
	 * @param hasLearncont
	 * @param hasExercise
	 * @return
	 */
	public List<Contentcate> getProdCatetory(Long productid, boolean hasLearncont, boolean hasExercise);
	
	/**
	 * 查询产品的某个目录内容，包括学习内容和联系内容
	 * @param catelist
	 * @param cateid
	 * @param productid
	 * @return
	 */
	public Contentcate getCateWithLearncont(List<Contentcate> catelist, Long cateid, Long productid);
	
    /**
     * select some record by PK Array
     */
    public Map<Long,Contentcate> selectByPKList(List<Long> pkList);
    
    public void deleteByPK(Long productid, Long contentcateid);
    
    /**
     * get next contentcate from its list by the default sequence in the list
     * @param list
     * @param pkId
     * @return
     */
    public Contentcate getNextContentcateFromList(List<Contentcate> list, Long pkId);
    
	/**
	 * 在本目录中查询用户下一个可以学些的资料(包括文档资料和练习)
	 * a. 如果传入了objectid，则从该object后一个资料开始查询，查询第一个没有学习完成的资料或练习
	 * b. 如果没有传入objectid, 则从本目录的第一个资料开始查询起
	 * @param objectid_current
	 * @param objecttype_current
	 * @param contcateid
	 * @param productid
	 * @param curcatevo
	 * @param userid
	 * @param needReturnFirst: when cannot get next learn/exercise, do we need return the first vo of this chapter
	 * @return
	 */
	public BaseEmptyEntity getNextLearnInCurrentContcate(Long objectid_current, 
					 String objecttype_current, Long contcateid, Long productid,
					 Contentcate curcatevo, Long userid, boolean needReturnFirst);

}
package netTest.prodcont.logic.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netTest.bean.BOFactory;
import netTest.bean.BeanFactory;
import netTest.exercise.dao.ExerciseDao;
import netTest.exercise.dao.impl.ExerciseDaoImpl;
import netTest.exercise.vo.Exercise;
import netTest.learncont.dao.LearncontentDao;
import netTest.learncont.dao.impl.LearncontentDaoImpl;
import netTest.learncont.vo.Learncontent;
import netTest.prodcont.dao.ContentcateDao;
import netTest.prodcont.dao.impl.ContentcateDaoImpl;
import netTest.prodcont.logic.ContentcateLogic;
import netTest.prodcont.vo.Contentcate;
import netTest.product.dao.LearnactivityDao;
import netTest.product.vo.Learnactivity;

import org.springframework.cache.annotation.Cacheable;

import commonTool.base.BaseEmptyEntity;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.exception.HasChildException;
import commonTool.exception.HasReferenceException;
import commonTool.util.AssertUtil;

public class ContentcateLogicImpl implements ContentcateLogic {

	private ContentcateDao dao;
	
	private LearncontentDao learncontentDao;
	
	private ExerciseDao exerciseDao;
	
	
	/**
	 * get content category, may include its learncontent list or exercise list
	 * @param productid
	 * @param hasLearncont
	 * @param hasExercise
	 * @return
	 */
	@Cacheable(value="netTest.productCache", key="'ContentcateLogic.getProdCatetory~'+#productid+'~'+#hasLearncont+'~'+#hasExercise", unless="#result==null")
	public List<Contentcate> getProdCatetory(Long productid, boolean hasLearncont, boolean hasExercise){
		List<Contentcate> list = dao.getAllInOne(productid);
		if(list!=null){
			for(Contentcate tempcatevo : list){
				deeploopset(tempcatevo, hasLearncont, hasExercise);
			}
			// build cache association
			CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
	 				"ContentcateLogic.getProdCatetory~"+productid+"~"+hasLearncont+"~"+hasExercise, 
	 				new String[]{ContentcateDaoImpl.ProductChangeCacheType+":"+productid.toString()});
			if(hasLearncont){
				CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
		 				"ContentcateLogic.getProdCatetory~"+productid+"~"+hasLearncont+"~"+hasExercise, 
		 				new String[]{LearncontentDaoImpl.LearncontentChangeCacheType+":"+productid.toString()});
			}
			if(hasExercise){
				CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
		 				"ContentcateLogic.getProdCatetory~"+productid+"~"+hasLearncont+"~"+hasExercise, 
		 				new String[]{ExerciseDaoImpl.ExerciseChangeCacheType+":"+productid.toString()});
			}
		}
		return list;
	}
	
	private Contentcate deeploopset(Contentcate vo, boolean hasLearncont, boolean hasExercise){
		List<Learncontent> learncontList = null;
		List<Exercise> exerList = null;
		if(hasLearncont){
			learncontList = learncontentDao.selectContents(vo.getProductbaseid(), vo.getContentcateid(), null);
		    vo.setLearncontentlist(learncontList);
		}
		if(hasExercise){
			exerList = exerciseDao.selectExerByProd(vo.getProductbaseid(), vo.getContentcateid());
		    vo.setExerlist(exerList);
		}
		if(vo.getSublist()!=null && vo.getSublist().size()>0){
			for(Contentcate subvo : vo.getSublist()){
				deeploopset(subvo, hasLearncont, hasExercise);
			}
		}
		return vo;
	}
	
	/**
	 * 查询产品的某个目录内容，包括学习内容和联系内容
	 * @param catelist
	 * @param cateid
	 * @param productid
	 * @return
	 */
	public Contentcate getCateWithLearncont(List<Contentcate> catelist, Long cateid, Long productid){
		AssertUtil.assertNotNull(productid, null);
		Contentcate returnvo = null;
		if(cateid==null||CommonConstant.TreeTopnodePid.equals(cateid)){
			// top level content category
			returnvo = new Contentcate();
			returnvo.setProductbaseid(productid);
			returnvo.setContentcateid(cateid);
			//List<Learncontent> learncontList = BOFactory.getLearncontentDao().selectContents(productid, null, null);
			List<Learncontent> learncontList = BOFactory.getLearncontentDao().selectContents(productid, CommonConstant.NullPK_Parameter, null);
			returnvo.setLearncontentlist(learncontList);
		    List<Exercise> exerList = BOFactory.getExerciseDao().selectExerByProd(productid, CommonConstant.NullPK_Parameter);
		    returnvo.setExerlist(exerList);
		}else {
			if(catelist==null){
				catelist = getInstance().getProdCatetory(productid, true, true);
			}
			if(catelist!=null){
				returnvo = getMapVO(catelist, cateid);
			}
		}
		return returnvo;
	}
	
	private Contentcate getMapVO(List<Contentcate> catelist, Long cateid){
		if(catelist==null||cateid==null){
			return null;
		}
		Contentcate vo_rtn = null;
		for(Contentcate vo:catelist){
			if(cateid.equals(vo.getContentcateid())){
				return vo;
			}else if(vo.getSublist()!=null){
				vo_rtn = getMapVO(vo.getSublist(), cateid);
				if(vo_rtn!=null){
					return vo_rtn;
				}
			}
		}
		return null;
	}
	
    /**
     * select some record by PK Array
     */
    public Map<Long,Contentcate> selectByPKList(List<Long> pkList){
    	Map<Long,Contentcate> map = new HashMap<Long,Contentcate>();
    	if(pkList==null||pkList.size()==0)
    		return map;
    	Long[] pkArr = new Long[pkList.size()];
    	pkList.toArray(pkArr);
    	List<Contentcate> list = dao.selectByPKArr(pkArr);
    	for(Contentcate vo : list){
    		map.put(vo.getContentcateid(), vo);
    	}
    	return map;
    }
    
    /**
     * 批量删除，如果有引用就报错
     * @param productid
     * @param keys
     * @return
     * @throws Exception
     */
    public void deleteByPK(Long productid, Long contentcateid) {
    	if(contentcateid==null||productid==null)
    		return;
		List childList = dao.getChildNodes(productid, contentcateid);
		
		if(childList!=null && childList.size()>0){
	       throw new HasChildException("category-Reference");
		}else{ 
			// 检查目录是否被学习资料
			Integer learnNum = dao.getLearnCountInCate(productid, contentcateid);
			if(learnNum<1){
				dao.deleteByPK(contentcateid);
			}else {
				throw new HasReferenceException("learncontent-Reference");
			}
		}
    }
    
    /**
     * get next contentcate from its list by the default sequence in the list
     * @param list
     * @param pkId
     * @return
     */
    public Contentcate getNextContentcateFromList(List<Contentcate> list, Long pkId){
    	Contentcate vo = null;
    	if(list!=null && list.size()>0){
    		int index = 0;
    		if(pkId==null){
    			index = 0;
    		}else {
	    		for(int i=0;i<list.size();i++){
	    			if(pkId.equals(list.get(i).getContentcateid())){
	    				if(i<list.size()-1){
	    					index = i+1;
	    					break;
	    				}
	    			}
	    		}
    		}
    		vo = list.get(index);
    	}
    	return vo;
    }
    
    
	private BaseEmptyEntity getFirstObjectForNext(
			List<Learncontent> learncontentlist, List<Exercise> exerlist) {
		if (learncontentlist != null && learncontentlist.size() > 0) {
			return new BaseEmptyEntity(learncontentlist.get(0).getContentid(),
					Learncontent.ObjectType);
		} else if (exerlist != null && exerlist.size() > 0) {
			return new BaseEmptyEntity(exerlist.get(0).getExerid(),
					Exercise.ObjectType);
		} else {
			return null;
		}
	}

	/**
	 * get learncontent in learncontent list, get next learncontent
	 * 
	 * @param objectid
	 *            : objectid is the learn content which user had finished it
	 * @return
	 */
	private BaseEmptyEntity loopLearncontentInContentCate(Long objectid,
			Long userid, List<Learncontent> list) {
		BaseEmptyEntity vo = null;
		if (list != null && list.size() > 0) {
			int index_need = 0;
			if (objectid != null) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getContentid().equals(objectid)) {
						index_need = i + 1;
						break;
					}
				}
				// input object is the last learn content in current
				// contentcate, also need search next contcate
				if (index_need >= list.size()) {
					index_need = 0;
				}
			}

			LearnactivityDao dao = BOFactory.getLearnactivityDao();
			Learncontent learnvo = null;
			Learnactivity activityvo = null;
			for (int i = index_need; i < list.size(); i++) {
				learnvo = list.get(index_need);
				// check whether this has been learned
				activityvo = dao
						.selectByPK(userid, learnvo.getContentid(),
								Learncontent.ObjectType,
								Learnactivity.Actiontype_Learn);
				if (activityvo == null
						|| !Learnactivity.Learnstatus_Finished
								.equals(activityvo.getLearnstatus())) {
					vo = new BaseEmptyEntity(learnvo.getContentid(),
							Learncontent.ObjectType);
					break;
				}
			}
		}
		return vo;
	}

	// objectid is the learn exercise content which user had finished it
	private BaseEmptyEntity loopExerciseInContentCate(Long objectid,
			Long userid, List<Exercise> list) {
		BaseEmptyEntity vo = null;
		if (list != null && list.size() > 0) {
			int index_need = 0;
			if (objectid != null) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getExerid().equals(objectid)) {
						index_need = i + 1;
						break;
					}
				}
				// input object is the last learn content in current
				// contentcate, also need search next contcate
				if (index_need >= list.size()) {
					index_need = 0;
				}
			}

			LearnactivityDao dao = BOFactory.getLearnactivityDao();
			Exercise exervo = null;
			Learnactivity activityvo = null;
			for (int i = index_need; i < list.size(); i++) {
				exervo = list.get(index_need);
				// check whether this has been learned
				activityvo = dao.selectByPK(userid, exervo.getExerid(),
						Exercise.ObjectType, Learnactivity.Actiontype_Exercise);
				if (activityvo == null
						|| !Learnactivity.Learnstatus_Finished
								.equals(activityvo.getLearnstatus())) {
					vo = new BaseEmptyEntity(exervo.getExerid(),
							Exercise.ObjectType);
					break;
				}
			}
		}
		return vo;
	}
	
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
					 Contentcate curcatevo, Long userid, boolean needReturnFirst)
	{
		BaseEmptyEntity vo = null;
		if(curcatevo==null){
			ContentcateLogic catelogic = BOFactory.getContentcateLogic();
			List<Contentcate> catelist = catelogic.getProdCatetory(productid, true, true);
			curcatevo = catelogic.getCateWithLearncont(catelist, contcateid, productid);
		}
		List<Learncontent> learncontentlist = curcatevo.getLearncontentlist();
		List<Exercise> exerlist = curcatevo.getExerlist();
		
		if(objectid_current==null || objecttype_current==null){
			// find the first unfinished learn content(including exercise)
			vo = loopLearncontentInContentCate(objectid_current, userid, learncontentlist);
			if(vo==null){
				vo = loopExerciseInContentCate(null, userid, exerlist);
			}
			// here, get first learn content or exercise for next
			if(needReturnFirst && vo==null){
				vo = getFirstObjectForNext(learncontentlist, exerlist);
			}
		}else {
			// find the next learn content after the given learn content
			if(Learncontent.ObjectType.equals(objecttype_current)){
			   vo = loopLearncontentInContentCate(objectid_current, userid, learncontentlist);
			   if(vo==null){
				   vo = loopExerciseInContentCate(null, userid, exerlist);
			   }
			   if(needReturnFirst && vo==null){
				   vo = getFirstObjectForNext(learncontentlist, exerlist);
			   }
			}else if(Exercise.ObjectType.equals(objecttype_current)){
			   vo = loopExerciseInContentCate(objectid_current, userid, exerlist);
			   // if all exercise has finished, then loop learn content to get next
			   if(vo==null){
				   vo = loopLearncontentInContentCate(null, userid, learncontentlist);
			   }
			   // if still empty, get first learn content for next
			   if(needReturnFirst && vo==null){
				   vo = getFirstObjectForNext(learncontentlist, exerlist);
			   }
			}
		}
		
		return vo;
	}
	
    
	 public static ContentcateLogic getInstance(){
		 ContentcateLogic logic = (ContentcateLogic)BeanFactory.getBeanFactory().getBean("contentcateLogic");
	     return logic;
	 }

	public ContentcateDao getDao() {
		return dao;
	}

	public void setDao(ContentcateDao dao) {
		this.dao = dao;
	}

	public LearncontentDao getLearncontentDao() {
		return learncontentDao;
	}

	public void setLearncontentDao(LearncontentDao learncontentDao) {
		this.learncontentDao = learncontentDao;
	}

	public ExerciseDao getExerciseDao() {
		return exerciseDao;
	}

	public void setExerciseDao(ExerciseDao exerciseDao) {
		this.exerciseDao = exerciseDao;
	}

}

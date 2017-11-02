package netTest.paper.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import netTest.bean.BeanFactory;
import netTest.exception.ExceptionConstant;
import netTest.paper.constant.PaperConstant;
import netTest.paper.dao.PaperDao;
import netTest.paper.dao.PaperpatternratioDao;
import netTest.paper.dao.PaperpropDao;
import netTest.paper.dao.PaperquestypeDao;
import netTest.paper.dto.PaperQuery;
import netTest.paper.logic.PaperLogic;
import netTest.paper.logic.PaperquesLogic;
import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperpatternratio;
import netTest.paper.vo.Paperprop;
import netTest.paper.vo.Paperques;
import netTest.paper.vo.Paperquestype;
import netTest.wareques.dao.WareDao;

import org.springframework.cache.annotation.Cacheable;

import commonTool.base.Page;
import commonTool.cache.CacheSynchronizer;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;

public class PaperLogicImpl implements PaperLogic {
	
	private PaperDao dao;
	private PaperquestypeDao typeDao;
	private PaperpatternratioDao ratioDao;
	private PaperpropDao propDao;
	private WareDao wareDao;

	private PaperquesLogic paperquesLogic;

	/**
	 * 列出有权限看到的试卷，暂时只是能看到自己单位创建的试卷
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page listPaperPage(PaperQuery queryVO,int pageIndex,int pageSize,Integer total){
		//TODO 以后这里能看到试卷逻辑是要改变的
		Page page = dao.selectByVOPage(queryVO, pageIndex, pageSize, total);
		return page;
	}
	
	/**
	 * 查询一份试卷中某种题型的题目集合
	 * @param paperid
	 * @param questyepUse: 需要查询的题型。
	 *        如果为空，则取试卷的第一种题型，如果为-1，则查询全部题型
	 * @return
	 */
	public Paper qryPaperquesPattern(Long paperid,Long questypeidUse){
		Paper vo = dao.selectByPK(paperid);
		Integer questypeUse = null;
		if(vo!=null){
			List questypeList = typeDao.qryByPaperid(paperid);
			if(questypeList!=null&&questypeList.size()>0){
			    vo.setQuestypeList(questypeList);
			    Paperquestype typeVO = null;
			    List<Paperques> listTemp = null;
				if(!PaperConstant.GeneType_dynamic.equals(vo.getGenetype())){
					// 从PaperQues表查询试卷题目
					if(questypeidUse==null){
						typeVO = (Paperquestype)questypeList.get(0);
						questypeidUse = typeVO.getQuestypeid();
						questypeUse = typeVO.getQuestype();
					}
					if(questypeidUse.longValue()==-1)
						questypeidUse = null;
					for(int i=0;i<questypeList.size();i++){
						typeVO = (Paperquestype)questypeList.get(i);
						if(questypeidUse!=null){
							if(questypeidUse.equals(typeVO.getQuestypeid())){
								listTemp = paperquesLogic.qryPaperquesByPattern(paperid,typeVO.getQuestypeid(),typeVO.getQuestype());	
								typeVO.setPaperquesList(listTemp);
								break;
							}
						}
						//当不指定题目类型时，则查询所有类型题目
						listTemp = paperquesLogic.qryPaperquesByPattern(paperid,typeVO.getQuestypeid(),typeVO.getQuestype());	
						typeVO.setPaperquesList(listTemp);
					}
				}else { // 动态试卷
					//TODO 从Question中查询试卷题目
					
				}
				vo.setQuestypeidUse(questypeidUse);
				vo.setQuestyepUse(questypeUse);
			}
		}
		return vo;
	}
	
	/**
	 * 查询一份试卷，包括其中的题目
	 * @param paperid
	 * @return
	 */
	@Cacheable(value="netTest.paperCache", key="'PaperLogicImpl.qryPaperWhole~paper:'+#paperid", unless="#result==null")
	public Paper qryPaperWhole(Long paperid){
		if(paperid==null)
			return null;
		Paper vo = this.qryPaperquesPattern(paperid, -1l);
		
		// add cache key into key assoic map
    	CacheSynchronizer.getInstance().buildAssoc("netTest.paperCache", 
    				"PaperLogicImpl.qryPaperWhole~"+Paper.ObjectType+":"+paperid,
    				new String[]{Paper.ObjectType+":"+paperid});
		return vo;
	}
	
	/**
	 * 保存试卷和试卷的各种题型和各个比例配置
	 * @param vo
	 * @param questypeList
	 * @param contcateList
	 * @param diffList
	 * @return
	 */
	private Long addPaperSet(Paper vo,int createtype,List questypeList,
							List contcateList,List diffList)
	{
		float papertotalscore = 0;
		Paperquestype typeVO = null;
		List<Paperquestype> list1 = new ArrayList<Paperquestype>();
		// 处理题型
		for(int i=0;i<questypeList.size();i++){
			typeVO = (Paperquestype)questypeList.get(i);
			typeVO.setShopid(vo.getShopid());
			// 分数默认为0
			typeVO.setPatternscore(0f);
//			typeVO.setPatternscore(typeVO.getQuesscore()*typeVO.getPatternquesnum());
//			papertotalscore += typeVO.getPatternscore();
			list1.add(typeVO);
		}
		Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
		vo.setCreatetime(currentDate);
		vo.setStatus(PaperConstant.PaperStatus_valide);
		if(createtype==1 || PaperConstant.GeneType_dynamic.equals(vo.getGenetype())){
			vo.setPhase(PaperConstant.PaperPhase_toMake);
		}else {
			vo.setPhase(PaperConstant.PaperPhase_finished);
		}
		vo.setPapertotalscore(papertotalscore);
		vo.setModitime(currentDate);
		vo.setVersion(1);
		Long pk = dao.insert(vo);
		//  插入题目附属属性
		Paperprop propVO = new Paperprop();
		propVO.setPaperid(pk);
		propVO.setOrgname(vo.getOrgname());
		propVO.setProductname(vo.getProductname());
		propVO.setPaperusenum(0);
		propVO.setShopid(vo.getShopid());
		propVO.setWarenamestr(vo.getWarenamestr());
		propDao.insert(propVO);
        // 设置试卷id
		for(int i=0;i<list1.size();i++){
			typeVO = (Paperquestype)questypeList.get(i);
			//TODO 在此先将questype赋值给questypeid，以后如果允许用户自定义题型的时候再改
			typeVO.setQuestypeid(Paperquestype.getQuestypeID(typeVO.getQuestype()));
			typeVO.setPaperid(pk);
		}
		typeDao.insertBatch(list1);
		if(contcateList!=null&&contcateList.size()>0){
			List list2 = new ArrayList();
			Paperpatternratio ratioVO = null;
			for(int j=0;j<contcateList.size();j++){
				ratioVO = (Paperpatternratio)contcateList.get(j);
				ratioVO.setPaperid(pk);
				ratioVO.setShopid(vo.getShopid());
				//TODO 在此先将questype赋值给questypeid，以后如果允许用户自定义题型的时候再改
				ratioVO.setQuestypeid(new Long(ratioVO.getQuestype().toString()));
				list2.add(ratioVO);
			}
			ratioDao.insertBatch(list2);
		}
		return pk;
	}
	
	/**
	 * 生成试卷，先保存试卷(包括配置)，然后返回生成后的试卷
	 * @param vo
	 * @param createtype 是否自动生成试卷，0代表只创建试卷不自动选题，1代表要自动选题生成试卷
	 * @param questypeList
	 * @param contcateList
	 * @param diffList
	 * @return
	 */
	public Long genePaper(Paper vo, int createtype, 
			        List questypeList, List contcateList, List diffList)
	{
		Long pk = this.addPaperSet(vo, createtype, questypeList, contcateList, diffList);
		// 调用存储过程,根据试卷的配置，生成试卷题目
		if(createtype==1){
		   dao.genePaperQues(pk);
		}
		return pk;
	}
		
	/**
	 * 删除试卷及试卷设置，包括试卷题目。包括试卷的引用信息，考试等信息
	 * @param paperid
	 * @return
	 */
	public int delWholePaper(Long paperid, Paper vo){
		if(paperid==null)
			AssertUtil.assertNotNull(paperid, ExceptionConstant.Error_Need_Paramter);
		if(vo==null) {
			vo = dao.selectByPK_plain(paperid);
		}
		//先需要判断试卷是否被考试等信息引用，如果有引用信息，则报错
		int count = dao.countTestinfoNumber(paperid, vo.getShopid());
		if(count>0){
			throw new LogicException(ExceptionConstant.Error_Paper_Delete_HasTestinfoRef);
		}
		// 删除试卷，包括试卷相关的题目信息，配置等	
		// 删除试卷, 试卷题目等内容
		dao.deleteByPK(paperid,vo.getGenetype());
		return 1;
	}
	
	/**
	 * 根据product删除所有的paper, 用于删除产品时的被调用
	 * @param productid
	 * @param shopid
	 */
	public void deletePaperByProd(Long productid, Long shopid){
		PaperQuery queryVO = new PaperQuery();
		queryVO.setProductbaseid(productid);
		queryVO.setShopid(shopid);
		List paperlist = dao.selectByVO(queryVO);
		if(paperlist!=null && paperlist.size()>0){
			Paper votemp = null;
			for(int i=0;i<paperlist.size();i++){
				votemp = (Paper)paperlist.get(i);
				delWholePaper(votemp.getPaperid(), votemp);
			}
		}
	}
	
	/**
	 * 删除多份试卷，包括试卷的引用信息，考试等信息
	 * 不允许一次删除多份试卷
	 * @param keys
	 * @return
	 */
//	public int delMutiWholePapers(String[] keys){
//		int result = 0;
//		if(keys==null||keys.length==0)
//			return result;
//		//TODO 检查是否有被引用的试卷。如果有，则抛出HasReferenceException型异常
//
//		for(int i=0;i<keys.length;i++){
//			this.delWholePaper(new Long(keys[i]), null);
//			result++;
//		}
//		return result;
//	}

	public PaperDao getDao() {
		return dao;
	}

	public void setDao(PaperDao dao) {
		this.dao = dao;
	}

	public PaperpatternratioDao getRatioDao() {
		return ratioDao;
	}

	public void setRatioDao(PaperpatternratioDao ratioDao) {
		this.ratioDao = ratioDao;
	}

	public PaperquestypeDao getTypeDao() {
		return typeDao;
	}

	public void setTypeDao(PaperquestypeDao typeDao) {
		this.typeDao = typeDao;
	}
	
	/**
	 * static spring getMethod
	 */
	 public static PaperLogic getInstance(){
		 PaperLogic logic = (PaperLogic)BeanFactory.getBeanFactory().getBean("paperLogic");
	     return logic;
	 }

	public PaperquesLogic getPaperquesLogic() {
		return paperquesLogic;
	}

	public void setPaperquesLogic(PaperquesLogic paperquesLogic) {
		this.paperquesLogic = paperquesLogic;
	}

	public PaperpropDao getPropDao() {
		return propDao;
	}

	public void setPropDao(PaperpropDao propDao) {
		this.propDao = propDao;
	}

	public WareDao getWareDao() {
		return wareDao;
	}

	public void setWareDao(WareDao wareDao) {
		this.wareDao = wareDao;
	}
	
}

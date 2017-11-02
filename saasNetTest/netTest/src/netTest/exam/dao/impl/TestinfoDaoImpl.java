package netTest.exam.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import commonTool.base.BaseDao;
import commonTool.base.Page;
import netTest.exam.dao.TestinfoDao;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.exception.LogicException;
import commonTool.exception.NeedParamsException;
import commonTool.util.DateUtil;
import netTest.exam.vo.Testinfo;
import netTest.exam.dto.TestinfoQuery;
import netTest.exception.ExceptionConstant;
import netTest.bean.BeanFactory;

public class TestinfoDaoImpl extends BaseDao implements TestinfoDao {

	static Logger log = Logger.getLogger(TestinfoDaoImpl.class.getName());
	
	private final static String ProductType_InTest = "Product_InTest";
	
    /**
     *
     */
    public TestinfoDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    @Cacheable(value="netTest.testinfoCache", key="'TestinfoDao.selectByPK~'+#pk", unless="#result==null")
    public Testinfo selectByPK(Long pk){
    	if(pk==null)
    		throw new NeedParamsException("--TestinfoDaoImpl.selectByPK--");
		Testinfo record = (Testinfo) this.queryForObject("Testinfo.selectByPK", pk);
		
		if(record!=null){
		   CacheSynchronizer.getInstance().buildAssoc("netTest.testinfoCache", 
				"TestinfoDao.selectByPK~"+pk, new String[]{Testinfo.ObjectType+":"+pk});
		}
		
		return record;
    }
           
    /**
     * select records by queryVO
     */
    public List selectByVO(TestinfoQuery queryVO){
		if(queryVO==null)
			return new ArrayList();
		List list = this.queryForList("Testinfo.selectByVO", queryVO);
		return list;
    }
    
    /**
     * 查询产品正在进行的或即将进行的考试
     */
    @Cacheable(value="netTest.testinfoCache", key="'TestinfoDao.selectUpcomingByProduct~'+#productid", unless="#result==null")
    public List<Testinfo> selectUpcomingByProduct(Long productid){
		if(productid==null)
			return new ArrayList<Testinfo>();
		List<Testinfo> list = (List<Testinfo>)this.queryForList("Testinfo.selectUpcomingByProduct", productid);
		if(list!=null){
		   CacheSynchronizer.getInstance().buildAssoc("netTest.testinfoCache", 
				"TestinfoDao.selectUpcomingByProduct~"+productid, new String[]{ProductType_InTest+":"+productid});
		}
		return list;
    }
    
    /**
     * 根据产品id查询考试数量
     * @param productid
     * @param shopid
     * @param teststatus
     * @return
     */
    public int countTestinfoNumber(Long productid, Long shopid, Short teststatus){
    	if(productid==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
    	TestinfoQuery queryVO = new TestinfoQuery();
    	queryVO.setProductbaseid(productid);
    	queryVO.setTeststatus(teststatus);
    	queryVO.setShopid(shopid);
		Integer count = (Integer)this.queryForObject("Testinfo.countTestinfoNumber", queryVO);
		return count;
    }
    
    /**
     * 根据产品信息查询考试信息
     */
//    public Page listTestByProdCatePage(TestinfoQuery queryVO,int pageIndex,int pageSize,Integer total){
//		if(queryVO==null)
//			return Page.EMPTY_PAGE;
//		if(pageIndex<=0)
//        	pageIndex = 1;
//        if(pageSize<=0)
//        	pageSize = CommonConstant.PAGESIZE;
//        Page page = null;
//        page = queryForPage("Testinfo.listTestByProdCate", queryVO, pageIndex, pageSize,total);
//		return page;
//    }

    /**
     * 新增考试时判断用户输入的考试时间是否和本单位已有的考试有时间冲突
     * @param shopid
     * @param teststartdate
     * @param testenddate
     * @param orgbaseid
     * @return
     */
    // 查询sql含义：根据输入的时间查找本单位的现有时间有冲突的考试，并且考试的状态时未开始和正在进行考试的
    public List qryTestCollideAddTest(Long shopid,Date teststartdate,Date testenddate,Long orgbaseid,Long testid)
    {
    	if(shopid==null||teststartdate==null||testenddate==null||orgbaseid==null)
    		throw new NeedParamsException("--TestinfoDaoImpl.qryTestCollideAddTest--");
    	TestinfoQuery queryVO = new TestinfoQuery();
    	queryVO.setShopid(shopid);
    	queryVO.setOrgbaseid(orgbaseid);
    	queryVO.setTeststartdate(teststartdate);
    	queryVO.setTestenddate(testenddate);
    	queryVO.setTestid(testid);
    	List list = this.queryForList("Testinfo.qryTestCollide1", queryVO);
    	return list;
    }
    
//    /**
//     * 在考试信息列表页面显示某个单位是否有时间冲突的考试
//     * @param shopid
//     * @param teststartdate
//     * @param testenddate
//     * @param orgbaseid
//     * @return
//     */ -- deleted in 2012-08-22
//    // 查询sql含义：关联2个testinfo表找一个单位的现有时间有冲突的考试，并且考试的状态时未开始和正在进行考试的
//    public List qryTestCollideForDept(Long shopid,Long orgbaseid){
//    	if(shopid==null||orgbaseid==null)
//    		throw new NeedParamsException("--TestinfoDaoImpl.qryTestCollideForDept--");
//    	TestinfoQuery queryVO = new TestinfoQuery();
//    	queryVO.setShopid(shopid);
//    	queryVO.setOrgbaseid(orgbaseid);
//    	List list = this.queryForList("Testinfo.qryTestCollide2", queryVO);
//    	return list;
//    }
    
    /**
     * select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(TestinfoQuery queryVO,int pageIndex,int pageSize,Integer total){
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        Page page = null;
        String sqlStr = "";
        if(queryVO==null){
        	page = Page.EMPTY_PAGE;
        } else {
        	sqlStr = "Testinfo.selectByVO";
        	page = queryForPage(sqlStr, queryVO, pageIndex, pageSize,total);
        }
        return page;
    }
       
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Testinfo record){
    	if(record==null || record.getProductbaseid()==null)
    		return null;
    	record.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
		Long pk = (Long)super.insert("Testinfo.insert", record);
		record.setTestid(pk);
		super.insert("Testinfo.insertTestinfoprop", record);
		
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", ProductType_InTest, record.getProductbaseid().toString());
		return pk;
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Testinfo record){
    	if(record==null||record.getTestid()==null)
    		return 0;
		int rows1 = 0;
		if(record.isChangedTestinfo()){
			rows1 = super.update("Testinfo.updateByPK", record);
		}
		int rows2 = 0;
		if(record.isChangedProp()){
		   rows2 = super.update("Testinfo.updateTestinfopropByPk", record);
		}
		int rows = (rows1>rows2)?rows1:rows2;
		
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", Testinfo.ObjectType, record.getTestid().toString());
		Long productid = record.getProductbaseid();
		if(productid==null){
			Testinfo testvo = selectByPK(record.getTestid());
			productid = testvo.getProductbaseid();
		}
		CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", ProductType_InTest, productid.toString());
		return rows;
    }
    
    /**
     * 修改考试状态
     * @param testid
     * @param nextteststatus
     * @return
     */
    public int updTeststatus(Long testid,Short nextteststatus,Date updatetime){
    	if(testid==null||nextteststatus==null||updatetime==null)
    		throw new NeedParamsException();
    	Testinfo vo = new Testinfo();
    	vo.setTestid(testid);
    	vo.setTeststatus(nextteststatus);
    	vo.setUpdatetime(updatetime);
    	int rows = super.update("Testinfo.updTeststatus", vo);
    	// flush cache
    	CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", Testinfo.ObjectType, testid.toString());
    	Testinfo testvo = selectByPK(testid);
    	CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", ProductType_InTest, testvo.getProductbaseid().toString());
    	return rows;
    }

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Testinfo save(Testinfo record){
    	if(record==null)
    		return null;
		if(record.getTestid()==null||record.getTestid().intValue()==0){
			Long pkValue = this.insert(record);
			record.setTestid(pkValue);
    		return record;
		}else{
			this.updateByPK(record);
			return record;
		}
    }

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk){
    	if(pk==null)
    		throw new NeedParamsException("--TestinfoDaoImpl.deleteByPK--");
    	Testinfo testvo = selectByPK(pk);
    	super.delete("Testinfo.delTestinfopropByPK", pk);
		int rows = super.delete("Testinfo.deleteByPK", pk);
		// flush cache
    	CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", Testinfo.ObjectType, pk.toString());
    	CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", ProductType_InTest, testvo.getProductbaseid().toString());
		return rows;
    }
        
    /**
     * deleteBatch records by the String Array of PK
     */
//    public int deleteBatchByPK(String[] pkArray){
//    	if(pkArray==null||pkArray.length<=0)
//    		return 0;
//    	int rows = 0;
//    	Long[] arrs = new Long[pkArray.length];
//		for(int i=0;i<pkArray.length;i++){
//			if(pkArray[i]!=null)
//				arrs[i] = new Long(Long.parseLong(pkArray[i]));
//		}
//		rows = super.deleteBatch("Testinfo.deleteByPK", arrs);
//		return rows;
//    }
    
    /**
     * static spring getMethod
     */
     public static TestinfoDao getInstance(){
       	 TestinfoDao dao = (TestinfoDao)BeanFactory.getBeanFactory().getBean("testinfoDao");
         return dao;
     }
    
}

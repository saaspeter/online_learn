package netTest.exam.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netTest.bean.BOFactory;
import netTest.bean.BeanFactory;
import netTest.exam.constant.TestuserConstant;
import netTest.exam.dao.TestuserDao;
import netTest.exam.dto.TestuserQuery;
import netTest.exam.dto.UseranswerQuery;
import netTest.exam.vo.Testuser;
import netTest.orguser.dao.OrguserDao;
import netTest.orguser.vo.Orguser;
import netTest.wareques.constant.QuestionConstant;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import commonTool.base.BaseDao;
import commonTool.base.Page;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.exception.NeedParamsException;

public class TestuserDaoImpl extends BaseDao implements TestuserDao {

	static Logger log = Logger.getLogger(TestuserDaoImpl.class.getName());
	
	private final static String TestChangeCacheType = "TestChangeCacheType";
	
    /**
     *
     */
    public TestuserDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    @Cacheable(value="netTest.testinfoCache", key="'TestuserDao.selectByPK~'+#pk", unless="#result==null")
    public Testuser selectByPK(Long pk){
    	if(pk==null)
    		throw new NeedParamsException("--TestuserDaoImpl.selectByPK--");
		Testuser record = (Testuser) this.queryForObject("Testuser.selectByPK", pk);
		if(record!=null){
		   CacheSynchronizer.getInstance().buildAssoc("netTest.testinfoCache", 
				"TestuserDao.selectByPK~"+pk, new String[]{Testuser.ObjectType+":"+pk, TestChangeCacheType+":"+record.getTestid()});
		}
		return record;
    }
    
    /**
     * select some record by LogicPK
     */
    @Cacheable(value="netTest.testinfoCache", key="'TestuserDao.selectByLogicPK~'+#testid+'~'+#userid", unless="#result==null")
    public Testuser selectByLogicPK(Long testid,Long userid){
    	if(testid==null||userid==null)
    		throw new NeedParamsException("--TestuserDaoImpl.selectByLogicPK--");
    	TestuserQuery queryVO = new TestuserQuery();
    	queryVO.setTestid(testid);
    	queryVO.setUserid(userid);
		Testuser record = (Testuser) this.queryForObject("Testuser.selectByLogicPK", queryVO);
		
		// build assoic with Test
		CacheSynchronizer.getInstance().buildAssoc("netTest.testinfoCache", 
				"TestuserDao.selectByLogicPK~"+testid+"~"+userid, new String[]{Testuser.ObjectType+":"+record.getTestuserid(), TestChangeCacheType+":"+testid});
		return record;
    }
           
    /**
     * select records by queryVO
     */
    public List selectByVO(TestuserQuery queryVO){
		if(queryVO==null)
			return null;
		String sqlStr = "Testuser.selectByVO1";
		if(queryVO.getQueryType()==2)
			sqlStr = "Testuser.selectByVO2";
		List list = this.queryForList(sqlStr, queryVO);
		return list;
    }
    
    /**
     * 为了给考生排名而查询的信息。vo中只包含分数和主键
     */
    public List selectForOrderByVO(TestuserQuery queryVO){
		if(queryVO==null)
			return new ArrayList();
		String sqlStr = "Testuser.selectForOrderByVO";
		List list = this.queryForList(sqlStr, queryVO);
		return list;
    }
    
    /**
     * select page by queryVO 
     * @param queryVO: QueryType 1 表示只查询testuser表，2表示关联testuser和testinfo表
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(TestuserQuery queryVO,int pageIndex,int pageSize,Integer total){
    	if(queryVO==null){
    		return Page.EMPTY_PAGE;
    	}
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        String sqlStr = null;
        if(queryVO.getQueryType()==2){
			sqlStr = "Testuser.selectByVO2";
        } else {
        	sqlStr = "Testuser.selectByVO1";
        }
        Page page = queryForPage(sqlStr, queryVO, pageIndex, pageSize,total);
        
        return page;
    }
    
    /**
     * 查询用户考生是否有重复的考试信息
     * @param shopid
     * @param userid
     * @return
     */
    public List qryTestCollideUser(Long shopid,Long userid){
    	if(userid==null)
    		throw new NeedParamsException("--TestinfoDaoImpl.qryTestCollideUser--");
    	TestuserQuery queryVO = new TestuserQuery();
    	queryVO.setShopid(shopid);
    	queryVO.setUserid(userid);
    	List list = this.queryForList("Testuser.qryTestCollideUser", queryVO);
    	return list;
    }
    
    /**
     * 查询设置考试中有哪些人员是本次考试中已经有的。用于过滤考试人员
     * 返回的是人员的串，用','隔开
     * @param testid
     * @param deptIdstr
     * @param shopid
     * @return
     */
    public String qryJoinTestusers(Long testid,String userIdstr,Long shopid){
    	if(testid==null||shopid==null)
    		throw new NeedParamsException("-- need the parameters in TestuserDaoImpl.qryJoinTestusers");
    	TestuserQuery queryVO = new TestuserQuery();
    	queryVO.setTestid(testid);
    	queryVO.setShopid(shopid);
    	if(userIdstr!=null&&userIdstr.trim().length()>0){
    	    if(userIdstr.trim().endsWith(",")){
    	    	userIdstr = userIdstr.trim().substring(0,userIdstr.length()-1);
    	    }
    		queryVO.setUserIdstr(userIdstr);
    	}
    	List list = this.queryForList("Testuser.qryJoinTestusers", queryVO);
    	StringBuffer buffer = new StringBuffer();
    	if(list!=null&&list.size()>0)
    		for(int i=0;i<list.size();i++){
    			buffer.append(((Long)list.get(i)).toString()).append(",");
    		}
    	return buffer.toString();
    }
       
    /**
     * 查询某场考试中正在考试的考生数
     * 用于手动结束考试时，提示操作员的信息
     * @param testid
     * @return
     */
    public long countInTestinguser(Long testid){
    	TestuserQuery queryVO = new TestuserQuery();
    	queryVO.setTestid(testid);
    	// 查询考试没有提交考卷的考生数目
    	String statusStr = TestuserConstant.Status_examing.toString();
    	queryVO.setStatusStr(statusStr);
    	long examnum = 0;
    	Object obj1 = this.queryForObject("Testuser.countUsernum", queryVO);
    	if(obj1!=null){
    		examnum = (Long)obj1;
    	}
    	return examnum;
    }
    
    /**
     * 查询考生数目
     * @param queryVO
     * @return
     */
    public long countTestuser(TestuserQuery queryVO){
    	long num = 0;
    	Object obj1 = this.queryForObject("Testuser.countUsernum", queryVO);
    	if(obj1!=null){
    		num = (Long)obj1;
    	}
    	return num;
    }
    
    /**
     * insert a record,and update user's orgbaseid
     */
    public Long insert(Testuser vo){
    	if(vo==null)
    		return null;
		Long pk = (Long)super.insert("Testuser.insert", vo);
		// 更新testuser中的orgbaseid
		if(vo.getOrgbaseid()==null&&vo.getShopid()!=null){
			OrguserDao orguserDao = BOFactory.getOrguserDao();
			Orguser orguserVO = orguserDao.selectOrgForUser(vo.getUserid(), vo.getShopid());
			if(orguserVO!=null){
				Testuser voTemp = new Testuser();
				voTemp.setTestuserid(pk);
				voTemp.setOrgbaseid(orguserVO.getOrgbaseid());
				this.updateByPK(voTemp);
			}
		}
		return pk;
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Testuser record){
    	if(record==null||record.getTestuserid()==null)
    		return 0;
		int rows = super.update("Testuser.updateByPK", record);
		// pub flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", Testuser.ObjectType, record.getTestuserid().toString());
		return rows;
    }
    
    /**
     * update all test user status in a test
     */
    public int updateStatusByTestinfo(Long testid, Short fromStatus, Short toStatus){
    	if(testid==null||fromStatus==null||toStatus==null)
    		return 0;
    	Map param = new HashMap();
    	param.put("testid", testid);
    	param.put("fromstatus", fromStatus);
    	param.put("tostatus", toStatus);
		int rows = super.update("Testuser.updateStatusByTestinfo", param);
		// pub flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", TestChangeCacheType, testid.toString());
		return rows;
    }
    
    /**
     * 批量更新考生的考试排名情况，其余字段不会更新
     */
    public int updateTestOrderByPK(List<Testuser> list){
    	if(list==null||list.size()<1)
    		return 0;
		int rows = super.updateBatch("Testuser.updateTestOrderByPK", list);
        // pub flush cache
		for(Testuser vo : list){
			CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", Testuser.ObjectType, vo.getTestuserid().toString());
		}
		return rows;
    }

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Testuser save(Testuser record){
    	if(record==null)
    		return null;
		if(record.getTestuserid()==null||record.getTestuserid().intValue()==0){
			Long pkValue = this.insert(record);
			record.setTestuserid(pkValue);
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
    		return 0;
		int rows = super.delete("Testuser.deleteByPK", pk);
		// pub flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", Testuser.ObjectType, pk.toString());
		return rows;
    }
    
    /**
     * delete a record by testid
     */
    public int deleteByTest(Long testid, Long shopid){
    	if(testid==null||shopid==null)
    		return 0;
    	Testuser vo = new Testuser();
    	vo.setTestid(testid);
    	vo.setShopid(shopid);
		int rows = super.delete("Testuser.deleteByTest", vo);
		
		// pub flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", TestChangeCacheType, testid.toString());
		return rows;
    }

    
	/**
     * insertBatch records of List, and update their orgbase
     */
    public int insertBatch(List list){
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
       	rows = super.insertBatch("Testuser.insert", list);
       	// 更新插入user的orgbaseID
       	if(rows>0){
       		StringBuffer buffer = new StringBuffer();
       		String useridStr;
       		Testuser testuserVO = null;
       		TestuserQuery queryVO = new TestuserQuery();
       	    for(int i=0;i<list.size();i++){
       	    	testuserVO = (Testuser)list.get(i);
       	    	if(i==0){
       	    		queryVO.setTestid(testuserVO.getTestid());
       	    		queryVO.setShopid(testuserVO.getShopid());
       	    	}
       		    buffer.append(testuserVO.getUserid()).append(",");
       	    }
       	    useridStr = buffer.substring(0, buffer.length()-1);
       	    queryVO.setUserIdstr(useridStr);
       	    super.update("Testuser.updateOrgbase", queryVO);
       	}
       	return rows;
    }
    
    /**
     * deleteBatch records by the String Array of PK
     */
    public int deleteBatchByPK(String[] pkArray){
    	if(pkArray==null||pkArray.length<=0)
    		return 0;
    	int rows = 0;
    	Long[] arrs = new Long[pkArray.length];
		for(int i=0;i<pkArray.length;i++){
			if(pkArray[i]!=null)
				arrs[i] = new Long(Long.parseLong(pkArray[i]));
		}
		rows = super.deleteBatch("Testuser.deleteByPK", arrs);
		
		CacheSynchronizer.getInstance().pubFlushBatch("netTest.testinfoCache", Testuser.ObjectType, pkArray);
		return rows;
    }
    
    /**
     * 统计考生的成绩，并且更新考生的分数
     * @param testid
     * @param paperid
     * @param queschecktype: 评阅类型，1自动评阅，null 代表 手动判题和 自动手动判题 的两种情况
     * @return
     */
    public List<Testuser> sumUserScore(Long testid,Long paperid,Short queschecktype){
    	if(testid==null||paperid==null)
    		return null;
    	UseranswerQuery queryVO = new UseranswerQuery();
    	queryVO.setTestid(testid);
    	queryVO.setPaperid(paperid);
    	queryVO.setQueschecktype(queschecktype);
    	String sql = "Testuser.sumUserScore1";
    	if(!QuestionConstant.Queschecktype_auto.equals(queschecktype)){
    		sql = "Testuser.sumUserScore2";
    		queryVO.setQueschecktype(null); // 代表查询手动判题和 自动手动判题 的两种情况
    	}
    	List<Testuser> list = this.queryForList(sql, queryVO);
    	return list;
    }
    
    /**
     * 考试的时候更新用户的答案。仅仅更新答案和分数的信息
     * @param list
     * @return
     */
    public int updBatchUserScore(List<Testuser> list){
    	int rows = 0;
    	if(list!=null&&list.size()>0){
    		Testuser vo = null;
    		for(int i=list.size()-1;i>-1;i--){
    			vo = list.get(i);
    			if(vo.getObjectscore()==null && vo.getTotalscore()==null
    			   && vo.getIsqualify()==null && vo.getOrdernodept()==null
    			   && vo.getOrdernoall()==null){
    				list.remove(i);
    			}
    			// pub flush cache
    			CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", Testuser.ObjectType, vo.getTestuserid().toString());
    		}
    		rows = super.updateBatch("Testuser.updUserScore", list);
    	}
    	return rows;
    }
    
    /**
     * 设置一场考试中考试及格。其中用了Map作为参数，需要验证
     * @param testid
     * @param paperquascore
     * @return
     */
    public int setUserQualify(Long testid,Float paperquascore){
    	if(testid==null)
    		return 0;
    	Testuser uservo = new Testuser();
    	uservo.setTestid(testid);
    	Map map = new HashMap();
    	map.put("testid", testid);
    	map.put("paperquascore", paperquascore);
    	int rows = super.update("Testuser.updUserQualify", map);
    	// pub flush cache
    	CacheSynchronizer.getInstance().pubFlush("netTest.testinfoCache", TestChangeCacheType, testid.toString());
    	return rows;
    }
    
    /**
     * static spring getMethod
     */
     public static TestuserDao getInstance(){
       	 TestuserDao dao = (TestuserDao)BeanFactory.getBeanFactory().getBean("testuserDao");
         return dao;
     }
    
}

package netTest.prodcont.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netTest.bean.BeanFactory;
import netTest.prodcont.dao.ContentcateDao;
import netTest.prodcont.dto.ContentcateQuery;
import netTest.prodcont.vo.Contentcate;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import commonTool.base.BaseDao;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;

public class ContentcateDaoImpl extends BaseDao implements ContentcateDao {

	static Logger log = Logger.getLogger(ContentcateDaoImpl.class.getName());
	
	public final static String ProductChangeCacheType = "ContentcateChangeCacheType";
	
    /**
     *
     */
    public ContentcateDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    @Cacheable(value="netTest.productCache", key="'ContentcateDao.selectByPK~'+#pk", unless="#result==null")
    public Contentcate selectByPK(Long pk){
    	if(pk==null)
    		return null;
		Contentcate record = (Contentcate) this.queryForObject("Contentcate.selectByPK", pk);
		
		if(record!=null){
		   CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
				"ContentcateDao.selectByPK~"+pk, 
				new String[]{ProductChangeCacheType+":"+record.getProductbaseid()});
		}
		return record;
    }
    
    /**
     * select some record by PK Array
     */
    public List<Contentcate> selectByPKArr(Long[] pkArr){
    	if(pkArr==null||pkArr.length==0)
    		return null;
    	List<Contentcate> list = new ArrayList<Contentcate>();
    	Contentcate vo = null;
    	for(Long pk : pkArr){
    		vo = getInstance().selectByPK(pk);
    		if(vo!=null){
    		   list.add(vo);	
    		}
    	}
    	return list;
    }
           
    /**
     * select records by queryVO
     */
    public List selectByVO(ContentcateQuery queryVO){
		if(queryVO==null)
			return new ArrayList();
		List list = this.queryForList("Contentcate.selectByVO", queryVO);
		return list;
    }
    
    /**
     * 查询pid对应对象的下一级目录
     * @param pid
     * @return
     */
    //@Cacheable(value="netTest.productCache", key="'ContentcateDao.getChildNodes~'+#productbaseid+'~'+#pid", unless="#result==null")
    public List<Contentcate> getChildNodes(Long productbaseid,Long pid){
//    	List<Contentcate> list = null;
//    	if(pid==null)
//    		list = new ArrayList<Contentcate>();
//    	ContentcateQuery queryVO = new ContentcateQuery();
//    	queryVO.setProductbaseid(productbaseid);
//    	queryVO.setPid(pid);
//    	list = (List<Contentcate>)this.queryForList("Contentcate.qryChildNodes", queryVO);
//    	
//    	if(list!=null && list.size()>0){
// 		   CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
// 				"ContentcateDao.getChildNodes~"+productbaseid+"~"+pid, 
// 				new String[]{Contentcate.ObjectType+":"+pid, ProductChangeCacheType+":"+productbaseid.toString()});
// 		}
    	// 使用递归方式查找
    	if(productbaseid==null){
    		return null;
    	}
    	
    	List<Contentcate> rtnlist = getInstance().getAllInOne(productbaseid);
    	if(CommonConstant.TreeTopnodePid.equals(pid)){
    		return rtnlist;
    	}else {
    		List<Contentcate> temlist = null;
    		for(Contentcate vo : rtnlist){
    			temlist = loopGetSubList(pid, vo);
    			if(temlist!=null){
    				break;
    			}
    		}
    		return temlist;
    	}
    }
    
    // 递归寻找该id对应对象的下级List
    private List<Contentcate> loopGetSubList(Long id, Contentcate vo){
    	if(id==null||vo==null){
    		return null;
    	}
    	List<Contentcate> retlist = null;
    	if(vo.getPk().equals(id)){
    		retlist = vo.getSublist();
    		// retlist用来判断是否匹配上了，如果为Null表示没有匹配上，如果不为空表示匹配上了
    		// 此处匹配上了，如果是Null，则返回Empty List, 用以通知上层已经匹配上了
    		if(retlist==null){
    			retlist = new ArrayList<Contentcate>();
    		}
		}else if(vo.getSublist()!=null && vo.getSublist().size()>0){
   			for(Contentcate temvo : vo.getSublist()){
   				retlist = loopGetSubList(id, temvo);
   				if(retlist!=null){
   					break;
   				}
   			}

    	}
    	return retlist;
    }
    
    
    /**
     * 查询整个产品的所有目录，并把目录按父子关系组成一个list, 放到每个vo中，返回最上层vo
     */
    @Cacheable(value="netTest.productCache", key="'ContentcateDao.getAllInOne~'+#productbaseid", unless="#result==null")
    public List<Contentcate> getAllInOne(Long productbaseid){
    	if(productbaseid==null){
    		return null;
    	}
    	List<Contentcate> list = (List<Contentcate>)this.queryForList("Contentcate.selectAllCateByProd", productbaseid);
    	Contentcate vo = null;
    	List<Contentcate> firstLayerList = null;
    	if(list!=null && list.size()>0){
    		firstLayerList = new ArrayList<Contentcate>();
   			
			Map<Long, List<Contentcate>> tempMap = new HashMap<Long, List<Contentcate>>();
			tempMap.put(CommonConstant.TreeTopnodePid, firstLayerList);
			// put all same parent vo into one list, then put into map
			for(Contentcate temvo : list){
				if(tempMap.get(temvo.getPid())!=null){
					tempMap.get(temvo.getPid()).add(temvo);
				}else {
					List<Contentcate> tempSubList = new ArrayList<Contentcate>();
					tempSubList.add(temvo);
					tempMap.put(temvo.getPid(), tempSubList);
				}
    		}
			// set sublist of each vo
			List<Contentcate> tempSubList2 = null;
			for(Contentcate temvo2 : list){
				tempSubList2 = tempMap.get(temvo2.getPk());
				if(tempSubList2!=null){
					temvo2.setSublist(tempSubList2);
					temvo2.setChildnum(tempSubList2.size());
				}else {
					temvo2.setChildnum(0);
				}
			}
    		
    	}
    	
    	CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
 				"ContentcateDao.getAllInOne~"+productbaseid, 
 				new String[]{ProductChangeCacheType+":"+productbaseid.toString()});
    	
    	return firstLayerList;
    }
    
    /**
     * 查询一个目录中learncontent的数量
     * @param productid
     * @param contentcateid
     * @return
     */
    public int getLearnCountInCate(Long productid, Long contentcateid){
    	if(productid==null || contentcateid==null){
    		return 0;
    	}
    	ContentcateQuery queryVO = new ContentcateQuery();
    	queryVO.setProductbaseid(productid);
    	queryVO.setContentcateid(contentcateid);
    	Integer number = (Integer)this.queryForObject("Contentcate.getLearnCountInCate", queryVO);
    	if(number==null){
    		number = 0;
    	}
		return number;
    }
    
    
    /**
     * 查询课程目录中没有学习资料的章节目录. 当课程没有章节的时候返回也是null
     * @return 章节目录id list
     */
    public List<Contentcate> getCateNotExistInLearncontent(Long productid){
		if(productid==null)
			return null;
		List<Contentcate> list = (List<Contentcate>)this.queryForList("Contentcate.getCateNotExistInLearncontent", productid);
		return list;
    }
    
    
    /**
     * insert a record,and set path also: parentPath,pk
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Contentcate record){
    	if(record==null)
    		return null;
    	if(record.getPid()==null){
    		record.setPid(CommonConstant.TreeTopnodePid);
    		record.setPath("");
    	}
		Long pk = (Long)super.insert("Contentcate.insert", record);
		String path = "";
		if(CommonConstant.TreeTopnodePid.equals(record.getPid())){
			path = String.valueOf(pk);
		}else{
			path = record.getPath()+","+String.valueOf(pk);
		}
		record.setPath(path);
		// update current path, also flush parent node query cache
		this.updateByPK(record);
		return pk;
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Contentcate record){
    	if(record==null||record.getContentcateid()==null)
    		return 0;
    	int rows = super.update("Contentcate.updateByPK", record);
		Long pid = record.getPid();
    	if(record.getPid()==null){
    		record = getInstance().selectByPK(record.getContentcateid());
    		pid = record.getPid();
		}
    	
    	CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProductChangeCacheType, record.getProductbaseid().toString());

		return rows;
    }

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Contentcate save(Contentcate record){
    	if(record==null)
    		return null;
		if(record.getContentcateid()==null||record.getContentcateid().intValue()==0){
			Long pkValue = this.insert(record);
			record.setContentcateid(pkValue);
    		return record;
		}else{
			this.updateByPK(record);
			return record;
		}
    }

//    /**
//     * delete a record by PK,同时删除下级
//     */
//    public int deleteByPKAll(Long pk){
//    	if(pk==null)
//    		return 0;
//    	String pathlike = String.valueOf(pk)+".";
//		int rows = super.delete("Contentcate.deleteByPkPath", pathlike);
//		return rows;
//    }
    
	 /**
	  * delete a record by PK
	  */
	 public int deleteByPK(Long pk){
	 	if(pk==null)
	 		return 0;
	 	int count = 0;
	 	Contentcate vo = getInstance().selectByPK(pk);
	 	if(vo!=null){
	 		super.delete("Contentcate.deleteByPK", pk);
	 		count = 1;
	 		
	 		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProductChangeCacheType, vo.getProductbaseid().toString());
	 	}
	 	return count;
	 }
    
    /**
     * delete records by product
     */
    public int deleteByProd(Long productid){
    	if(productid==null)
    		return 0;
		int rows = super.delete("Contentcate.deleteByProd",productid);
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProductChangeCacheType, productid.toString());
		return rows;
    }
    
        
    /**
     * deleteBatch records by the String Array of PK
     */
//    public int deleteBatchByPK(List<Long> pkList){
//    	if(pkList==null||pkList.size()<1)
//    		return 0;
//    	int rows = 0;
//    	int temcount = 0;
//    	for(Long pk : pkList){
//    		temcount = deleteByPK(pk);
//    		if(temcount==1){
//    			rows++;
//    		}
//    	}
//		//rows = super.deleteBatch("Contentcate.deleteByPK", pkList);
//		return rows;
//    }
    
    /**
     * static spring getMethod
     */
     public static ContentcateDao getInstance(){
       	 ContentcateDao dao = (ContentcateDao)BeanFactory.getBeanFactory().getBean("contentcateDao");
         return dao;
     }
    
}

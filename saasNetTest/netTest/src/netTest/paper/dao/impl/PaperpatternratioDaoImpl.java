package netTest.paper.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import commonTool.base.BaseDao;
import commonTool.base.Page;
import netTest.paper.dao.PaperpatternratioDao;
import commonTool.constant.CommonConstant;
import netTest.paper.vo.Paperpatternratio;
import netTest.paper.dto.PaperpatternratioQuery;
import netTest.bean.BeanFactory;

public class PaperpatternratioDaoImpl extends BaseDao implements PaperpatternratioDao {

	static Logger log = Logger.getLogger(PaperpatternratioDaoImpl.class.getName());
	
    /**
     *
     */
    public PaperpatternratioDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    public Paperpatternratio selectByPK(Long pk){
    	if(pk==null)
    		return null;
		Paperpatternratio record = (Paperpatternratio) this.queryForObject("Paperpatternratio.selectByPK", pk);
		return record;
    }
           
    /**
     * select records by queryVO
     */
    public List selectByVO(PaperpatternratioQuery queryVO){
		if(queryVO==null)
			return new ArrayList();
		List list = this.queryForList("Paperpatternratio.selectByVO", queryVO);
		return list;
    }
    
    /**
     * select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(PaperpatternratioQuery queryVO,int pageIndex,int pageSize,Integer total){
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        Page page = null;
        String sqlStr = "";
        if(queryVO==null){
        	page = Page.EMPTY_PAGE;
        } else {
        	sqlStr = "Paperpatternratio.selectByVO";
        	page = queryForPage(sqlStr, queryVO, pageIndex, pageSize, total);
        }
        return page;
    }
       
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Paperpatternratio record){
    	if(record==null)
    		return null;
		return (Long)super.insert("Paperpatternratio.insert", record);
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Paperpatternratio record){
    	if(record==null||record.getElementid()==null)
    		return 0;
		int rows = super.update("Paperpatternratio.updateByPK", record);
		return rows;
    }

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Paperpatternratio save(Paperpatternratio record){
    	if(record==null)
    		return null;
		if(record.getElementid()==null||record.getElementid().intValue()==0){
			Long pkValue = this.insert(record);
			record.setElementid(pkValue);
    		return record;
		}else{
			this.updateByPK(record);
			return record;
		}
    }

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long paperid,Long questypeid,Long elementid,Short elementtype){
    	if(paperid==null||questypeid==null||elementid==null||elementtype==null)
    		return 0;
    	Paperpatternratio vo = new Paperpatternratio();
    	vo.setPaperid(paperid);
    	vo.setQuestypeid(questypeid);
    	vo.setElementid(elementid);
    	vo.setElementtype(elementtype);
		int rows = super.delete("Paperpatternratio.deleteByPK", vo);
		return rows;
    }
    
    /**
     * delete a record by Paperques pattern
     */
    public int deleteByQuesPattern(Long paperid,Long questypeid){
    	if(paperid==null||questypeid==null)
    		return 0;
    	Paperpatternratio vo = new Paperpatternratio();
    	vo.setPaperid(paperid);
    	vo.setQuestypeid(questypeid);
		int rows = super.delete("Paperpatternratio.deleteByQuesPattern", vo);
		return rows;
    }
    
    /**
     * delete a record by Paperid
     */
    public int delByPaperid(Long paperid){
    	if(paperid==null)
    		return 0;
		int rows = super.delete("Paperpatternratio.deleteByPaper", paperid);
		return rows;
    }
    
	/**
     * insertBatch records of List
     */
    public int insertBatch(List list){
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
       	rows = super.insertBatch("Paperpatternratio.insert", list);
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
		rows = super.deleteBatch("Paperpatternratio.deleteByPK", arrs);
		return rows;
    }
    
    /**
     * static spring getMethod
     */
     public static PaperpatternratioDao getInstance(){
       	 PaperpatternratioDao dao = (PaperpatternratioDao)BeanFactory.getBeanFactory().getBean("paperpatternratioDao");
         return dao;
     }
    
}
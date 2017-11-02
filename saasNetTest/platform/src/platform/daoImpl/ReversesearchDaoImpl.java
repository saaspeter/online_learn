package platform.daoImpl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import commonTool.base.BaseDao;
import platform.bean.BeanFactory;
import platform.dao.ReversesearchDao;
import platform.vo.Reversesearch;

public class ReversesearchDaoImpl extends BaseDao implements ReversesearchDao {

	static Logger log = Logger.getLogger(ReversesearchDaoImpl.class.getName());
	
    /**
     *
     */
    public ReversesearchDaoImpl() {
        super();
    }
           
    /**
     * select records by search text
     */
    public List<Long> selectByText(String searchtext, String entitytype){
		if(searchtext==null||searchtext.trim().length()<1
				||entitytype==null||entitytype.trim().length()<1)
			return new ArrayList<Long>();
		Reversesearch vo = new Reversesearch();
		vo.setEntitytype(entitytype);
		vo.setSearchtext(searchtext);
		List<Long> list = (List<Long>)this.queryForList("Reversesearch.selectByText", vo);
		return list;
    }
       
    /**
     * insert a record
     */
    public void insert(Reversesearch record){
    	if(record==null)
    		return;
		super.insert("Reversesearch.insert", record);
    }
    
	/**
     * insertBatch records of List
     */
    public int insertBatch(List<Reversesearch> list){
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
       	rows = super.insertBatch("Reversesearch.insert", list);
       	return rows;
    }

    /**
     * delete a record by search text
     */
    public int deleteByText(String searchtext, String entitytype){
    	if(searchtext==null||searchtext.trim().length()<1
				||entitytype==null||entitytype.trim().length()<1)
    		return 0;
    	Reversesearch vo = new Reversesearch();
		vo.setEntitytype(entitytype);
		vo.setSearchtext(searchtext);
		int rows = super.delete("Reversesearch.deleteByText", vo);
		return rows;
    }

    /**
     * delete a record by entity
     */
    public int deleteByEntity(Long entityID, String entitytype){
    	if(entityID==null
				||entitytype==null||entitytype.trim().length()<1)
    		return 0;
    	Reversesearch vo = new Reversesearch();
		vo.setEntitytype(entitytype);
		vo.setEntityid(entityID);
		int rows = super.delete("Reversesearch.deleteByEntity", vo);
		return rows;
    }
    
    /**
     * static spring getMethod
     */
     public static ReversesearchDao getInstance(){
       	 ReversesearchDao dao = (ReversesearchDao)BeanFactory.getBeanFactory().getBean("reversesearchDao");
         return dao;
     }
    
}

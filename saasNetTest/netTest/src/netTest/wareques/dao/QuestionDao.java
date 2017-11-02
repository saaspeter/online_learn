package netTest.wareques.dao;

import java.util.List;
import java.util.Map;

import netTest.wareques.dto.QuestionQuery;
import netTest.wareques.vo.Question;
import commonTool.base.Page;

public interface QuestionDao {
   
	/**
     * select some record by PK
     */
    public Question selectByPK(Long pk);
    
    /**
     * 根据父题目id查询所有的子题目
     * @param pid
     * @return
     */
    public List<Question> selectByPid(Long pid);
    
    public Map<Long, Question> selectByPkStr(String selectByPkStr);
    
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Question record);

    /**
     * update a record By PK
     */
    public int updateByPK(Question record);
    
    /**
     * 更新问题的文件链接地址
     * @param quesid
     * @param fileurl
     * @return
     */
    public boolean updateFileurl(Long quesid,String fileurl);

	/**
     * update the record if exists pk, else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Question save(Question record);

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk);
    
    /**
     * 根据主问题id删除其下的子问题，包括Questionitem,Quesanswer,Question
     * @param pid
     * @return
     */
    public int delSubquesByPid(Long pid);

	/**
     * insertBatch records of List
     */
    public int insertBatch(List<Question> list);
    
    /**
     * deleteBatch records by the String Array of PK
     */
    public int deleteBatchByPK(String[] pkArray);
    
    /**
     * 更新题目的子题目个数quesOptNum字段
     * @param quesid
     * @param num
     * @return
     */
    public int updateQuesOptNum(Long quesid,int num);
	
    /**
     * operator select records by queryVO 
     */
    public List<Question> qryList(QuestionQuery queryVO);
    
    /**
     * operator select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page qryPage(QuestionQuery queryVO,int pageIndex,int pageSize,Integer total);
    
    /**
     * 根据product删除题目
     * @param productid
     * @return
     */
    public int deleteByProd(Long productid);

}
package netTest.exercise.dao;

import java.util.List;
import commonTool.base.Page;
import netTest.exercise.vo.Exerques;
import netTest.exercise.dto.ExerquesQuery;

public interface ExerquesDao {
   
    /**
     * select some record by PK
     */
	public Exerques selectByPK(Long pk);
            
    /**
     * select records by queryVO
     */
    public List selectByVO(ExerquesQuery queryVO);
    
    /**
     * select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(ExerquesQuery queryVO,int pageIndex,int pageSize,Integer total);
    
    /**
     * 根据练习id和问题类型查询试卷题目
     * @param papre
     * @param questypeid
     * @return
     */
    public List qryQuesByPatt(Long exerid,Integer questype);
    
    /**
     * 查询数目
     * @param queryVO
     * @return
     */
    public int countByVO(ExerquesQuery queryVO);
    
    /**
     * 根据题目id或题库id，删除练习中题目引用和用户练习答案中的引用
     * @param quesIdStr
     * @param wareid
     * @return
     */
    public void delExerByWareQues(String quesIdStr, Long wareid);
    
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Exerques record);

    /**
     * update a record By PK
     */
    public int updateByPK(Exerques record);

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Exerques save(Exerques record);

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk);
    
    /**
     * delete a record by question
     */
    public int deleteByQuesId(Long quesid);
    
    /**
     * 删除该次练习的所有题目
     */
    public int deleteByExer(Long exerid);
    
	/**
     * insertBatch records of List
     */
    public int insertBatch(List list);
    
    /**
     * deleteBatch records by the String Array of PK
     */
    public int deleteBatchByPK(String[] pkArray);
	
}
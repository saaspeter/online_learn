package netTest.wareques.dao;

import java.util.List;
import commonTool.base.Page;
import netTest.wareques.vo.Ware;
import netTest.wareques.dto.WareQuery;

public interface WareDao {
   
    /**
     * select some record by PK
     */
    public Ware selectByPK(Long pk);
            
    /**
     * 根据题库id字符串得到题库名字字符串
     * @param wareidStr
     * @return
     */
    public String qryNamesByIds(String wareidStr);
    
    /**
     * operator select records by queryVO
     */
    public List selectByVO(WareQuery queryVO);

    
    /**
     * operator select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(WareQuery queryVO,int pageIndex,int pageSize,Integer total);
    
    /**
     * 根据productid查询题库ware
     */
    public int delWareByProd(Long productid);
    
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Ware record);

    /**
     * update a record By PK
     */
    public int updateByPK(Ware record);
    
    /**
     * 更新一个题库中题目的数量
     */
    public void updateQuesNumByPK(Long wareid, long number);

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Ware save(Ware record);

    /**
     * 删除表Ware,如果表WareQuesRL中有外键，则会抛出异常
     */
    public int delWareByPK(Long pk);
    	
}

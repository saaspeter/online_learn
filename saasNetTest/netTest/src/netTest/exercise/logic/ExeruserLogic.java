package netTest.exercise.logic;

import java.util.List;
import java.util.Map;

import netTest.exercise.vo.Exerques;
import netTest.exercise.vo.Exeruser;
import netTest.exercise.vo.Userexeranswer;

public interface ExeruserLogic {

	/**
	 * 根据练习试题生成用户的用户答案列表
	 * @param testid
	 * @param userid
	 * @param shopid
	 * @param exerVO
	 * @return
	 */
	public Map<Long, Userexeranswer> initUserAnswer(Long userid, List<Exerques> quesList, boolean needupdatedb);
	
    /**
     * 查询出用户对某次练习答案
     * 返回Map,主键是练习题题目id(exerquesid),值是用户答案Userexeranswer.
     * 在该VO中包含了用户该种类型题目答案List集合。同时把子问题放入了父问题的subanswList里
     * @param shopid
     * @param userid
     * @param exerid
     * @return
     */
    public Map<Long, Userexeranswer> qryUserExerAnswer(Long shopid,Long userid,Long exerid, List<Exerques> quesList);
    
    /**
     * 根据exercise和user查询Exeruser对象
     */
    public Exeruser selectByExer(Long exerid, Long userid);
    
    /**
     * 检查用户的练习题答案中是否有没有评阅的试题，如果有则评阅试题并更新用户答案
     * @param quesList
     * @param usranwMap
     * @return 有多少之前没有评阅的试题
     */
    public int checkAllExerques(List<Exerques> quesList, Map<Long, Userexeranswer> usranwMap);
    
    /**
     * 更新用户的use exercise状态，并且发送消息到learn activity
     * @param vo
     * @param userid
     * @param exerid
     */
    public void updateUserExer(Exeruser vo, Long userid, Long exerid);
	
}

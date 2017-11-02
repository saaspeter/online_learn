package platform.user.logic.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;
import org.apache.log4j.Logger;
import platform.bean.BeanFactory;
import platform.logicImpl.BOFactory_Platform;
import platform.user.dao.AccountvalidatetaskDao;
import platform.user.dao.UseractivityDao;
import platform.user.logic.AccountvalidatetaskLogic;
import platform.user.vo.Accountvalidatetask;
import platform.user.vo.Useractivity;
import platform.vo.User;

import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;

public class AccountvalidatetaskLogicImpl implements AccountvalidatetaskLogic {

	static Logger log = Logger.getLogger(AccountvalidatetaskLogicImpl.class.getName());
	
	private AccountvalidatetaskDao accountvalidatetaskDao;
	
	private UseractivityDao useractivityDao;
	
	/**
	 * 生成active task
	 * @param userid
	 * @param email
	 * @param validatetype
	 * @return active code
	 */
	public String generateActiveTask(Long userid, String email,
			String validatetype, Integer aliveday) {
		if(email==null||email.trim().length()<1||validatetype==null){
			return null;
		}
		Date curDate = DateUtil.getInstance().getNowtime_GLNZ();
		// 7天内激活有效
		Date aliveDate = null;
		if(aliveday!=null) {
		   aliveDate = DateUtil.dateAddDays(curDate, 7);
		}
		Accountvalidatetask vo = new Accountvalidatetask();
		vo.setUserid(userid);
		vo.setEmail(email);
		vo.setCreatedate(curDate);
		vo.setAliveenddate(aliveDate);
		vo.setStatus(Accountvalidatetask.Status_Created);
		vo.setValidatetype(validatetype);
		// 生成code
		String activecode = generateActiveCode(email);
		vo.setValidatecode(activecode);
		accountvalidatetaskDao.insert(vo);
		
		return activecode;
	}
	
	private String generateActiveCode(String email){
		String uuid = UUID.randomUUID().toString();
		try {
			uuid = URLEncoder.encode(UUID.randomUUID().toString(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("error in AccountvalidatetaskLogicImpl ", e);
		}
		return uuid;
	}
	
    /**
     * 根据email更新用户密码，主要是为了忘记密码的case
     * @param oldpass
     * @param newpass
     * @param userid
     */
    public void saveResetPasswordByMail(String email, String newpass){
    	if(email==null||email.trim().length()<1||newpass==null){
    		return;
    	}
    	User vo = BOFactory_Platform.getUserDao().selectByEmail(email);
    	AssertUtil.assertNotNull(vo, null);
        // 用MD5加密密码
		newpass = StringUtil.md5_saltSource(newpass, vo.getLoginname());
		BOFactory_Platform.getUserDao().updatePassword(newpass, vo.getUserid());
		// insert user activity
		Useractivity activityVO = new Useractivity();
		activityVO.setActiontype(Useractivity.Actiontype_UpdateForgotPassword);
		activityVO.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
		activityVO.setResult(Useractivity.Result_SUCCESS);
		activityVO.setUserid(vo.getUserid());
		useractivityDao.insert(activityVO);
		// delete active data
		accountvalidatetaskDao.deleteUserTask(email, Accountvalidatetask.Validatetype_ForgetPassValidate, null);
    }
	

	public AccountvalidatetaskDao getAccountvalidatetaskDao() {
		return accountvalidatetaskDao;
	}

	public void setAccountvalidatetaskDao(
			AccountvalidatetaskDao accountvalidatetaskDao) {
		this.accountvalidatetaskDao = accountvalidatetaskDao;
	}
	
    public static AccountvalidatetaskLogic getInstance(){
    	AccountvalidatetaskLogic logic = (AccountvalidatetaskLogic)BeanFactory.getBeanFactory().getBean("accountvalidatetaskLogic");
        return logic;
    }

	public UseractivityDao getUseractivityDao() {
		return useractivityDao;
	}

	public void setUseractivityDao(UseractivityDao useractivityDao) {
		this.useractivityDao = useractivityDao;
	}

}

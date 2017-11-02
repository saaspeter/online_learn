package netTest.orguser.logic.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import netTest.bean.BOFactory;
import netTest.bean.BeanFactory;
import netTest.bean.SysparamConstantNettest;
import netTest.exception.ExceptionConstant;
import netTest.orguser.constant.OrguserConstant;
import netTest.orguser.dao.OrgbaseDao;
import netTest.orguser.dao.OrguserDao;
import netTest.orguser.dto.OrguserQuery;
import netTest.orguser.logic.OrguserLogic;
import netTest.orguser.vo.Orgbase;
import netTest.orguser.vo.Orguser;
import netTest.product.logic.UserproductLogic;
import netTest.product.logic.impl.UserproductLogicImpl;
import netTest.util.ResourceBundleUtil;
import org.apache.log4j.Logger;
import platform.constant.CustomerConstant;
import platform.constant.UsershopConstant;
import platform.dao.UsershopDao;
import platform.event.EventHandlerPlatform;
import platform.logic.UserLogic;
import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.logicImpl.UsershopLogicImpl;
import platform.vo.Usercontactinfo;
import platform.vo.Usershop;
import commonTool.base.Page;
import commonTool.biz.logic.ConstantInf;
import commonTool.biz.logicImpl.ConstantLogicImpl;
import commonTool.constant.CommonConstant;
import commonTool.event.Event;
import commonTool.exception.LogicException;
import commonTool.iface.IDoExcelRow;
import commonTool.util.DateUtil;
import commonTool.util.ExcelUtil;
import commonTool.util.ObjUtil;

/**
 * 
 * @author fan
 *
 */
public class OrguserLogicImpl implements OrguserLogic {
	
	static Logger log = Logger.getLogger(OrguserLogicImpl.class.getName());
	
	private OrgbaseDao orgDao;
	private OrguserDao orguserDao;

	private UsershopDao usershopdao;
	
	/**
	 * 根据userid和shopid得到orguser,如果user在单位中，则其中org信息不为空
	 * @param userid
	 * @param shopid
	 * @return
	 */
	public Orguser qryOrguserByUsershop(Long userid, Long shopid){
		Usershop usershopVO = BOFactory_Platform.getUsershopLogic().selectByLogicPK(shopid, userid);
		Orguser orguserVO = orguserDao.selectOrgForUser(userid, shopid);
		if(orguserVO==null){
			orguserVO = new Orguser();
		}
		ObjUtil.copyProperties(orguserVO, usershopVO);
		return orguserVO;
	}
	
	/**
	 * 查询机构的人员列表分页信息（可以查询学员和操作员），用于点击查询按钮时查询
	 * @param orgid：机构id
	 * @param includeChild:是否包含下级单位，1为不包括下级，2为包括下级
	 * @param order_By_Clause：排序规则，必须以order开头
	 * @param pageNO
	 * @param pageSize
	 * @return 的分页信息
	 * @throws Exception
	 */
	public Page qryOrguserPage(OrguserQuery queryVO,int pageNO,int pageSize,Integer total){
		if(queryVO==null)
			return null;
		Page page = null;
		//主要区别是机构所属人员需要知道每名人员的所属单位情况。	
		page = orguserDao.selectUserByVOPage(queryVO, pageNO, pageSize, total);
		return page;
	}
		
	/**
	 * 更新单位用户信息(操作员或用户的信息)
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Orguser updateUserDept(Orguser vo) {
		if(vo!=null){
			//orguserDao.updateByPK(vo);  // orguser表中没有需要更新的字段
			//IPlatform_Usershop iface = (IPlatform_Usershop)WSHelper.getInterface(IPlatform_Usershop.class, WSUrl.getUrl(WSUrl.platform_Usershop));
			//if(iface!=null)
			//	iface.update(vo);
			this.getUsershopLogic().update(vo);
		}
		return vo;
	}
	
	/**
	 * 新增或更新单位用户信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Orguser saveUserDept(Orguser vo) {
		if(vo!=null){
			if(vo.getOrguserid()==null)
				this.addOrguserDept(vo);
			else
				this.updateUserDept(vo);
		}
		return vo;
	}
	
	/**
	 * 把用户加入到该商店的默认单位中，如果该商店没有设置默认商店则返回失败
	 * 供平台系统调用
	 * @param shopid
	 * @param userid
	 * @return
	 */
	public boolean putUserIntoOrg(Long shopid,Long userid, Long orgid){
		if(shopid==null||userid==null||orgid==null)
		   return false;
		Orguser orguser = new Orguser();
		orguser.setShopid(shopid);
		orguser.setUserid(userid);
		orguser.setOrgbaseid(orgid);
		if(orguser.getOrgpath()==null || orguser.getOrgpath().trim().length()<1){
			Orgbase orgVO = orgDao.selectByPK(orguser.getOrgbaseid());
			orguser.setOrgpath(orgVO.getPath());
		}
		orguserDao.insert(orguser);
		// 更新usershop中的usershoptype
		orguser.setUsershoptype(UsershopConstant.Usershoptype_Orguser);
		usershopdao.updateByLogicPK(orguser);
		return true;
	}
		
	/**
	 * 新增单位人员信息，
	 * 需要在平台系统中新增用户信息，在商店人员中添加该人员信息，如果有人员使用产品的信息，需要添加用户产品表
	 * 把人员插入到orgbaseid单位中
	 */
	public Orguser addOrguserDept(Orguser orguser) 
	{
		if(orguser==null||orguser.getShopid()==null||orguser.getOrgbaseid()==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		// 在此调用platform中增加人员的逻辑，并且把人员添加到相应的商店中
		orguser.setDisplayname(orguser.getNickname());
		orguser.setJoindate(DateUtil.getInstance().getNowtime_GLNZ());
		orguser.setUsershoptype(UsershopConstant.Usershoptype_Orguser);
		orguser.setJoinway(UsershopConstant.JoinWay_shopadd);
		orguser.setAreainproduct(UsershopConstant.AreaInProduct_some);
		orguser.setUsershopstatus(UsershopConstant.UserShopStatus_active);
		orguser.setUsertype(CustomerConstant.UserType_user);
		orguser.setStagestatus(CustomerConstant.StageStatus_notSetLoginName);
		orguser.setStatus(CustomerConstant.CustomerStatus_register);
		
		Long userid = this.getUsershopLogic().addUserAndIntoShop(orguser);
		
		//IPlatform_Usershop iface = (IPlatform_Usershop)WSHelper.getInterface(IPlatform_Usershop.class, WSUrl.getUrl(WSUrl.platform_Usershop));
		//if(iface!=null)
		//	userid = iface.addUserInShop(orguser);
		   
		//TODO 调用平台接口逻辑把人员使用产品的关系记录到用户产品表中,
		// 暂时不做，即从考试系统中加入的用户可以使用该系统中的任何产品。
	   	//if(productbaseids!=null&&productbaseids.trim().length()>0){
	   		
	   	//}
		// 把人员添加到单位中
		if(userid!=null){
			orguser.setUserid(userid);	
			if(orguser.getOrgpath()==null || orguser.getOrgpath().trim().length()<1){
				Orgbase orgVO = orgDao.selectByPK(orguser.getOrgbaseid());
				orguser.setOrgpath(orgVO.getPath());
			}			
		   	Long orguserid = orguserDao.insert(orguser);
		   	orguser.setOrguserid(orguserid);

		}else
			orguser = null;
		
	   	return orguser;
	}
	
	
	/**
	 * // 目前一个用户只能在一个单位中，所以删除时把该用户从shop中直接删除(利用Event)
	 * 根据人员ID数组(userID)，删除所有人员信息和人员所在的单位信息，
	 * 如果人员已经设置了全局帐号信息，则不能把该人的信息(在平台系统中)删除，
	 * 因此需要先检查人员是否已经设置了全局帐号信息.
	 * 如果符合删除条件则其过程应该是，先把用户从单位小组中去除，
	 * 然后把人员在用户产品表中的数据删除，然后删除用户商店表中的数据，然后从平台人员信息中删除
	 * @param keys: userID的数组
	 * @return
	 */
	public int delUserDept(Long userid, Long shopid) 
	{
        int rows = 0;       		
		if(userid==null||shopid==null)
		{
           return rows;
		}
        // 检查用户是否还有正在使用的产品
		UserproductLogic usrprodlogic = UserproductLogicImpl.getInstance();
		String refResult = usrprodlogic.checkUserProdLink(userid, shopid);
		if("1".equals(refResult)){
			throw new LogicException(ExceptionConstant.Error_Record_BeenReference);
		}
		// TODO 删除用户在商店中的权限信息
			
		// 删除单位人员信息
		int rowsTemp = this.removeOrguserDept(userid,shopid);
		if(rowsTemp>0){
	        rows++;
	        // event处理: 删除UserShop 和 user本身(如果可以删除)
			Map paraMap = new HashMap();
			paraMap.put("handleType", UsershopLogicImpl.HandleType_DeleteUserInShop);
			paraMap.put("shopid", shopid);
			paraMap.put("useridStr", userid.toString());
			Event event = new Event(EventHandlerPlatform.EventType_UserShop_Handle, paraMap);
			EventHandlerPlatform.getInstance().publishEvent(event, EventHandlerPlatform.HandleType_Asyschronized_Thread);
		}
        return rows;
	}
	
	/**
	 * 然后从单位人员中删除，然后从商店人员中删除
	 * TODO 需要根据用户在商店中的消费等情况、学习考试情况判断能否移除该用户
	 * 此处的移除不会把人员从人员商店和用户产品表中删除
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	private int removeOrguserDept(Long userid,Long shopid){
		int rows = 0;
		//TODO 查看人员是否满足要删除的条件，如果不满足则返回
		//TODO 备份人员的业务数据，学习考试记录
		//TODO 删除人员在该单位的业务数据,包括考试学习数据等
		// 删除人员小组信息和人员单位信息
		rows = orguserDao.delOrguser(userid, shopid);
		// delete user role in shop
		commonWeb.security.logic.BOFactory.getUserRoleDao().deleteUserRoleInShop(userid, shopid, CommonConstant.SysCode_Education);
		return rows;
	}
	
	/**
	 * 人员从一个单位调到另一个单位,//TODO 检查人员是否有业务上的限制，例如人员正在考试，此时应该不允许转单位
	 * @param userid
	 * @param oldDeptid
	 * @param newDeptid
	 * @return
	 */
	public int transferDept(Long userid,Long oldDeptid,Long newDeptid){
		if(userid==null||newDeptid==null)
		    return -1;
		if(newDeptid.equals(oldDeptid))
			return 0;
		Orgbase orgVO = orgDao.selectByPK(newDeptid);
		int rows = orguserDao.updateDept(userid, oldDeptid, newDeptid, orgVO.getPath());
		return rows;
	}
	
    /**
     * 查询user对应的org信息, 为dwr使用
     */
    public Orguser selectOrgForUser(Long userid, Long shopid){
    	if(userid==null || shopid==null)
    		return null;
    	return orguserDao.selectOrgForUser(userid, shopid);
    }

    private static final String[] orguserExcelColumn = 
    	       new String[]{"nickname","email","telephone","gendername"};
    
    /**
     * 批量导入orguser
     * @param input
     * @param shopid
     * @param shopcode
     * @param vo
     * @return
     */
	public List<String> importOrguserFromExcel(InputStream input,Long shopid, 
							String shopcode, Orguser vo, Locale locale) {
		if(input==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);		
        String successNumber = "0";
        Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
		Map map = new HashMap();
        map.put("shopid",shopid);
        map.put("localeid",vo.getLocaleid());
        map.put("orgbaseid",vo.getOrgbaseid());
        map.put("timezone", vo.getTimezone());
        map.put("shopcode", shopcode);
	    // 
        ExcelUtil helper = new ExcelUtil(input, SysparamConstantNettest.FileImportUser_maxNumLimit,
        								 new OrguserimportRow(), locale);
        List list = helper.readExcel("netTest.orguser.vo.Orguser",1,orguserExcelColumn,map);
        // 保存, TODO 以后要改成批量插入db
        OrguserLogic logic = BOFactory.getOrguserLogic();
        if(list!=null&&list.size()>0){
        	Orguser votemp = null;
            for(int i=0; i<list.size(); i++){
            	votemp = (Orguser)list.get(i);
            	logic.saveUserDept(votemp);
            }
            successNumber = String.valueOf(list.size());
        }
        List<String> messageList = helper.getMessageList();
        messageList.add(successNumber);
        return messageList;
	}
	
	private class OrguserimportRow implements IDoExcelRow{
		@Override
		public List doChildColumn(Object obj, List list, String columnName,
				Object cellValue) {
			return null;
		}

		@Override
		public void setChildProperty(Object obj, List list) { }

		@Override
		public Object transRow(Object obj) {
			if(obj==null)
	    		return null;
			Orguser vo = (Orguser)obj;
			// check the nickname format
			boolean isformatok = OrguserConstant.checkUserShopNameFormat(vo.getNickname());
			if(!isformatok){
				throw new LogicException(ExceptionConstant.Error_StringFormat);
			}
			// check telephone format
			isformatok = OrguserConstant.checkTelephoneFormat(vo.getTelephone());
			if(!isformatok){
				throw new LogicException(ExceptionConstant.Error_StringFormat);
			}
			if(vo.getTelephone()!=null && !"".equals(vo.getTelephone())){
				Usercontactinfo contactinfo = new Usercontactinfo();
				contactinfo.setTelephone(vo.getTelephone());
				vo.setContactinfo(contactinfo);
			}
			String genderName = vo.getGendername();
			ConstantInf inf = ConstantLogicImpl.getInstance();
			String genderStr = inf.getValue(CustomerConstant.Gender_ConstCode, genderName);
			if(genderStr!=null&&genderStr.trim().length()>0){
				Short gender = Short.parseShort(genderStr);
				vo.setGender(gender);
			}
			//
			UserLogic userLogic = BOFactory_Platform.getUserLogic();
			String loginname = userLogic.geneShopUserLoginName(vo.getNickname(), vo.getShopcode(), CommonConstant.SysCode_Education);
            // check whether loginname is valid
            boolean checkresult = userLogic.isExistsLoginname(loginname);
            if(checkresult){
            	throw new LogicException(platform.exception.ExceptionConstant.Error_UserLoginname_alreadyExist);
            }
            vo.setLoginname(loginname);
            // check email
            checkresult = userLogic.isExistsEmail(vo.getEmail());
            if(checkresult){
            	throw new LogicException(platform.exception.ExceptionConstant.Error_Email_alreadyExist);
            }
			
			return vo;
		}

		@Override
		public String showRowErrorInfo(Object obj, Exception e, Locale locale) {
			String mess = "";
			if(obj!=null){
				mess = ((Orguser)obj).getNickname();
				if(e instanceof LogicException){
					String errormess = platform.util.ResourceBundleUtil.getInstance().getValue(e.getMessage(), locale);
					if(errormess==null||"".equals(errormess)){
						errormess = ResourceBundleUtil.getInstance().getValue(e.getMessage(), locale);
					}
					mess += " -- " + errormess;
				}else {
					mess += " -- "+e.getMessage();
				}
			}
			return mess;
		}
		
	}

    /**
     * static spring getMethod
     */
     public static OrguserLogic getInstance() {
       	 OrguserLogic logic = (OrguserLogic)BeanFactory.getBeanFactory().getBean("orguserLogic");
         return logic;
     }

	public OrguserDao getOrguserDao() {
		return orguserDao;
	}

	public void setOrguserDao(OrguserDao orguserDao) {
		this.orguserDao = orguserDao;
	}

	public UsershopLogic getUsershopLogic() {
		return BOFactory_Platform.getUsershopLogic();
	}

	public UsershopDao getUsershopdao() {
		return usershopdao;
	}

	public void setUsershopdao(UsershopDao usershopdao) {
		this.usershopdao = usershopdao;
	}

	public OrgbaseDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(OrgbaseDao orgDao) {
		this.orgDao = orgDao;
	}

	
}

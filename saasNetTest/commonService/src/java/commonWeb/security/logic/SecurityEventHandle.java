package commonWeb.security.logic;

import java.util.Map;

import org.apache.log4j.Logger;

import platform.event.EventHandlerPlatform;

import commonTool.event.Event;
import commonTool.event.EventHandle;
import commonWeb.security.constant.RolesConstant;
import commonWeb.security.constant.UserRolesConstant;
import commonWeb.security.dao.RolesDao;
import commonWeb.security.dao.UserRoleDao;
import commonWeb.security.vo.UserRole;

public class SecurityEventHandle implements EventHandle{
	
	private static SecurityEventHandle instance;
	
	static Logger log = Logger.getLogger(SecurityEventHandle.class.getName());
	
	private SecurityEventHandle(){};
	

	// 处理别的系统需要处理的用户权限方面的case
	public void onEvent(Event event) {
		Map paraMap = event.getParaMap();
		String eventType = event.getEventType();
		try {
			if(EventHandlerPlatform.EventType_Shop_ApprovePass.equals(eventType)){ 
				// 批准新建商店的消息，为商店的creator创建角色
				Long shopid = (Long)paraMap.get("shopid");
				Long userid = (Long)paraMap.get("userid");
				String syscode = (String)paraMap.get("syscode");
				RolesDao roleDao = BOFactory.getRolesDao();
				String roleidStr = roleDao.selRoleidStrByCode(new String[]{RolesConstant.ROLE_ShopCreator}, syscode);
				UserRole vo = new UserRole();
				vo.setShopid(shopid);
				vo.setUserid(userid);
				vo.setSyscode(syscode);
				vo.setUsetype(UserRolesConstant.UseType_useAssign);
                vo.setRoleid(new Long(roleidStr));
                UserRoleDao userroleDao = BOFactory.getUserRoleDao();
                userroleDao.insert(vo);
			}
		} catch (Exception e) {
			log.error("SecurityEventHandle方法onEvent时出错误, eventType:"+eventType+"|paraMap:"+paraMap, e);
		}
		
	}
	
	public static SecurityEventHandle getInstance(){
		if(instance==null){
			instance = new SecurityEventHandle();
		}
		return instance;
	}
	
}

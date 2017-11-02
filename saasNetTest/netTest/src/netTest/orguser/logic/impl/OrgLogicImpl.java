package netTest.orguser.logic.impl;

import java.util.List;
import java.util.Map;

import netTest.bean.BeanFactory;
import netTest.orguser.constant.DeptinfoConstant;
import netTest.orguser.constant.OrgbaseConstant;
import netTest.orguser.dao.DeptinfoDao;
import netTest.orguser.dao.OrgbaseDao;
import netTest.orguser.dto.DeptinfoQuery;
import netTest.orguser.dto.OrgbaseQuery;
import netTest.orguser.logic.OrgLogic;
import netTest.orguser.vo.Deptinfo;
import netTest.orguser.vo.Orgbase;

import org.apache.log4j.Logger;

import platform.event.EventHandlerPlatform;

import commonTool.event.Event;
import commonTool.event.EventHandle;
import commonTool.util.DateUtil;

public class OrgLogicImpl implements OrgLogic, EventHandle {

	static Logger log = Logger.getLogger(OrgLogicImpl.class.getName());

	DeptinfoDao deptinfoDao;
	OrgbaseDao orgbaseDao;
	
	/* (non-Javadoc)
	 * 得到单位信息，这里的信息包括了单位的所有信息（上级单位名称）
	 */
	public Deptinfo getDeptinfoByPK(Long pk){
		if(pk==null||pk.longValue()<0)
			return null;
		Deptinfo vo = deptinfoDao.selectByPK(pk);
		Long pid = vo.getPid();
		if(pid!=null&&pid>0){
			Orgbase org = orgbaseDao.selectByPK(pid);
			if(org!=null&&org.getOrgname()!=null)
				vo.setPidName(org.getOrgname());
		}
		return vo;
	}
		
	/**
	 * 根据shopid查找其对应的顶层单位
	 * @param shopid
	 * @return
	 */
	public Deptinfo qryTopDept(Long shopid){
		if(shopid==null)
			return null;
		DeptinfoQuery query = new DeptinfoQuery();
		query.setShopid(shopid);
		query.setPid(OrgbaseConstant.OrgPidTop);
		List list = deptinfoDao.selectByVO(query);
		if(list!=null&&list.size()>0)
			return (Deptinfo)list.get(0);
		else return null;
	}
	
	/**
	 * 生成形式如下的xml文件：
	  <?xml version="1.0"?>
		<tree>
			<tree text="Loads tree2.xml" src="tree2.xml"/>
			<tree text="Loads NOT_AVAILABLE.xml" src="NOT_AVAILABLE.xml"
				icon="images/xp/folder.png"/>
			<tree text="Loads emptytree.xml" src="emptytree.xml"
				icon="images/xp/folder.png"/>
			<tree text="Loaded Item 3" action="javascript:action('3','name')" />
			<tree text="Loaded Item 4" action="javascript:action('4','name')">
				<tree text="Loaded Item 4.1" action="javascript:action('4.1','name')" />
				<tree text="Loaded Item 4.2">
					<tree text="Loaded Item 4.2.1">
						<tree text="Loaded Item 4.2.1.2"/>
						<tree text="Loaded Item 4.2.1.3"/>
					</tree>
					<tree text="Loaded Item 4.2.2"/>
				</tree>
			</tree>
			<tree text="WebFX Home" action="http://webfx.eae.net"
				icon="http://webfx.eae.net/images/favicon.gif"/>
	    </tree>
	 * @param pid:需要查看树的id,即要查询pid下级的数据
	 * @param action：要执行的js函数
	 * @param url：生成树的链接，即点击展开文件夹执行的生成树的命令
	 * @param target
	 * @param localeid
	 * @return
	 * @throws Exception
	 */
	public String createOrgTreeXml(Long pid,Long shopid,String action,
			String url,String target) throws Exception
	{
		if(pid==null||pid.longValue()<0)
    		return "";
        if(action!=null&&action.trim().length()==0){
        	action = null;
        }
		
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("<tree>").append("\n");
    	try {
    		OrgbaseQuery queryVO = new OrgbaseQuery();
    		Orgbase temp;
    		queryVO.setPid(pid);
    		queryVO.setShopid(shopid);
	    	List list = orgbaseDao.selectByVOChildnum(queryVO);
	    	if(list!=null&&list.size()>0)
	    	for(int i=0;i<list.size();i++){
	    		temp = (Orgbase)list.get(i);
	    		if(temp==null)
	    			continue;
	    		buffer.append("<tree pk=\"").append(temp.getOrgbaseid()).append("\" ");
	    		buffer.append("text=\"").append(temp.getOrgname()).append("\" ");
	    		if(action!=null)
	    		    buffer.append("action=\"javascript:").append(action).append("('").append(temp.getOrgbaseid()).append("','").append(temp.getOrgname()).append("')").append("\" ");
	    		if(temp.getChildnum()!=null&&temp.getChildnum().intValue()>0&&url!=null)
	    		    buffer.append("childNum=\"").append(String.valueOf(temp.getChildnum().intValue())).append("\" ");
	    		if(target!=null&&target.trim().length()>0)
	    			buffer.append("target=\"").append(target).append("\" ");
	    		// 如果是小组，则更改图标
//	    		if(temp.getOrgtype().equals(OrgbaseConstant.OrgType_GroupInfo)&&groupIcon!=null&&groupIcon.trim().length()>0){
//	    			buffer.append("icon=\"").append(groupIcon).append("\" ");
//	    		}
	    		buffer.append("/>").append("\n");
	    	}
    	} catch (Exception e) {
			log.error("OrgLogicImpl用createOrgTreeXml方法生成xml tree时出错误!", e);
			throw e;
		}
    	buffer.append("</tree>");
    	return buffer.toString();
	}
	
	/**
	 * 新建一个商店对应的顶级商店单位
	 * @param shopid
	 * @return
	 * @throws Exception
	 */
	private Long addShopDept(Long shopid,String shopName) {
		if(shopid==null)
		   return null;
		Deptinfo deptinfo = new Deptinfo();
		Long deptid = null;

	    deptinfo.setShopid(shopid);
	    deptinfo.setOrgname(shopName);
	    deptinfo.setOrgnamesim(shopName);
	    deptinfo.setPid(OrgbaseConstant.OrgPidTop);
	    deptinfo.setIssetfornew(DeptinfoConstant.IsSetForNew_set);
	    deptinfo.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
	    DeptinfoDao deptDao = netTest.bean.BOFactory.getDeptinfoDao();
	    deptid = deptDao.insert(deptinfo);
		
		return deptid;
	}
	
    /**
     * 查找该单位的所有上级单位，包括本单位。返回的单位的名称字符串
     * @param orgbaseid
     * @return
     * @throws Exception
     */
    public String getTopOrgStr(String orgbaseidStr)throws Exception{
    	if(orgbaseidStr==null||orgbaseidStr.trim().length()<1)
    		return "";
    	Long orgbaseid = new Long(orgbaseidStr.trim());
    	List list = orgbaseDao.getTopOrgs(orgbaseid);
    	String arrowStr = "->";
    	Orgbase vo = null;
    	StringBuffer buffer = new StringBuffer();
    	if(list!=null&&list.size()>0){
    		for(int i=0;i<list.size();i++){
    		   vo = (Orgbase)list.get(i);
    		   buffer.append(vo.getOrgname());
    		   if(i!=list.size()-1){
    			   buffer.append(arrowStr);
    		   }
    		}
    	}
    	return buffer.toString();
    }
    
    public void onEvent(Event event) {
		Map paraMap = event.getParaMap();
		String eventType = event.getEventType();
		try {
			if(EventHandlerPlatform.EventType_Shop_ApprovePass.equals(eventType)){ // 新建商店
				String shopname = (String)paraMap.get("shopname");
				Long shopid = (Long)paraMap.get("shopid");
				addShopDept(shopid, shopname);
			}
		} catch (Exception e) {
			log.error("OrgLogicImpl方法onEvent时出错误, eventType:"+eventType+"|paraMap:"+paraMap, e);
		}
	}
	
    /**
     * static spring getMethod
     */
     public static OrgLogic getInstance() {
       	 OrgLogic logic = (OrgLogic)BeanFactory.getBeanFactory().getBean("orgLogic");
         return logic;
     }
	
	public DeptinfoDao getDeptinfoDao() {
		return deptinfoDao;
	}


	public OrgbaseDao getOrgbaseDao() {
		return orgbaseDao;
	}

	public void setOrgbaseDao(OrgbaseDao orgbaseDao) {
		this.orgbaseDao = orgbaseDao;
	}

	public void setDeptinfoDao(DeptinfoDao deptinfoDao) {
		this.deptinfoDao = deptinfoDao;
	}
	
}

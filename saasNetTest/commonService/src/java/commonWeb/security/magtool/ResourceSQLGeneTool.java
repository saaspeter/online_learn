package commonWeb.security.magtool;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import commonTool.util.ExcelUtil;
import commonTool.util.StringUtil;
import commonWeb.security.dao.ResourcesDao;
import commonWeb.security.dao.RoleRescDao;
import commonWeb.security.dao.RolesDao;
import commonWeb.security.dao.impl.ResourcesDaoImpl;
import commonWeb.security.dao.impl.RoleRescDaoImpl;
import commonWeb.security.dao.impl.RolesDaoImpl;
import commonWeb.security.dto.ResourcesQuery;
import commonWeb.security.vo.RoleResc;
import commonWeb.security.vo.Roles;

/**
 * 生成resource基础数据
 * 包括表resources和role_resc表
 * @author peter
 *
 */
public class ResourceSQLGeneTool {

    public static final String[] ResourcesExcelColumn = 
        new String[]{"name","resType","resString", "roleStr",
    	             "path","pidName","rescfold","descn","objectparam","expression"};
    
    private static final String Syscode = "00000002";
    
    private static final String PathName_deli_str = "-";
	
    public static void importResourceData() throws Exception{
    	ResourcesDao rescDao = ResourcesDaoImpl.getInstance();
	    RoleRescDao rolerescDao = RoleRescDaoImpl.getInstance();
		InputStream input = ResourceSQLGeneTool.class.getResourceAsStream("resources_data.xls");
		ExcelUtil helper = new ExcelUtil(input, null, null, null);
		Map map = new HashMap();
		map.put("syscode", Syscode);
		map.put("status", new Short("1"));
		map.put("visible", new Short("1"));
		map.put("localeid", new Long("1"));
	    List rescListTemp = helper.readExcel("commonWeb.security.dto.ResourcesQuery",1,ResourcesExcelColumn,map);
	    // 检查数据, resc中的name为空则删除, 
	    // 如果resc中的name或res_string重复则应该抛出错误
	    ResourcesQuery queryVO = null;
	    Map<String, String> rescnameMap = new HashMap<String, String>();
	    Map<String, String> res_stringMap = new HashMap<String, String>();
	    Map<String, Long> rescNameIDMap = new HashMap<String, Long>();
	    List<ResourcesQuery> rescList = new ArrayList<ResourcesQuery>();
	    for(int i=0; i<rescListTemp.size(); i++){
	    	queryVO = (ResourcesQuery)rescListTemp.get(i);
	    	if(queryVO!=null && queryVO.getName()!=null && queryVO.getName().trim().length()>0){
	    		if(rescnameMap.get(queryVO.getName())!=null){
	    			throw new Exception("--------resource name is duplicated, name:"+queryVO.getName());
	    		}
	    		if(res_stringMap.get(queryVO.getResString())!=null){
	    			throw new Exception("--------resource res_string is duplicated, resc_string:"+queryVO.getResString());
	    		}
	    		rescList.add(queryVO);
	    		if(queryVO.getName()!=null && queryVO.getName().trim().length()>0){
	    		   rescnameMap.put(queryVO.getName(), queryVO.getName());
	    		}
	    		if(queryVO.getResString()!=null && queryVO.getResString().trim().length()>0){
	    		   res_stringMap.put(queryVO.getResString(), queryVO.getResString());
	    		}
	    	}
	    }
	    
	    Long pk = null;
	    String pathTemp = null;
	    for(ResourcesQuery voTemp : rescList){
	    	// 不更新path，因为这时的path是文字描述的路径，不是id字符串
	    	pathTemp = voTemp.getPath();
	    	voTemp.setPath(null);
	    	if("".equals(voTemp.getObjectparam())){
	    		voTemp.setObjectparam(null);
	    	}
	    	// 清理resource中的express表达式中的换行空格等
	    	voTemp.setExpression(StringUtil.filterLineBreakSpaceFromXMLStr(voTemp.getExpression()));
	    	// 插入
	    	pk = rescDao.insert(voTemp);
	    	voTemp.setId(pk);
	    	voTemp.setPath(pathTemp);
	    	rescNameIDMap.put(voTemp.getName(), voTemp.getId());
	    }
	    // get所有现有role map
	    RolesDao roleDao = RolesDaoImpl.getInstance();
	    List roleList = roleDao.selectAll();
	    Map<String, Long> roleMap = new HashMap<String, Long>();
	    Roles roleVo = null;
	    for(int i=0; i<roleList.size(); i++){
	    	roleVo = (Roles)roleList.get(i);
	    	roleMap.put(roleVo.getCode(), roleVo.getId());
	    }
	    //TODO 循环rescList, 把其中的每个对象中的parent和parentPath都替换成id，更新该条resource记录db
	    String pathStr = null;
	    Long rescIdTemp = null;
	    String pathIdStr = "";
	    List<RoleResc> rolerescList = new ArrayList<RoleResc>();
	    RoleResc rolerescTemp = null;
	    for(ResourcesQuery voTemp : rescList){
	    	if(voTemp.getPidName()!=null && voTemp.getPidName().trim().length()>0){
		    	rescIdTemp = rescNameIDMap.get(voTemp.getPidName());
		    	if(rescIdTemp==null){
		    		throw new Exception("--------11 cannot find the pidName:"+voTemp.getPidName());
		    	}
		    	voTemp.setPid(rescIdTemp);
		    	pathStr = voTemp.getPath();
		    	if(pathStr!=null && pathStr.trim().length()>0){
		    		String[] pathArr = StringUtil.splitString(pathStr, PathName_deli_str);
		    		pathIdStr = "";
		    		for(String rescTemp : pathArr){
		    			rescIdTemp = rescNameIDMap.get(rescTemp);
		    			if(rescIdTemp==null){
				    		throw new Exception("--------22 cannot find the pathName:"+rescTemp);
				    	}
		    			pathIdStr += rescIdTemp.toString()+",";
		    		}
		    		voTemp.setPath(pathIdStr);
		    	}
	    	}
	    	// 同时对每一个资源, 创建所属的roleresc关联数据
	    	if(voTemp.getRoleStr()!=null && voTemp.getRoleStr().trim().length()>0){
	    		String roleStr = "";
	    		Long roleIdTemp = null;
	    		String[] roleArr = StringUtil.splitString(voTemp.getRoleStr(), ",");
	    		for(String roleTemp : roleArr){
	    			if(roleTemp!=null){
	    				roleIdTemp = roleMap.get(roleTemp.trim());
		    			if(roleIdTemp==null){
		    				throw new Exception("--------33 cannot find the role:"+roleTemp);
		    			}
		    			rolerescTemp = new RoleResc();
		    			rolerescTemp.setRescId(voTemp.getId());
		    			rolerescTemp.setRoleId(roleIdTemp);
		    			rolerescTemp.setSyscode(Syscode);
		    			rolerescList.add(rolerescTemp);
	    			}
	    		}
	    	}
	    }
	    // 更新所有resources中的pid和path
	    rescDao.updatePathBatch(rescList);
	    // 插入表resc_role
	    rolerescDao.insertBatch(rolerescList);
	    
	    //TODO 导出resources表和resourcesValue表和resc_role表记录到sql文件中
    }
    
	public static void main(String[] args) throws Exception{
		importResourceData();
	}
	
}

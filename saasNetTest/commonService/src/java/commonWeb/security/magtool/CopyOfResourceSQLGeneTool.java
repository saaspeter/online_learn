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

public class CopyOfResourceSQLGeneTool {

    public static final String[] ResourcesExcelColumn = 
        new String[]{"name","resType","resString", "roleStr",
    	             "path","pidName","rescfold"};
    
    private static final String Syscode = "00000002";
    
    private static final String PathName_deli_str = "-";
	
	public static void main(String[] args) throws Exception{
		InputStream input = CopyOfResourceSQLGeneTool.class.getResourceAsStream("resources_data.xls");
		ExcelUtil helper = new ExcelUtil(input, null, null, null);
		Map map = new HashMap();
		map.put("syscode", Syscode);
		map.put("status", new Short("1"));
		map.put("visible", new Short("1"));
	    List rescListTemp = helper.readExcel("commonWeb.security.dto.ResourcesQuery",2,ResourcesExcelColumn,map);
	    System.out.println();
	    
	}
	
}

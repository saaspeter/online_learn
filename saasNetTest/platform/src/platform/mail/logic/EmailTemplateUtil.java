package platform.mail.logic;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import platform.mail.dao.EmailtemplateDao;
import platform.mail.dao.impl.EmailtemplateDaoImpl;
import platform.mail.vo.Emailtemplate;
import commonTool.util.DateUtil;
import commonTool.util.ExcelUtil;

/**
 * 生成EmailTemplate数据
 * @author peter
 *
 */
public class EmailTemplateUtil {

    public static final String[] excelColumn = 
        new String[]{"templatename","recipient","fromwho", "replyto",
    	             "subject","locale","syscode","content"};
    
    private static final String Syscode = "00000002";
	
    public static void importData() throws Exception{
    	EmailtemplateDao dao = EmailtemplateDaoImpl.getInstance();
	    InputStream input = EmailTemplateUtil.class.getResourceAsStream("emailtemplate_data.xls");
		ExcelUtil helper = new ExcelUtil(input, null, null, null);
		Date date = DateUtil.getInstance().getNowtime_GLNZ();
		Map map = new HashMap();
		//map.put("localeid", new Long("1"));
	    List listTemp = helper.readExcel("platform.mail.vo.Emailtemplate",1,excelColumn,map);
	    Emailtemplate votemp = null;
	    for(int i=0;i<listTemp.size();i++){
	    	votemp = (Emailtemplate)listTemp.get(i);
	    	votemp.setLasttime(date);
	    }
	    dao.insertBatch(listTemp);
    }
    
	public static void main(String[] args) throws Exception{
		importData();
	}
	
}

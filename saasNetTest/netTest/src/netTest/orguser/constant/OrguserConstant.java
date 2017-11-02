package netTest.orguser.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrguserConstant {
    /** 1代表只管理本单位 **/
//	public static final Short OperDeptType_selfOrg = 1;
//	/** 2代表可以管理本单位及其下级单位 **/
//	public static final Short OperDeptType_downWardOrg = 2;

	
//	/** 管理数据范围，1 本单位和下级单位 **/
//	public static final Short Managescope_allbelow = new Short("1");
//	/** 管理数据范围，2 本单位 **/
//	public static final Short Managescope_selfOrg = new Short("2");
//	
	
	public static boolean checkUserShopNameFormat(String nickname){
		Pattern p = Pattern.compile("\\S{1,24}");
		Matcher m = p.matcher(nickname);
		return m.matches();
	}
	
	public static boolean checkTelephoneFormat(String telephone){
		if(telephone==null||"".equals(telephone)){
			return true;
		}
		Pattern p = Pattern.compile("(\\+\\d{1,3} )?\\d{2,25}");
		Matcher m = p.matcher(telephone);
		return m.matches();
	}
	
}

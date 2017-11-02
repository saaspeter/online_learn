import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import commonTool.util.Base64Util;
import commonTool.util.StringUtil;


public class TestMain {
	
	private static String getObjectType(String objecttypeStr, Map<String, String[]> paramap){
		String objecttype = objecttypeStr;
		if(objecttypeStr!=null){
			if(objecttypeStr.startsWith("$")){
				String[] objectidArr = paramap.get(objecttypeStr.substring(1));
				if(objectidArr!=null && objectidArr.length>0){
					objecttype = objectidArr[0];
				}
			}
		}
		return objecttype;
	}

	 public static void main(String[] args) throws Exception{
//		 Object nullObj = null;
//		 List list = (List)nullObj;
//		 List tempList = new ArrayList();
//		 tempList.add("ROLE_shop_superadmin");
//		 Collection coll = tempList;
//		 System.out.println(coll.contains("ROLE_shop_superadmin"));
//		 Locale[] locales = Locale.getAvailableLocales();  
//		 for( Locale locale : locales ){  
//		     System.out.println( locale.getDisplayCountry() + "=" + locale.getCountry() 
//		           + "             " + locale.getDisplayLanguage() + "=" + locale.getLanguage() );
////			 System.out.println( locale.getCountry() 
////			           + "    " +locale.getLanguage() );
//		 } 
		 String pwd = StringUtil.md5_saltSource("111111", "xiongcong");
		 System.out.println(pwd);

		 String response_qq = "callback( {\"client_id\":\"100290348\",\"openid\":\"81F333B3EA50F6A0C1E4E6C66659E16F\"} );";
		 response_qq = StringUtil.stripStrSimple(response_qq, "callback(", ");");
		 System.out.println(response_qq);
		 JSONObject jsonObj_qq = (JSONObject)JSONValue.parse(response_qq);
		 if(jsonObj_qq.get("openid")!=null){
		    String userid = jsonObj_qq.get("openid").toString();
		    System.out.println("openid:"+userid);
		 }
		 
		 testBase64EncodeURL();
		 
		 testRemoveElementFromList();
	 }
	 
	 private static void testBase64EncodeURL() throws UnsupportedEncodingException{
		 System.out.println(new String(Base64.encodeBase64URLSafe("qiniuphotos:gogopher.jpg".getBytes()),"UTF-8"));
		 System.out.println(Base64Util.encode("tomylearn-filestorage:test_peter_pic".getBytes()));
	 }
	 
	 private static void testRemoveElementFromList(){
		 List<Integer> list = new ArrayList<Integer>();
		 list.add(1);
		 list.add(2);
		 list.add(3);
		 list.add(4);
		 list.add(5);
		 list.add(6);
		 list.add(7);
		 list.add(8);
		 
		 Integer[] removeArr = new Integer[]{8,4,2,1}; 
		 
		 for(int i=list.size()-1;i>-1;i--){
			 if(ArrayUtils.contains(removeArr, list.get(i))){
				 list.remove(i);
			 }
		 }
		 System.out.println("after remove, size is:"+list.size());
		 for(int j=0;j<list.size();j++){
			 System.out.println(list.get(j));
		 }
	 }
	
}

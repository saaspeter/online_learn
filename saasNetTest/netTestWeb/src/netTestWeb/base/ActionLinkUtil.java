package netTestWeb.base;

import java.util.Iterator;
import java.util.Map;

import commonTool.util.PropertyFileUtil;


public class ActionLinkUtil {

	private static Map<String,String> keyMap = null;
	
	public static final String pageLinkFile = "netTestWeb/base/pageLink.properties";
	
	public static Map<String,String> getPageLinkMap(){
		if(keyMap==null){
			synchronized (ActionLinkUtil.class) {
				if(keyMap==null){
			        PropertyFileUtil util = new PropertyFileUtil(ActionLinkUtil.pageLinkFile);
			        keyMap = util.readPropertyFile();
				}
			}
		}
		return keyMap;
	}
	
	public static String getProperty(String key){
		String property = "";
		if(key!=null&&key.trim().length()>0){
			property = getPageLinkMap().get(key);
		}
		return property;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map map = ActionLinkUtil.getPageLinkMap();
        if(map!=null&&map.entrySet().size()>0){
        	Iterator ite = map.entrySet().iterator();
        	while(ite.hasNext()){
        		Map.Entry entry=(Map.Entry)ite.next();
        		Object key = entry.getKey();
                Object value = entry.getValue();
        		System.out.println(key+"：2"+value);
        	}
        }
	}

}

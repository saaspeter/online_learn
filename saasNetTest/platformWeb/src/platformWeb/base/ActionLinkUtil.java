package platformWeb.base;

import java.util.Iterator;
import java.util.Map;

import commonTool.util.PropertyFileUtil;


public class ActionLinkUtil {

	public static final String pageLinkFile = "platformWeb/base/pageLink.properties";
	
	public static Map getPageLinkMap(){
        PropertyFileUtil util = new PropertyFileUtil(ActionLinkUtil.pageLinkFile);
        return util.readPropertyFile();
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

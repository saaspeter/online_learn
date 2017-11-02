package platform.base;

import java.util.Iterator;
import java.util.Map;

import commonTool.util.PropertyFileUtil;

import junit.framework.TestCase;

public class PropertyFileUtilTest extends TestCase {

	PropertyFileUtil util = new PropertyFileUtil("platform/base/pageLink.properties");
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPropertyFileUtil() {
		Map map = util.readPropertyFile();
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

	public void testReadPropertyFile() {
		
	}

}

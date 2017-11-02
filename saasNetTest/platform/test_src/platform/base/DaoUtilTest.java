package platform.base;

import java.util.ArrayList;
import java.util.List;
import commonTool.base.DaoUtil;
import platform.vo.Productcategory;
import junit.framework.TestCase;

public class DaoUtilTest extends TestCase {

	private DaoUtil dao = DaoUtil.getInstance();
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDeleteBatch() {
		
	}

	public void testInsertBatch() {
		List list = new ArrayList();
		for(int i=0;i<50;i++){
			Productcategory vo = new Productcategory();
			list.add(vo);
		}
		int num = dao.insertBatch("Productcategory.insert", list);
		this.assertEquals(num, 50);
	}

	public void testUpdateBatch() {
		
	}

}

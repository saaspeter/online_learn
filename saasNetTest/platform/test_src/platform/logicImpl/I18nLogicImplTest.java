package platform.logicImpl;

import java.util.List;
import java.util.Locale;

import org.apache.struts.util.LabelValueBean;

import commonTool.biz.logic.I18nLogic;
import commonTool.biz.logicImpl.I18nLogicImpl;

import junit.framework.TestCase;

public class I18nLogicImplTest extends TestCase {

	I18nLogic logic;
	
	protected void setUp() throws Exception {
		super.setUp();
		logic = I18nLogicImpl.getInstance();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testImportExcel() {

	}

	public void testGetLabelList() {
		Locale locale = new Locale("en","US");
		List list = logic.getLabelList(locale);
		LabelValueBean bean = null;
		if(list!=null)
		for(int i=0;i<list.size();i++){
			bean = (LabelValueBean)list.get(i);
			if(bean!=null)
			System.out.println("语言："+bean.getLabel()+" ||localeid:"+bean.getValue());
		}
	}

}

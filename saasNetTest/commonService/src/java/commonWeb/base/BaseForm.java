package commonWeb.base;

import org.apache.log4j.Logger;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class BaseForm extends BaseFormBase {
	
	static Logger log = Logger.getLogger(BaseForm.class.getName());
	
	protected Long recordId; // 代表可以修改的记录id
    
    protected String removeStr = null; // 代表下拉菜单要过滤掉的选项的值的字符串

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public String getRemoveStr() {
		return removeStr;
	}

	public void setRemoveStr(String removeStr) {
		this.removeStr = removeStr;
	}

	
}

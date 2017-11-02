
package netTestWeb.platform.systemset.form;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import commonTool.biz.dto.I18nQuery;
import commonTool.biz.vo.I18n;


/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class I18nForm extends BaseForm {
	
	private I18n vo;
	private I18nQuery queryVO;
		
	/** 标明该Locale是否生效 **/
	private int isset;
	/** 表明该Locale是否是默认设置 **/
	private int isdefaultset;
	/** 导入的excel文件 **/
    private FormFile xmlFile;
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		vo = new I18n();
		queryVO = new I18nQuery();
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		return null;
	}
	
	public int getIsdefaultset() {
		return isdefaultset;
	}

	public int getIsset() {
		return isset;
	}

	public void setIsdefaultset(int isdefaultset) {
		this.isdefaultset = isdefaultset;
	}

	public void setIsset(int isset) {
		this.isset = isset;
	}
	
	public I18nQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(I18nQuery queryVO) {
		this.queryVO = queryVO;
	}

	public I18n getVo() {
		return vo;
	}

	public void setVo(I18n vo) {
		this.vo = vo;
	}

	public FormFile getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(FormFile xmlFile) {
		this.xmlFile = xmlFile;
	}
		
}

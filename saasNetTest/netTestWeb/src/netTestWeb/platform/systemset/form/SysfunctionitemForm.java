
package netTestWeb.platform.systemset.form;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.dto.SysfunctionitemQuery;
import platform.vo.Sysfunctionitem;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysfunctionitemForm extends BaseForm {
	
	private Sysfunctionitem vo;
	private SysfunctionitemQuery queryVO;
	
	/** 系统代码和功能的对应Map **/
	private Map sysFuncMap;
	
	/** 系统列表 **/
	private List sysList;
	
	private List paytypeList;
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Sysfunctionitem();
		queryVO = new SysfunctionitemQuery();
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		//if(queryVO!=null){
		//   if(request.getParameter("queryVO.categorylevel")==null
		//      ||(!((String)request.getParameter("queryVO.categorylevel")).matches("^\\d+$")))
		//    	queryVO.setCategorylevel(null);
		//}
		return null;
	}
	
	public SysfunctionitemQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(SysfunctionitemQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Sysfunctionitem getVo() {
		return vo;
	}

	public void setVo(Sysfunctionitem vo) {
		this.vo = vo;
	}

	public List getPaytypeList() {
		return paytypeList;
	}

	public void setPaytypeList(List paytypeList) {
		this.paytypeList = paytypeList;
	}

	public Map getSysFuncMap() {
		return sysFuncMap;
	}

	public void setSysFuncMap(Map sysFuncMap) {
		this.sysFuncMap = sysFuncMap;
	}

	public List getSysList() {
		return sysList;
	}

	public void setSysList(List sysList) {
		this.sysList = sysList;
	}
		
}

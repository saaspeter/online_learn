
package netTestWeb.platform.systemset.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import commonTool.biz.dto.SysparamQuery;
import commonTool.biz.vo.Sysparam;


/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysparamForm extends BaseForm {
	
	private Sysparam vo;
	private SysparamQuery queryVO;
	private List codeList;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Sysparam();
		queryVO = new SysparamQuery();
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
	
	public SysparamQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(SysparamQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Sysparam getVo() {
		return vo;
	}

	public void setVo(Sysparam vo) {
		this.vo = vo;
	}

	public List getCodeList() {
		return codeList;
	}

	public void setCodeList(List codeList) {
		this.codeList = codeList;
	}
		
}

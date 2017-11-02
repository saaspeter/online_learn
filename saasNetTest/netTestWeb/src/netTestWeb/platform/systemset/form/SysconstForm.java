
package netTestWeb.platform.systemset.form;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import commonTool.biz.dto.SysconstQuery;
import commonTool.biz.vo.Sysconst;


public class SysconstForm extends BaseForm {
	
	private Sysconst vo;
	private SysconstQuery queryVO;
	
	private List sysList;
	private Locale locale;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Sysconst();
		queryVO = new SysconstQuery();
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
	
	public SysconstQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(SysconstQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Sysconst getVo() {
		return vo;
	}

	public void setVo(Sysconst vo) {
		this.vo = vo;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public List getSysList() {
		return sysList;
	}

	public void setSysList(List sysList) {
		this.sysList = sysList;
	}
		
}

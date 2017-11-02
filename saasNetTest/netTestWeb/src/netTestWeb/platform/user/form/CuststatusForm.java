
package netTestWeb.platform.user.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.dto.CuststatusQuery;
import platform.vo.Custstatus;


public class CuststatusForm extends BaseForm {
	
	private Custstatus vo;
	private CuststatusQuery queryVO;
	
	private Long userid;
	
	private List datalist;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Custstatus();
		queryVO = new CuststatusQuery();
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
	
	public CuststatusQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(CuststatusQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Custstatus getVo() {
		return vo;
	}

	public void setVo(Custstatus vo) {
		this.vo = vo;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public List getDatalist() {
		return datalist;
	}

	public void setDatalist(List datalist) {
		this.datalist = datalist;
	}
		
}

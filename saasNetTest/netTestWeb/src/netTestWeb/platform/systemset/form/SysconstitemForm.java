
package netTestWeb.platform.systemset.form;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import commonTool.biz.dto.SysconstitemQuery;
import commonTool.biz.vo.Sysconstitem;


public class SysconstitemForm extends BaseForm {
	
	private Sysconstitem vo;
	private SysconstitemQuery queryVO;
	/** 用于显示所属常量的名称 **/
	private String constname;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Sysconstitem();
		queryVO = new SysconstitemQuery();
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
	
	public SysconstitemQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(SysconstitemQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Sysconstitem getVo() {
		return vo;
	}

	public void setVo(Sysconstitem vo) {
		this.vo = vo;
	}

	public String getConstname() {
		return constname;
	}

	public void setConstname(String constname) {
		this.constname = constname;
	}
		
}

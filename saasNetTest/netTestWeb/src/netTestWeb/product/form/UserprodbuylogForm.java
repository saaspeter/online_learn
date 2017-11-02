
package netTestWeb.product.form;

import javax.servlet.http.HttpServletRequest;

import netTest.product.dto.UserprodbuylogQuery;
import netTest.product.vo.Userprodbuylog;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import netTestWeb.base.BaseForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class UserprodbuylogForm extends BaseForm {
	
	private Userprodbuylog vo;
	private UserprodbuylogQuery queryVO;
	
	/** 查询产品类型, 1代表正在使用的产品, 2代表已经过期的产品 **/
	private int selectproducttype;
	/** 查询产品范围，1为查询单一产品，0为查询多个产品，页面有选择产品 **/
	private int selectproductscope;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Userprodbuylog();
		queryVO = new UserprodbuylogQuery();
		selectproducttype = 2;
		selectproductscope = 0;
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
	
	public UserprodbuylogQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(UserprodbuylogQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Userprodbuylog getVo() {
		return vo;
	}

	public void setVo(Userprodbuylog vo) {
		this.vo = vo;
	}

	public int getSelectproducttype() {
		return selectproducttype;
	}

	public void setSelectproducttype(int selectproducttype) {
		this.selectproducttype = selectproducttype;
	}

	public int getSelectproductscope() {
		return selectproductscope;
	}

	public void setSelectproductscope(int selectproductscope) {
		this.selectproductscope = selectproductscope;
	}
		
}

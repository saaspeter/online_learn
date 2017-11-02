
package netTestWeb.wareques.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import netTestWeb.base.BaseForm;
import netTest.wareques.dto.QuesdifficultQuery;
import netTest.wareques.vo.Quesdifficult;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class QuesdifficultForm extends BaseForm {
	
	private Quesdifficult vo;
	private QuesdifficultQuery queryVO;
	
	/** 转向的列表页面类型：1为管理列表页面；2为选择列表页面 **/
	private String listType;
	/** 1代表单选，2代表多选 **/
	private String selectType;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Quesdifficult();
		queryVO = new QuesdifficultQuery();
		listType = "1";
		selectType = "2";
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
	
	public QuesdifficultQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(QuesdifficultQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Quesdifficult getVo() {
		return vo;
	}

	public void setVo(Quesdifficult vo) {
		this.vo = vo;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

		
}


package netTestWeb.wareques.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import netTestWeb.base.BaseForm;
import netTest.wareques.dto.QuestypeshopQuery;
import netTest.wareques.vo.Questypeshop;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class QuestypeshopForm extends BaseForm {
	
	private Questypeshop vo;
	private QuestypeshopQuery queryVO;
	private List list;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Questypeshop();
		queryVO = new QuestypeshopQuery();
		list = null;
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
	
	public QuestypeshopQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(QuestypeshopQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Questypeshop getVo() {
		return vo;
	}

	public void setVo(Questypeshop vo) {
		this.vo = vo;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

		
}

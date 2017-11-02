
package netTestWeb.platform.shop.form;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import platform.dto.ShopstyleconfigQuery;
import platform.vo.Shopstyleconfig;

/** 
 */
public class ShopstyleconfigForm extends BaseForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7787338199605582088L;
	private Shopstyleconfig vo;
	private ShopstyleconfigQuery queryVO;
		
	private FormFile uploadFile;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Shopstyleconfig();
		queryVO = new ShopstyleconfigQuery();
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
	
	public ShopstyleconfigQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ShopstyleconfigQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Shopstyleconfig getVo() {
		return vo;
	}

	public void setVo(Shopstyleconfig vo) {
		this.vo = vo;
	}

	public FormFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	
}

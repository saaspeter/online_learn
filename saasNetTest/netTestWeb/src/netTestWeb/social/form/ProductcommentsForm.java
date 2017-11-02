package netTestWeb.social.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import netTest.product.vo.Productbase;
import commonWeb.social.form.CommentsForm;

public class ProductcommentsForm extends CommentsForm{
    
	private static final long serialVersionUID = -8414489361680601452L;
	
	private Productbase productvo;
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		productvo = null;
	}

	public Productbase getProductvo() {
		return productvo;
	}

	public void setProductvo(Productbase productvo) {
		this.productvo = productvo;
	}
	
}

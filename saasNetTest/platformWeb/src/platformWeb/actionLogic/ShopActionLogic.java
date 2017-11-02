package platformWeb.actionLogic;

public class ShopActionLogic {

	public static ShopActionLogic logic = null;
	

	
	public static ShopActionLogic newInstance(){
		if(logic==null)
			logic = new ShopActionLogic();
		return logic;
	}
	
}

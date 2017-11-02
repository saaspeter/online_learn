package commonWeb.base;

public class KeyInMemConstBase {
	
	// session中用来保存用户信息的key
	public static final String CustomerInfoKey = "CustomerInfoSession";
	
	// 用来标识在request中存放字符串的key
	/**  存放在application中的Map，存放所有页面action链接地址  **/
	public static final String PageAddressMapKey = "PageAddressMapInApplication";
   
	/**  在request中存放信息页面的显示信息的常量Key  **/
	public static final String DisMessKey = "DisplayMessageKey";
	/**  在request中存放信息页面中的需要跳转链接list页面类型  **/
	public static final String PageTypeListKey = "PageTypeListInMessage.jsp";
	/**  在request中存放信息页面中的需要跳转链接add页面类型  **/
	public static final String PageTypeAddKey = "PageTypeAddInMessage.jsp";
	/**  在request中存放信息页面中的需跳转页面的ActionName  **/
	public static final String ActionNameKey = "ActionNameInMessage.jsp";
	/**  在request中存放用于生成树的xml文件的字符串变量  **/
	public static final String treeXmlKey = "TreeXmlStream";
	/**  在request中存放信息页面的显示信息的参数arg0的Key  **/
	public static final String DisMessArg0Key = "DisplayMessageArg0Key";
	
	// 用于java类中的比较
    /**  定义消息页面类型的列表页面整数型  **/
	public static final int ActionTypeListInt = 1;
	/**  定义消息页面类型的新增页面整数型  **/
	public static final int ActionTypeAddInt = 2;
	/**  定义消息页面类型的编辑页面整数型  **/
	public static final int ActionTypeEditInt = 3;
	/**  定义消息页面类型的删除页面整数型  **/
	public static final int ActionTypeDeleteInt = 4;
	/**  定义消息页面类型的导入文件页面整数型  **/
	public static final int ActionTypeImportInt = 5;
	
	//------------------ 固定的新增修改删除的消息代码 ------------
	public static final String AddSuccess = "commonWeb.page.pubs.message.jsp.addSuccess";
	public static final String AddMutiSuccess = "commonWeb.page.pubs.message.jsp.addMutiSuccess";
	public static final String modifySuccess = "commonWeb.page.pubs.message.jsp.modifySuccess";
	public static final String saveSuccess = "commonWeb.page.pubs.message.jsp.saveSuccess";
	// 删除成功的信息，带有删除成功的数字
	public static final String deleteSuccess = "commonWeb.page.pubs.message.jsp.deleteSuccess";
	// 删除成功的一般信息，没有带数字信息
	public static final String deleteSuccessCommon = "commonWeb.page.pubs.message.jsp.deleteSuccessCommon";
	public static final String importSuccess = "commonWeb.page.pubs.message.jsp.importSuccess";
	
	public static final String saveFail = "commonWeb.java.commonaction.errors.save";
	

    /**  定义消息页面类型的列表页面字符型  **/
	public static final String PageTypeListString = "list";
	/**  定义消息页面类型的新增页面字符型  **/
	public static final String PageTypeAddString = "add";
	/**  定义消息页面类型的编辑页面字符型  **/
	public static final String PageTypeEditString = "edit";
	
	/**  用于存放用户自己选择的Locale在request中的键值，，用于主界面显示结果的  **/
	public static final String SessionKey_LocaleidUserSelect = "session.localeid.userSelect";
	/**  用于存放用户自己选择的Locale在request中的键值，，用于主界面显示结果的  **/
	public static final String SessionKey_LocaleUserSelect = "session.locale.userSelect";
	
	public static final String SessionKey_UserJsSuffix = "session.jsSuffix.userSelect";
		
	/**  用于记录消息页面应该跳转的列表页面的链接  **/
	public static final String backUrl = "backUrl";
	/**  用于记录消息页面应该跳转的列表页面的链接的encode形式  **/
	public static final String backUrlEncode = "backUrlEncode";
		
}

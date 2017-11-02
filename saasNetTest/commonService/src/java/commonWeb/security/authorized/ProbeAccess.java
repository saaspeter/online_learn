package commonWeb.security.authorized;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;

import commonTool.condexec.ConditionalExec;
import commonTool.condexec.ConditionalReturn;
import commonTool.condexec.ContextObj;

public class ProbeAccess{
	
	//private String configfile;

	public static Map<String,Element> actionMap = new HashMap<String,Element>();
	
	private boolean noactionAllow = true;
	
	public final static String Error_NoAction_Mapping = "no_action_mapping";
	
	/**
	 * 初始化policy action element
	 * @param filename
	 * @throws Exception
	 */
//	protected void initValue(String filename) throws Exception{
//		if(filename==null||!filename.endsWith(".xml")){
//			throw new LogicException(ExceptionConstantBase.Error_Invalide_Parameter);
//		}
//		Document policyActionEle = DOM4JUtil.parseResource(filename);
//		Element rootElement = policyActionEle.getRootElement();
//        int iNodeCount = rootElement.nodeCount();
//        Node testNode;
//        Element testElem;
//        for (int idx = 0; idx < iNodeCount; idx++) {
//        	testNode = rootElement.node(idx);
//            if (testNode instanceof Element) {
//                testElem = (Element) testNode;
//                String elementName = testElem.getName();
//                actionMap.put(elementName, testElem);
//            }
//        }
//	}
	
	/**
	 * 
	 * @param action
	 * @param targetObj
	 * @param paramMap
	 * @return
	 */
//	public ConditionalReturn check(String action, Object targetObj, Map<String,Object> paramMap){
//		Element condElem = actionMap.get(action);
//		if(condElem==null){
//			if(noactionAllow)
//				return new ConditionalReturn(true, null);
//			else
//				return new ConditionalReturn(true, Error_NoAction_Mapping);
//		}
//		ContextObj contObj = new ContextObj(targetObj, paramMap);
//		ConditionalReturn crn = ConditionalExec.getInstance().evaluateExpression(condElem, contObj);
//		return crn;
//	}
	
	public boolean check(Element policyrule, ContextObj contObj){
		if(policyrule==null){
			if(noactionAllow)
				return true;
			else
				return false;
		}
		ConditionalReturn crn = ConditionalExec.getInstance().evaluateExpression(policyrule, contObj);
		return crn.isResult();
	}

	public boolean isNoactionAllow() {
		return noactionAllow;
	}

	public void setNoactionAllow(boolean noactionAllow) {
		this.noactionAllow = noactionAllow;
	}
	
	// 把url转化成action
//	public String transUrlToAction(String url){
//		if(url==null || url.trim().length()<1)
//			return url;
//		// 过滤url to action, 去除过于参数url
//		int firstQuestionMarkIndex = url.lastIndexOf("?");
//		if (firstQuestionMarkIndex != -1) {
//			url = url.substring(0, firstQuestionMarkIndex);
//		}
//		if(url.startsWith("/")){
//        	url = url.substring(1);
//        }
//        if(url.endsWith(".do")){
//        	url = url.substring(0, url.length()-3);
//        }
//        String action = url.replaceAll("/", "-");
//        return action;
//	}
	
}

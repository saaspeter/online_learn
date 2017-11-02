package commonWeb.security.resource;

import java.io.Serializable;

import org.dom4j.Element;
import org.springframework.security.GrantedAuthority;

/**
 * 提供资源信息
 *
 * @author cac
 */
public interface ResourceInf extends Serializable {

	/**
	 * 资源串
	 */
	public String getResString();

	/**
	 * 资源类型,如URL,FUNCTION
	 */
	public String getResType();

	/**
	 * 返回属于该resource的authorities
	 */
	public GrantedAuthority[] getAuthorities();
	
	public void setAuthorities(GrantedAuthority[] authorities);
	
	/** 得到object id在url中对应的name **/
	public String getRequestObjectidName();
	/** 得到object type在url中对应的name **/
	public String getRequestObjecttype();
	/** 得到container id在url中对应的name **/
	public String getRequestContaineridname();
	/** 得到container type在url中对应的name **/
	public String getRequestContainertype();
	
	/**
	 * 得到url定义的policy rule element
	 * @return
	 */
	public Element getPolicyRule();
}

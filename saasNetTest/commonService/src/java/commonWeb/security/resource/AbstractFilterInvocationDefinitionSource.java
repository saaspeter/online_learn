/**
 *
 */
package commonWeb.security.resource;

import java.util.Collection;

import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;

/**
 * @author Administrator
 *
 */
public abstract class AbstractFilterInvocationDefinitionSource implements
		FilterInvocationDefinitionSource {

	/* (non-Javadoc)
	 * @see org.springframework.security.intercept.ObjectDefinitionSource#getAttributes(java.lang.Object)
	 */
	public ConfigAttributeDefinition getAttributes(Object object)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (object == null || !(this.supports(object.getClass()))) {
			throw new IllegalArgumentException("Object must be a FilterInvocation");
		}
		String url = ((FilterInvocation)object).getRequestUrl();
		return this.lookupAttributes(url);
	}

	public abstract ConfigAttributeDefinition lookupAttributes(String url);

	/* (non-Javadoc)
	 * @see org.springframework.security.intercept.ObjectDefinitionSource#getConfigAttributeDefinitions()
	 */
	@SuppressWarnings("unchecked")
	public abstract Collection getConfigAttributeDefinitions();

	/* (non-Javadoc)
	 * @see org.springframework.security.intercept.ObjectDefinitionSource#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}

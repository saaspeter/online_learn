package commonWeb.security.resource;

import java.lang.reflect.Method;
import java.util.Collection;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.method.MethodDefinitionSource;
import org.springframework.util.Assert;

public abstract class AbstractMethodDefinitionSource implements
		MethodDefinitionSource {

	private static final Log logger = LogFactory.getLog(AbstractMethodDefinitionSource.class);

	@SuppressWarnings("unchecked")
	public ConfigAttributeDefinition getAttributes(Object object)
			throws IllegalArgumentException {
		Assert.notNull(object, "Object cannot be null");
		if (object instanceof MethodInvocation) {
			MethodInvocation mi = (MethodInvocation)object;
			Method method = mi.getMethod();
			Class targetClass = mi.getThis().getClass();
			return lookupAttributes(method, targetClass);
		}
		if (object instanceof JoinPoint) {
			JoinPoint point = (JoinPoint)object;
			Class targetClazz = point.getTarget().getClass();
			String targetMethodName = point.getStaticPart().getSignature().getName();
			Class[] types = ((CodeSignature)point.getStaticPart().getSignature()).getParameterTypes();
			Method method = null;
			try {
				method = targetClazz.getMethod(targetMethodName, types);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				throw new IllegalArgumentException("Could not obtain target method from JoinPoint: " + point);
			}

			if (logger.isDebugEnabled()) {
                logger.debug("Target Class: " + targetClazz);
                logger.debug("Target Method Name: " + targetMethodName);

                for (int i = 0; i < types.length; i++) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Target Method Arg #" + i + ": " + types[i]);
                    }
                }
            }
			return lookupAttributes(method, targetClazz);
		}
		throw new IllegalArgumentException("Object must be a MethodInvocation or JoinPoint");
	}

	@SuppressWarnings("unchecked")
	protected abstract ConfigAttributeDefinition lookupAttributes(Method method, Class targetClazz);

	@SuppressWarnings("unchecked")
	public ConfigAttributeDefinition getAttributes(Method method, Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public Collection getConfigAttributeDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return MethodInvocation.class.isAssignableFrom(clazz)
				|| JoinPoint.class.isAssignableFrom(clazz);
	}

}

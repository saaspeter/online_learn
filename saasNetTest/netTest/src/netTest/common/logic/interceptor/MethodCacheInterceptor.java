package netTest.common.logic.interceptor;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class MethodCacheInterceptor implements MethodInterceptor, InitializingBean {
  private static final Logger logger = Logger.getLogger(MethodCacheInterceptor.class.getName());

  private Cache cache;

  /**
   * sets cache name to be used
   */
  public void setCache(Cache cache) {
    this.cache = cache;
  }

  /**
   * Checks if required attributes are provided.
   */
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(cache, "A cache is required. Use setCache(Cache) to provide one.");
  }

  /**
   * main method
   * caches method result if method is configured for caching
   * method results must be serializable
   */
  public Object invoke(MethodInvocation invocation) throws Throwable {
    String targetName  = invocation.getThis().getClass().getName();
    String methodName  = invocation.getMethod().getName();
    Object[] arguments = invocation.getArguments();
    Object result;

    String cacheKey = getCacheKey(targetName, methodName, arguments);
    Element element = cache.get(cacheKey);
    if (element == null) {
      //call target/sub-interceptor
      logger.debug("calling intercepted method");
      result = invocation.proceed();
      
      //cache method result
      element = new Element(cacheKey, (Serializable) result);
      cache.put(element);
      logger.debug("----added cache:"+cacheKey);
      System.out.println("----out.print:added cache:"+cacheKey);
    }
    return element.getValue();
  }

  /**
   * creates cache key: targetName.methodName.argument0.argument1...
   */
  protected String getCacheKey(String targetName,
                             String methodName,
                             Object[] arguments) {
    StringBuffer sb = new StringBuffer();
    sb.append(targetName).append(".").append(methodName);
    if ((arguments != null) && (arguments.length != 0)) {
      for (int i=0; i<arguments.length; i++) {
        sb.append(".").append(arguments[i]);
      }
    }

    return sb.toString();
  }
}

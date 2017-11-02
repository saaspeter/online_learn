package netTest.common.logic.interceptor;

import java.lang.reflect.Method;

import net.sf.ehcache.Cache;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
  
/**
 * 调用方法后的拦截器，用于当需要缓存的对象发生改变时清除旧的缓存对象信息 
 */  
public class MethodCacheAfterAdvice implements AfterReturningAdvice,InitializingBean {  
	private static final Logger logger = Logger.getLogger(MethodCacheAfterAdvice.class.getName()); 
      
    private Cache cache;  
      
    public void setCache(Cache cache){  
         this.cache = cache;      
    }  
    /** 
     *  
     */  
    public MethodCacheAfterAdvice(){  
        super();
    }  
  
    /* （非 Javadoc） 
     * @see org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object); 
     */  
    public void afterReturning(Object returnValue, Method method, Object[] args,  
            Object target) throws Throwable {  
            String cacheKey = getCacheKey(target.getClass().getName(),method.getName(),args);
            cache.remove(cacheKey); 
            logger.debug("----cache:"+cacheKey+" has been removed!");
    }  
    
    /**
     * creates cache key: targetName.methodName.argument0.argument1...
     */
    protected String getCacheKey(String targetName, String methodName,
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
  
    /* （非 Javadoc） 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet(); 
     */  
    public void afterPropertiesSet() throws Exception {  
        Assert.notNull(cache, "需要一个缓存. 使用setCache(Cache)分配一个.");;  
    }  
  
}  

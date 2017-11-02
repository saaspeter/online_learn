package commonWeb.security.authentication;

import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.http.HttpServletRequest;


/**
* change from org.springframework.security.ui.WebAuthenticationDetailsSource
* for login with Google/Facebook/QQ case
*/
public class SocialAuthenticationDetailsSource implements AuthenticationDetailsSource {
   //~ Instance fields ================================================================================================

   private Class clazz = SocialAuthenticationDetails.class;

   //~ Methods ========================================================================================================

   /**
    * @param context the <tt>HttpServletRequest</tt> object.
    */
   public Object buildDetails(Object context) {
       Assert.isInstanceOf(HttpServletRequest.class, context);
       try {
           Constructor constructor = clazz.getConstructor(new Class[] {HttpServletRequest.class});

           return constructor.newInstance(new Object[] {context});
       } catch (NoSuchMethodException ex) {
           ReflectionUtils.handleReflectionException(ex);
       } catch (InvocationTargetException ex) {
           ReflectionUtils.handleReflectionException(ex);
       } catch (InstantiationException ex) {
           ReflectionUtils.handleReflectionException(ex);
       } catch (IllegalAccessException ex) {
           ReflectionUtils.handleReflectionException(ex);
       }

       return null;
   }

   public void setClazz(Class clazz) {
       Assert.notNull(clazz, "Class required");
       this.clazz = clazz;
   }
}


package commonWeb.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharsetEncodingFilter implements Filter {
  /**
   * The default character encoding to set for requests that pass through
   * this filter.
   */
  protected String encoding = null;

  /**
   * The filter configuration object we are associated with. If this value
   * is null, this filter instance is not currently configured.
   */
  protected FilterConfig filterConfig = null;

  /**
   * Should a character encoding specified by the client be ignored?
   */
  protected boolean ignore = true;

//	--------------------------------------------------------- Public Methods

  /**
   * Select and set (if specified) the character encoding to be used to
   * interpret request parameters for this request.
   * @param chain The filter chain we are processing
   */
  public void doFilter(ServletRequest request, ServletResponse response,
					   FilterChain chain) throws IOException, ServletException {
	if ((request.getCharacterEncoding() == null)) {
	  //String newEncoding = this.encoding;
	  //newEncoding = (newEncoding == null?"UTF-8":newEncoding);
	  //if (newEncoding != null) {
		request.setCharacterEncoding("UTF-8");
	  //}
	}
	chain.doFilter(request, response);

  }

  /**
   * Place this filter into service.
   *
   * @param filterConfig The filter configuration object
   */
  public void init(FilterConfig filterConfig) throws ServletException {

	this.filterConfig = filterConfig;
	//this.encoding = filterConfig.getInitParameter("encoding");
    
  }

  /**
   * Take this filter out of service.
   */
  public void destroy() {
	this.encoding = null;
	this.filterConfig = null;
  }
  
}

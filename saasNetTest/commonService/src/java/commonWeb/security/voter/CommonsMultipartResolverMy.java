package commonWeb.security.voter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class CommonsMultipartResolverMy extends CommonsMultipartResolver {

	public Map<String, String[]> getMultiMap(HttpServletRequest request) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		ServletFileUpload fileUpload = new ServletFileUpload();
	    try {
			FileItemIterator items = fileUpload.getItemIterator(request);
			while (items.hasNext()) {
			    FileItemStream item = items.next();
			    if (item.isFormField()) {
//			    	String value = inputStream2String(item.openStream());
//			        map.put(item.getFieldName(), new String[]{value});
			    }
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return map;
	}
	
	private String inputStream2String(InputStream is) throws IOException{ 
		   BufferedReader in = new BufferedReader(new InputStreamReader(is)); 
		   StringBuffer buffer = new StringBuffer(); 
		   String line = ""; 
		   while ((line = in.readLine()) != null){ 
		     buffer.append(line); 
		   } 
		   return buffer.toString(); 
		} 
	
}

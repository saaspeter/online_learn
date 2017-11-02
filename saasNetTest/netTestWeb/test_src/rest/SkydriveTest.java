package rest;

import java.util.Map;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MultivaluedMap;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class SkydriveTest {

	private final static String client_id = "000000004C0E29BD";

	private final static String redirect_uri = "http://www.mylearn.com/netTest/MyHtml.html";

	private final static String client_secret = "ry8OnbTQrnN6RDq6QYrtW4rGURSWeWW8";

	private final static String grant_type = "authorization_code";

	private final static String code = "8d11ffc9-4435-0c39-a072-fc0df3df97e1";

	private static String skydriveService = "https://login.live.com/oauth20_token.srf";

	private static String translateString() {
		ClientConfig config = new DefaultClientConfig();

		Client c = Client.create(config);

		WebResource r = c.resource(skydriveService);
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();

		params.add("client_id", client_id);
		params.add("redirect_uri", redirect_uri);
		params.add("client_secret", client_secret);
		params.add("grant_type", grant_type);
		params.add("code", code);

		String response = r.queryParams(params).get(String.class);
		return response;
	}
	
	private static String extractTranslationFromJSON(String response) {
	    final JSONObject jsonObj = (JSONObject)JSONValue.parse(response);
	    String translation=null;
	    if (jsonObj != null && jsonObj.containsKey("responseData")) {
	      final JSONObject responseData = (JSONObject)jsonObj.get("responseData");
	      translation=  responseData.get("translatedText").toString();
	    }
	    return translation;
	  }

	public static void main(String[] args) {
		// String response = translateString();
		String response = translateString();
		System.out.println(response);
	}

}
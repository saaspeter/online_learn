package platform.social.logic.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import platform.exception.ExceptionConstant;
import platform.social.constant.SocialoathtokenConstant;
import platform.social.dto.SocialNewsResult;
import platform.social.logic.SocialNewsReader;
import platform.social.vo.SocialNews;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import commonTool.exception.LogicException;
import commonTool.image.ImageBase64Util;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;

public class SocialNewsReaderImpl implements SocialNewsReader {

	static Logger log = Logger.getLogger(SocialNewsReaderImpl.class.getName());
	
	// max size each time to get, should no more than 50
	private final static int MaxSize_Response = 50;
	
	private static ClientConfig config = new DefaultClientConfig();
	private static Client c = Client.create(config);
	
	static{
		c.setConnectTimeout(4000); // set read time 4 second
		c.setReadTimeout(4000);
	}
	
	@Override
	public SocialNewsResult readSocialNewsFromAPI(String fromUserId, String serviceTypeName,
			 									  Long newscategoryid, String accessToken) {
		if(SocialoathtokenConstant.ServiceType_Google_Name.equalsIgnoreCase(serviceTypeName)){
			return readSocialNewsFromGoogleAPI(fromUserId, newscategoryid);
		}
		return null;
	}
	
	private SocialNewsResult readSocialNewsFromGoogleAPI(String userId, Long newscategoryid){
		if(StringUtil.isEmpty(userId)){
			log.error("--error, userId is empty");
			return null;
		}
		
		SocialNewsResult vo = null;
		String apiKey = SocialoathtokenConstant.getAPIKey(SocialoathtokenConstant.ServiceType_Google);
		
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("fields", "items(id,url,published,object(content,attachments/url,attachments/image),title,updated,actor(displayName,id,image)),nextLink,nextPageToken,updated");
		params.add("maxResults", String.valueOf(MaxSize_Response));
		params.add("key", apiKey);

		try {
			WebResource r = c.resource("https://www.googleapis.com/plus/v1/people/"+userId+"/activities/public");
			// according to api doc, should use Get Method
			String response = r.queryParams(params).get(String.class);
			vo = readSocialNewsFromStringForGoogle(response, newscategoryid, true);
		} catch (UniformInterfaceException e) {
			String detail = e.getResponse().getEntity(String.class).toString();
			log.error(detail, e);
			throw e;
		} catch (com.sun.jersey.api.client.ClientHandlerException e){
			log.error("error here, userID:"+userId, e);
			if((e.getCause() instanceof java.net.ConnectException)
					||(e.getCause() instanceof java.net.SocketTimeoutException)){
				throw new LogicException(ExceptionConstant.Error_Cannot_ConnectToService).appendExtraInfo_FirstKey("Google+");
			}else {
			    throw e;
			}
		}
		
		return vo;
	}
	
	private SocialNewsResult readSocialNewsFromStringForGoogle(String srContent,
														Long newscategoryid, boolean imageEncodeBase64) 
	{
		if(StringUtil.isEmpty(srContent)){
			return null;
		}
		SocialNewsResult vo = new SocialNewsResult();
		JSONObject jsonObj = (JSONObject)JSONValue.parse(srContent);
		if(jsonObj != null) {
			if(jsonObj.get("nextPageToken")!=null){
				vo.setNextPageToken(jsonObj.get("nextPageToken").toString());
			}
			if(jsonObj.get("updated")!=null){
				vo.setUpdatedTime(DateUtil.parseStrToDate(jsonObj.get("updated").toString(), DateUtil.DATE_TIME_FORMAT_TimeZone));
			}
			if(jsonObj.get("items")!=null){
				List<SocialNews> newsList = new ArrayList<SocialNews>();
				JSONArray items = (JSONArray)jsonObj.get("items");
				JSONObject itemObj = null;
				JSONObject tempJsonObj = null;
				String attachmentUrl = null;
				String imageUrl = null;
				String imageType = null;
				String imageWidth = null;
				String imageHeight = null;
				String imageContent = null;
				for(int i=0; i<items.size(); i++){
					SocialNews newsVO = new SocialNews();
					newsVO.setNewscategoryid(newscategoryid);
					newsVO.setFromService(SocialoathtokenConstant.ServiceType_Google_Name);
					itemObj = (JSONObject)items.get(i);
					if(itemObj.get("actor")!=null){
						tempJsonObj = (JSONObject)itemObj.get("actor");
						newsVO.setAuthorId(tempJsonObj.get("id").toString());
						newsVO.setAuthorName(tempJsonObj.get("displayName").toString());
					}
					if(itemObj.get("title")!=null){
						newsVO.setTitle(itemObj.get("title").toString());
					}
					if(itemObj.get("published")!=null){
						newsVO.setCreatedTime(DateUtil.parseStrToDate(itemObj.get("published").toString(), DateUtil.DATE_TIME_FORMAT_TimeZone));
					}
					if(itemObj.get("updated")!=null){
						newsVO.setUpdatedTime(DateUtil.parseStrToDate(itemObj.get("updated").toString(), DateUtil.DATE_TIME_FORMAT_TimeZone));
					}
					if(itemObj.get("id")!=null){
						newsVO.setSourceId(itemObj.get("id").toString());
					}
					if(itemObj.get("url")!=null){
						newsVO.setSourceUrl(itemObj.get("url").toString());
					}
					if(itemObj.get("object")!=null){
						tempJsonObj = (JSONObject)itemObj.get("object");
						newsVO.setContent((tempJsonObj.get("content")==null)?null:(tempJsonObj.get("content").toString()));
						JSONArray attachments = (JSONArray)tempJsonObj.get("attachments");
						if(attachments!=null){
							for(int j=0; j<attachments.size(); j++){
								if(attachments.get(j)!=null){
									attachmentUrl =  (((JSONObject)attachments.get(j)).get("url")==null)?null:((JSONObject)attachments.get(j)).get("url").toString();
									tempJsonObj = (JSONObject)((JSONObject)attachments.get(j)).get("image");
									if(tempJsonObj!=null){
										imageUrl = (tempJsonObj.get("url")==null)?null:(tempJsonObj.get("url").toString());
										// get the image from imageUrl, then transfer to Base64 String
										if(imageEncodeBase64 && !StringUtil.isEmpty(imageUrl)){
											try {
												imageContent = ImageBase64Util.encodeUrlToString(imageUrl);
											} catch (IOException e) {
												log.error("--error, for sourceID:"+newsVO.getSourceId()+" , cannot get this url:"+imageUrl, e);
											}
										}else {
											imageContent = null;
										}
										imageType = (tempJsonObj.get("type")==null)?null:(tempJsonObj.get("type").toString());
										imageWidth = (tempJsonObj.get("width")==null)?null:(tempJsonObj.get("width").toString());
										imageHeight = (tempJsonObj.get("height")==null)?null:(tempJsonObj.get("height").toString());
									}
									newsVO.addAttachment(attachmentUrl, imageUrl, imageType, imageWidth, imageHeight, imageContent);
								}
							}
						}
					}
					newsList.add(newsVO);
				}
				vo.setNewsList(newsList);
			}
		}
		return vo;
	}

}

package platform.social.logic.impl;

import commonTool.constant.SysparamConstant;

import platform.social.constant.SocialoathtokenConstant;
import platform.social.dto.SocialNewsResult;
import junit.framework.TestCase;

public class SocialNewsReaderImplTest extends TestCase {

	
	
	public void testReadSocialNewsFromAPI() {
		SysparamConstant.addProperty("Oauth.Google.APIKey", "AIzaSyDTPDQMrx4sJ6ITvxrZZWmeXAaCTun2biw");
		long time1 = System.currentTimeMillis();
		SocialNewsReaderImpl reader = new SocialNewsReaderImpl();
		SocialNewsResult result = reader.readSocialNewsFromAPI("102371865054310418159", SocialoathtokenConstant.ServiceType_Google_Name, -1l, null);
	    System.out.println(result.getUpdatedTime()+" | next token:"+result.getNextPageToken()+"|total time:"+(System.currentTimeMillis()-time1));
	    if(result.getNewsList()!=null && result.getNewsList().size()>0){
	    	System.out.println("----total size:"+result.getNewsList().size());
	    	int index = 6;
	    	System.out.println("----content:"+result.getNewsList().get(index).getContent());
	    	if(result.getNewsList().get(index).getAttachmentList()!=null && result.getNewsList().get(index).getAttachmentList().size()>0){
	    		System.out.println("----attachment url:"+result.getNewsList().get(index).getAttachmentList().get(0).getUrl());
	    		System.out.println("----attachment width:"+result.getNewsList().get(index).getAttachmentList().get(0).getWidth());
	    		System.out.println(result.getNewsList().get(index).getAttachmentList().get(0).getImageContent());
	    	}
	    	
	    }
	}

}

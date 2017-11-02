package platform.social.logic;

import platform.social.dto.SocialNewsResult;

public interface SocialNewsReader {

	/**
	 * read social news from any resources
	 * e.g: from Google+, facebook
	 * @return
	 */
	public SocialNewsResult readSocialNewsFromAPI(String userId, String serviceTypeName, Long newscategoryid, String accessToken);
	
}

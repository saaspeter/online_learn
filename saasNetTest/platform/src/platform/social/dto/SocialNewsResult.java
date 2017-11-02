package platform.social.dto;

import java.util.Date;
import java.util.List;

import platform.social.vo.SocialNews;

public class SocialNewsResult {

	private Date updatedTime;
	private String nextPageToken;
	private List<SocialNews> newsList;
	
	
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getNextPageToken() {
		return nextPageToken;
	}
	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}
	public List<SocialNews> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<SocialNews> newsList) {
		this.newsList = newsList;
	}
	
}

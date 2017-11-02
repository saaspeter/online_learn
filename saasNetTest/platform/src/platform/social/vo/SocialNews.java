package platform.social.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import commonTool.util.StringUtil;

@Entity
public class SocialNews {
	
    /** pk in db **/
	@Id private ObjectId objectId;
	/** e.g: in google+, field is: items[].actor.id **/
	private String authorId;
	/** e.g: in google+, items[].actor.displayName **/
	private String authorName;
	/** post title, e.g: in google+, items[].title **/
	private String title;
	/** post published time, e.g: in google+, items[].published **/
	private Date createdTime;
	/** post updated time, e.g: in google+, items[].updated **/
	private Date updatedTime;
	/** post id in its original service, e.g: in google+, items[].id .If this is empty, which means: it is created by local system **/
	private String sourceId;
	/** post original url, e.g: in google+, items[].url **/
	private String sourceUrl;
	/** post content, e.g: in google+, items[].object.content **/
	private String content;
	
	/** reference to Entity: Newscategory **/
	@Indexed(name="Idx_newscategoryid")
	private Long newscategoryid;
	/** reference to Entity: SocialNewsSource.source, e.g: google+ or facebook **/
	private String fromService;
	
	// by default fields are @Embedded, can also add this annotation tag
	private List<Attachment> attachmentList;
	
	private List<NewsComment> commentList;
	
	// must declare inner class as static, or morphia will throw exception
	@Embedded
	public static class Attachment implements Serializable{
		/** post attachment url, e.g: in google+, items[].object.attachments[].url **/
		private String url;
		/** post attachment image url, e.g: in google+, items[].object.attachments[].image.url **/
		private String imageUrl;
		/** attachment type, default: image/jpeg, e.g: in google+, items[].object.attachments[].image.type **/
		private String type;
		/** attachment size, format: width, e.g: in google+, items[].object.attachments[].image.width **/
		private String width;
		/** attachment size, format: height, e.g: in google+, items[].object.attachments[].image.height **/
		private String height;
		/** image base64 content from url **/
		private String imageContent;
		
		public Attachment(){}
		
		public Attachment(String attachmentUrl, String imageUrl, String imageType,
				String imageWidth, String imageHeight, String imageContent) {
			super();
			this.url = attachmentUrl;
			this.imageUrl = imageUrl;
			this.type = imageType;
			this.width = imageWidth;
			this.height = imageHeight;
			this.imageContent = imageContent;
		}
		
		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public String getUrl() {
			return url;
		}
		public String getType() {
			return type;
		}
		public String getWidth() {
			return width;
		}
		public String getHeight() {
			return height;
		}
		public String getImageContent() {
			return imageContent;
		}
	}
	
	@Embedded
	public static class NewsComment{
		private String content;
	    private List<NewsComment> childList;
	    /** comment userid **/
	    private Long userid;
	    private String username;
	    private Date updatedate;
	    
	    public NewsComment(){}
	    
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public List<NewsComment> getChildList() {
			return childList;
		}
		public void setChildList(List<NewsComment> childList) {
			this.childList = childList;
		}
		public Long getUserid() {
			return userid;
		}
		public void setUserid(Long userid) {
			this.userid = userid;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public Date getUpdatedate() {
			return updatedate;
		}
		public void setUpdatedate(Date updatedate) {
			this.updatedate = updatedate;
		}
	}
	
	public ObjectId getObjectId() {
		return objectId;
	}
	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}
	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
	
	public void addAttachment(String attachmentUrl, String imageUrl, String imageType, 
					String imageWidth, String imageHeight, String content)
	{
		if(attachmentList==null){
			attachmentList = new ArrayList<Attachment>();
		}
		Attachment attVO = new Attachment(attachmentUrl, imageUrl, imageType,
								imageWidth, imageHeight, content);
		attachmentList.add(attVO);
	}
	
	public List<NewsComment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<NewsComment> commentList) {
		this.commentList = commentList;
	}
	
	public Long getNewscategoryid() {
		return newscategoryid;
	}
	public void setNewscategoryid(Long newscategoryid) {
		this.newscategoryid = newscategoryid;
	}
	public String getFromService() {
		return fromService;
	}
	public void setFromService(String fromService) {
		this.fromService = fromService;
	}
	
	public String getImageUrlShow(){
		if(attachmentList!=null && attachmentList.size()>0){
			if(attachmentList.get(0).getImageContent()!=null){
				return "data:"+attachmentList.get(0).getType()+";base64,"+attachmentList.get(0).getImageContent();
			}else {
				return attachmentList.get(0).getImageUrl();
			}
		}else {
			return null;
		}
	}
	
	/**
	 * get attachment click url, when click attachment, go to this url
	 * @return
	 */
	public String getAttachmentClickUrl(){
		if(attachmentList!=null && attachmentList.size()>0){
			return attachmentList.get(0).getUrl();
		}else {
			return null;
		}
	}
	
	/**
	 * 是不是本系统user创建的social news, 或者是从别的网站读取的(e.g.: from google+)
	 * @return
	 */
	public boolean isFromLocalUser(){
		if(StringUtil.isEmpty(sourceId) && StringUtil.isEmpty(sourceUrl)){
			return true;
		}else {
			return false;
		}
	}
	
}

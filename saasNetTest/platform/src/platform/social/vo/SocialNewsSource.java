package platform.social.vo;

import java.util.Date;

public class SocialNewsSource {
	
	private Long id;

    private Long newscategoryid;

    /** google+ or facebook **/
    private String source;

    /** get news from which account **/
    private String fromaccount;

    /** 取得的最新消息的时间,用户比较下一批抓取的消息,比这个时间晚的才插入db **/
    private Date synctime;
    
    private Date lastupdatetime;

    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNewscategoryid() {
		return newscategoryid;
	}

	public void setNewscategoryid(Long newscategoryid) {
		this.newscategoryid = newscategoryid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFromaccount() {
		return fromaccount;
	}

	public void setFromaccount(String fromaccount) {
		this.fromaccount = fromaccount;
	}

	public Date getSynctime() {
		return synctime;
	}

	public void setSynctime(Date synctime) {
		this.synctime = synctime;
	}

	public Date getLastupdatetime() {
		return lastupdatetime;
	}

	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}

}
package com.joybeacon.model;

import java.util.Date;

public class Infopage {

	private Long articleid;

    private Long shopid;
	
    private String title;

    private Long beaconid;

    private Date createtime;

    private Date updatetime;

    private String content;


    public Long getArticleid() {
		return articleid;
	}

	public void setArticleid(Long articleid) {
		this.articleid = articleid;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getBeaconid() {
        return beaconid;
    }

    public void setBeaconid(Long beaconid) {
        this.beaconid = beaconid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
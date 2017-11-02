package com.joybeacon.model;

import java.util.Date;

public class Beacon {
    
	private Long beaconid;
	
	private String citycode;
	
	private Long shopid;
	
    private String beaconname;

    private String uuid;

    private String major;

    private String minor;

    private Date createtime;

    private Date updatetime;

    
    public Long getBeaconid() {
		return beaconid;
	}

	public void setBeaconid(Long beaconid) {
		this.beaconid = beaconid;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public String getBeaconname() {
        return beaconname;
    }

    public void setBeaconname(String beaconname) {
        this.beaconname = beaconname;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
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
    
}
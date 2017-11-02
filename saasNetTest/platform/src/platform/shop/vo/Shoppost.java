package platform.shop.vo;

import java.util.Date;

import platform.logic.Container;
import platform.vo.ShopMini;
import commonTool.base.BaseVOBase;

public class Shoppost extends BaseVOBase implements Container{

    private Long id;

    private Long shopid;

    private String syscode;

    private String caption;

    private Date createtime;

    private Long creator;
    
    private String content = "";

    /** 1:商店简介；2商店公告；3产品推广；50来自netTest的消息 **/
    private Short type;
    
    private String logourl;
    
    private String openurl;
    
    private Long refid;
    
    private Short status;
    
    private Integer orderno;
    
    public static final String ObjectType = "shoppost";
    
    public static final Short TYPE_ShopPost = 2;
//    public static final Short TYPE_FromNestest = 50;
    
    
    /** 自动保存状态，不能被正常搜索出来。为了在新增的post页面上可以上传文件 **/
    public static final Short Status_AutoSave = 3;
    

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getSyscode() {
        return syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public Short getType() {
		return type;
	}


	public void setType(Short type) {
		this.type = type;
	}


	public String getLogourl() {
		return logourl;
	}


	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}


	public String getOpenurl() {
		return openurl;
	}


	public void setOpenurl(String openurl) {
		this.openurl = openurl;
	}


	public Long getRefid() {
		return refid;
	}


	public void setRefid(Long refid) {
		this.refid = refid;
	}


	public Short getStatus() {
		return status;
	}


	public void setStatus(Short status) {
		this.status = status;
	}


	public Integer getOrderno() {
		return orderno;
	}


	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}


	@Override
	protected String getObjectType() {
		return ObjectType;
	}


	public Long getContainerID() {
		return shopid;
	}

	public String getContainerType() {
		return ShopMini.ObjectType;
	}
	
	public boolean isSameShop(Long shopid){
		if(shopid!=null && shopid.equals(this.shopid)){
			return true;
		}else {
			return false;
		}
	}
}
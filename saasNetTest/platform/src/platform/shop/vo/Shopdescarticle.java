package platform.shop.vo;

import java.util.Date;

import platform.logic.Container;
import platform.vo.Shop;

public class Shopdescarticle implements Container{

    private Long articleid;

    private Long shopid;

    private Short articletype;

    private Date createtime;

    private Long creator;

    private String content;
    
    public static final String ObjectType = "shopdescarticle";
    
    /** 商店介绍的文章，目前不放在该表中，放在shop.desc字段中 **/
    public static final Short ArticleType_ShopIntroduce = 1;
    /** 商店教师情况的文章 **/
    public static final Short ArticleType_TeacherIntroduce = 2;


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


    public Short getArticletype() {
        return articletype;
    }


    public void setArticletype(Short articletype) {
        this.articletype = articletype;
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


	public Long getContainerID() {
		return shopid;
	}

	public String getContainerType() {
		return Shop.ObjectType;
	}
    
	public boolean isSameShop(Long shopid){
		if(shopid!=null && shopid.equals(this.shopid)){
			return true;
		}else {
			return false;
		}
	}
}
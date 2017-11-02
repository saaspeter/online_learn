package platform.vo;

public class Shopstyleconfig {

    private Long shopid;

    private String pagestyle;

    private String pagetemplate;

    private String bannerimg;
    
    public static final String ObjectType = "shopstyleconfig";


    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getPagestyle() {
        return pagestyle;
    }

    public void setPagestyle(String pagestyle) {
        this.pagestyle = pagestyle;
    }

    public String getBannerimg() {
        return bannerimg;
    }

    public void setBannerimg(String bannerimg) {
        this.bannerimg = bannerimg;
    }

	public String getPagetemplate() {
		return pagetemplate;
	}

	public void setPagetemplate(String pagetemplate) {
		this.pagetemplate = pagetemplate;
	}

}
package commonWeb.security.vo;

import java.io.Serializable;

public class Rolesvalue implements Serializable{

    private Long valueid;

    private Long id;

    private Long localeid;

    private String name;

    private Long shopid;
    
    private String descn;


    public Long getValueid() {
        return valueid;
    }

    public void setValueid(Long valueid) {
        this.valueid = valueid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocaleid() {
        return localeid;
    }

    public void setLocaleid(Long localeid) {
        this.localeid = localeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}
    
}
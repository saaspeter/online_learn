package platform.vo;

import java.util.Date;

public class Reversesearch {
 
    private String searchtext;

    private Long entityid;

    private String entitytype;

    private Date createtime;

    public String getSearchtext() {
        return searchtext;
    }

    public void setSearchtext(String searchtext) {
        this.searchtext = searchtext;
    }

     public Long getEntityid() {
        return entityid;
    }

    public void setEntityid(Long entityid) {
        this.entityid = entityid;
    }

    public String getEntitytype() {
        return entitytype;
    }

    public void setEntitytype(String entitytype) {
        this.entitytype = entitytype;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
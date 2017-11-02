package commonTool.biz.vo;

import java.util.Date;

public class Systemlog {

    private Long logid;

    private String logcode;

    private Date createtime;

    private Date endtime;

    private Short result;

    private String content;


    public Long getLogid() {
        return logid;
    }

    public void setLogid(Long logid) {
        this.logid = logid;
    }

    public String getLogcode() {
        return logcode;
    }

    public void setLogcode(String logcode) {
        this.logcode = logcode;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Short getResult() {
        return result;
    }

    public void setResult(Short result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
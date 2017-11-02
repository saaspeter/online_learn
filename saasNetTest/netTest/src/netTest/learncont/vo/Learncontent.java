package netTest.learncont.vo;

import java.util.Date;

import netTest.common.vo.NettestBaseVO;
import netTest.learncont.constant.LearncontentConstant;
import netTest.product.vo.ProductMini;

public class Learncontent extends NettestBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1960885379546479049L;

	private Long contentid;

    private String caption;

    private Long contentcateid;
    
    private String contentcatename;
    
    /** 学习的类型， 0: zipped file type, 1：html文档资料，2：pdf文档; 3：word文档，4: powerpoint；5:excel文档，6：视频资料，7 音频资料,8：flash课件(swf) **/
    private Integer contenttype;
    
    private String content;

    /** 参见constant **/
    private Short linktype;
    /** 是否是试用产品资料,1 正常使用 2 试用 **/
    private Short istry;
    
    /** 外链文件id,这个主要对第3方存储, 例如存储skydrive中的fileID */
    private String linkfileid;
    /** 外链文件地址，一般是social file的source */
    private String linkfilesource;
    /** file name **/
    private String linkfilename;
    /** update time for linkeFileSource, because linkFileSource will become inactive some time later, 
     *  then need update fileSource using accessToken, then update this record
     */
    private Date linkfiledate;
    
    /** when use dropbox/skydrive link, here is for 3-part userID **/
    private String linkuserid;
    
    private Long filesize;
    
    // 1: can be downloaded, 2: cannot be downloaded
    private Short downloadable;
    // e.g: when a learcontent has a downloadable source code, then the parentID is the source learncontent
    private Long parentid;
    
    private Integer orderno;
    
    private Date createtime;
    
    private Date lastupdatetime;

    private Long creator;
    
    /** 1:有效，2失效 **/
    private Short status;
    
    /** 记录第3方存储的帐号名称, 不对应db字段 **/
    private String linkusername;
    
    //  上次学习时间，不对应db字段
    private Date lastaccesstime; 
    
    // 见LearncontentConstant中的Learntype定义, 不对应db中字段
 	private Short learntype;
    
    // 暂时不用这个功能
    //private List<Learncontent> sublist;
    
    public static final String ObjectType = "learncontent";
    
        

    public Long getContentid() {
        return contentid;
    }

    public void setContentid(Long contentid) {
        this.contentid = contentid;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Long getContentcateid() {
        return contentcateid;
    }

    public void setContentcateid(Long contentcateid) {
        this.contentcateid = contentcateid;
    }

    public Integer getContenttype() {
        return contenttype;
    }

    public void setContenttype(Integer contenttype) {
        this.contenttype = contenttype;
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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getContentcatename() {
		return contentcatename;
	}

	public void setContentcatename(String contentcatename) {
		this.contentcatename = contentcatename;
	}
	
	public Short getLinktype() {
		return linktype;
	}

	public void setLinktype(Short linktype) {
		this.linktype = linktype;
	}

	// 展现每种不同类型文件的图标，在列表页面会用到
	public String getFileicon(){
		String str = "filetype_blank.png";
		if(LearncontentConstant.ContentType_HTML.equals(contenttype))
			str = "filetype_webpage.png";
		else if(LearncontentConstant.ContentType_PDF.equals(contenttype))
			str = "filetype_pdf.png";
		else if(LearncontentConstant.ContentType_WORD.equals(contenttype))
			str = "filetype_word.png";
		else if(LearncontentConstant.ContentType_PPT.equals(contenttype))
			str = "filetype_ppt.png";
		else if(LearncontentConstant.ContentType_EXCEL.equals(contenttype))
			str = "filetype_excel.png";
		else if(LearncontentConstant.ContentType_VIDEO.equals(contenttype))
			str = "filetype_video.png";
		else if(LearncontentConstant.ContentType_AUDIO.equals(contenttype))
			str = "filetype_audio.png";
		else if(LearncontentConstant.ContentType_FLASH.equals(contenttype))
			str = "filetype_flash.png";
		else if(LearncontentConstant.ContentType_Zipped.equals(contenttype))
			str = "filetype_download.png";
		return str;
	}

	@Override
	protected String getObjectType() {
		return "learncontent";
	}

	public Short getIstry() {
		return istry;
	}

	public void setIstry(Short istry) {
		this.istry = istry;
	}

	public String getLinkfileid() {
		return linkfileid;
	}

	public void setLinkfileid(String linkfileid) {
		this.linkfileid = linkfileid;
	}

	public String getLinkfilesource() {
		return linkfilesource;
	}

	public void setLinkfilesource(String linkfilesource) {
		this.linkfilesource = linkfilesource;
	}
	
	public Long getContainerID() {
		return productbaseid;
	}

	public String getContainerType() {
		return ProductMini.ObjectType;
	}

	public Date getLastaccesstime() {
		return lastaccesstime;
	}

	public void setLastaccesstime(Date lastaccesstime) {
		this.lastaccesstime = lastaccesstime;
	}

	public Date getLinkfiledate() {
		return linkfiledate;
	}

	public void setLinkfiledate(Date linkfiledate) {
		this.linkfiledate = linkfiledate;
	}

	public String getLinkuserid() {
		return linkuserid;
	}

	public void setLinkuserid(String linkuserid) {
		this.linkuserid = linkuserid;
	}

	public String getLinkusername() {
		return linkusername;
	}

	public void setLinkusername(String linkusername) {
		this.linkusername = linkusername;
	}

	public String getLinkfilename() {
		return linkfilename;
	}

	public void setLinkfilename(String linkfilename) {
		this.linkfilename = linkfilename;
	}

	public Date getLastupdatetime() {
		return lastupdatetime;
	}

	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}

	public Short getDownloadable() {
		return downloadable;
	}

	public void setDownloadable(Short downloadable) {
		this.downloadable = downloadable;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	
	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public Short getLearntype() {
		if(LearncontentConstant.ContentType_Zipped.equals(contenttype)){
			return LearncontentConstant.LearnType_Attachment;
		}else {
			return LearncontentConstant.LearnType_Online;
		}
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

//	public List<Learncontent> getSublist() {
//		return sublist;
//	}
//
//	public void setSublist(List<Learncontent> sublist) {
//		this.sublist = sublist;
//	}
	
}
package netTest.prodcont.vo;

import java.util.ArrayList;
import java.util.List;

import netTest.common.vo.NettestBaseVO;
import netTest.exercise.vo.Exercise;
import netTest.learncont.constant.LearncontentConstant;
import netTest.learncont.vo.Learncontent;
import netTest.product.vo.ProductMini;

import commonTool.base.TreeVOInf;

public class Contentcate extends NettestBaseVO implements TreeVOInf {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long contentcateid;

    private String contentcatename;

    private String description;

    private Long pid;

    private String path;

    private Integer disorder;
    
	private Integer childnum;
	
	private List<Contentcate> sublist;
	
	private List<Learncontent> learncontentlist;
	// learncontent which can learn online, not dowanloaded zip file, 由learncontentlist生成
	private List<Learncontent> onlinelearnlist;
	// learncontent which is downloadable, 由learncontentlist生成
	private List<Learncontent> downloadlearnlist;
	
	private List<Exercise> exerlist;

	public final static String ObjectType = "contentcate";
	

    public Long getContentcateid() {
        return contentcateid;
    }

    public void setContentcateid(Long contentcateid) {
        this.contentcateid = contentcateid;
    }

    public String getContentcatename() {
        return contentcatename;
    }

    public void setContentcatename(String contentcatename) {
        this.contentcatename = contentcatename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getDisorder() {
        return disorder;
    }

    public void setDisorder(Integer disorder) {
        this.disorder = disorder;
    }
    
    /**
     * 得到主键的值
     * @return
     */
	public Long getPk(){
		return contentcateid;
	}
	
	/**
	 * 将vo转换为树叶要显示的值
	 * @return
	 */
	public String toTreeString(){
		return contentcatename;
	}
	
	/**
	 * 得到该项下的节点的数目
	 * @return
	 */
	public Integer getChildnum(){
		return childnum;
	}

	public void setChildnum(Integer childnum) {
		this.childnum = childnum;
	}

	@Override
	protected String getObjectType() {
		return "contentcate";
	}
	
	public Long getContainerID() {
		return productbaseid;
	}

	public String getContainerType() {
		return ProductMini.ObjectType;
	}
	
	public List<Contentcate> getSublist() {
		return sublist;
	}

	public void setSublist(List<Contentcate> sublist) {
		this.sublist = sublist;
	}
	
	public boolean hasSubList(){
		if(sublist!=null && sublist.size()>0){
			return true;
		}else {
			return false;
		}
	}

	public List<Learncontent> getLearncontentlist() {
		return learncontentlist;
	}

	public void setLearncontentlist(List<Learncontent> learncontentlist) {
		this.learncontentlist = learncontentlist;
		List<Learncontent>[] array = autoSplitLearnList(learncontentlist);
		if(array!=null){
		    this.onlinelearnlist = array[0];
		    this.downloadlearnlist = array[1];
		}else {
			this.onlinelearnlist = null;
		    this.downloadlearnlist = null;
		}
	}
	
	public List<Exercise> getExerlist() {
		return exerlist;
	}

	public void setExerlist(List<Exercise> exerlist) {
		this.exerlist = exerlist;
	}

	public List<Learncontent> getOnlinelearnlist() {
		return onlinelearnlist;
	}
    
	public List<Learncontent> getDownloadlearnlist() {
		return downloadlearnlist;
	}
	
	public boolean hasLearncontent(){
		if((onlinelearnlist!=null && onlinelearnlist.size()>0)
             || (exerlist!=null && exerlist.size()>0)
             || (downloadlearnlist!=null && downloadlearnlist.size()>0))
		{
			return true;
		}else {
			return false;
		}
	}

	private List<Learncontent>[] autoSplitLearnList(List<Learncontent> alllist){
		List<Learncontent> onlinelist = null;
		List<Learncontent> downloadlist = null;
		if(alllist!=null && alllist.size()>0){
			onlinelist = new ArrayList<Learncontent>();
			downloadlist = new ArrayList<Learncontent>();
			for(Learncontent vo : alllist){
				if(LearncontentConstant.LearnType_Attachment.equals(vo.getLearntype())){
					downloadlist.add(vo);
				}else {
					onlinelist.add(vo);
				}
			}
		}
		return new List[]{onlinelist, downloadlist};
	}
	
}
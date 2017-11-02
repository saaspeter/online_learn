
package netTestWeb.learncont.form;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.exercise.vo.Exercise;
import netTest.learncont.dto.LearncontentQuery;
import netTest.learncont.vo.Learncontent;
import netTest.prodcont.vo.Contentcate;
import netTest.product.vo.Learnactivity;
import netTest.product.vo.Productbase;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


public class LearncontentForm extends BaseForm {
	
	private Learncontent vo;
	private LearncontentQuery queryVO;
	/** 1为管理界面的，2为学员自学列出的 **/
	private int listType;
	
	private FormFile uploadFile;
	
	private List<Learncontent> datalist;
	
	private List<Exercise> exerlist;
	
	private List<Contentcate> catelist;
	
	/** 正在使用的学习资料对应的contentcate **/
	private Contentcate usecatevo;
	
	/** 课程中不属于任何章节的虚拟对象，其中包括了: 学习资料和练习 **/
	private Contentcate topcatevo;
	
	// 当用户点击继续学习是传递当前学习的学习资料信息
	private Long objectid;
	private String objecttype;
	
	private Learnactivity activityvo;
	
	private String nouse;
	
	private Long lastcontentcateid;
	
	private String lastcontentcatename;
	
	private Date lastlearndate;
	/** whether it is attachment for learncontent? 0 means: no, 1:yes **/
	//private int isattachment;
	
	private String uploadtoken;
	
	private String verifykey;
	
	private boolean ableuploadlocal;
	
	private boolean ableuploadfileserver;
	
	private Productbase productvo;
	
	
	public String getNouse() {
		return nouse;
	}

	public void setNouse(String nouse) {
		this.nouse = nouse;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Learncontent();
		queryVO = new LearncontentQuery();
		ableuploadfileserver = false;
		ableuploadlocal = false;
		verifykey = null;
	}
	
	public String getDoclevelcheckbox(){
		if(queryVO.getQuerydoclevel()==1){
			return "checked=\"checked\"";
		}else {
			return "";
		}
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}
	
	public LearncontentQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(LearncontentQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Learncontent getVo() {
		return vo;
	}

	public void setVo(Learncontent vo) {
		this.vo = vo;
	}

	public FormFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public int getListType() {
		return listType;
	}

	public void setListType(int listType) {
		this.listType = listType;
	}

	public List<Learncontent> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<Learncontent> datalist) {
		this.datalist = datalist;
	}

	public List<Exercise> getExerlist() {
		return exerlist;
	}

	public void setExerlist(List<Exercise> exerlist) {
		this.exerlist = exerlist;
	}

	public List<Contentcate> getCatelist() {
		return catelist;
	}

	public void setCatelist(List<Contentcate> catelist) {
		this.catelist = catelist;
	}

	public Contentcate getUsecatevo() {
		return usecatevo;
	}

	public void setUsecatevo(Contentcate usecatevo) {
		this.usecatevo = usecatevo;
	}

	public Contentcate getTopcatevo() {
		return topcatevo;
	}

	public void setTopcatevo(Contentcate topcatevo) {
		this.topcatevo = topcatevo;
	}

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getObjecttype() {
		return objecttype;
	}

	public void setObjecttype(String objecttype) {
		this.objecttype = objecttype;
	}

	public Learnactivity getActivityvo() {
		return activityvo;
	}

	public void setActivityvo(Learnactivity activityvo) {
		this.activityvo = activityvo;
	}

	public Long getLastcontentcateid() {
		return lastcontentcateid;
	}

	public void setLastcontentcateid(Long lastcontentcateid) {
		this.lastcontentcateid = lastcontentcateid;
	}

	public String getLastcontentcatename() {
		return lastcontentcatename;
	}

	public void setLastcontentcatename(String lastcontentcatename) {
		this.lastcontentcatename = lastcontentcatename;
	}

	public Date getLastlearndate() {
		return lastlearndate;
	}

	public void setLastlearndate(Date lastlearndate) {
		this.lastlearndate = lastlearndate;
	}

	public String getUploadtoken() {
		return uploadtoken;
	}

	public void setUploadtoken(String uploadtoken) {
		this.uploadtoken = uploadtoken;
	}

	public boolean isAbleuploadlocal() {
		return ableuploadlocal;
	}

	public void setAbleuploadlocal(boolean ableuploadlocal) {
		this.ableuploadlocal = ableuploadlocal;
	}

	public boolean isAbleuploadfileserver() {
		return ableuploadfileserver;
	}

	public void setAbleuploadfileserver(boolean ableuploadfileserver) {
		this.ableuploadfileserver = ableuploadfileserver;
	}

	public String getVerifykey() {
		return verifykey;
	}

	public void setVerifykey(String verifykey) {
		this.verifykey = verifykey;
	}

	public Productbase getProductvo() {
		return productvo;
	}

	public void setProductvo(Productbase productvo) {
		this.productvo = productvo;
	}
	
}

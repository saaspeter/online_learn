
package netTestWeb.orguser.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.orguser.dto.OrguserQuery;
import netTest.orguser.vo.Orgbase;
import netTest.orguser.vo.Orguser;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import platform.vo.Custinfoex;
import platform.vo.Usercontactinfo;


public class OrguserForm extends BaseForm {
	
	private Orguser vo;
	private OrguserQuery queryVO;
	private Orgbase orgVO;
	
	/** 选择的产品id字符串 **/
	private String productStr;
	/** 用户产品的状态 **/
	private Short userprodstat;
	
	private Long neworgid;
	
	/** 商店代号，用于生成用户的全局名称 **/
	private String shopcode;
	/** 为2时代表选择人员的界面，其余为管理人员的界面 **/
	private int urlType;
	
	/** session orgid **/
	private Long sessionorgid;
	/** session org name **/
	private String sessionorgname;
	
	/** 用户扩展信息 **/
	private Custinfoex custinfoex;
	/** 用户联系信息，用于保存用户信息时 **/
	private Usercontactinfo contactinfo;

	/** 导入用户文件 **/
	private FormFile userFile;
    
	/** shop收费类型 **/
    private Short shopchargetype;
    
    /** 导入orguser成功数目 **/
    private int importSuccessNumber;
    /** 导入orguser失败数目 **/
	private int importFailNumber;
	private List<String> messageList;
	
	
	public FormFile getUserFile() {
		return userFile;
	}

	public void setUserFile(FormFile userFile) {
		this.userFile = userFile;
	}

	public int getUrlType() {
		return urlType;
	}

	public void setUrlType(int urlType) {
		this.urlType = urlType;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Orguser();
		queryVO = new OrguserQuery();
		shopcode = null;
		urlType = 0;
		userprodstat = null;
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
	
	public OrguserQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(OrguserQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Orguser getVo() {
		return vo;
	}

	public void setVo(Orguser vo) {
		this.vo = vo;
	}

	public String getShopcode() {
		return shopcode;
	}

	public void setShopcode(String shopcode) {
		this.shopcode = shopcode;
	}

	public String getProductStr() {
		return productStr;
	}

	public void setProductStr(String productStr) {
		this.productStr = productStr;
	}

	public Short getUserprodstat() {
		return userprodstat;
	}

	public void setUserprodstat(Short userprodstat) {
		this.userprodstat = userprodstat;
	}

	public Orgbase getOrgVO() {
		return orgVO;
	}

	public void setOrgVO(Orgbase orgVO) {
		this.orgVO = orgVO;
	}

	public Long getNeworgid() {
		return neworgid;
	}

	public void setNeworgid(Long neworgid) {
		this.neworgid = neworgid;
	}

	public Long getSessionorgid() {
		return sessionorgid;
	}

	public void setSessionorgid(Long sessionorgid) {
		this.sessionorgid = sessionorgid;
	}

	public String getSessionorgname() {
		return sessionorgname;
	}

	public void setSessionorgname(String sessionorgname) {
		this.sessionorgname = sessionorgname;
	}

	public Custinfoex getCustinfoex() {
		if(custinfoex==null){
			custinfoex = new Custinfoex();
		}
		return custinfoex;
	}

	public void setCustinfoex(Custinfoex custinfoex) {
		this.custinfoex = custinfoex;
	}
	
	public Usercontactinfo getContactinfo() {
		if(contactinfo==null){
			contactinfo = new Usercontactinfo();
		}
		return contactinfo;
	}

	public void setContactinfo(Usercontactinfo contactinfo) {
		this.contactinfo = contactinfo;
	}
	
	public Short getShopchargetype() {
		return shopchargetype;
	}

	public void setShopchargetype(Short shopchargetype) {
		this.shopchargetype = shopchargetype;
	}
	
	public int getImportSuccessNumber() {
		return importSuccessNumber;
	}

	public void setImportSuccessNumber(int importSuccessNumber) {
		this.importSuccessNumber = importSuccessNumber;
	}

	public List<String> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}

	public int getImportFailNumber() {
		return importFailNumber;
	}

	public void setImportFailNumber(int importFailNumber) {
		this.importFailNumber = importFailNumber;
	}

}

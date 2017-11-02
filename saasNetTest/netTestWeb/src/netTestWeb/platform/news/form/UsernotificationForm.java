
package netTestWeb.platform.news.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import commonTool.biz.vo.Usernotification;
import commonTool.util.StringUtil;


public class UsernotificationForm extends BaseForm {
	
	private Usernotification vo;
	private List volist;
	
	/** 支持多个loginname, 间隔符是英文; **/
	private String touserloginame;
	/** 支持多个userid, 间隔符是英文; **/
	private String touseridStr;
	
	/**
	 * 0: search by touserID, 1: search by creatorID
	 */
	private int showusertype;
	
	/** 是否可以reply, 0代表不可以，1代表可以 **/
	private int canreply;
	
	/** 支持直接发送给shop或product admin, 此时要传shop or product **/
	private String objectadmintype;
	
	private Long objectid; 

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Usernotification();
		showusertype = 0;
		touserloginame = null;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		//if(queryVO!=null){
		//   if(request.getParameter("queryVO.categorylevel")==null
		//      ||(!((String)request.getParameter("queryVO.categorylevel")).matches("^\\d+$")))
		//    	queryVO.setCategorylevel(null);
		//}
		return null;
	}
	
	public Usernotification getVo() {
		return vo;
	}

	public void setVo(Usernotification vo) {
		this.vo = vo;
	}

	public List getVolist() {
		return volist;
	}

	public void setVolist(List volist) {
		this.volist = volist;
	}

	public int getShowusertype() {
		return showusertype;
	}

	public void setShowusertype(int showusertype) {
		this.showusertype = showusertype;
	}

	public String getTouserloginame() {
		return touserloginame;
	}

	public void setTouserloginame(String touserloginame) {
		this.touserloginame = touserloginame;
	}

	public String getTouseridStr() {
		return touseridStr;
	}

	public void setTouseridStr(String touseridStr) {
		this.touseridStr = touseridStr;
	}
	
	public List<Long> getTouseridArr(){
		List<Long> idArr = null;
		if(touseridStr!=null && touseridStr.length()>0){
			String[] idstrArr = StringUtil.splitString(touseridStr, ";");
			idArr = new ArrayList<Long>();
			for(int i=0; i<idstrArr.length; i++){
				if(idstrArr[i]!=null && idstrArr[i].trim().length()>0){
					idArr.add(new Long(idstrArr[i].trim()));
				}
			}
		}
		return idArr;
	}

	public int getCanreply() {
		return canreply;
	}

	public void setCanreply(int canreply) {
		this.canreply = canreply;
	}

	public String getObjectadmintype() {
		return objectadmintype;
	}

	public void setObjectadmintype(String objectadmintype) {
		this.objectadmintype = objectadmintype;
	}

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

}

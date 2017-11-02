
package commonWeb.security.webform;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import platform.vo.User;
import commonWeb.base.BaseForm;
import commonWeb.security.dto.UserRoleQuery;
import commonWeb.security.vo.Roles;
import commonWeb.security.vo.UserRole;

/** 
 * MyEclipse Struts
 */
public class UserRoleForm extends BaseForm {
	
	private User usersVO;
	
	private Roles rolesVO;
	
	private UserRole vo;
	private UserRoleQuery queryVO;
	
	private String roleIds; // 权限id字符串，其中以','隔开
	
	private String userIds; // 用户id字符串，其中以','隔开
	
	private String[] keys;

	/** 搜索类型 1为只搜索本目录，2为包括下级数据的搜索�?3为包含所有级别的数据 **/
	private String complexSearchDivStatus;
	
	
	private List rescList;
	
	private List roleList;
	
	private String syscode;
	
	/** 0代表shop admin自己检查权限的. 1代表系统管理员查看 **/
	private String securitylevel;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new UserRole();
		usersVO = new User();
		rolesVO = new Roles();
		queryVO = new UserRoleQuery();
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
	
	public UserRoleQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(UserRoleQuery queryVO) {
		this.queryVO = queryVO;
	}

	public UserRole getVo() {
		return vo;
	}

	public void setVo(UserRole vo) {
		this.vo = vo;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	public String getComplexSearchDivStatus() {
		return complexSearchDivStatus;
	}

	public void setComplexSearchDivStatus(String complexSearchDivStatus) {
		this.complexSearchDivStatus = complexSearchDivStatus;
	}

	public Roles getRolesVO() {
		return rolesVO;
	}

	public void setRolesVO(Roles rolesVO) {
		this.rolesVO = rolesVO;
	}

	public User getUsersVO() {
		return usersVO;
	}

	public void setUsersVO(User usersVO) {
		this.usersVO = usersVO;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public List getRescList() {
		return rescList;
	}

	public void setRescList(List rescList) {
		this.rescList = rescList;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public List getRoleList() {
		return roleList;
	}

	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}

	public String getSecuritylevel() {
		return securitylevel;
	}

	public void setSecuritylevel(String securitylevel) {
		this.securitylevel = securitylevel;
	}
		
}

package netTest.common.session;

import netTest.product.constant.UserproductConstant;

import org.springframework.security.GrantedAuthority;

import commonTool.util.StringUtil;
import commonWeb.security.authentication.UserinfoSession;

public class LoginInfoEdu extends UserinfoSession {
	
	private static final long serialVersionUID = -8873354506487817710L;

	/** 登录用户类型：1为租户，2为学员，3为单位操作员 **/
    //private Short usershoptype;
    private Short isfreeforall;
    // 用户所在的org
    private Long orgbaseid;
    private String orgname;
    /** 用户在商店中显示的名称,是usershop中的nickname **/
    private String nickname;
    
    /** 选择的产品id **/
    protected Long productid;
    /** 选择的产品名 **/
    protected String productname;
    
    /** 用户和产品的关系,仅在为2（应用于部分产品）时，才在productList中记录拥有的产品 **/
    private Short areainproduct;
    /** 记录用户拥有所有可用产品id的字符串，其中用","隔开产品id **/
    //private String allProdIdStr;
    /** 可以使用产品的id的字符串 **/
    private String prodIdUseStr;
    /** 可以管理产品的id的字符串 **/
    private String prodIdMagStr;
    
    

    public LoginInfoEdu(String loginname, String password, boolean enabled,
			boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired,
			GrantedAuthority[] authorities)
	{
    	super(loginname, password, enabled, accountNonExpired, accountNonLocked, credentialsNonExpired,
    			authorities);
	}

	public Long getOrgbaseid() {
		return orgbaseid;
	}

	public void setOrgbaseid(Long orgbaseid) {
		this.orgbaseid = orgbaseid;
	}

	public Short getIsfreeforall() {
		return isfreeforall;
	}

	public void setIsfreeforall(Short isfreeforall) {
		this.isfreeforall = isfreeforall;
	}

//	public Short getUsershoptype() {
//		return usershoptype;
//	}
//
//	public void setUsershoptype(Short usershoptype) {
//		this.usershoptype = usershoptype;
//	}

	public Short getAreainproduct() {
		return areainproduct;
	}

	public void setAreainproduct(Short areainproduct) {
		this.areainproduct = areainproduct;
	}

//	public String getAllProdIdStr() {
//		return allProdIdStr;
//	}
//
//	public void setAllProdIdStr(String allProdIdStr) {
//		this.allProdIdStr = allProdIdStr;
//	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public Long getProductid() {
		return productid;
	}

	public void setProductid(Long productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProdIdMagStr() {
		return prodIdMagStr;
	}

	public void setProdIdMagStr(String prodIdMagStr) {
		this.prodIdMagStr = prodIdMagStr;
	}

	public String getProdIdUseStr() {
		return prodIdUseStr;
	}

	public void setProdIdUseStr(String prodIdUseStr) {
		this.prodIdUseStr = prodIdUseStr;
	}

	public void addProdId(Long productid, Short usetype){
		if(UserproductConstant.ProdUseType_operatorMag.equals(usetype)){
			if(!StringUtil.includeStr(prodIdMagStr, productid.toString(), ",")){
				if(prodIdMagStr==null){
					setProdIdMagStr(productid.toString());
				}else if(prodIdMagStr.endsWith(",")){
					setProdIdMagStr(prodIdMagStr+productid.toString());
				}else {
					setProdIdMagStr(prodIdMagStr+","+productid.toString());
				}
			}
			// add productid into use string
			if(!StringUtil.includeStr(prodIdUseStr, productid.toString(), ",")){
				if(prodIdUseStr==null){
					setProdIdUseStr(productid.toString());
				}else if(prodIdUseStr.endsWith(",")){
					setProdIdUseStr(prodIdUseStr+productid.toString());
				}else {
					setProdIdUseStr(prodIdUseStr+","+productid.toString());
				}
			}
		}else {
			if(!StringUtil.includeStr(prodIdUseStr, productid.toString(), ",")){
				if(prodIdUseStr==null){
					setProdIdUseStr(productid.toString());
				}else if(prodIdUseStr.endsWith(",")){
					setProdIdUseStr(prodIdUseStr+productid.toString());
				}else {
					setProdIdUseStr(prodIdUseStr+","+productid.toString());
				}
			}
		}
	}

	public String getNickname() {
		if(nickname!=null) {
		    return nickname;
		}else {
			return this.getDisplayname();
		}
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}

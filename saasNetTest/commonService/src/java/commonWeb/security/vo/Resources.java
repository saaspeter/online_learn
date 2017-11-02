package commonWeb.security.vo;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.security.GrantedAuthority;
import org.springframework.util.Assert;
import commonTool.base.BaseVOBase;
import commonTool.base.TreeVOInf;
import commonTool.exception.LogicException;
import commonTool.util.DOM4JUtil;
import commonWeb.security.resource.ResourceInf;

public class Resources extends BaseVOBase implements ResourceInf,TreeVOInf{

    protected Long id;

    protected String name;

    protected String resType;

    protected String resString; 
   
    protected String syscode;
    
    protected Long pid;
    
    protected String pidName;
    
    protected Short operType;
    
    protected Short status;
    
    protected String path;
    /** 是否是资源目录文件夹，0代表是正常的privilege，1代表是资源目录，是为了更好地分类privilege **/
    protected Short rescfold;
    
    protected Integer ordNo;
    
    protected Long linkid;
    
    protected Short visible;
    
    protected String descn;
    
    protected Long localeid;
    
    private Integer childnum;
    
    /** 记录request url中记录objectid的参数名字和objectype. 例如:
     * objectidparam: vo.id, objecttype:ware
     * 代表request中的vo.id记录的是objectid, 而objecttype是ware,因为每个请求的都应该是知道objecttype的 **/
    private String objectparam;
    // see objectparam
    private Map<String, String> objectnameMap;
    
    /** policy表达式 **/
    private String expression;
    
    private Element policyruleElement;
    
    private GrantedAuthority[] authorities;
    
    
    /** 资源类型常量，URL **/
    public static final String ResType_URL = "URL";
    /** 资源类型常量，TAG **/
    public static final String ResType_MENU = "MENU";
    /** 资源类型常量，METHOD **/
    public static final String ResType_METHOD = "METHOD";
    /** 资源类型常量，TAG **/
    public static final String ResType_TAG = "TAG";
    
    /** 记录参数中object相关key **/
    public static final String Key_Objectid_name = "objectidname";
    public static final String Key_Objecttype = "objecttype";
    public static final String Key_containerid_name = "containeridname";
    public static final String Key_containertype = "containertype";
    
    /** 是否是资源目录文件夹，0代表是正常的privilege，1代表是资源目录，是为了更好地分类privilege **/
    public static final Short RescFold_Priv = 0;
    public static final Short RescFold_Fold = 1;
    
    public static final Short Visible_visible = 1;
    public static final Short Visible_invisible = 0;

	/** 查询类型。1为本菜单的权限，2为菜单下所有权限 **/
	private Integer searchListType;

	public final static String ObjectType = "resources";
	
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getResString() {
        return resString;
    }

    public void setResString(String resString) {
        this.resString = resString;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Integer getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(Integer ordNo) {
		this.ordNo = ordNo;
	}

	public Long getLocaleid() {
		return localeid;
	}

	public void setLocaleid(Long localeid) {
		this.localeid = localeid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Short getRescfold() {
		return rescfold;
	}

	public void setRescfold(Short rescfold) {
		this.rescfold = rescfold;
	}

	public String getPidName() {
		return pidName;
	}

	public void setPidName(String pidName) {
		this.pidName = pidName;
	}
	
    /**
     * 得到主键的值
     * @return
     */
	public Long getPk(){
		return id;
	}
	
	/**
	 * 将vo转换为树叶要显示的值
	 * @return
	 */
	public String toTreeString(){
		return name;
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


	public Short getOperType() {
		return operType;
	}


	public void setOperType(Short operType) {
		this.operType = operType;
	}


	public Long getLinkid() {
		return linkid;
	}


	public void setLinkid(Long linkid) {
		this.linkid = linkid;
	}


	public Short getVisible() {
		return visible;
	}


	public void setVisible(Short visible) {
		this.visible = visible;
	}

	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}


	public void setAuthorities(GrantedAuthority[] authorities) {
		//Assert.notNull(authorities, "res_string:"+resString+" , Cannot pass a null GrantedAuthority array");
		if(authorities!=null){
		    for(int i=0;i<authorities.length;i++){
			    Assert.notNull(authorities[i],
					"Granted authority element " + i + " is null - GrantedAuthority[] cannot contain any null elements");
		    }
		}
		this.authorities = authorities;	
	}
	
	@Override
	public int hashCode() {
		int code = 168;
		if (getAuthorities() != null) {
			for(int i=0;i<authorities.length;i++){
				code *= authorities[i].hashCode() % 7;
			}
		}
		if (getResString() != null)
			code *= getResString().hashCode() % 7;
		if (getResType()!=null){
			code *= getResType().hashCode() % 7;
		}
		return code;
	}
	
	@Override
	public boolean equals(Object rhs) {
		if (!(rhs instanceof Resources))
			return false;
		Resources resauth = (Resources) rhs;
		if(!getResString().equals(resauth.getResString()))
			return false;
		if(!getResType().equals(resauth.getResType()))
			return false;
		if (resauth.getAuthorities().length != authorities.length)
			return false;
		GrantedAuthority[] authArr = resauth.getAuthorities();
		
		boolean find = false;
		for(int i=0;i<authArr.length;i++){
			for(int j=0;i<authorities.length;j++){
				if(authorities[j].equals(authArr[i])){
					find = true;
					break;
				}
				if(!find){
				    return false;
				}else {
					find = false;
				}
			}
		}
		return  true ;
	}

	public Integer getSearchListType() {
		return searchListType;
	}

	public void setSearchListType(Integer searchListType) {
		this.searchListType = searchListType;
	}

	@Override
	public String getObjectType(){
		return ObjectType;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Element getPolicyRule() {
		if(policyruleElement==null && 
				expression!=null && expression.trim().length()>0){
		    try {
			   policyruleElement = DOM4JUtil.parseStringToElement(expression);
			} catch (DocumentException e) {
				throw new LogicException("error in resource:"+id+", parse policy expression:"+expression, e);
			}
		}
		return policyruleElement;
	}
	
	public void setObjectparam(String objectparam) {
		this.objectparam = objectparam;
	}

	public String getRequestObjectidName() {
		if(objectnameMap==null){
			constructObjectMap(objectparam);
		}
		return objectnameMap.get(Key_Objectid_name);
	}

	public String getRequestObjecttype() {
		if(objectnameMap==null){
			constructObjectMap(objectparam);
		}
		return objectnameMap.get(Key_Objecttype);
	}
	
	public String getRequestContaineridname() {
		if(objectnameMap==null){
			constructObjectMap(objectparam);
		}
		return objectnameMap.get(Key_containerid_name);
	}

	public String getRequestContainertype() {
		if(objectnameMap==null){
			constructObjectMap(objectparam);
		}
		return objectnameMap.get(Key_containertype);
	}
	
	private Map constructObjectMap(String inputstr){
		objectnameMap = new HashMap<String, String>();
		if(inputstr!=null && inputstr.trim().length()>0){
			String[] paramArr = inputstr.split(",");
			if(paramArr!=null && paramArr.length>0){
				String[] objArr = null;
				for(String paramStr : paramArr){
					if(paramStr!=null&&paramStr.trim().length()>0){
						objArr = paramStr.trim().split(":");
						if(objArr!=null && objArr.length==2)
						{
							if(Key_Objectid_name.equals(objArr[0])||Key_Objecttype.equals(objArr[0])
								||Key_containerid_name.equals(objArr[0])||Key_containertype.equals(objArr[0]))
							{
							    objectnameMap.put(objArr[0], objArr[1]);
							}else {
								throw new LogicException("error in resource:"+id+","+objArr[0]+" is not supported, it must be:objectidname or objecttype or containeridname or containertype");
							}
						}else{
							throw new LogicException("error in resource:"+id+", construct object param Map:"+inputstr);
						}
					}
				}
			}
		}
		return objectnameMap;
	}

	public String getExpression() {
		return expression;
	}

	public String getObjectparam() {
		return objectparam;
	}
    
}
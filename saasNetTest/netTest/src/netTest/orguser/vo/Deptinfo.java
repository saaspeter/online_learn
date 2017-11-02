package netTest.orguser.vo;

public class Deptinfo extends Orgbase{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer deptchildnum;
    
    private Short issetfornew;
    
    public final static String ObjectType = "deptinfo";
    

    public Integer getDeptchildnum() {
        return deptchildnum;
    }

    public void setDeptchildnum(Integer deptchildnum) {
        this.deptchildnum = deptchildnum;
    }

	public Short getIssetfornew() {
		return issetfornew;
	}

	public void setIssetfornew(Short issetfornew) {
		this.issetfornew = issetfornew;
	}
	
	public String getObjectType() {
		return ObjectType;
	}

}
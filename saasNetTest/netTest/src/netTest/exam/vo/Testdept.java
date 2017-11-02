package netTest.exam.vo;

import netTest.common.vo.NettestBaseVO;

public class Testdept extends NettestBaseVO{
    
    private Long testdeptid;

    private Long testid;

    private Long orgbaseid;

    private String orgname;

    private Float qualifyper;

    private Float selfqulifyper;
    
    private Integer qualifynum;

    private Integer selfqulifynum;

    private Float avescore;

    private Float selfavescore;

    private Integer examernum;

    private Integer selfexamernum; 

    private Integer examingnum; 

    private Integer selfexamingnum;

    private Integer endexamnum;

    private Integer selfendexamnum;
    /** 还没有开始考试的人数，不对应数据库表字段 **/
    private Integer notexamnum;
    private Integer selfnotexamnum;
    /** 1: 整个考试的统计信息(显示在考试详细信息中)  2: 考试单位的统计信息 **/
    private Short type;
    private Integer orglevel;
    private String deptpath;
    
    public final static String ObjectType = "testdept";

    
    public Long getTestdeptid() {
        return testdeptid;
    }


    public void setTestdeptid(Long testdeptid) {
        this.testdeptid = testdeptid;
    }

    public Long getTestid() {
        return testid;
    }

    public void setTestid(Long testid) {
        this.testid = testid;
    }

    public Long getOrgbaseid() {
        return orgbaseid;
    }

    public void setOrgbaseid(Long orgbaseid) {
        this.orgbaseid = orgbaseid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public Float getQualifyper() {
    	if(qualifyper==null&&qualifynum!=null&&examernum!=null){
    		qualifyper = qualifynum.floatValue()/examernum.floatValue();
    	}
        return qualifyper;
    }

    public void setQualifyper(Float qualifyper) {
        this.qualifyper = qualifyper;
    }

    public Float getSelfqulifyper() {
    	if(selfqulifyper==null&&selfqulifynum!=null&&selfexamernum!=null){
    		selfqulifyper = selfqulifynum.floatValue()/selfexamernum.floatValue();
    	}
        return selfqulifyper;
    }

    public void setSelfqulifyper(Float selfqulifyper) {
        this.selfqulifyper = selfqulifyper;
    }

    public Float getAvescore() {
        return avescore;
    }

    public void setAvescore(Float avescore) {
        this.avescore = avescore;
    }

    public Float getSelfavescore() {
        return selfavescore;
    }

    public void setSelfavescore(Float selfavescore) {
        this.selfavescore = selfavescore;
    }

    public Integer getExamernum() {
        return examernum==null?0:examernum;
    }

    public void setExamernum(Integer examernum) {
        this.examernum = examernum;
    }

    public Integer getSelfexamernum() {
        return selfexamernum==null?0:selfexamernum;
    }

    public void setSelfexamernum(Integer selfexamernum) {
        this.selfexamernum = selfexamernum;
    }

    public Integer getExamingnum() {
        return examingnum==null?0:examingnum;
    }

    public void setExamingnum(Integer examingnum) {
        this.examingnum = examingnum;
    }

    public Integer getSelfexamingnum() {
        return selfexamingnum==null?0:selfexamingnum;
    }

    public void setSelfexamingnum(Integer selfexamingnum) {
        this.selfexamingnum = selfexamingnum;
    }

    public Integer getEndexamnum() {
        return endexamnum==null?0:endexamnum;
    }

    public void setEndexamnum(Integer endexamnum) {
        this.endexamnum = endexamnum;
    }

    public Integer getSelfendexamnum() {
        return selfendexamnum==null?0:selfendexamnum;
    }

    public void setSelfendexamnum(Integer selfendexamnum) {
        this.selfendexamnum = selfendexamnum;
    }

	public Integer getNotexamnum() {
		this.notexamnum = getExamernum()-getExamingnum()-getEndexamnum();
		return notexamnum;
	}

	public void setNotexamnum(Integer notexamnum) {
		this.notexamnum = notexamnum;
	}

	public Integer getSelfnotexamnum() {
		this.selfnotexamnum = getSelfexamernum()-getSelfexamingnum()-getSelfendexamnum();
		return selfnotexamnum;
	}


	public void setSelfnotexamnum(Integer selfnotexamnum) {
		this.selfnotexamnum = selfnotexamnum;
	}


	public Integer getOrglevel() {
		return orglevel;
	}


	public void setOrglevel(Integer orglevel) {
		this.orglevel = orglevel;
	}


	public String getDeptpath() {
		return deptpath;
	}
	
	public String getDeptpath2() {
		String deptpath2 = ",";
		if(deptpath!=null){
			deptpath2 = deptpath;
			if(!deptpath.endsWith(","))
				deptpath2 = deptpath2+",";
			if(!deptpath.startsWith(","))
				deptpath2 = ","+deptpath2;
		}
		return deptpath2;
	}

	public void setDeptpath(String deptpath) {
		this.deptpath = deptpath;
	}

	public Integer getQulifynum() {
		return qualifynum;
	}

	public void setQulifynum(Integer qualifynum) {
		this.qualifynum = qualifynum;
	}

	public Integer getSelfqulifynum() {
		return selfqulifynum;
	}

	public void setSelfqulifynum(Integer selfqulifynum) {
		this.selfqulifynum = selfqulifynum;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}
	
}
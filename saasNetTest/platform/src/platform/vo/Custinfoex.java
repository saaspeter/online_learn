package platform.vo;

public class Custinfoex {

    private Long userid;

    private String birthday;

    private String occupation;

    private Long creator;

    private String tipquestion;

    private String tipanswer;

    private String notes;

    /** 判断该Vo是否为空 **/
    public boolean isEmpty(){
    	if(this.userid!=null)
    		return false;
    	if(this.birthday!=null&&birthday.trim().length()>0)
    		return false;
    	if(this.tipquestion!=null&&tipquestion.trim().length()>0)
    		return false;
    	if(this.tipanswer!=null&&tipanswer.trim().length()>0)
    		return false;
    	if(this.occupation!=null&&occupation.trim().length()>0)
    		return false;
    	if(this.notes!=null&&notes.trim().length()>0)
    		return false;
    	return true;
    }

    public Long getUserid() {
        return userid;
    }


    public void setUserid(Long userid) {
        this.userid = userid;
    }


    public String getTipquestion() {
        return tipquestion;
    }


    public void setTipquestion(String tipquestion) {
        this.tipquestion = tipquestion;
    }


    public String getTipanswer() {
        return tipanswer;
    }


    public void setTipanswer(String tipanswer) {
        this.tipanswer = tipanswer;
    }

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

}
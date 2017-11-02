package netTest.wareques.vo;

import netTest.common.vo.NettestBaseVO;
import netTest.wareques.constant.QuestionConstant;

import org.apache.struts.upload.FormFile;

public class Questionitem extends NettestBaseVO implements QuesBaseVOInf{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long quesitemid;

	private Long quesid;

	private String quesitemflag;

	/** 1正确，2错误.默认错误 **/
	private Short isright = QuestionConstant.IsRight_wrong;

	private String fileurl;

	private Short filetype;
	
	private String filetypeStr;
	
    /** 数据库中数据的文件类型，从页面传过来，不用再次查询数据库即可知道修改前的状态 **/
	private Short filetypeOrigin;

	private String quesitemcontent;
	
	private FormFile itemFile;
	
	private Integer disorder;
	/** 需要交换的显示顺序 **/
	private Integer disorderSwitch;
	/** 需要交换显示顺序的题目的flag **/
	private String quesitemflagSwitch;
    
	private String order_By_Clause;
	
	public final static String ObjectType = "questionitem";
	

	public Long getQuesitemid() {
		return quesitemid;
	}

	public void setQuesitemid(Long quesitemid) {
		this.quesitemid = quesitemid;
	}

	public Long getQuesid() {
		return quesid;
	}

	public void setQuesid(Long quesid) {
		this.quesid = quesid;
	}

	public String getQuesitemflag() {
		return quesitemflag;
	}

	public void setQuesitemflag(String quesitemflag) {
		this.quesitemflag = quesitemflag;
	}

	public Short getIsright() {
		return isright;
	}

	public void setIsright(Short isright) {
		this.isright = isright;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public Short getFiletype() {
		return filetype;
	}

	public void setFiletype(Short filetype) {
		this.filetype = filetype;
	}

	public String getQuesitemcontent() {
		return quesitemcontent;
	}

	public void setQuesitemcontent(String quesitemcontent) {
		this.quesitemcontent = quesitemcontent;
	}

	public FormFile getItemFile() {
		return itemFile;
	}

	public void setItemFile(FormFile itemFile) {
		this.itemFile = itemFile;
	}
	
	/**
	 * 判断可不可以插入，即该选项是不是为空的
	 * @return
	 */
	public boolean canInsert(){
		if(quesitemcontent!=null||(filetype!=null&&!QuestionConstant.FileType_None.equals(filetype)&&itemFile!=null&&itemFile.getFileSize()>0))
			return true;
		else 
			return false;
	}

	public Short getFiletypeOrigin() {
		return filetypeOrigin;
	}

	public void setFiletypeOrigin(Short filetypeOrigin) {
		this.filetypeOrigin = filetypeOrigin;
	}

	public Integer getDisorder() {
		return disorder;
	}

	public void setDisorder(Integer disorder) {
		this.disorder = disorder;
	}

	public Integer getDisorderSwitch() {
		return disorderSwitch;
	}

	public void setDisorderSwitch(Integer disorderSwitch) {
		this.disorderSwitch = disorderSwitch;
	}

	public String getQuesitemflagSwitch() {
		return quesitemflagSwitch;
	}

	public void setQuesitemflagSwitch(String quesitemflagSwitch) {
		this.quesitemflagSwitch = quesitemflagSwitch;
	}
	
	/** 得到问题的种类：题库题目 **/
	public int getQuesKind(){
		return QuestionConstant.Question_ware;
	}

	public String getFiletypeStr() {
		return filetypeStr;
	}

	public void setFiletypeStr(String filetypeStr) {
		this.filetypeStr = filetypeStr;
	}

	public String getOrder_By_Clause() {
		return order_By_Clause;
	}

	public void setOrder_By_Clause(String order_By_Clause) {
		this.order_By_Clause = order_By_Clause;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}

}
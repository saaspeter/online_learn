
package netTestWeb.wareques.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.wareques.dto.QuestionQuery;
import netTest.wareques.vo.Quesanswer;
import netTest.wareques.vo.Question;
import netTest.wareques.vo.Questionitem;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class WarequesForm extends BaseForm {

	private static final long serialVersionUID = 3028400693054326436L;

	/** 一般是子题目的vo **/
	private Question vo;
	private QuestionQuery queryVO;
	
	private Question vo2;
	/** 要编辑的子题目的主键 **/
	private String editquesid;
	/** 子题目的操作。0代表是页面的保存该题目操作；1是新增子题目；2单独保存子题目；3要编辑子题目；4删除子题目;5交换子题目（完形填空题） **/
	private String subquesOpt;
	
	private Quesanswer answerVO;
	
	/** 将要被删除的题目选项的主键的字符串 **/
	private String delItemStr;
	
	private List itemList;
	/** 如果在试卷上修改题目，则会传入paperid **/
	private Long paperid;
	
	/** 完形填空题的交换子题目类型，1为向上移动(disorder变小)，2为向下移动 **/
	private int switchtype;
	
	/** 完形填空删除子题目时需要的 **/
	private Long delquesid;
	private Integer deldisorder;
	/** 删除子问题的问题类型 **/
	private Integer delquestype;
	
	/** 转向的列表页面类型：1为管理列表页面；2为选择列表页面 **/
	private String listType;
	
	/** 导入文件类型，"excel"和"txt" **/
	private String importtype;
	
	/** 导入题目成功数目 **/
	private int importSuccessNumber;
	
	/** 导入题目失败数目 **/
	private int importFailNumber;
	
	private List<String> messageList;
	
	/** 播放fileurl的方式，1表示使用系统swf播放器播放,0表示直接播放 **/
	private String fileurlplaytype;
	
	private boolean ableuploadlocal;
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Question();
		vo2 = new Question();
		queryVO = new QuestionQuery();
		answerVO = new Quesanswer();
		itemList = null;
		delItemStr = "";
		editquesid = null;
		subquesOpt = "0";
		switchtype = 0;
		delquesid = null;
		deldisorder = null;
		delquestype = null;
		listType = "1";
		ableuploadlocal = false;
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
	
	public QuestionQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(QuestionQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Question getVo() {
		return vo;
	}

	public void setVo(Question vo) {
		this.vo = vo;
	}

	public List getItemList() {
		if(itemList==null)
			itemList = new ArrayList();
		return itemList;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}
	
	/**
	 * 收集题目选项
	 * @param key
	 * @return
	 */
    public Object getQuesitem(int key) {
    	itemList = getItemList();
        if(itemList.size()<=key){
            int iCount = key-itemList.size()+1;
            for(int i=0;i<iCount;i++)
            {
            	Questionitem voTemp = new Questionitem();
            	itemList.add(voTemp);
            }        	
        	return itemList.get(key);
        }
    	return itemList.get(key);
    }

	public String getDelItemStr() {
		return delItemStr;
	}

	public void setDelItemStr(String delItemStr) {
		this.delItemStr = delItemStr;
	}

	public Quesanswer getAnswerVO() {
		return answerVO;
	}

	public void setAnswerVO(Quesanswer answerVO) {
		this.answerVO = answerVO;
	}

	public Question getVo2() {
		return vo2;
	}

	public void setVo2(Question vo2) {
		this.vo2 = vo2;
	}

	public String getSubquesOpt() {
		return subquesOpt;
	}

	public void setSubquesOpt(String subquesOpt) {
		this.subquesOpt = subquesOpt;
	}

	public String getEditquesid() {
		return editquesid;
	}

	public void setEditquesid(String editquesid) {
		this.editquesid = editquesid;
	}

	public int getSwitchtype() {
		return switchtype;
	}

	public void setSwitchtype(int switchtype) {
		this.switchtype = switchtype;
	}

	public Integer getDeldisorder() {
		return deldisorder;
	}

	public void setDeldisorder(Integer deldisorder) {
		this.deldisorder = deldisorder;
	}

	public Long getDelquesid() {
		return delquesid;
	}

	public void setDelquesid(Long delquesid) {
		this.delquesid = delquesid;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public Integer getDelquestype() {
		return delquestype;
	}

	public void setDelquestype(Integer delquestype) {
		this.delquestype = delquestype;
	}

	public String getImporttype() {
		return importtype;
	}

	public void setImporttype(String importtype) {
		this.importtype = importtype;
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

	public String getFileurlplaytype() {
		return fileurlplaytype;
	}

	public void setFileurlplaytype(String fileurlplaytype) {
		this.fileurlplaytype = fileurlplaytype;
	}

	public boolean isAbleuploadlocal() {
		return ableuploadlocal;
	}

	public void setAbleuploadlocal(boolean ableuploadlocal) {
		this.ableuploadlocal = ableuploadlocal;
	}

	public Long getPaperid() {
		return paperid;
	}

	public void setPaperid(Long paperid) {
		this.paperid = paperid;
	}
	
}

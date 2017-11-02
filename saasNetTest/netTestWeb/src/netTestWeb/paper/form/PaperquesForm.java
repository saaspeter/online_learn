
package netTestWeb.paper.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.paper.dto.PaperquesQuery;
import netTest.paper.vo.Paperques;
import netTest.wareques.vo.Quesanswer;
import netTest.wareques.vo.Questionitem;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class PaperquesForm extends BaseForm {

	private static final long serialVersionUID = -2135725020633263228L;
	
	/** 一般是子题目的vo **/
	private Paperques vo;
	private PaperquesQuery queryVO;
	
	private Paperques vo2;
	/** 要编辑的子题目的主键 **/
	private String editquesid;
	/** 子题目的操作。0代表是页面的保存该题目操作；1是新增子题目；2单独保存子题目；3要编辑子题目；4删除子题目;5交换子题目（完形填空题） **/
	private String subquesOpt;
	
	private Quesanswer answerVO;
	
	/** 将要被删除的题目选项的主键的字符串 **/
	private String delItemStr;
	
	private List itemList;
	
	/** 完形填空题的交换子题目类型，1为向上移动(disorder变小)，2为向下移动 **/
	private int switchtype;
	
	/** 完形填空删除子题目时需要的 **/
	private Long delquesid;
	private Integer deldisorder;
	/** 删除子问题的问题类型 **/
	private Integer delquestype;
	
	/** 新增或变更题目时选择的新的题目 **/
	private String quesidStr;
	
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
		vo = new Paperques();
		vo2 = new Paperques();
		queryVO = new PaperquesQuery();
		answerVO = new Quesanswer();
		itemList = null;
		delItemStr = "";
		editquesid = null;
		subquesOpt = "0";
		switchtype = 0;
		delquesid = null;
		deldisorder = null;
		delquestype = null;
		quesidStr = null;
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

	public PaperquesQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(PaperquesQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Paperques getVo() {
		return vo;
	}

	public void setVo(Paperques vo) {
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

	public Paperques getVo2() {
		return vo2;
	}

	public void setVo2(Paperques vo2) {
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

	public String getQuesidStr() {
		return quesidStr;
	}

	public void setQuesidStr(String quesidStr) {
		this.quesidStr = quesidStr;
	}

	public Integer getDelquestype() {
		return delquestype;
	}

	public void setDelquestype(Integer delquestype) {
		this.delquestype = delquestype;
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
}

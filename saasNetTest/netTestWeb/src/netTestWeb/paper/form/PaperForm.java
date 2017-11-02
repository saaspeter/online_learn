
package netTestWeb.paper.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.paper.dto.PaperQuery;
import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperpatternratio;
import netTest.paper.vo.Paperprop;
import netTest.paper.vo.Paperquestype;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class PaperForm extends BaseForm {
	
	private Paper vo;
	private PaperQuery queryVO;
	private Paperprop paperpropvo;
	
	/** 问题类型集合，内是Paperquestype **/
	private List questypeList;
    /** 选择的题库的集合 **/
    private List wareList;
    /** 难度集合，内是Paperpatternratio **/
    private List diffList;
    /** 知识点集合，内是Paperpatternratio **/
    private List contcateList;
    
    /** 显示问题类型的集合，里是LabelValueVO **/
    private List quetyporiList;
    
    /** 是否有难度设置，1有难度设置，2没有难度设置 **/
    private int diffControl;
    /** 是否有知识点设置，1有知识点设置，2没有知识点设置 **/
    private int contCateControl;
    
    /** 正在使用的题目类型（展现编辑的题型） **/
    private Integer questypeUse;
    private Long questypeidUse;
    
	/** 链接地址类型,2为进入选择试卷界面，其余值为试卷管理界面 **/
	private int urlType;
	/** 选择类型，1代表单选(radio)，2代表多选(checkbox) **/
	private String selectType;
	
	private Long wareid;
	
	private FormFile file;
	
	/** 导入题目成功数目 **/
	private int importSuccessNumber;
	
	/** 导入题目失败数目 **/
	private int importFailNumber;
	
	private List<String> messageList;
	
	/** 是否需要更新试卷字段 **/
	private int needupdatepaper;
	/** 是否自动生成试卷，0代表只创建试卷不自动选题，1代表要自动选题生成试卷 **/
	private int createtype;
	
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Paper();
		queryVO = new PaperQuery();
		paperpropvo = new Paperprop();
		questypeList = null;
		wareList = null;
		diffList = null;
		contcateList = null;
		diffControl = 2;
		contCateControl = 2;
		quetyporiList = null;
		questypeUse = null;
		questypeidUse = null;
		urlType = 1;
		selectType = "1";
		createtype=0;
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
	
	public PaperQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(PaperQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Paper getVo() {
		return vo;
	}

	public void setVo(Paper vo) {
		this.vo = vo;
	}

	public Paperprop getPaperpropvo() {
		return paperpropvo;
	}

	public void setPaperpropvo(Paperprop paperpropvo) {
		this.paperpropvo = paperpropvo;
	}

	public List getQuestypeList() {
		if(questypeList==null)
			questypeList = new ArrayList();
		return questypeList;
	}
	
	public List getQuestypeList_clean() {
		List<Paperquestype> listTemp = new ArrayList<Paperquestype>();
        if(questypeList!=null&&questypeList.size()>0){
        	Paperquestype voTemp = null;
        	for(int i=0;i<questypeList.size();i++)
        	{
        		voTemp = (Paperquestype)questypeList.get(i);
        		if(voTemp.getQuestype()!=null){
        			listTemp.add(voTemp);
        		}
        	}
        }
        return listTemp;
	}

	public void setQuestypeList(List questypeList) {
		this.questypeList = questypeList;
	}

	public int getContCateControl() {
		return contCateControl;
	}

	public void setContCateControl(int contCateControl) {
		this.contCateControl = contCateControl;
	}

	public int getDiffControl() {
		return diffControl;
	}

	public void setDiffControl(int diffControl) {
		this.diffControl = diffControl;
	}

	public List getContcateList() {
		if(contcateList==null)
			contcateList = new ArrayList();
		return contcateList;
	}
	
	public List getContcateList_clean() {
		List<Paperpatternratio> listTemp = new ArrayList<Paperpatternratio>();
        if(contcateList!=null&&contcateList.size()>0){
        	Paperpatternratio voTemp = null;
        	for(int i=0;i<contcateList.size();i++)
        	{
        		voTemp = (Paperpatternratio)contcateList.get(i);
        		if(voTemp.getElementid()!=null){
        			listTemp.add(voTemp);
        		}
        	}
        }
        return listTemp;
	} 

	public void setContcateList(List contcateList) {
		this.contcateList = contcateList;
	}

	public List getDiffList() {
		if(diffList==null)
			diffList = new ArrayList();
		return diffList;
	}
	
	public List getDiffList_clean() {
		List<Paperpatternratio> listTemp = new ArrayList<Paperpatternratio>();
        if(diffList!=null&&diffList.size()>0){
        	Paperpatternratio voTemp = null;
        	for(int i=0;i<diffList.size();i++)
        	{
        		voTemp = (Paperpatternratio)diffList.get(i);
        		if(voTemp.getElementid()!=null){
        			listTemp.add(voTemp);
        		}
        	}
        }
        return listTemp;
	}

	public void setDiffList(List diffList) {
		this.diffList = diffList;
	}

	public List getWareList() {
		if(wareList==null)
			wareList = new ArrayList();
		return wareList;
	}
	
	public void setWareList(List wareList) {
		this.wareList = wareList;
	}
	
	/** 构造题目类型 **/
    public Object getQuestype(int key) {
    	questypeList = getQuestypeList();
        if(questypeList.size()<=key){
            int iCount = key-questypeList.size()+1;
            for(int i=0;i<iCount;i++)
            {
            	Paperquestype voTemp = new Paperquestype();
            	questypeList.add(voTemp);
            }        	
        	return questypeList.get(key);
        }
    	return questypeList.get(key);
    }
    
	/** 构造难度比例 **/
    public Object getDiff(int key) {
    	diffList = getDiffList();
        if(diffList.size()<=key){
            int iCount = key-diffList.size()+1;
            for(int i=0;i<iCount;i++)
            {
            	Paperpatternratio voTemp = new Paperpatternratio();
            	diffList.add(voTemp);
            }        	
        	return diffList.get(key);
        }
    	return diffList.get(key);
    }
    
	/** 构造知识点比例 **/
    public Object getContcate(int key) {
    	contcateList = getContcateList();
        if(contcateList.size()<=key){
            int iCount = key-contcateList.size()+1;
            for(int i=0;i<iCount;i++)
            {
            	Paperpatternratio voTemp = new Paperpatternratio();
            	contcateList.add(voTemp);
            }        	
        	return contcateList.get(key);
        }
    	return contcateList.get(key);
    }

	public List getQuetyporiList() {
		return quetyporiList;
	}

	public void setQuetyporiList(List quetyporiList) {
		this.quetyporiList = quetyporiList;
	}

	public Integer getQuestypeUse() {
		return questypeUse;
	}

	public void setQuestypeUse(Integer questypeUse) {
		this.questypeUse = questypeUse;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public int getUrlType() {
		return urlType;
	}

	public void setUrlType(int urlType) {
		this.urlType = urlType;
	}

	public Long getQuestypeidUse() {
		return questypeidUse;
	}

	public void setQuestypeidUse(Long questypeidUse) {
		this.questypeidUse = questypeidUse;
	}

	public Long getWareid() {
		return wareid;
	}

	public void setWareid(Long wareid) {
		this.wareid = wareid;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public int getImportFailNumber() {
		return importFailNumber;
	}

	public void setImportFailNumber(int importFailNumber) {
		this.importFailNumber = importFailNumber;
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

	public int getNeedupdatepaper() {
		return needupdatepaper;
	}

	public void setNeedupdatepaper(int needupdatepaper) {
		this.needupdatepaper = needupdatepaper;
	}

	public int getCreatetype() {
		return createtype;
	}

	public void setCreatetype(int createtype) {
		this.createtype = createtype;
	}
		
}

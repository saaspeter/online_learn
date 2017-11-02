package platform.logicImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import platform.bean.BeanFactory;
import platform.constant.ProductcategoryConstant;
import platform.dao.ProductcategoryDao;
import platform.dao.ProductcategoryvalueDao;
import platform.dao.SysproductcateDao;
import platform.exception.ExceptionConstant;
import platform.logic.ProductcategoryLogic;
import platform.util.ResourceBundleUtil;
import platform.vo.Productcategory;
import platform.vo.Sysproductcate;

import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.constant.CommonConstant;
import commonTool.exception.LogicException;
import commonTool.util.StringUtil;

public class ProductcategoryLogicImpl implements ProductcategoryLogic {
	
	static Logger log = Logger.getLogger(ProductcategoryLogicImpl.class.getName());
	private ProductcategoryDao dao;
	private ProductcategoryvalueDao daoValue;
	private SysproductcateDao sysprodcateDao;


	public ProductcategoryDao getDao() {
		return dao;
	}

	public void setDao(ProductcategoryDao dao) {
		this.dao = dao;
	}

	/**
	 * @param pid
	 * @param action
	 * @param url
	 * @param target
	 * @return
	 * @throws Exception
	 */
//    public String createTreeXml(Long pid,String syscode,String action,String url,String target,Long localeid) throws Exception{
//    	if(pid==null)
//    		pid = CommonConstant.TreeTopnodePid;
//
//    	StringBuffer buffer = new StringBuffer();
//    	
//    	buffer.append("<tree>").append("\n");
//    	try {
//	    	//dao = ProductcategoryDaoImpl.getInstance();
//	    	ProductcategoryQuery queryVO = new ProductcategoryQuery();
//	    	ViewproductcategoryQuery temp;
//	    	queryVO.setPid(pid);
//	    	queryVO.setSyscode(syscode);
//	    	queryVO.setLocaleid(localeid);
//	    	List list = dao.selectByVOChildnum(queryVO);
//	    	if(list!=null&&list.size()>0)
//	    	for(int i=0;i<list.size();i++){
//	    		temp = (ViewproductcategoryQuery)list.get(i);
//	    		if(temp==null)
//	    			continue;
//	    		buffer.append("<tree text=\"").append(temp.getCategoryname()).append("\" ")
//	    		.append("action=\"javascript:").append(action).append("('").append(temp.getCategoryid()).append("','").append(temp.getCategoryname()).append("')").append("\" ");
//	    		if(temp.getChildnum()!=null&&temp.getChildnum().intValue()>0&&url!=null)
//	    			buffer.append("src=\"").append(url).append(temp.getCategoryid()).append("\" ");
//	    		if(target!=null&&target.trim().length()>0)
//	    			buffer.append("target=\"").append(target).append("\" ");
//	    		buffer.append("/>").append("\n");
//	    	}
//    	} catch (Exception e) {
//			log.error("ProductcategoryLogicImpl用createTreeXml方法生成xml tree时出错误!", e);
//			throw e;
//		}
//    	buffer.append("</tree>");
//    	return buffer.toString();
//    }
    
	
	/**
	 * 查询目录名称，主要是要为dwr页面调用，需要有cache
	 */
	public String qryCategoryname(Long pk, Long localeid){
		Productcategory vo = null;
		if(CommonConstant.TreeTopnodePid.equals(pk)){
			vo = new Productcategory();
		    vo.setCategoryid(CommonConstant.TreeTopnodePid);
		    Locale locale = I18nLogicImpl.getLocale(localeid);
			String topcategoryname = ResourceBundleUtil.getInstance().getValue("CommonConstant.TreeTopnodeName", locale);
		    vo.setCategoryname(topcategoryname);
		}else {
			vo = dao.selectByLogicPK(pk, localeid);
		}
		if(vo!=null){
			return vo.getCategoryname();
		}else {
			return "";
		}
	}
	   
    
    /**
     * 根据商品目录id选择其平级目录的列表，即该目录的兄弟目录，用于向上一级查询中
     * @param ProductcategoryQuery：需要查询的平级目录的id,需要包括的参数有pid,localeid
     * @return Page
     */
//    public Page qryUpperCate(ProductcategoryQuery queryVO,int pageIndex,int pageSize,Integer total)
//           throws Exception
//    {
//    	if(queryVO==null||queryVO.getPid()==null)
//    		return null;
//    	Page page = null;
//        try {
//        	Productcategory vo = dao.selectByPK(queryVO.getPid());
//			if(vo!=null){
//				if(vo.getPid()==null)
//				   vo.setPid(CommonConstant.TreeTopnodePid);
//				queryVO.setPid(vo.getPid());
//			}
//			page = dao.selectByVOPage(queryVO, pageIndex, pageSize, total);
//		} catch (Exception e) {
//			log.error("ProductcategoryLogicImpl的方法qryUpperCate出错!", e);
//			throw e;
//		}
//    	return page;
//    }
    
    /**
     * 根据提交的产品目录ViewVO，将其中信息分解分别存入对应的数据表中。
     * 主要是为了区分不同语言对目录的不同描述。
     * @param viewVO
     * @return
     * @throws Exception
     */
    public boolean save(Productcategory vo) {
    	if(vo==null)
    		return false;
        boolean isnew = true;
        if(vo.getCategoryid()!=null){
        	isnew = false;
        }
        // if is update, and set status is inactive, need check the child product number
     	if(!isnew && CommonConstant.Status_invalide.equals(vo.getStatus())){
 			int prodnum = dao.selectProdnumByCate(vo.getCategoryid(), true);
 			if(prodnum>0){
 				throw new LogicException(ExceptionConstant.Error_Record_BeenReference)
 						.appendExtraInfo_Display("category has products, cannot deactive");
 			}
 			int catenum = dao.selectChildNum(vo.getCategoryid(), CommonConstant.Status_invalide);
 			if(catenum>0){
 				throw new LogicException(ExceptionConstant.Error_Record_BeenReference)
 						.appendExtraInfo_Display("this category has some children category which are active, cannot deactive");
 			}
 		}
     	// set parent path
		if(isnew && vo.getPid()!=null && !CommonConstant.TreeTopnodePid.equals(vo.getPid())){
			Productcategory pidVO = dao.selectByPK(vo.getPid());
			vo.setPath(pidVO.getPath());
		}
		
		vo = dao.save(vo);
		if(isnew){
		   daoValue.save(vo);
		   // 如果是新增目录需要添加系统产品目录表记录
		   if(vo.getSyscodesStr()!=null&&vo.getSyscodesStr().trim().length()>0){
				List list = new ArrayList();
			    String[] arrs = StringUtil.splitString(vo.getSyscodesStr(), ",");
			    if(arrs!=null&&arrs.length>0){
			    	for(int i=0;i<arrs.length;i++){
				        Sysproductcate syscateVO = new Sysproductcate();
				        syscateVO.setCategoryid(vo.getCategoryid());
				        syscateVO.setSyscode(arrs[i]);
				        list.add(syscateVO);
			    	}
			    	sysprodcateDao.insertBatch(list);
			    }
			}
		}
		return true;
    }
    
    
    /**
     * 根据syscode得到该系统top的category
     * @param syscode
     * @param localeid
     * @return
     */
    public Productcategory getSystemTopCategory(String syscode, Long localeid){
    	Productcategory vo = null;
    	if(syscode==null||"".equals(syscode))
    		return vo;
    	Long categoryid = dao.getSystemTopCategory(syscode);
    	if(categoryid!=null) {
    		vo = dao.selectByLogicPK(categoryid, localeid);
    	}
    	return vo;
    }
    
    /**
     * static spring getMethod
     */
     public static ProductcategoryLogic getInstance() {
       	 ProductcategoryLogic logic = (ProductcategoryLogic)BeanFactory.getBeanFactory().getBean("productcategoryLogic");
         return logic;
     }
    

	public ProductcategoryvalueDao getDaoValue() {
		return daoValue;
	}

	public void setDaoValue(ProductcategoryvalueDao daoValue) {
		this.daoValue = daoValue;
	}

	public SysproductcateDao getSysprodcateDao() {
		return sysprodcateDao;
	}

	public void setSysprodcateDao(SysproductcateDao sysprodcateDao) {
		this.sysprodcateDao = sysprodcateDao;
	}

}

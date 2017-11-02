package netTest.bean;

import java.io.Serializable;

import netTest.exam.vo.Testchecker;
import netTest.exam.vo.Testdept;
import netTest.exam.vo.Testinfo;
import netTest.exam.vo.Testuser;
import netTest.exam.vo.Useranswer;
import netTest.exercise.vo.Exercise;
import netTest.exercise.vo.Exerques;
import netTest.exercise.vo.Exerquestype;
import netTest.exercise.vo.Exeruser;
import netTest.exercise.vo.Userexeranswer;
import netTest.learncont.vo.Learncontent;
import netTest.order.vo.Custorder;
import netTest.order.vo.Orderproduct;
import netTest.orguser.vo.Deptinfo;
import netTest.orguser.vo.Orgbase;
import netTest.orguser.vo.Orguser;
import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperques;
import netTest.paper.vo.Paperquestype;
import netTest.prodcont.vo.Contentcate;
import netTest.product.vo.Coursepost;
import netTest.product.vo.Openactivity;
import netTest.product.vo.Productbase;
import netTest.product.vo.Userprodstatuslog;
import netTest.product.vo.Userproduct;
import netTest.wareques.vo.Question;
import netTest.wareques.vo.Questionitem;
import netTest.wareques.vo.Questypeshop;
import netTest.wareques.vo.Ware;


public class EntityManager {

	private static EntityManager instance;
	
	public static EntityManager getInstance(){
		if (instance == null) {  
	        synchronized (EntityManager.class) {  
	           if (instance == null) {  
	        	   instance = new EntityManager();  
	           }  
	        }  
	    }  
	    return instance;  
	}
	
	private EntityManager() { }
	
	/**
	 * 
	 * @param objectType
	 * @param pk
	 * @param pk2 有的表是由多主键组成的
	 * @return
	 */
	public Object getEntity(String objectType, Serializable pk, Serializable pk2){
		Object rtnObj = null;
		if(objectType==null || objectType.trim().length()<1 || pk==null)
			return rtnObj;
		Long pklong = null;
		if(pk instanceof String){
			if(((String)pk).trim().length()>0){
			   pklong = new Long((String)pk);
			}else {
				return null;
			}
		}else {
			pklong = (Long)pk;
		}
		Long pklong2 = null;
		if(pk2!=null && (pk2 instanceof String)){
			if(((String)pk2).trim().length()>0){
			   pklong2 = new Long((String)pk2);
			}else {
				pklong2 = null;
			}
		}else {
			pklong2 = (Long)pk2;
		}
		
		if(Questypeshop.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getQuestypeshopDao().selectByPK(pklong);
		}else if(Deptinfo.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getDeptinfoDao().selectByPK(pklong);
		}else if(Orgbase.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getOrgbaseDao().selectByPK(pklong);
		}else if(Orguser.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getOrguserDao().selectByPK(pklong);
		}else if(Contentcate.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getContentcateDao().selectByPK(pklong);
		}else if(Ware.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getWareDao().selectByPK(pklong);
		}else if(Question.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getQuestionDao().selectByPK(pklong);
		}else if(Questionitem.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getQuestionitemDao().selectByPK(pklong);
		}else if(Paper.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getPaperDao().selectByPK(pklong);
		}else if(Paperques.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getPaperquesDao().selectByPK(pklong);
		}else if(Paperquestype.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getPaperquestypeDao().selectByPK(pklong);
		}else if(Testdept.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getTestdeptDao().selectByPK(pklong);
		}else if(Testinfo.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getTestinfoDao().selectByPK(pklong);
		}else if(Testuser.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getTestuserDao().selectByPK(pklong);
		}else if(Testchecker.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getTestcheckerDao().selectByPK(pklong);
		}else if(Useranswer.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getUseranswerDao().selectByPK(pklong);
		}else if(Learncontent.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getLearncontentDao().selectPlainByPK(pklong);
		}else if(Exercise.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getExerciseDao().selectByPK(pklong);
		}else if(Exerquestype.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getExerquestypeDao().selectByPK(pklong);
		}else if(Exerques.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getExerquesDao().selectByPK(pklong);
		}else if(Exeruser.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getExeruserDao().selectByPK(pklong);
		}else if(Userexeranswer.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getUserexeranswerDao().selectByPK(pklong);
		}else if(Userproduct.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getUserproductDao().selectByPK(pklong);
		}else if(Custorder.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getCustorderDao().selectByPK(pklong);
		}else if(Orderproduct.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getOrderproductDao().selectByPK(pklong, pklong2);
		}else if(Userprodstatuslog.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getUserprodstatuslogDao().selectByPK(pklong);
		}else if(Productbase.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getProductbaseDao().selectByPK(pklong);
		}else if(Coursepost.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getCoursepostDao().selectByPK(pklong);
		}else if(Openactivity.ObjectType.equals(objectType)){
			rtnObj = BOFactory.getOpenactivityDao().selectByPKSimple(pklong);
		}else {
			// else get Entity from Platform
			rtnObj = platform.bean.EntityManager.getInstance().getEntity(objectType, pklong, pklong2);
		}
		return rtnObj;
	}
	
}

package netTest.bean;

import netTest.common.logic.UserLoginSessionLogic;
import netTest.common.logic.impl.UserLoginSessionLogicImpl;
import netTest.exam.dao.TestcheckerDao;
import netTest.exam.dao.TestdeptDao;
import netTest.exam.dao.TestinfoDao;
import netTest.exam.dao.TestuserDao;
import netTest.exam.dao.UseranswerDao;
import netTest.exam.dao.impl.TestcheckerDaoImpl;
import netTest.exam.dao.impl.TestdeptDaoImpl;
import netTest.exam.dao.impl.TestinfoDaoImpl;
import netTest.exam.dao.impl.TestuserDaoImpl;
import netTest.exam.dao.impl.UseranswerDaoImpl;
import netTest.exam.logic.CheckPaperLogic;
import netTest.exam.logic.ExampaperLogic;
import netTest.exam.logic.TestdeptLogic;
import netTest.exam.logic.TestinfoLogic;
import netTest.exam.logic.TestuserLogic;
import netTest.exam.logic.impl.CheckPaperLogicImpl;
import netTest.exam.logic.impl.ExampaperLogicImpl;
import netTest.exam.logic.impl.TestdeptLogicImpl;
import netTest.exam.logic.impl.TestinfoLogicImpl;
import netTest.exam.logic.impl.TestuserLogicImpl;
import netTest.exercise.dao.ExerciseDao;
import netTest.exercise.dao.ExerquesDao;
import netTest.exercise.dao.ExerquestypeDao;
import netTest.exercise.dao.ExeruserDao;
import netTest.exercise.dao.UserexeranswerDao;
import netTest.exercise.dao.impl.ExerciseDaoImpl;
import netTest.exercise.dao.impl.ExerquesDaoImpl;
import netTest.exercise.dao.impl.ExerquestypeDaoImpl;
import netTest.exercise.dao.impl.ExeruserDaoImpl;
import netTest.exercise.dao.impl.UserexeranswerDaoImpl;
import netTest.exercise.logic.ExerciseLogic;
import netTest.exercise.logic.ExerquesLogic;
import netTest.exercise.logic.ExerquestypeLogic;
import netTest.exercise.logic.ExeruserLogic;
import netTest.exercise.logic.impl.ExerciseLogicImpl;
import netTest.exercise.logic.impl.ExerquesLogicImpl;
import netTest.exercise.logic.impl.ExerquestypeLogicImpl;
import netTest.exercise.logic.impl.ExeruserLogicImpl;
import netTest.learncont.dao.LearncontentDao;
import netTest.learncont.dao.impl.LearncontentDaoImpl;
import netTest.learncont.logic.LearncontentLogic;
import netTest.learncont.logic.impl.LearncontentLogicImpl;
import netTest.order.dao.CustorderDao;
import netTest.order.dao.OrderproductDao;
import netTest.order.dao.impl.CustorderDaoImpl;
import netTest.order.dao.impl.OrderproductDaoImpl;
import netTest.order.logic.OrderLogic;
import netTest.order.logic.OrderproductLogic;
import netTest.order.logic.impl.OrderLogicImpl;
import netTest.order.logic.impl.OrderproductLogicImpl;
import netTest.orguser.dao.DeptinfoDao;
import netTest.orguser.dao.OrgbaseDao;
import netTest.orguser.dao.OrgbaserelDao;
import netTest.orguser.dao.OrglevelDao;
import netTest.orguser.dao.OrguserDao;
import netTest.orguser.dao.impl.DeptinfoDaoImpl;
import netTest.orguser.dao.impl.OrgbaseDaoImpl;
import netTest.orguser.dao.impl.OrgbaserelDaoImpl;
import netTest.orguser.dao.impl.OrglevelDaoImpl;
import netTest.orguser.dao.impl.OrguserDaoImpl;
import netTest.orguser.logic.OrgLogic;
import netTest.orguser.logic.OrguserLogic;
import netTest.orguser.logic.impl.OrgLogicImpl;
import netTest.orguser.logic.impl.OrguserLogicImpl;
import netTest.paper.dao.PaperDao;
import netTest.paper.dao.PaperpatternratioDao;
import netTest.paper.dao.PaperpropDao;
import netTest.paper.dao.PaperquesDao;
import netTest.paper.dao.PaperquestypeDao;
import netTest.paper.dao.impl.PaperDaoImpl;
import netTest.paper.dao.impl.PaperpatternratioDaoImpl;
import netTest.paper.dao.impl.PaperpropDaoImpl;
import netTest.paper.dao.impl.PaperquesDaoImpl;
import netTest.paper.dao.impl.PaperquestypeDaoImpl;
import netTest.paper.logic.PaperLogic;
import netTest.paper.logic.PaperquesLogic;
import netTest.paper.logic.PaperquestypeLogic;
import netTest.paper.logic.impl.PaperLogicImpl;
import netTest.paper.logic.impl.PaperquesLogicImpl;
import netTest.paper.logic.impl.PaperquestypeLogicImpl;
import netTest.prodcont.dao.ContentcateDao;
import netTest.prodcont.dao.impl.ContentcateDaoImpl;
import netTest.prodcont.logic.ContentcateLogic;
import netTest.prodcont.logic.impl.ContentcateLogicImpl;
import netTest.product.dao.CoursepostDao;
import netTest.product.dao.GoodproductDao;
import netTest.product.dao.LearnactivityDao;
import netTest.product.dao.ProductbaseDao;
import netTest.product.dao.OpenactivityDao;
import netTest.product.dao.OpenactivitymemberDao;
import netTest.product.dao.UserprodbuylogDao;
import netTest.product.dao.UserprodstatuslogDao;
import netTest.product.dao.UserproductDao;
import netTest.product.dao.impl.CoursepostDaoImpl;
import netTest.product.dao.impl.GoodproductDaoImpl;
import netTest.product.dao.impl.LearnactivityDaoImpl;
import netTest.product.dao.impl.ProductbaseDaoImpl;
import netTest.product.dao.impl.OpenactivityDaoImpl;
import netTest.product.dao.impl.OpenactivitymemberDaoImpl;
import netTest.product.dao.impl.UserprodbuylogDaoImpl;
import netTest.product.dao.impl.UserprodstatuslogDaoImpl;
import netTest.product.dao.impl.UserproductDaoImpl;
import netTest.product.logic.LearnactivityLogic;
import netTest.product.logic.OpenactivityLogic;
import netTest.product.logic.ProductLogic;
import netTest.product.logic.UserproductLogic;
import netTest.product.logic.impl.CoursepostLogicImpl;
import netTest.product.logic.impl.LearnactivityLogicImpl;
import netTest.product.logic.impl.OpenactivityLogicImpl;
import netTest.product.logic.impl.ProductLogicImpl;
import netTest.product.logic.impl.UserproductLogicImpl;
import netTest.wareques.dao.QuesdifficultDao;
import netTest.wareques.dao.QuestionDao;
import netTest.wareques.dao.QuestionitemDao;
import netTest.wareques.dao.QuestypeshopDao;
import netTest.wareques.dao.WareDao;
import netTest.wareques.dao.impl.QuesdifficultDaoImpl;
import netTest.wareques.dao.impl.QuestionDaoImpl;
import netTest.wareques.dao.impl.QuestionitemDaoImpl;
import netTest.wareques.dao.impl.QuestypeshopDaoImpl;
import netTest.wareques.dao.impl.WareDaoImpl;
import netTest.wareques.logic.QuestionLogic;
import netTest.wareques.logic.WareLogic;
import netTest.wareques.logic.WarequesLogic;
import netTest.wareques.logic.impl.QuestionLogicImpl;
import netTest.wareques.logic.impl.WareLogicImpl;
import netTest.wareques.logic.impl.WarequesLogicImpl;

public class BOFactory {
	
	  public static QuestypeshopDao getQuestypeshopDao(){
		  return QuestypeshopDaoImpl.getInstance();
	  }
	  
	  public static OrguserLogic getOrguserLogic() {
		  return OrguserLogicImpl.getInstance();
	  }
	
	  public static DeptinfoDao getDeptinfoDao(){
	      return DeptinfoDaoImpl.getInstance();
	  }
	
	  public static OrgbaserelDao getOrgbaserelDao() {
	      return OrgbaserelDaoImpl.getInstance();
	  }
	
	  public static OrgbaseDao getOrgbaseDao() {
	      return OrgbaseDaoImpl.getInstance();
	  }
	  
	  public static OrgLogic getOrgLogic() {
		  return OrgLogicImpl.getInstance();
	  }

	  public static OrglevelDao getOrglevelDao() {
	    return OrglevelDaoImpl.getInstance();
	  }

	  public static OrguserDao getOrguserDao() {
	    return OrguserDaoImpl.getInstance();
	  }

	  public static ContentcateDao getContentcateDao(){
		  return ContentcateDaoImpl.getInstance();
	  }
	  
	  public static ContentcateLogic getContentcateLogic(){
		  return ContentcateLogicImpl.getInstance();
	  }
	  
	  public static WareDao getWareDao(){
		  return WareDaoImpl.getInstance();
	  }

	  public static QuesdifficultDao getQuesdifficultDao(){
		  return QuesdifficultDaoImpl.getInstance();
	  }
	  
	  public static WareLogic getWareLogic(){
		  return WareLogicImpl.getInstance();
	  }
	  
	  public static QuestionLogic getQuestionLogic(){
		  return QuestionLogicImpl.getInstance();
	  }
	  
	  public static WarequesLogic getWarequesLogic(){
		  return WarequesLogicImpl.getInstance();
	  }
	  
	  public static QuestionDao getQuestionDao(){
		  return QuestionDaoImpl.getInstance();
	  }
	  
	  public static QuestionitemDao getQuestionitemDao(){
		  return QuestionitemDaoImpl.getInstance();
	  }
  
	  public static PaperDao getPaperDao() {
		  return PaperDaoImpl.getInstance();
	  }

	  public static PaperpropDao getPaperpropDao() {
	      return PaperpropDaoImpl.getInstance();
	  }
	  
	  public static PaperquesDao getPaperquesDao() {
	      return PaperquesDaoImpl.getInstance();
	  }

	  public static PaperquestypeDao getPaperquestypeDao() {
	      return PaperquestypeDaoImpl.getInstance();
	  }

	  public static PaperpatternratioDao getPaperpatternratioDao() {
	      return PaperpatternratioDaoImpl.getInstance();
	  }
	  
	  public static PaperLogic getPaperLogic(){
		  return PaperLogicImpl.getInstance();
	  }
	  
	  public static PaperquesLogic getPaperquesLogic(){
		  return PaperquesLogicImpl.getPaperquesInstance();
	  }
	  
	  public static ExampaperLogic getExampaperLogic(){
		  return ExampaperLogicImpl.getInstance();
	  }
	  
	  public static CheckPaperLogic getCheckPaperLogic(){
		  return CheckPaperLogicImpl.getInstance();
	  }
	  
	  public static TestdeptDao getTestdeptDao(){
		  return TestdeptDaoImpl.getInstance();
	  }
	  
	  public static TestinfoDao getTestinfoDao(){
		  return TestinfoDaoImpl.getInstance();
	  }
	  
	  public static TestuserDao getTestuserDao(){
		  return TestuserDaoImpl.getInstance();
	  }
	  
	  public static TestcheckerDao getTestcheckerDao() {
	      return TestcheckerDaoImpl.getInstance();
	  }
	  
	  public static TestinfoLogic getTestinfoLogic(){
		  return TestinfoLogicImpl.getInstance();
	  }
	  
	  public static TestdeptLogic getTestdeptLogic(){
		  return TestdeptLogicImpl.getInstance();
	  }
	  
	  public static TestuserLogic getTestuserLogic(){
		  return TestuserLogicImpl.getInstance();
	  }
	  
	  public static PaperquestypeLogic getPaperquestypeLogic(){
		  return PaperquestypeLogicImpl.getInstance();
	  }
	  
	  public static UseranswerDao getUseranswerDao(){
		  return UseranswerDaoImpl.getInstance();
	  }
	   
	  public static LearncontentDao getLearncontentDao(){
		  return LearncontentDaoImpl.getInstance();  
	  }
	  
	  public static LearncontentLogic getLearncontentLogic(){
		  return LearncontentLogicImpl.getInstance();  
	  }
	  
	  public static ExerciseLogic getExerciseLogic(){
		  return ExerciseLogicImpl.getInstance();
	  }
	  
	  public static ExerquesLogic getExerquesLogic(){
		  return ExerquesLogicImpl.getExerquesInstance();
	  }
	  
	  public static ExerciseDao getExerciseDao(){
		  return ExerciseDaoImpl.getInstance();
	  }
	  
	  public static ExerquestypeDao getExerquestypeDao() {
	      return ExerquestypeDaoImpl.getInstance();
	  }

	  public static ExerquesDao getExerquesDao(){
	    return ExerquesDaoImpl.getInstance();
	  }

	  public static ExeruserDao getExeruserDao(){
	    return ExeruserDaoImpl.getInstance();
	  }

	  public static UserexeranswerDao getUserexeranswerDao() {
	    return UserexeranswerDaoImpl.getInstance();
	  }
	  
	  public static UserLoginSessionLogic getUserLoginSessionLogic(){
		  return UserLoginSessionLogicImpl.getInstance();
	  }
	  
	  public static UserproductDao getUserproductDao(){
		  return UserproductDaoImpl.getInstance();
	  }
	  
	  public static UserproductLogic getUserproductLogic(){
		  return UserproductLogicImpl.getInstance();
	  }
	  
	  public static OrderproductLogic getOrderproductLogic(){
		  return OrderproductLogicImpl.getInstance();
	  }
	  
	  public static CustorderDao getCustorderDao(){
		  return CustorderDaoImpl.getInstance();
	  }
	  
	  public static OrderLogic getOrderLogic(){
		  return OrderLogicImpl.getInstance();
	  }
	  
	  public static OrderproductDao getOrderproductDao(){
		  return OrderproductDaoImpl.getInstance();
	  }
	  
	  public static UserprodbuylogDao getUserprodbuylogDao(){
		  return UserprodbuylogDaoImpl.getInstance();
	  }
	  
	  public static UserprodstatuslogDao getUserprodstatuslogDao(){
		  return UserprodstatuslogDaoImpl.getInstance();
	  }
	  
	  public static ExerquestypeLogic getExerquestypeLogic(){
		  return ExerquestypeLogicImpl.getInstance();
	  }
	  
	  public static ExeruserLogic getExeruserLogic(){
		  return ExeruserLogicImpl.getInstance();
	  }
	  	  
	  public static ProductbaseDao getProductbaseDao() {
		  return ProductbaseDaoImpl.getInstance();
	  }
	  
	  public static ProductLogic getProductLogic() {
		  return ProductLogicImpl.getInstance();
	  }
	  
	  public static GoodproductDao getGoodproductDao(){
		  return GoodproductDaoImpl.getInstance();
	  }
	  
	  public static LearnactivityLogic getLearnactivityLogic(){
		  return LearnactivityLogicImpl.getInstance();
	  }
	  
	  public static LearnactivityDao getLearnactivityDao(){
		  return LearnactivityDaoImpl.getInstance();
	  }
	  
	  public static CoursepostDao getCoursepostDao(){
		  return CoursepostDaoImpl.getInstance();
	  }
	  
	  public static CoursepostLogicImpl getCoursepostLogic(){
		  return CoursepostLogicImpl.getInstance();
	  }
	  
	  public static OpenactivityDao getOpenactivityDao(){
		  return OpenactivityDaoImpl.getInstance();
	  }
	  
	  public static OpenactivitymemberDao getOpenactivitymemberDao(){
		  return OpenactivitymemberDaoImpl.getInstance();
	  }
	  
	  public static OpenactivityLogic getOpenactivityLogic(){
		  return OpenactivityLogicImpl.getInstance();
	  }
	  
}

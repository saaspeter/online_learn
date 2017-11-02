package netTest.wareques.logic.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import netTest.bean.BeanFactory;
import netTest.bean.SysparamConstantNettest;
import netTest.event.EventHandlerNetTest;
import netTest.exception.ExceptionConstant;
import netTest.paper.vo.Paperques;
import netTest.util.ImportQuesFromTxtHelper;
import netTest.util.QueshowUtil;
import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.constant.QuestionImportConstant;
import netTest.wareques.dao.QuesanswerDao;
import netTest.wareques.dao.QuestionDao;
import netTest.wareques.dao.QuestionitemDao;
import netTest.wareques.logic.QuestionLogic;
import netTest.wareques.vo.Quesanswer;
import netTest.wareques.vo.Question;
import netTest.wareques.vo.Questionitem;

import org.apache.log4j.Logger;

import commonTool.event.Event;
import commonTool.exception.LogicException;
import commonTool.exception.NeedParamsException;
import commonTool.util.DateUtil;
import commonTool.util.ExcelUtil;
import commonTool.util.StringUtil;

public class QuestionLogicImpl implements QuestionLogic {
	
	static Logger log = Logger.getLogger(QuestionLogicImpl.class.getName());

	protected QuestionDao dao;
	protected QuestionitemDao itemDao;
	protected QuesanswerDao answerDao;
	
	
	public QuestionLogicImpl(){}
	
	/**
	 * 新增保存题目，总的入口，会根据不同的题目类型做相应的处理
	 * @param vo
	 * @return
	 */
	public Long addQues(Question vo){
		if(vo==null||vo.getQuestype()==null)
			throw new NeedParamsException("netTest.wareques.logic.impl.QuestionLogicImpl:addQues()");
		// 检查shop中的storage大小，包括shop的总storage大小和单个文件的storage大小
		
		Integer questype = vo.getQuestype();
		Long pk = null;
		boolean isSingleQues = true;
		try {
			if(QuestionConstant.QuesType_DanXuan.equals(questype)
					||QuestionConstant.QuesType_DuoXuan.equals(questype)
					||QuestionConstant.QuesType_XZ_NoTrunk.equals(questype))
			{  // 包括新增单选多选和完形填空题的子题目和阅读理解的子题目
				if(vo.getPid()==null){
				    pk = this.addQues_XZ(vo); // 插入单选和多选题
				}else {
					isSingleQues = false;
					pk = this.addQues_subques(vo); // 插入完形填空的子题目和阅读理解的子题目
				}
			}else if(QuestionConstant.QuesType_WenDa.equals(questype)){
				vo.setQueschecktype(QuestionConstant.Queschecktype_manual);
				vo.setQuesoptnum(1);
				if(vo.getComptype()==null)
				   vo.setComptype(QuestionConstant.Comptype_whole);
				pk = this.saveQues(vo);
			}else if(QuestionConstant.QuesType_PanDuan.equals(questype)){
				vo.setQueschecktype(QuestionConstant.Queschecktype_auto);
				vo.setQuesoptnum(1);
				if(vo.getComptype()==null)
				   vo.setComptype(QuestionConstant.Comptype_whole);
				pk = this.saveQues(vo);
			}else if(QuestionConstant.QuesType_YueDuLiJie.equals(questype)){
				// 这里只包括主题干的保存，其下的子题目在上面保存
				vo.setQueschecktype(QuestionConstant.Queschecktype_auto);
				vo.setQuesoptnum(0);
				if(vo.getComptype()==null)
				   vo.setComptype(QuestionConstant.Comptype_compWhole);
				pk = this.saveQues(vo);
			}else if(QuestionConstant.QuesType_TianKong.equals(questype)){
				vo.setQueschecktype(QuestionConstant.Queschecktype_both);
				// 得到填空题中题干需要填写的空格数目
				int optNum = QueshowUtil.getQuesAnswerCountTK(vo.getQuestion());
				vo.setQuesoptnum(optNum);
				if(vo.getComptype()==null)
				   vo.setComptype(QuestionConstant.Comptype_whole);
				pk = this.saveQues(vo);
			}else if(QuestionConstant.QuesType_WanXingTianKong.equals(questype)){
				// 这里只包括主题干的保存，其下的子题目在上面保存
				vo.setQueschecktype(QuestionConstant.Queschecktype_auto);
				vo.setQuesoptnum(0);
				if(vo.getComptype()==null)
				   vo.setComptype(QuestionConstant.Comptype_compWhole);
				pk = this.saveQues(vo);
			}
		} catch (Throwable e) {
			log.error(e);
			// delete the question, 因为目前用的是mysql的MyISAM表,不支持事务,
			// 所以在此处如果新增失败手动删除该question. 
			// 主要是出于性能考虑，以后用了全文检索，就可以把Question表变回InnoDB
			if(pk==null){
				pk = vo.getQuesid();
			}
			this.delSingleQues(pk, vo.getQuestype(), vo.getShopid(), vo.getPid());
			if(e instanceof LogicException){
				throw (LogicException)e;
			}else {
				throw new LogicException(e);
			}
		}
		// 更新题库题目
		if(isSingleQues) {
		   updateWareQuesNumber(vo.getWareid(), vo.getProductbaseid(), 1);
		}
		// 更新shop的storage大小
		
		return pk;
	}
		
	/**
	 * 插入选择题，包括单选、多选、完形填空的子题目和阅读理解的子题目
	 * @param vo
	 * @return
	 */
	private Long addQues_XZ(Question vo){
		if(vo==null)
			return null;
		String quesitemflag = null;
		// 组装题库问题属性
		vo.setQuescreatetime(DateUtil.getInstance().getNowtime_GLNZ());
		vo.setQueschecktype(QuestionConstant.Queschecktype_auto);
		if(vo.getComptype()==null)
		   vo.setComptype(QuestionConstant.Comptype_whole);
		vo.setStatus(QuestionConstant.Status_valide);
		if(vo.getFiletype()==null)
			vo.setFiletype(QuestionConstant.FileType_None);
		if(vo.getQuesoptnum()==null)
			vo.setQuesoptnum(1);
		Integer questype = vo.getQuestype();
		Long pk = null;
		// 如果是单选或多选则新增；如果是无题干的选择题，则不插入题干
		if(questype!=null&&QuestionConstant.QuesType_DanXuan.equals(questype)
				||QuestionConstant.QuesType_DuoXuan.equals(questype)){
			pk = dao.insert(vo);
		    addQuesFile(vo); // 处理题干文件
		}else{
			pk = vo.getPid(); // 对于没有题干的选择题（完形填空），设置其父id为其子答案选项的quesid
			// 在此生成子题目中的quesItemFlag字段的值
			quesitemflag = itemDao.qryItemFlagSEQ();
		}
		if(vo.getAnswerVO()!=null){
			Quesanswer answerVO = vo.getAnswerVO();
		    answerVO.setQuesid(pk);
		    answerVO.setShopid(vo.getShopid());
		    answerDao.insert(answerVO);
		}
		if(vo.getItemList()!=null&&vo.getItemList().size()>0){
			Questionitem itemVO = null;
			for(int i=0;i<vo.getItemList().size();i++){
				itemVO = (Questionitem)(vo.getItemList().get(i));
				if(pk!=null)
				   itemVO.setQuesid(pk);
				itemVO.setShopid(vo.getShopid());
				itemVO.setProductbaseid(vo.getProductbaseid());
				itemVO.setQuesitemflag(quesitemflag);
				if(itemVO.getFiletype()==null)
					itemVO.setFiletype(QuestionConstant.FileType_None);
				addQuesItem(itemVO);
			}	
		}
		return pk;
	}
	
	/**
	 * 插入完形填空的子题目和阅读理解的子题目
	 * @param vo
	 * @return
	 */
	private Long addQues_subques(Question vo){
		if(vo==null)
			return null;
		Long pk = this.addQues_XZ(vo);
		if(vo.getPid()!=null){ // 是复合题目的子题目
			// 更新父题目的字题目个数quesoptnum
			dao.updateQuesOptNum(vo.getPid(), 1);
		}
		return pk;
	}
	
	/**
	 * 更新题目，所有类型的题目的入口
	 * @param vo
	 * @param delItemStr：对于选择题时有用，其余类型的题目可以留空
	 * @return
	 */
	public Question updateQues(Question vo,String delItemStr){
		if(vo==null||vo.getQuestype()==null||vo.getQuesid()==null)
			throw new NeedParamsException("netTest.wareques.logic.impl.QuestionLogicImpl:updateQues()");
		Integer questype = vo.getQuestype();
		if(!QuestionConstant.QuesType_XZ_NoTrunk.equals(questype)){
			Question vodb = dao.selectByPK(vo.getQuesid());
			if(vodb==null){
				throw new LogicException(ExceptionConstant.Error_Record_Not_Exists);
			}else {
				vo.setShopid(vodb.getShopid());
				vo.setProductbaseid(vodb.getProductbaseid());
				if(vo.getAnswerVO()!=null){
					vo.getAnswerVO().setShopid(vodb.getShopid());
				}
			}
		}
		if(QuestionConstant.QuesType_DanXuan.equals(questype)
				||QuestionConstant.QuesType_DuoXuan.equals(questype)
				||QuestionConstant.QuesType_XZ_NoTrunk.equals(questype)){
			this.updateQues_XZ(vo, delItemStr);
		}else if(QuestionConstant.QuesType_WenDa.equals(questype)){
			this.saveQues(vo);
		}else if(QuestionConstant.QuesType_PanDuan.equals(questype)){
			this.saveQues(vo);
		}else if(QuestionConstant.QuesType_YueDuLiJie.equals(questype)){
			this.saveQues(vo);
		}else if(QuestionConstant.QuesType_TianKong.equals(questype)){
			// 更新填空题中题干需要填写的空格数目
			int optNum = QueshowUtil.getQuesAnswerCountTK(vo.getQuestion());
			vo.setQuesoptnum(optNum);
			this.saveQues(vo);
		}else if(QuestionConstant.QuesType_WanXingTianKong.equals(questype)){
			this.saveQues(vo);
		}
		return vo;
	}
	
	/**
	 * 更新选择题（单选和多选）
	 * @param vo
	 * @param delItemStr
	 * @return
	 */
	private Long updateQues_XZ(Question vo,String delItemStr){
		if(vo==null||vo.getQuesid()==null)
			return null;
		Integer questype = vo.getQuestype();
		Long subquesid = null;
		if(questype==null||QuestionConstant.QuesType_DanXuan.equals(questype)
		     ||QuestionConstant.QuesType_DuoXuan.equals(questype))
		{
		   updateQuesFile(vo); // 处理题干的文件
		   // 更新题干信息
		   dao.updateByPK(vo);
		   subquesid = vo.getQuesid();
		}else{
		   subquesid = vo.getPid();  // 对于没有题干的选择题，设置其父id为其子答案选项的quesid
		}
		// 处理答案选项内容
		if(vo.getItemList()!=null&&vo.getItemList().size()>0){
			Questionitem itemVO = null;
			for(int i=0;i<vo.getItemList().size();i++){
				itemVO = (Questionitem)(vo.getItemList().get(i));
				if(itemVO!=null){
					itemVO.setShopid(vo.getShopid());
					itemVO.setProductbaseid(vo.getProductbaseid());
					if(itemVO.getQuesitemid()==null){
						itemVO.setQuesid(subquesid);
						this.addQuesItem(itemVO);
					}else {
						this.updateQuesItem(itemVO);
					}
				}
			}
		}
		// 更新答案解析
		Quesanswer answerVO = vo.getAnswerVO();
		if(answerVO!=null){
			answerVO.setQuesid(vo.getQuesid());
			answerDao.save(answerVO);
		}
		// 删除要提交删除的题目选项
		if(delItemStr!=null&&delItemStr.trim().length()>0){
			String[] itemArr = StringUtil.splitString(delItemStr, ",");
			if(itemArr!=null&&itemArr.length>0){
				for(int j=0;j<itemArr.length;j++){
					delQuesItem(vo.getQuesid(),itemArr[j],vo.getShopid(),vo.getProductbaseid(),vo.getQuesKind());
				}
			}
		}
		return vo.getQuesid();
	}
	
	/**
	 * 保存问答题或判断题或填空题或阅读理解的主题，包括新增和更新
	 * @param vo
	 * @return
	 */
	private Long saveQues(Question vo){
		if(vo==null)
			return null;
		Long pk = vo.getQuesid();
		Quesanswer answerVO = vo.getAnswerVO();
		if(pk==null){
			// 组装题库问题属性
			vo.setQuescreatetime(DateUtil.getInstance().getNowtime_GLNZ());
			if(vo.getQueschecktype()==null)
			   vo.setQueschecktype(QuestionConstant.Queschecktype_manual);
			if(vo.getComptype()==null)
			   vo.setComptype(QuestionConstant.Comptype_whole);
			vo.setStatus(QuestionConstant.Status_valide);
			if(vo.getFiletype()==null)
				vo.setFiletype(QuestionConstant.FileType_None);
		    pk = dao.insert(vo);
		    if(answerVO!=null){
		       answerVO.setQuesid(pk);
		       answerVO.setShopid(vo.getShopid());
		       answerDao.insert(answerVO);
		    }
		    addQuesFile(vo); // 处理新增题目时题干的文件
		}else{
			updateQuesFile(vo); // 处理保存题目时题干的文件
		    dao.updateByPK(vo);
		    if(answerVO!=null){
		    	answerVO.setQuesid(pk);
		    	answerDao.save(answerVO);
		    }
		}
	    return pk;
	}
	
	/**
	 * 新增题目答案选项。答案选项中必须有quesid和shopid
	 * @param itemVO
	 * @return
	 */
	public boolean addQuesItem(Questionitem itemVO){
		if(itemVO==null||itemVO.getQuesid()==null||itemVO.getShopid()==null)
			return false;
		boolean result = true;
		if(itemVO.canInsert()){ // 排除掉可能为空的题目选项
			Long pk_item = null;
			String itemUrl = null;
			pk_item = itemDao.insert(itemVO);
			if(pk_item!=null&&itemVO.getFiletype()!=null&&!QuestionConstant.FileType_None.equals(itemVO.getFiletype())
					&&itemVO.getItemFile()!=null&&itemVO.getItemFile().getFileSize()>0){
				itemUrl = QuestionUtil.saveQuesItemFile(itemVO.getQuesid(),pk_item.toString(),
						                        itemVO.getShopid(), itemVO.getProductbaseid(), 
						                            itemVO.getQuesKind(),itemVO.getItemFile());
				// 更新文件路径
				itemDao.updateFileurl(pk_item, itemUrl);
			}
		}else{
			result = false;
		}
		return result;
	}
	
	/**
	 * 更新题目答案选项，包括更新文件
	 * @param itemVO
	 * @return
	 */
	public boolean updateQuesItem(Questionitem itemVO){
		if(itemVO==null||itemVO.getQuesid()==null||itemVO.getQuesitemid()==null)
			return false;
		// 处理文件
		if(QuestionConstant.FileType_None.equals(itemVO.getFiletype())){
			// 如果原来的文件类型不是为空，则用户是要删除附件
			if(!QuestionConstant.FileType_None.equals(itemVO.getFiletypeOrigin())){
				QuestionUtil.delQuesPerfixFile(itemVO.getQuesid(), itemVO.getQuesitemid().toString(),
						          itemVO.getShopid(),itemVO.getProductbaseid(),itemVO.getQuesKind(),2);
			}
		}else if(itemVO.getItemFile()!=null&&itemVO.getItemFile().getFileSize()>0){
			// 新增新上传的附件或替换掉原来旧的附件
			if(!QuestionConstant.FileType_None.equals(itemVO.getFiletypeOrigin())){
				QuestionUtil.delQuesPerfixFile(itemVO.getQuesid(), itemVO.getQuesitemid().toString(),
						          itemVO.getShopid(),itemVO.getProductbaseid(),itemVO.getQuesKind(),2);
			}
			String fileurl = QuestionUtil.saveQuesItemFile(itemVO.getQuesid(),
							itemVO.getQuesitemid().toString(),itemVO.getShopid(),
							itemVO.getProductbaseid(),itemVO.getQuesKind(),itemVO.getItemFile());
			itemVO.setFileurl(fileurl);
		}
		itemDao.updateByPK(itemVO);
		return true;
	}
	
	/**
	 * 删除单个答案选项
	 * @param quesid
	 * @param itemId
	 * @return
	 */
	public boolean delQuesItem(Long quesid,String itemId,Long shopid,
			                   Long productid,int quesKind){
		if(quesid==null||itemId==null||itemId.trim().length()<1)
			return false;
		// 删除文件
		QuestionUtil.delQuesPerfixFile(quesid,itemId,shopid,
				                       productid,quesKind,2);
		itemDao.deleteByPK(new Long(itemId));
		return true;
	}
	
	/**
	 * 查询单个题目，包括完整的题目选项
	 * @param quesid
	 * @param shopid
	 * @return
	 */
	public Question qryQuesByPk(Long quesid,Long shopid){
		if(quesid==null)
			return null;
		Question vo = dao.selectByPK(quesid);
		if(vo!=null){ 
			// 题目的从题干分离题目操作
			Short comptype = vo.getComptype();
			Integer questype = vo.getQuestype();
			if(QuestionConstant.QuesType_DanXuan.equals(questype)
					||QuestionConstant.QuesType_DuoXuan.equals(questype)){
				List itemList2 = itemDao.selectByQues(quesid, shopid);
				vo.setItemList(itemList2);
			}
			// 查询题目答案表
			Quesanswer answerVO = answerDao.selectByPK(quesid);
			vo.setAnswerVO(answerVO);
            // 加载题目知识点
			
			// 如果是复合题目，在此加载子题目
			if(QuestionConstant.Comptype_compWhole.equals(comptype)){
				List subquesList = null;
				// 完形填空的处理比较特别，子题目没有提干，只有答案选项
				if(QuestionConstant.QuesType_WanXingTianKong.equals(questype)){
					List itemList = itemDao.selectByQues(quesid, shopid);
					subquesList = shapeSubquesList(itemList,vo,vo.getQuestypeid(),null,0);
					vo.setSubquesList(subquesList);
				}else{
					subquesList = dao.selectByPid(quesid);
					vo.setSubquesList(subquesList);
					if(subquesList!=null&&subquesList.size()>0){
						Question subVO = null;
						List itemList1 = null;
						for(int i=0;i<subquesList.size();i++){
							subVO = (Question)subquesList.get(i);
							if(subVO!=null&&(QuestionConstant.QuesType_DanXuan.equals(subVO.getQuestype())
									||QuestionConstant.QuesType_DuoXuan.equals(subVO.getQuestype())))
							{
								itemList1 = itemDao.selectByQues(subVO.getQuesid(), shopid);
								subVO.setItemList(itemList1);
							}
							// set rowitems
							if(subVO.getRowitems()==null){
								subVO.setRowitems(vo.getRowitems());
							}
						}
					}
				}
				
			}
			
		}
		return vo;
	}
	
	/**
	 * 删除一个题库题目，包括题目的文件
	 * @param quesid
	 * @param quesKind 问题的种类：题库题目（1）还是试卷题目（2）
	 * @param pid 父题目id，如果还有父题目，则更新父题目的子题目数量字段
	 * @return
	 */
	public boolean delSingleQues(Long quesid,Integer questype,Long shopid,Long pid){
		if(quesid==null||shopid==null)
			throw new NeedParamsException("netTest.wareques.logic.impl.QuestionLogicImpl:delSingleQues()");
		// 删除题目本身和题目选项
		Question quesVO = dao.selectByPK(quesid);
		if(questype==null){
			questype = quesVO.getQuestype();
		}
		dao.deleteByPK(quesid);
		// 删除该问题的文件
		QuestionUtil.delWholeQuesFile(quesid,shopid,quesVO.getProductbaseid());
        // 删除其子题目（如果是复合题目）-- 暂时不删除复合题目的附件，暂时也没有附件
		if(QuestionConstant.QuesType_WanXingTianKong.equals(questype)||QuestionConstant.QuesType_YueDuLiJie.equals(questype))
		{
			if(QuestionConstant.QuesType_WanXingTianKong.equals(questype)){
				itemDao.deleteByQuesid(quesid);
			}else{
				dao.delSubquesByPid(quesid);
			}
		}
		if(pid!=null){
			// 如果还有父题目，则更新父题目的字题目数量字段
			dao.updateQuesOptNum(pid, -1);			
		}
		return true;
	}
	
	// 处理新增题目的题干的附件处理
	private void addQuesFile(Question vo){
		if(vo==null)
			return;
		Long pk = vo.getQuesid();
		if(vo.getFiletype()!=null&&!QuestionConstant.FileType_None.equals(vo.getFiletype())
				&&vo.getQuesFile()!=null&&vo.getQuesFile().getFileSize()>0){
			String fileurl = QuestionUtil.saveQuesFile(pk,vo.getShopid(),vo.getProductbaseid(),
															vo.getQuesKind(),vo.getQuesFile());
			// 更新文件路径
			dao.updateFileurl(pk, fileurl);
	   }
	}
	
	// 处理更新题目时的题干的附件的处理
	private void updateQuesFile(Question vo){
		if(vo==null)
			return;
		if(QuestionConstant.FileType_None.equals(vo.getFiletype()))
		{
			if((vo.getFiletypeOrigin()!=null&&!QuestionConstant.FileType_None.equals(vo.getFiletypeOrigin()))){
			   // 如果原来题干是有附件的，则在此删除该附件
			   QuestionUtil.delQuesPerfixFile(vo.getQuesid(), String.valueOf(vo.getQuesid()),
					          		vo.getShopid(),vo.getProductbaseid(),vo.getQuesKind(),1);
			   // 更新文件路径
		       vo.setFileurl("");
			}
		}else if(vo.getQuesFile()!=null&&vo.getQuesFile().getFileSize()>0){
            // 如果原来题干是有附件的，则在此删除该附件
			if((vo.getFiletypeOrigin()!=null&&!QuestionConstant.FileType_None.equals(vo.getFiletypeOrigin()))){
			   QuestionUtil.delQuesPerfixFile(vo.getQuesid(), String.valueOf(vo.getQuesid()),
					   				vo.getShopid(),vo.getProductbaseid(), vo.getQuesKind(),1);
			}
			// 保存新的文件附件
			String fileurl = QuestionUtil.saveQuesFile(vo.getQuesid(),vo.getShopid(),vo.getProductbaseid(),
					                                   vo.getQuesKind(),vo.getQuesFile());
			// 更新文件路径
			vo.setFileurl(fileurl);
		}
	}
	
	/**
	 * 删除多个题目，包括删除复合题目的子题目
	 * 也可以删除多个子题目，同时处理这些子题目的父题目的一些操作
	 * @param quesArr:二维数组，[0]代表主键quesid，[1]代表题目类型
	 * @param quesKind: 1题目题目，2试卷题目
	 * @param pid: 子题目的父题目，如果是独立的题目，则传入空即可
	 * @return
	 */
	public int delQuesBatch(String[][] quesArr,Long pid,Long shopid,Long productid){
		int rows = 0;
		if(quesArr==null||quesArr.length<1||shopid==null)
			return rows;
		Long wareid = null;
		Long tempproductid = null;
		StringBuffer buffer = new StringBuffer();
		if(quesArr!=null&&quesArr.length>0){
			Integer questype = null;
			Long quesidTemp = null;
			for(int i=0;i<quesArr.length;i++){
				if(quesArr[i][0]!=null){
					if(quesArr[i][1]!=null && quesArr[i][1].trim().length()>0){
						questype = new Integer(quesArr[i][1]);
					}
					quesidTemp = new Long(quesArr[i][0]);
					// 检查所要删除的question是否都是同一个product中的，如果不是则认为是非法访问
				    Question voTemp = dao.selectByPK(quesidTemp);
				    if(voTemp!=null){
				       wareid = voTemp.getWareid();
				       tempproductid = voTemp.getProductbaseid();
				       if(!tempproductid.equals(productid)){
				    	  throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
				       }
				    }
					delSingleQues(quesidTemp, questype, shopid, pid);
					buffer.append(quesArr[i][0]).append(",");
				}
			}
			rows = quesArr.length;
		}
		// publish event, 删除引用题目的练习题和用户练习答案
		if(buffer.toString().length()>0){
			Map paraMap = new HashMap();
			paraMap.put("quesidStr", buffer.toString());
			Event event = new Event(EventHandlerNetTest.EventType_WareQuestion_DelQuesBatch, paraMap);
			EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Thread);
		}
		// 更新题库题目
		updateWareQuesNumber(wareid, productid, -rows);
		return rows;
	}
	
	/**
	 * 删除完形填空题的子题目
	 * @return
	 */
	public int delSubQues_WXTK(Long quesid,Integer disorder){
		if(quesid==null||disorder==null)
			return 0;
		int rows = 0;
		rows = itemDao.delByQuesidOrder(quesid, disorder);
		// 更新父题目的字题目个数quesoptnum
		dao.updateQuesOptNum(quesid, -1);
		return rows;
	}
	
    /**
     * 根据子题目选项id和显示序号查询比指定disorder略大的显示序号
     * 如果disorder为空，则查询所属的quesid下的子选项中最大的显示号disorder
     * @param quesid
     * @param disorder
     * @return
     */
    public Integer qryLargerDisorder(Long quesid,Integer disorder){
    	return itemDao.qryLargerDisorder(quesid, disorder);
    }
    
	/**
	 * 针对完形填空题，把所有完形填空题的子选项按照disOrder分离成单个子问题，每个字问题包含具有相同disOrder的List
	 * 每个子问题的quesid：直接取题目选项中的quesItemFlag字段
	 * @param votype:0为Question，1为Paperques
	 * @param paperquesscore:如果是试卷试题，则传入试题分数
	 * @param itemList
	 * @return
	 */
	public static List shapeSubquesList(List itemList,Question parentQuesVO,Long questypeid,Float paperquesscore,int votype){
		if(itemList==null||itemList.size()<1)
			return null;
		List rtnList = new ArrayList();
		List<Questionitem> subitemList = null;
		Question subVO = null;
		Map orderMap = new HashMap();
		Questionitem itemVO = null;
		for(int i=0;i<itemList.size();i++){
			itemVO = (Questionitem)itemList.get(i);
			if(itemVO.getDisorder()!=null){
				if(orderMap.get(itemVO.getDisorder())==null)
				{
					if(votype==1){
					    subVO = new Paperques();
					    if(paperquesscore==null)
					    	paperquesscore = 0f;
					    ((Paperques)subVO).setPaperquesscore(paperquesscore);
					}else{
						subVO = new Question();
					}
					subVO.setPid(itemVO.getQuesid());
					// 组装子题目的quesid
					subVO.setQuesid(shapeSubquesid_WXTK(itemVO));
					subVO.setQuestype(QuestionConstant.QuesType_XZ_NoTrunk);
					subVO.setQuestypeid(questypeid);
					subitemList = new ArrayList<Questionitem>();
					subitemList.add(itemVO);
					subVO.setItemList(subitemList);
					subVO.setDisorder(itemVO.getDisorder());
					subVO.setComptype(QuestionConstant.Comptype_compPart);
					subVO.setRowitems(parentQuesVO.getRowitems());
					orderMap.put(itemVO.getDisorder(), subVO);
					rtnList.add(subVO);
				}else{
					subVO = (Question)orderMap.get(itemVO.getDisorder());
					subVO.getItemList().add(itemVO);
				}
			}
		}
		return rtnList;
	}
	
	/**
	 * 根据完形填空题的子选项记录的quesid，直接取题目选项中的quesItemFlag字段
	 * @param quesid
	 * @param disorder
	 * @return
	 */
	private static Long shapeSubquesid_WXTK(Questionitem itemVO){
        if(itemVO==null||itemVO.getQuesitemflag()==null)
        	return null;
        else
		    return new Long(itemVO.getQuesitemflag());
	}

	/**
	 * 从excel文件中导入试题
	 * @param input
	 * @param wareid
	 * @param shopid
	 * @param productid
	 * @return 返回导入后的错误信息list, 最后一个元素是导入的成功数目
	 * @throws Exception
	 */
	public List<String> importQuesFromExcel(InputStream input,Long wareid, Long shopid, 
			Long productid, Long creatorID, Locale locale) {
    	if(input==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);		
        String successNumber = "0";
        Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
		Map map = new HashMap();
        map.put("wareid",wareid);
        map.put("shopid",shopid);
        map.put("productbaseid",productid);
        map.put("quescreatetime", currentDate);
        map.put("filetype", QuestionConstant.FileType_None);
        map.put("creatorid", creatorID);
	    // 解析excel文件从中提取出题目	
        ExcelUtil helper = new ExcelUtil(input, SysparamConstantNettest.FileImportQuestion_maxNumLimit,
        								 QuestionImportRow.getInstance(), locale);

        List list = helper.readExcel("netTest.wareques.vo.Question",1,QuestionImportConstant.questionExcelColumn,map);
        // 保存从excel文件中读取的题目到数据库中   
        if(list!=null&&list.size()>0){
            dao.insertBatch(list);
            successNumber = String.valueOf(list.size());
            // 更新题库中题目数量, 通过event机制
            updateWareQuesNumber(wareid, productid, list.size());
        }
        List<String> messageList = helper.getMessageList();
        messageList.add(successNumber);
        return messageList;
    }
	
	/**
	 * 从txt文件中导入试题
	 * @param input
	 * @param wareid
	 * @param shopid
	 * @param productid
	 * @return 返回导入后的错误信息list, 最后一个元素是导入的成功数目
	 * @throws Exception
	 */
	public List<String> importQuesFromTxt(InputStream input,Long wareid, Long shopid, 
			Long productid, Long creatorID) {
    	if(input==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);		
        String successNumber = "0";
        Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
		Map map = new HashMap();
        map.put("wareid",wareid);
        map.put("shopid",shopid);
        map.put("productbaseid",productid);
        map.put("quescreatetime", currentDate);
        map.put("filetype", QuestionConstant.FileType_None);
        map.put("creatorid", creatorID);
	    // 解析excel文件从中提取出题目	
        ImportQuesFromTxtHelper helper = ImportQuesFromTxtHelper.getInstance();
        List[] importRtnArr = helper.readFile(input, QuestionImportConstant.QuestionType_Question, map);
        List queslist = importRtnArr[0];
        // 保存从excel文件中读取的题目到数据库中   
        if(queslist!=null&&queslist.size()>0){
            dao.insertBatch(queslist);
            successNumber = String.valueOf(queslist.size());
            // 更新题库中题目数量, 通过event机制
            updateWareQuesNumber(wareid, productid, queslist.size());
        }
        List<String> messageList = (List<String>)importRtnArr[1];
        messageList.add(successNumber);
        return messageList;
    }
	
	/**
	 * 更新题库题目
	 * @param wareid
	 * @param number
	 */
	public void updateWareQuesNumber(Long wareid, Long productid, int number){
		Map paraMap = new HashMap();
		paraMap.put("wareid", wareid.toString());
		paraMap.put("productid", productid.toString());
		paraMap.put("quesnumber", String.valueOf(number));
		Event event = new Event(EventHandlerNetTest.EventType_Question_ImportQues, paraMap);
		EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Thread);
	}
	
    /**
     * 更换两个题目的题目选项的disorder，是对于复合题目的子题目来做的动作
     * @param vo
     * @param type:1代表向上移动(disorder变小)，2代表向下移动
     * @return
     */
    public boolean switDisorderQues(Questionitem vo,int type){
    	if(type!=1&&type!=2)
    		return false;
    	boolean ret = itemDao.switDisorderQues(vo, type);
    	return ret;
    }
	
	/**
	 * static spring getMethod
	 */
	 public static QuestionLogic getInstance(){
		 QuestionLogic logic = (QuestionLogic)BeanFactory.getBeanFactory().getBean("questionLogic");
	     return logic;
	 }

	public QuestionDao getDao() {
		return dao;
	}

	public void setDao(QuestionDao dao) {
		this.dao = dao;
	}

	public QuestionitemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(QuestionitemDao itemDao) {
		this.itemDao = itemDao;
	}

	public QuesanswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(QuesanswerDao answerDao) {
		this.answerDao = answerDao;
	}
	
}

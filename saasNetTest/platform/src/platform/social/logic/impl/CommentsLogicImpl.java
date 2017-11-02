package platform.social.logic.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;

import platform.bean.BeanFactory;
import platform.exception.ExceptionConstant;
import platform.logicImpl.BOFactory_Platform;
import platform.social.constant.CommentsConstant;
import platform.social.dao.CommentsDao;
import platform.social.dto.CommentsQuery;
import platform.social.logic.CommentsLogic;
import platform.social.vo.Comments;
import platform.social.vo.Leavemessage;
import platform.vo.User;
import commonTool.base.Page;
import commonTool.cache.CacheSynchronizer;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;


public class CommentsLogicImpl implements CommentsLogic {

	private CommentsDao commentsDao;
	
	public CommentsDao getCommentsDao() {
		return commentsDao;
	}

	public void setCommentsDao(CommentsDao commentsDao) {
		this.commentsDao = commentsDao;
	}
	
	/**
	 * 查询comments分页,page中包括顶级comments, 同时包括下级子comments
	 * @param queryVO
	 * @return
	 */
	@Cacheable(value="platform.socialContentCache", 
    		key="'CommentsLogic.qryComments~comments-'+#objecttype+':'+#objectid+'~'+#subclassid+'~'+#creatorid+'~'+#pageIndex+'~'+#pageSize")
	public Page qryComments(String objectid, String objecttype, String subclassid, Long creatorid, int pageIndex, int pageSize,Integer total){
		if(!CommentsConstant.isSupportObjectType(objecttype)){
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		}
		CommentsQuery queryVO = new CommentsQuery();
        queryVO.setObjectid(objectid);
        queryVO.setObjecttype(objecttype);
        queryVO.setSubclassid(subclassid);
        queryVO.setCreatorid(creatorid);
		// 仅仅查询顶级的comment
		queryVO.setSearchparent("1");
		Page page = commentsDao.selectByVOPage(queryVO, pageIndex, pageSize, total);
		List<Comments> list = page.getList();
		if(list!=null){
			Set<Long> useridSet = new HashSet<Long>();
			Map<Long, Comments> map = new HashMap<Long, Comments>();
			StringBuffer parentStrBuff = new StringBuffer();
			for(Comments vo : list){
				if(vo.getSubnum()!=null && vo.getSubnum()>0){
					parentStrBuff.append(vo.getCommentid().toString()).append(",");
					map.put(vo.getCommentid(), vo);
				}
				useridSet.add(vo.getCreatorid());
			}
			// 查询所有有子comment的记录，并把子comment加入到parent级的comment
			if(parentStrBuff!=null && parentStrBuff.toString().length()>0){
				CommentsQuery tempqueryVO = new CommentsQuery();
				tempqueryVO.setParentcommentidStr(parentStrBuff.substring(0, parentStrBuff.length()-1));
				List<Comments> rtnList = commentsDao.selectByVO(tempqueryVO);
				if(rtnList!=null && rtnList.size()>0){
					Comments tempVO = null;
					for(int i=0; i<rtnList.size(); i++){
						tempVO = rtnList.get(i);
						map.get(tempVO.getParent()).addSubVO(tempVO);
						useridSet.add(tempVO.getCreatorid());
					}
				}
			}
			
			// 添加user信息
			Map<Long, User> userMap = BOFactory_Platform.getUserLogic().qryBatchUser(useridSet);
			if(userMap!=null && !userMap.isEmpty()){
				for(Comments vo : list){
					if(vo.getCreatorid()!=null && userMap.get(vo.getCreatorid())!=null){
						vo.setCreatordisplayname(userMap.get(vo.getCreatorid()).getDisplayname());
					}
					if(vo.getSubList()!=null && vo.getSubList().size()>0){
						for(Comments subvo : vo.getSubList()){
							if(subvo.getCreatorid()!=null && userMap.get(subvo.getCreatorid())!=null){
								subvo.setCreatordisplayname(userMap.get(subvo.getCreatorid()).getDisplayname());
							}
						}
					}
				}
			}
		}
		// build assoc for cache
		CacheSynchronizer.getInstance().buildAssoc("platform.socialContentCache", 
				"CommentsLogic.qryComments~"+Comments.ObjectType+"-"+objecttype+":"+objectid
				+'~'+subclassid+'~'+creatorid+'~'+pageIndex+'~'+pageSize,
				new String[]{Comments.ObjectType+"-"+objecttype+":"+objectid});
		return page;
	}

	/**
	 * delete comments, and update its parent's sub comment number
	 * @param pk
	 */
	public void delCommentsByPK(Long pk){
		Comments vo = commentsDao.selectByPK(pk);
		//TODO do delete authentation
		if(vo!=null){
			commentsDao.deleteByPK(pk);
			commentsDao.deleteChildByPK(pk);
		    if(vo.getParent()!=null){
			   commentsDao.updateSubNum(vo.getParent(), -1);
		    }
		}
	}
	
	public static CommentsLogic getInstance() {
		CommentsLogic logic = (CommentsLogic)BeanFactory.getBeanFactory().getBean("commentsLogic");
        return logic;
    }

}

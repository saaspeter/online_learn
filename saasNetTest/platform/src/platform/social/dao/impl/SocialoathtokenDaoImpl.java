package platform.social.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import platform.bean.BeanFactory;
import platform.social.dao.SocialoathtokenDao;
import platform.social.vo.Socialoathtoken;
import commonTool.base.BaseDao;

public class SocialoathtokenDaoImpl extends BaseDao implements SocialoathtokenDao {

	static Logger log = Logger.getLogger(SocialoathtokenDaoImpl.class.getName());
	
    /**
     *
     */
    public SocialoathtokenDaoImpl() {
        super();
    }
    
    /**
     * select all social account binded by an identity
     */
    public Socialoathtoken selectByUser(Long identityid, Short identitytype){
    	if(identityid==null||identitytype==null)
    		return null;
    	Socialoathtoken vo = new Socialoathtoken();
    	vo.setIdentityid(identityid);
    	vo.setIdentitytype(identitytype);
    	Socialoathtoken record = (Socialoathtoken) this.queryForObject("Socialoathtoken.selectByUser", vo);
		return record;
    }
    
    @Cacheable(value="platform.socialoathtokenCache", key="'SocialoathtokenDao.Socialoathtoken~'+#identitytype+'~'+#servicetype+'~'+#socialuserid", unless="#result==null")
    public Socialoathtoken selectBySocialAccount(Short identitytype, Short servicetype, String socialuserid){
    	if(identitytype==null||servicetype==null||socialuserid==null)
    		return null;
    	Socialoathtoken vo = new Socialoathtoken();
    	vo.setIdentitytype(identitytype);
    	vo.setServicetype(servicetype);
    	vo.setSocialuserid(socialuserid);
    	Socialoathtoken record = (Socialoathtoken) this.queryForObject("Socialoathtoken.selectBySocialAccount", vo);
		return record;
    }
    
    /**
     * insert a record
     */
    @Caching(evict={@CacheEvict(value="platform.socialoathtokenCache", key="'SocialoathtokenDao.Socialoathtoken~'+#record.identitytype+'~'+#record.servicetype+'~'+#record.socialuserid")})
    public void insert(Socialoathtoken record){
    	if(record==null)
    		return;
		super.insert("Socialoathtoken.insert", record);	
    }

    /**
     * update a record By PK
     */
    @Caching(evict={@CacheEvict(value="platform.socialoathtokenCache", key="'SocialoathtokenDao.Socialoathtoken~'+#record.identitytype+'~'+#record.servicetype+'~'+#record.socialuserid")})
    public int updateByPK(Socialoathtoken record){
    	if(record==null||record.getIdentityid()==null||record.getServicetype()==null
    			||record.getIdentitytype()==null||record.getSocialuserid()==null)
    		return 0;
		int rows = super.update("Socialoathtoken.updateByPK", record);
		return rows;
    }

    /**
     * delete a record by PK
     */
    @Caching(evict={@CacheEvict(value="platform.socialoathtokenCache", key="'SocialoathtokenDao.Socialoathtoken~'+#identitytype+'~'+#servicetype+'~'+#socialuserid")})
    public int deleteByPK(Long identityid, Short identitytype, Short servicetype, String socialuserid){
    	if(identityid==null||servicetype==null||identitytype==null)
    		return 0;
    	Socialoathtoken vo = new Socialoathtoken();
    	vo.setIdentityid(identityid);
    	vo.setIdentityid(identityid);
    	vo.setServicetype(servicetype);
    	vo.setSocialuserid(socialuserid);
		int rows = super.delete("Socialoathtoken.deleteByPK", vo);
		return rows;
    }
    

    /**
     * static spring getMethod
     */
     public static SocialoathtokenDao getInstance(){
    	 SocialoathtokenDao dao = (SocialoathtokenDao)BeanFactory.getBeanFactory().getBean("socialoathtokenDao");
         return dao;
     }
    
}

package platform.mail.logic;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import platform.mail.dao.EmailtemplateDao;
import platform.mail.dao.impl.EmailtemplateDaoImpl;
import platform.mail.vo.Emailtemplate;

import commonTool.constant.I18nConstant;
import commonTool.constant.SysparamConstant;
import commonTool.exception.LogicException;
import commonTool.mail.MailCreator;
import commonTool.mail.MailData;

/**
 * This is the default implementation of the MailCreator interface, it gets the template
 * from the config and replaces the values in it.
 */
public class DefaultMailCreator implements MailCreator{
    
    private EmailtemplateDao emailtemplateDao = null;
    
    private static Map<String, Emailtemplate> templateMap = null;

    /**
     * This method is call ONLY ONCE by the MailProcessor.
     * This method should be used to initialize things in
     * this class.
     *
     * @param key          The Key that identifies the task
     * @param templateName The Template Name
     * @param replacementMap The replacement Map
     * @param locale       The Locale of the email
     * @throws WAPIException
     */
    public String preProcessMail(Object key, String templateName, MailData mailData, Locale locale) throws LogicException{
        Emailtemplate vo = getTemplateMap().get(EmailtemplateDaoImpl.getLogicCacheKey(templateName, I18nConstant.getLocaleString(locale)));
        String content = "";
        if(vo!=null){
        	content = vo.getContent();
        	// 替换mailDate中一些值
        	if(mailData.getFrom()==null){
        		mailData.setFrom(vo.getFromwho());
        	}
        	if(mailData.getRecipients()==null){
        		mailData.setRecipients(new String[]{vo.getRecipient()});
        	}
        	if(mailData.getSubject()==null){
        		mailData.setSubject(vo.getSubject());
        	}
        	if(mailData.getReplyTo()==null){
        		mailData.setReplyTo(new String[]{vo.getReplyto()});
        	}
        }
        return content;
    }

    /**
     * Creates the mail from the template. This method is
     * called once for every email address. The generated
     * String object should have the content of the mail,
     * all the other mail attributes it taken care off by
     * the MailProcessor
     *
     * @param key    The Key that identifies the task
     * @param to     The email address of the recipient
     * @param mailData The MailData object
     * @return The content of the mail
     * @throws WAPIException
     */
    public String createMail(Object key, String to, String content, MailData mailData) throws LogicException {
    	if (mailData.getReplacementMap()!=null) {
	        content = replaceTemplate(content, mailData.getReplacementMap());
	        return content;
    	}  
    	return "";
    }

    /**
     * This method is call ONLY ONCE by the MailProcessor.
     * This method should be used for clean up in this class
     *
     * @param key    The Key that identifies the task
     */
    public void postProcessMail(Object key) throws LogicException{
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private String replaceTemplate(String content, Map attrValues){
    	if(content==null || content.trim().length()<1)
    		return content;
        Iterator it = attrValues.keySet().iterator();
        while(it.hasNext()){
            String attr = (String)it.next();
            // Now replace
            if (attr != null) {
            	content = content.replaceAll(attr, attrValues.get(attr).toString());
            }
        }
        return content;
    }

	public EmailtemplateDao getEmailtemplateDao() {
		return emailtemplateDao;
	}

	public void setEmailtemplateDao(EmailtemplateDao emailtemplateDao) {
		this.emailtemplateDao = emailtemplateDao;
	}

	public Map<String, Emailtemplate> getTemplateMap() {
		if(templateMap==null){
			templateMap = emailtemplateDao.selectEmailtemplateMap(SysparamConstant.syscode);
		}
		return templateMap;
	}
}

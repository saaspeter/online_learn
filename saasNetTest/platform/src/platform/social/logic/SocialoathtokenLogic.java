package platform.social.logic;

import platform.social.vo.Socialoathtoken;
import platform.vo.User;

public interface SocialoathtokenLogic {
    
	/**
	 * 
	 * @param servicetype
	 * @param socialuserid
	 * @return
	 */
	public User selectUserBySocialAccount(Short servicetype, String socialuserid);
	
	/**
	 * 根据accesstoken得到文件的source,
	 * 如果商店对应的accesstoken过期就用refresh token重新生成一个，并且更新db中的access token
	 * @param shopid
	 * @param servicetype
	 * @param fileid
	 * @return
	 */
	public String getFileSource(Long identityid, Short identitytype, Short servicetype, String socialuserid, String fileid);
	
	public String getAccessTokenByRefreshToken(String refreshToken, Short servicetype);
    
    public String[] restGetAllToken(String athorizedCode, Short serviceType);
    
    /**
	 * 根据authorize code 得到user相关信息
	 * @param accesstoken
	 * @param serviceType
	 * @return
	 */
	public String[] restGetUserInfo(String accesstoken, Short serviceType);
	
	/**
	 * 验证access token是否有效
	 * 目前是为了用social帐号登录使用的
	 * @param accesstoken
	 * @param serviceType
	 * @return
	 */
	public boolean isAccessTokenValid(String accesstoken, Short serviceType, String socialuserid);
	
	/**
	 * 当用户使用social方式登录(例如google+)，验证成功后，返回系统，系统会为该social帐号自动创建user
	 * 当需要创建新的系统用户时，loginname为_Social_${social email}, see: userlogic.geneSocialUserLoginName
	 * @param socialVo
	 * @param userVo
	 * @return User, 为null表示该social email对应的User已经在系统中存在了，可以直接登录了
	 *               如果不为null表示user不存在，并且刚刚insert进入系统，需要在界面上提示用户设置下自己的系统帐号信息，例如: loginname
	 */
	public User saveSocialLoginUser(Socialoathtoken socialVo, User userVo);
	

}
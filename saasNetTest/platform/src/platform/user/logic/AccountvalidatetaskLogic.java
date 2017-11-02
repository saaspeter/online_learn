package platform.user.logic;

public interface AccountvalidatetaskLogic {

	public String generateActiveTask(Long userid, String email, String validatetype, Integer aliveday);
	
    /**
     * 根据email更新用户密码，主要是为了忘记密码的case
     * @param oldpass
     * @param newpass
     * @param userid
     */
    public void saveResetPasswordByMail(String email, String newpass);
	
}

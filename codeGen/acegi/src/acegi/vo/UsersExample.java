package acegi.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersExample extends Users {

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public static final int EXAMPLE_IGNORE = 0;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public static final int EXAMPLE_NULL = 1;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public static final int EXAMPLE_NOT_NULL = 2;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public static final int EXAMPLE_EQUALS = 3;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public static final int EXAMPLE_NOT_EQUALS = 4;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public static final int EXAMPLE_GREATER_THAN = 5;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public static final int EXAMPLE_GREATER_THAN_OR_EQUAL = 6;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public static final int EXAMPLE_LESS_THAN = 7;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public static final int EXAMPLE_LESS_THAN_OR_EQUAL = 8;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public static final int EXAMPLE_LIKE = 9;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    private boolean combineTypeOr;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    private int id_Indicator;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    private int loginid_Indicator;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    private int passwd_Indicator;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    private int name_Indicator;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    private int email_Indicator;
    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    private int status_Indicator;

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public void setCombineTypeOr(boolean combineTypeOr) {
	this.combineTypeOr = combineTypeOr;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public boolean isCombineTypeOr() {
	return combineTypeOr;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public int getId_Indicator() {
	return id_Indicator;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public void setId_Indicator(int id_Indicator) {
	this.id_Indicator = id_Indicator;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public int getLoginid_Indicator() {
	return loginid_Indicator;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public void setLoginid_Indicator(int loginid_Indicator) {
	this.loginid_Indicator = loginid_Indicator;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public int getPasswd_Indicator() {
	return passwd_Indicator;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public void setPasswd_Indicator(int passwd_Indicator) {
	this.passwd_Indicator = passwd_Indicator;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public int getName_Indicator() {
	return name_Indicator;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public void setName_Indicator(int name_Indicator) {
	this.name_Indicator = name_Indicator;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public int getEmail_Indicator() {
	return email_Indicator;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public void setEmail_Indicator(int email_Indicator) {
	this.email_Indicator = email_Indicator;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public int getStatus_Indicator() {
	return status_Indicator;
    }

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table users
     * @abatorgenerated  Wed Sep 24 23:16:28 CST 2008
     */
    public void setStatus_Indicator(int status_Indicator) {
	this.status_Indicator = status_Indicator;
    }
}
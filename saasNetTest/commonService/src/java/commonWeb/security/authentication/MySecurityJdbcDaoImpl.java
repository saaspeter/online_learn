/**
 *
 */
package commonWeb.security.authentication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContextException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.util.AuthorityUtils;

/**
 *
 */
public class MySecurityJdbcDaoImpl extends JdbcDaoSupport implements UserDetailsService {

	public static final String DEF_USERS_BY_USERNAME_QUERY =
		"SELECT userName, passWord, enabled, userId " +
		"FROM users " +
		"WHERE userName=?";
	public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
		"SELECT u.userName, r.roleName  " +
		"FROM users u, roles r, users_roles ur " +
		"WHERE u.userId = ur.userId " +
			"AND r.roleId = ur.roleId " +
			"AND u.userName=?";

	protected MappingSqlQuery authoritiesByUsernameMapping;
	protected MappingSqlQuery usersByUsernameMapping;
    private String authoritiesByUsernameQuery;
    private String usersByUsernameQuery;
    private String rolePrefix = "";
    private boolean usernameBasedPrimaryKey = true;

    public MySecurityJdbcDaoImpl() {
    	this.usersByUsernameQuery = DEF_USERS_BY_USERNAME_QUERY;
    	this.authoritiesByUsernameQuery = DEF_AUTHORITIES_BY_USERNAME_QUERY;
    }

    protected void initDao() throws ApplicationContextException {
        initMappingSqlQueries();
    }

    protected void initMappingSqlQueries() {
    	this.usersByUsernameMapping =
    				new UsersByUsernameMapping(getDataSource());
    	this.authoritiesByUsernameMapping =
    				new AuthoritiesByUsernameMapping(getDataSource());
    }

    @SuppressWarnings("unchecked")
	protected void addCustomAuthorities(String username, List authorities) {}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.jdbc.JdbcDaoImpl#loadUserByUsername(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		List users = usersByUsernameMapping.execute(username);

		if (users.size() == 0) {
			throw new UsernameNotFoundException("User Not Found...");
		}
		UserinfoSession userinfo = (UserinfoSession)users.get(0);

		//Set dbAuthsSet = new HashSet();
		//dbAuthsSet.addAll(authoritiesByUsernameMapping.execute(user.getUsername()));
		//List dbAuths = new ArrayList(dbAuthsSet);
		List<GrantedAuthority> dbAuths = authoritiesByUsernameMapping.execute(userinfo.getUsername());

		if (dbAuths.size() == 0) {
			//throw new UsernameNotFoundException("User Has no GrantedAuthority");
			dbAuths = new ArrayList<GrantedAuthority>();
		}

		addCustomAuthorities(userinfo.getUsername(), dbAuths);
		GrantedAuthority[] authorityArr = new GrantedAuthority[dbAuths.size()];
		userinfo.setAuthorities(dbAuths.toArray(authorityArr));

		if (!usernameBasedPrimaryKey) {
			userinfo.setUsername(username);
		}
		return userinfo;
	}

	protected class AuthoritiesByUsernameMapping extends MappingSqlQuery {

		protected AuthoritiesByUsernameMapping(DataSource ds) {
			super(ds, authoritiesByUsernameQuery);
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.object.MappingSqlQuery#mapRow(java.sql.ResultSet, int)
		 */
		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			String roleName = rolePrefix + rs.getString(2);
			GrantedAuthorityImpl authority = new GrantedAuthorityImpl(roleName);
			return authority;
		}
	}

	protected class UsersByUsernameMapping extends MappingSqlQuery {

		protected UsersByUsernameMapping(DataSource ds) {
			super(ds, usersByUsernameQuery);
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		/* (non-Javadoc)
		 * @see org.springframework.jdbc.object.MappingSqlQuery#mapRow(java.sql.ResultSet, int)
		 */
		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			String userName = rs.getString(1);
			String passWord = rs.getString(2);
			boolean enabled = rs.getBoolean(3);
			Long userId = rs.getLong(4);
			UserinfoSession user = new UserinfoSession(userName, passWord, enabled, 
					true, true, true, AuthorityUtils.NO_AUTHORITIES);
			user.setUserid(userId);
			return user;
		}

	}

	/**
	 * @return the authoritiesByUsernameQuery
	 */
	public String getAuthoritiesByUsernameQuery() {
		return authoritiesByUsernameQuery;
	}

	/**
	 * @param authoritiesByUsernameQuery the authoritiesByUsernameQuery to set
	 */
	public void setAuthoritiesByUsernameQuery(String authoritiesByUsernameQuery) {
		this.authoritiesByUsernameQuery = authoritiesByUsernameQuery;
	}

	/**
	 * @return the usersByUsernameQuery
	 */
	public String getUsersByUsernameQuery() {
		return usersByUsernameQuery;
	}

	/**
	 * @param usersByUsernameQuery the usersByUsernameQuery to set
	 */
	public void setUsersByUsernameQuery(String usersByUsernameQuery) {
		this.usersByUsernameQuery = usersByUsernameQuery;
	}

	/**
	 * @return the rolePrefix
	 */
	public String getRolePrefix() {
		return rolePrefix;
	}

	/**
	 * @param rolePrefix the rolePrefix to set
	 */
	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	/**
	 * @return the usernameBasedPrimaryKey
	 */
	public boolean isUsernameBasedPrimaryKey() {
		return usernameBasedPrimaryKey;
	}

	/**
	 * @param usernameBasedPrimaryKey the usernameBasedPrimaryKey to set
	 */
	public void setUsernameBasedPrimaryKey(boolean usernameBasedPrimaryKey) {
		this.usernameBasedPrimaryKey = usernameBasedPrimaryKey;
	}

}

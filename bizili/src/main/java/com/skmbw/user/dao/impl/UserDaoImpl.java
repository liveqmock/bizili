package com.skmbw.user.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.skmbw.user.dao.IUserDao;
import com.skmbw.user.model.User;
import com.vteba.tx.jdbc.spring.SpringJdbcTemplate;
import com.vteba.tx.jdbc.spring.impl.AbstractGenericDao;
import com.vteba.tx.jdbc.spring.impl.SqlType;

@Named
public class UserDaoImpl extends AbstractGenericDao<User, Long> implements IUserDao {

	public UserDaoImpl() {
		super();
	}

	public UserDaoImpl(Class<User> entityClass) {
		super(entityClass);
	}

	@Inject
	@Override
	public void setSpringJdbcTemplate(SpringJdbcTemplate skmbwJdbcTemplate) {
		this.springJdbcTemplate = skmbwJdbcTemplate;
	}

	@Override
	public User mapRows(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
    	user.setUserId(rs.getLong("user_id"));
    	user.setUserName(rs.getString("user_name"));
    	user.setNickName(rs.getString("nick_name"));
    	user.setEmail(rs.getString("email"));
    	user.setPassword(rs.getString("password"));
    	user.setUserAccount(rs.getString("user_account"));
    	user.setRegisterDate(rs.getDate("register_date"));
    	user.setLastLoginDate(rs.getDate("last_login_date"));
    	user.setAddress(rs.getString("address"));
    	user.setZipcode(rs.getInt("zipcode"));
    	user.setProvince(rs.getString("province"));
    	user.setCity(rs.getString("city"));
    	user.setDistrict(rs.getString("district"));
    	user.setStreet(rs.getString("street"));
    	user.setMobilePhone(rs.getString("mobile_phone"));
    	user.setTelphone(rs.getString("telphone"));
    	user.setLevel(rs.getInt("level"));
    	user.setEnable(rs.getInt("enable"));
    	user.setGender(rs.getInt("gender"));
		return user;
	}

	@Override
	public Object mapRows(ResultSet rs, String sql, Class<?> resultClass)
			throws SQLException {
		if (resultClass == User.class) {
			User user = new User();
			
			return user;
		}
		return null;
	}

	@Override
	public Map<String, Object> mapBean(User entity, SqlType sqlType) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (entity.getUserId() != null) {
			result.put("user_id", entity.getUserId());
		}
		result.put("user_name", entity.getUserName());
		result.put("nick_name", entity.getNickName());
		result.put("email", entity.getEmail());
		result.put("password", entity.getPassword());

		switch (sqlType) {
		case INSERT:

			break;
		case SELECT:

			break;
		case UPDATE:

			break;
		case UPDATESET:

			break;
		case DELETE:

			break;
		case WHERE:
			
			break;
		case NULL:

		default:
			break;
		}

		return result;
	}

	@Override
	public Map<String, Object> mapBean(Object params) {
		// TODO Auto-generated method stub
		return null;
	}

}

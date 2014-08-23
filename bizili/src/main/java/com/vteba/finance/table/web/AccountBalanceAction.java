package com.vteba.finance.table.web;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skmbw.user.dao.UserDao;
import com.skmbw.user.model.User;
import com.skmbw.user.model.UserBean;
import com.skmbw.user.service.UserServcie;
import com.vteba.finance.table.model.AccountBalance;
import com.vteba.finance.table.service.IAccountBalanceService;
import com.vteba.service.generic.BaseService;
import com.vteba.user.model.EmpUser;
import com.vteba.user.service.IEmpUserService;
import com.vteba.utils.common.BigDecimalUtils;
import com.vteba.utils.ofbiz.LangUtils;
import com.vteba.utils.reflection.ReflectUtils;
import com.vteba.web.action.BasicAction;

/**
 * 科目余额表action，相关的科目余额查询等
 * @author yinlei 
 * date 2012-7-2 下午5:45:17
 */
@Controller
@RequestMapping("/table")
public class AccountBalanceAction extends BasicAction<AccountBalance> {
	
	private IAccountBalanceService accountBalanceServiceImpl;
	
	@Inject // mybatis
	private UserDao userDao;
	
	@Inject // spring
	private UserServcie userServcieImpl;
	
	@Inject // hibernate
	private IEmpUserService empUserServiceImpl;
	
	@RequestMapping("/accountbalance-initial")
	public String initial(AccountBalance model) throws Exception {
		test();
		
		ReflectUtils.emptyToNull(model);
		listResult = accountBalanceServiceImpl.getEntityList(model, LangUtils.toMap("subjectCode", "asc"));
		
		//String hql = "select sum(ab.startBalanceDebit),sum(ab.startBalanceCredit) from AccountBalance ab";
		//可以使用hql汇总来做，但是where条件
		Double startDebit = 0D;//期初借方
		Double startCredit = 0D;//期初贷方
		Double currentDebit = 0D;//本期借方
		Double currentCredit = 0D;//本期贷方
		Double yearDebit = 0D;//本年借方
		Double yearCredit = 0D;//本年贷方
		Double endDebit = 0D;//期末借方
		Double endCredit = 0D;//期末贷方
		for (AccountBalance bean : listResult) {
			if (bean.getSubjectCode().length() == 4){
				startDebit = BigDecimalUtils.add(startDebit, bean.getStartBalanceDebit());
				startCredit = BigDecimalUtils.add(startCredit, bean.getStartBalanceCredit());
				currentDebit = BigDecimalUtils.add(currentDebit, bean.getCurrenceBalanceDebit());
				currentCredit = BigDecimalUtils.add(currentCredit, bean.getCurrenceBalanceCredit());
				yearDebit = BigDecimalUtils.add(yearDebit, bean.getYearSumBalanceDebit());
				yearCredit = BigDecimalUtils.add(yearCredit, bean.getYearSumBalanceCredit());
				endDebit = BigDecimalUtils.add(endDebit, bean.getEndBalanceDebit());
				endCredit = BigDecimalUtils.add(endCredit, bean.getEndBalanceCredit());
			}
		}
		setAttributeToRequest("startDebit", startDebit);
		setAttributeToRequest("startCredit", startCredit);
		setAttributeToRequest("currentDebit", currentDebit);
		setAttributeToRequest("currentCredit", currentCredit);
		setAttributeToRequest("yearDebit", yearDebit);
		setAttributeToRequest("yearCredit", yearCredit);
		setAttributeToRequest("endDebit", endDebit);
		setAttributeToRequest("endCredit", endCredit);
		return "table/accountbalance/accountbalance-initial-success";
	}

	protected void test() {
		long d = System.currentTimeMillis();
		userDao.selectByPrimaryKey(2L);
		//logger.info("mybatis, get by id time=[{}]", (System.currentTimeMillis() - d));
		print("mybatis get id : ", d);
		
		d = System.currentTimeMillis();
		userServcieImpl.get(3L);
		print("spring get id : ", d);
		
		d = System.currentTimeMillis();
		empUserServiceImpl.get(4L);
		print("hibernate get id : ", d);
		
		UserBean userBean = new UserBean();
		userBean.createCriteria().andUserAccountEqualTo("tongku2008@126.com");
		d = System.currentTimeMillis();
		userDao.selectByExample(userBean);
		print("mybatis query list : ", d);
		
		User entity = new User();
		entity.setUserAccount("tongku2008@126.com");
		d = System.currentTimeMillis();
		userServcieImpl.query(LangUtils.toMap("user_account", "tongku2008@126.com"));
		print("spring query list : ", d);
		
		//String hql = "select u from EmpUser u where u.userAccount = ?1";
		d = System.currentTimeMillis();
		empUserServiceImpl.getEntityList("userAccount", "tongku2008@126.com");
		print("hibernate query list ：", d);
		
		EmpUser user = new EmpUser();
		user.setEmail("asd");
		empUserServiceImpl.save(user);
	}
	
	protected void print(String type, long d) {
		System.out.println(type + (System.currentTimeMillis() - d));
	}
	
	@Inject
	@Override
	public void setBaseServiceImpl(
			BaseService<AccountBalance, ? extends Serializable> accountBalanceServiceImpl) {
		this.baseServiceImpl = accountBalanceServiceImpl; 
		this.accountBalanceServiceImpl = (IAccountBalanceService) accountBalanceServiceImpl;
	}

}

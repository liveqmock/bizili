package com.vteba.finance.table.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.table.dao.ITrialBalanceDao;
import com.vteba.finance.table.model.TrialBalance;
import com.vteba.finance.table.service.ITrialBalanceService;
import com.vteba.service.generic.impl.BaseServiceImpl;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.utils.date.DateUtils;
import com.vteba.utils.ofbiz.LangUtils;

/**
 * 试算平衡表Service实现
 * @author yinlei 
 * date 2012-6-30 下午12:45:39
 */
@Named
public class TrialBalanceServiceImpl extends BaseServiceImpl<TrialBalance, String>
		implements ITrialBalanceService {

	
	private ITrialBalanceDao trialBalanceDaoImpl;
	
	public TrialBalanceServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<TrialBalance, String> trialBalanceDaoImpl) {
		this.baseGenericDaoImpl = trialBalanceDaoImpl;
		this.trialBalanceDaoImpl = (ITrialBalanceDao) trialBalanceDaoImpl;
	}

	public ITrialBalanceDao getTrialBalanceDaoImpl() {
		return trialBalanceDaoImpl;
	}
	
	public void autoGenerateTrialBalanceTask() {
		String period = DateUtils.toDateString("yyyy-MM");
		createTrialBalance(period);
	}
	
	public void createTrialBalance(String period){
		
		//科目余额表已经具备了试算平衡的功能，不需要再从凭证中汇总，直接取数即可
		StringBuilder hql = new StringBuilder(" select new TrialBalance ");
		hql.append(" (ab.accountPeriod,ab.level,current_date(),ab.subjectCode,ab.subjectName,ab.startBalanceDebit,ab.startBalanceCredit,");
		hql.append(" ab.currenceBalanceDebit,ab.currenceBalanceCredit,ab.endBalanceDebit,ab.endBalanceCredit) ");
		hql.append(" from AccountBalance ab ");
		hql.append(" where ab.accountPeriod = ?1 ");
		
		List<TrialBalance> trialList = trialBalanceDaoImpl.getListByHql(hql.toString(), period);
		
		//删除原有的数据
		//String delHql = " delete from TrialBalance where accountPeriod = ?1 ";
		trialBalanceDaoImpl.deleteBatch(LangUtils.toMap("accountPeriod", period));
		
		for (TrialBalance entity : trialList) {
			trialBalanceDaoImpl.persist(entity);
		}
	}
}

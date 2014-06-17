package com.vteba.finance.report.web;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.account.model.AccountPeriod;
import com.vteba.finance.account.service.IAccountPeriodService;
import com.vteba.finance.report.model.AssetsLiabilities;
import com.vteba.finance.report.service.IAssetsLiabilitiesService;
import com.vteba.service.generic.IGenericService;
import com.vteba.utils.common.ObjectUtils;
import com.vteba.utils.date.JodaTimeUtils;
import com.vteba.utils.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 资产负债表action
 * @author yinlei 
 * date 2012-7-25 下午4:39:37
 */
@Controller
@RequestMapping("/report")
public class AssetsLiabilitiesAction extends BaseAction<AssetsLiabilities> {
	private IAssetsLiabilitiesService assetsLiabilitiesServiceImpl;
	private IAccountPeriodService accountPeriodServiceImpl;
	
	@RequestMapping("/assetsliabilities-initial")
	public String initial(AssetsLiabilities model, Map<String, Object> maps) throws Exception {
		ReflectUtils.emptyToNulls(model);
		if (model.getAccountPeriod() == null) {//默认查询当前会计期间
			String accountPeriod = accountPeriodServiceImpl.getCurrentPeriod();
			model.setAccountPeriod(accountPeriod);
		}
		Map<String, String> param = new LinkedHashMap<String, String>();
		param.put("orders", "asc");
		listResult = assetsLiabilitiesServiceImpl.getListByCriteria(AssetsLiabilities.class, model, param);
		maps.put("listResult", listResult);
		return "report/assetsliabilities/assetsliabilities-initial-success";
	}

	/**
	 * 初始化会计期间
	 * @author yinlei
	 * date 2012-7-27 下午8:16:41
	 */
	protected void initialAccountPeriod() {
		DateTime dateTime = new DateTime();
		for (int i = 0; i < 100; i++) {
			Date startDate = JodaTimeUtils.getFirstDayOfMonth(dateTime);
			Date endDate = JodaTimeUtils.getLastDayOfMonth(dateTime);
			String period = ObjectUtils.toDateString(startDate, "yyyy-MM");
			
			AccountPeriod entity = new AccountPeriod();
			
			entity.setPeriod(period);
			entity.setCheckout(false);
			entity.setCurrentPeriod(false);
			entity.setStartDate(startDate);
			entity.setEndDate(endDate);
			
			accountPeriodServiceImpl.persist(entity);
			
			dateTime = dateTime.plusMonths(1);
		}
		
	}
	
	@Inject
	public void setAssetsLiabilitiesServiceImpl(
			IAssetsLiabilitiesService assetsLiabilitiesServiceImpl) {
		this.assetsLiabilitiesServiceImpl = assetsLiabilitiesServiceImpl;
	}
	
	@Inject
	public void setAccountPeriodServiceImpl(
			IAccountPeriodService accountPeriodServiceImpl) {
		this.accountPeriodServiceImpl = accountPeriodServiceImpl;
	}

	@Override
	public void setGenericServiceImpl(
			IGenericService<AssetsLiabilities, ? extends Serializable> genericServiceImpl) {
		// TODO Auto-generated method stub
		
	}

}

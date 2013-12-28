package com.vteba.finance.account.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import com.vteba.common.model.AstModel;

/**
 * 会计期间
 * @author yinlei 
 * date 2012-7-27 下午3:57:47
 */
@Entity
@Table(name = "account_period", catalog = "bizili")
@Immutable
@Cacheable
@Cache(region = "com.vteba.finance.account.model.AccountPeriod", usage = CacheConcurrencyStrategy.READ_ONLY)
public class AccountPeriod implements AstModel {

	private static final long serialVersionUID = -649780828287185938L;
	private String period;//会计期间
	private Date startDate;//开始日期
	private Date endDate;//结束日期
	private boolean currentPeriod;//是否当前会计期间
	private boolean checkout;//是否已结账

	public AccountPeriod() {
		super();
	}

	public AccountPeriod(String period, Date startDate, Date endDate,
			boolean currentPeriod, boolean checkout) {
		super();
		this.period = period;
		this.startDate = startDate;
		this.endDate = endDate;
		this.currentPeriod = currentPeriod;
		this.checkout = checkout;
	}
	
	@Id
	@Column(name = "period", length = 10)
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column(name = "current_period")
	public boolean isCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(boolean currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	
	@Column(name = "checkout")
	public boolean isCheckout() {
		return checkout;
	}

	public void setCheckout(boolean checkout) {
		this.checkout = checkout;
	}
	
}

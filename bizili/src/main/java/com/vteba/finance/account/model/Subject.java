package com.vteba.finance.account.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.vteba.common.model.AstModel;

/**
 * 会计科目
 */
@Entity
@Table(name = "subject", catalog = "bizili")
@BatchSize(size = 20)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "com.vteba.finance.account.model.Subject")
@NamedQueries(value = {
		@NamedQuery(name = "subject.querySubByCode", query = "select s from Subject s where s.subjectCode = ?1 ")
		})
@NamedNativeQueries(value = {
		@NamedNativeQuery(name = "a123", query = "select 1", resultClass = Subject.class)
})
public class Subject implements AstModel {

	private static final long serialVersionUID = -2180300954170909046L;
	
	//private String id;
	private String subjectCode;//科目代码
	private String subjectName;//科目名字
	private String subjectType;//科目分类
	private String balanceDirection;//余额方向
	private String foreignCurrencyAccount;//外币核算
	private String aidAccount;//辅助核算
	private String state;//状态
	private String majorCate;//大类：资产，负债，权益，成本，损益
	private String parentName;//父类名：subjectName
	private Boolean adjustExrate;//期末调汇
	private Integer level;//科目的级别，1,2,3,4
	private Integer childNumber;//孩子数目
	private Integer ledgerFormat;//帐页格式，1金额式，2数量金额式
	
	/**一对多自关联**/
	private List<Subject> childSubjects = new ArrayList<Subject>();
	private Subject parentSubject;
	
	/*
	 * 以下是科目分类
	 */
	public static final String TYPE_ZC = "资产";
	public static final String TYPE_FZ = "负债";
	public static final String TYPE_GT = "共同";
	public static final String TYPE_QY = "权益";
	public static final String TYPE_CB = "成本";
	public static final String TYPE_SY = "损益";
	
	/*
	 * 以下是余额方向
	 */
	public static final String DIR_DEBIT = "借";
	public static final String DIR_CREDIT = "贷";
	public static final String DIR_PING = "平";//当借方和贷方一样时，报表中显示平
	
	/**
	 * 帐页格式，金额式
	 */
	public static final Integer FORMAT_MONEY = 1;
	/**
	 * 帐页格式，数量金额式
	 */
	public static final Integer FORMAT_AMOUNT = 2;
	
	public Subject() {
	}

	public Subject(String subjectCode, String subjectName,
			String subjectType, String balanceDirection,
			String foreignCurrencyAccount, String aidAccount, String state,
			String majorCate, String parentName, Boolean adjustExrate,
			Integer level, Integer childNumber, Integer ledgerFormat,
			List<Subject> childSubjects, Subject parentSubject) {
		super();
//		this.id = id;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.subjectType = subjectType;
		this.balanceDirection = balanceDirection;
		this.foreignCurrencyAccount = foreignCurrencyAccount;
		this.aidAccount = aidAccount;
		this.state = state;
		this.majorCate = majorCate;
		this.parentName = parentName;
		this.adjustExrate = adjustExrate;
		this.level = level;
		this.childNumber = childNumber;
		this.ledgerFormat = ledgerFormat;
		this.childSubjects = childSubjects;
		this.parentSubject = parentSubject;
	}

//	@Id
//	@GeneratedValue(generator="uuid")
//	@GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
//	@Column(name = "id", unique = true, nullable = false, length = 45)
//	public String getId() {
//		return this.id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	@Id
	@Column(name = "subject_code", unique = true, nullable = false, length = 45)
	public String getSubjectCode() {
		return this.subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	@Column(name = "subject_name", nullable = false, length = 100)
	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Column(name = "subject_type", nullable = false, length = 100)
	public String getSubjectType() {
		return this.subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	@Column(name = "balance_direction", nullable = false, length = 45)
	public String getBalanceDirection() {
		return this.balanceDirection;
	}

	public void setBalanceDirection(String balanceDirection) {
		this.balanceDirection = balanceDirection;
	}

	@Column(name = "foreign_currency_account", length = 45)
	public String getForeignCurrencyAccount() {
		return this.foreignCurrencyAccount;
	}

	public void setForeignCurrencyAccount(String foreignCurrencyAccount) {
		this.foreignCurrencyAccount = foreignCurrencyAccount;
	}

	@Column(name = "aid_account", length = 45)
	public String getAidAccount() {
		return this.aidAccount;
	}

	public void setAidAccount(String aidAccount) {
		this.aidAccount = aidAccount;
	}

	@Column(name = "state", nullable = false, length = 45)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "major_cate", nullable = false, length = 45)
	public String getMajorCate() {
		return this.majorCate;
	}

	public void setMajorCate(String majorCate) {
		this.majorCate = majorCate;
	}
	
	@Column(name = "parent_name", length = 45)
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	@Column(name = "adjust_exrate")
	public Boolean getAdjustExrate() {
		return adjustExrate;
	}

	public void setAdjustExrate(Boolean adjustExrate) {
		this.adjustExrate = adjustExrate;
	}
	
	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentSubject")
	@BatchSize(size = 10)
	@Fetch(value = FetchMode.SUBSELECT)
	@Cascade(CascadeType.SAVE_UPDATE)
	@LazyCollection(LazyCollectionOption.EXTRA)
	public List<Subject> getChildSubjects() {
		return childSubjects;
	}

	public void setChildSubjects(List<Subject> childSubjects) {
		this.childSubjects = childSubjects;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_code", nullable = true)
	@LazyToOne(LazyToOneOption.PROXY)
	public Subject getParentSubject() {
		return parentSubject;
	}

	public void setParentSubject(Subject parentSubject) {
		this.parentSubject = parentSubject;
	}
	
	@Column(name = "child_number")
	public Integer getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(Integer childNumber) {
		this.childNumber = childNumber;
	}
	
	@Column(name = "ledger_format")
	public Integer getLedgerFormat() {
		return ledgerFormat;
	}

	public void setLedgerFormat(Integer ledgerFormat) {
		this.ledgerFormat = ledgerFormat;
	}
}

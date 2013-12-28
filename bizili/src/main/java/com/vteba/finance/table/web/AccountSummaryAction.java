package com.vteba.finance.table.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.vteba.finance.currency.model.Currency;
import com.vteba.finance.table.model.AccountSummary;
import com.vteba.finance.table.service.IAccountSummaryService;
import com.vteba.service.xml.XmlServiceImpl;
import com.vteba.test.Customer;
import com.vteba.util.common.BigDecimalUtils;
import com.vteba.util.common.ObjectUtils;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 科目汇总表action
 * @author yinlei 
 * date 2012-7-12 上午11:29:47
 */
public class AccountSummaryAction extends BaseAction<AccountSummary> {
	private static final long serialVersionUID = -461322730925573340L;
	private AccountSummary model = new AccountSummary();
	private IAccountSummaryService accountSummaryServiceImpl;
	
	@Inject
	private XmlServiceImpl xmlServiceImpl;
	
	@Override
	public String initial() throws Exception {
		String hqls = ReflectUtils.emptyToNull(model);
		//JibxMarshaller jibxMarshaller = jibxMarshallerFactory.getJibxMarshaller(Customer.class);
		String path = "\\WEB-INF\\classes\\com\\skmbw\\test\\data.xml";
		path = getHttpSession().getServletContext().getRealPath(path);
		File file = new File(path);
		FileInputStream stream = new FileInputStream(file);
		StreamSource source = new StreamSource(stream);
		//Customer customer = (Customer)userJibxMarshaller.unmarshal(source);
		
		//Customer cus = (Customer) jibxMarshaller.unmarshal(source);
		//System.out.println(cus);
		
		Customer custom = xmlServiceImpl.xmlToObject(source, Customer.class);
		
		String customerXml = xmlServiceImpl.objectToXml(custom);
		//Customer cusBean = (Customer) xmlServiceImpl.fromXml(customerXml);
		//System.out.println(cusBean);
		Customer c = xmlServiceImpl.xmlToObject(customerXml, Customer.class);
		
		File outFile = new File("e:\\ddd.xml");
		FileOutputStream outputStream = new FileOutputStream(outFile);
		StreamResult result = new StreamResult(outputStream);
		
		//userJibxMarshaller.marshal(customer, result);
		
		//jibxMarshaller.marshal(cus, result);
		
		xmlServiceImpl.objectToXml(c, result);
		
		if (hqls != null) {
			
		}
		String currentPeriod = ObjectUtils.toDateString("yyyy-MM");
		Map<String, Object> param = new HashMap<String, Object>();
		if (model.getAccountPeriod() == null) {//默认查询当前会计期间
			model.setAccountPeriod(currentPeriod);
		}
		if (model.getEndPeriod() == null) {
			model.setEndPeriod(currentPeriod);
		}
		if (model.getCurrency() == null) {
			model.setCurrency(Currency.CUR_TYPE_CN);//默认查CNY
		}
		if (model.getLevel() == null) {
			model.setLevel(1);//默认查一级科目
			listResult = accountSummaryServiceImpl.getEntityListByNamedHql("accountSummary.queryAccountSumByEq", model);
		} else if (model.getLevel() == 0) {
			List<Integer> level = new ArrayList<Integer>();
			level.add(1);
			level.add(2);
			level.add(3);
			param.put("levels", level);
			listResult = accountSummaryServiceImpl.getEntityListByNamedHql("accountSummary.queryAccountSumByIn", param, model);
		} else {
			listResult = accountSummaryServiceImpl.getEntityListByNamedHql("accountSummary.queryAccountSumByEq", model);
		}
		
		//汇总借方和贷方
		Double debit = 0D;
		Double credit = 0D;
		for (AccountSummary bean : listResult) {
			if (bean.getSubjectCode().length() == 4) {//只需要汇总一级科目就OK
				debit = BigDecimalUtils.add(debit, bean.getDebit());
				credit = BigDecimalUtils.add(credit, bean.getCredit());
			}
		}
		setAttributeToRequest("debit", debit);
		setAttributeToRequest("credit", credit);
		return SUCCESS;
	}

	@Inject
	public void setAccountSummaryServiceImpl(
			IAccountSummaryService accountSummaryServiceImpl) {
		this.accountSummaryServiceImpl = accountSummaryServiceImpl;
	}

	@Override
	public AccountSummary getModel() {
		return model;
	}
	
}

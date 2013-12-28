package com.vteba.finance.account.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.vteba.cache.infinispan.InfinispanCache;
import com.vteba.cache.infinispan.InfinispanCacheManager;
import com.vteba.finance.account.dao.ISubjectDao;
import com.vteba.finance.account.dao.SubjectDao;
import com.vteba.finance.account.model.Subject;
import com.vteba.finance.account.service.ISubjectService;
import com.vteba.finance.cache.FinanceCacheName;
import com.vteba.persister.generic.Page;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.persister.hibernate.QueryStatement;
import com.vteba.persister.jdbc.spring.SpringJdbcTemplate;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.user.dao.UserDao;
import com.vteba.user.model.EmpUser;
import com.vteba.user.service.IEmpUserService;

/**
 * 会计科目service实现
 * @author yinlei 
 * date 2012-6-30 下午8:32:28
 */
@Named
public class SubjectServiceImpl extends GenericServiceImpl<Subject, String> implements
		ISubjectService {

	private ISubjectDao subjectDaoImpl;
	
	@Inject
	private IEmpUserService empUserServiceImpl;
	
	@Inject
	private InfinispanCacheManager infinispanCacheManager;
	
	@Inject
	private SubjectDao subjectDao;
	
	@Inject
	private UserDao userDao;
	
	public SubjectServiceImpl() {
		super();
	}
	
	@Inject
	public void setSpringJdbcTemplate(SpringJdbcTemplate biziliJdbcTemplate) {
		this.springJdbcTemplate = biziliJdbcTemplate;
	}
	
	@Override
	@Inject
	public void setHibernateGenericDaoImpl(IHibernateGenericDao<Subject, String> subjectDaoImpl) {
		this.hibernateGenericDaoImpl = subjectDaoImpl;
		this.subjectDaoImpl = (ISubjectDao) subjectDaoImpl;
	}
	
    public Page<Subject> getPageListByModelSelect(Page<Subject> page, Subject entity, Object... objects){
    	Page<Subject> pageSubject = page;//subjectDaoImpl.queryForPageByModelSelect(page, entity, objects);
    	
    	//StringBuilder hql = new StringBuilder(" select distinct p from Subject p ");
    	//hql.append(" left outer join fetch p.childSubjects ");
    	//hql.append(" where (p.level = 1 or p.level = 2) ");
    	List<Subject> list = pageSubject.getResult();
    	if (list != null && list.size() > 0) {//访问强制初始化集合，为了不使用左外连接
    		for (int i = 0; i < list.size(); i++) {
    			Subject su = list.get(i);
        		if (su != null && su.getChildNumber().intValue() > 0) {
        			su.getChildSubjects().get(0);
        		}
    		}
    	}
    	return pageSubject;
    }
    
    public Map<String, List<Subject>> getSubjectTreeList(Object... objects){
    	empUserServiceImpl.getAll(Subject.class);
    	StringBuilder hql = new StringBuilder(" select distinct p from Subject p ");
    	hql.append(" left join fetch p.childSubjects order by p.subjectCode desc ");
    	//hql.append(" where (p.level = 1 or p.level = 2) ");
    	List<Subject> tempList = subjectDaoImpl.getEntityListByHql(hql.toString());
    	List<Subject> zcList = new ArrayList<Subject>();
    	List<Subject> fzList = new ArrayList<Subject>();
    	List<Subject> gtList = new ArrayList<Subject>();
    	List<Subject> qyList = new ArrayList<Subject>();
    	List<Subject> cbList = new ArrayList<Subject>();
    	List<Subject> syList = new ArrayList<Subject>();
    	//只取一级科目，因为二级和三级会包含在一级的树形list中
    	if (tempList != null && tempList.size() > 0) {
    		for (int i = 0; i < tempList.size(); i++) {
    			Subject subject = tempList.get(i);
    			if (subject == null || subject.getLevel() != 1) {
    				continue;
    			}
    			
        		if (subject.getMajorCate().equals(Subject.TYPE_ZC)) {
        			zcList.add(subject);
        		} else if (subject.getMajorCate().equals(Subject.TYPE_FZ)) {
        			fzList.add(subject);
        		} else if (subject.getMajorCate().equals(Subject.TYPE_GT)) {
        			gtList.add(subject);
        		} else if (subject.getMajorCate().equals(Subject.TYPE_QY)) {
        			qyList.add(subject);
        		} else if (subject.getMajorCate().equals(Subject.TYPE_CB)) {
        			cbList.add(subject);
        		} else if (subject.getMajorCate().equals(Subject.TYPE_SY)) {
        			syList.add(subject);
        		}
    		}
    	}
    	Map<String, List<Subject>> map = new HashMap<String, List<Subject>>();
    	map.put("资产类", zcList);
    	map.put("负债类", fzList);
    	map.put("共同类", gtList);
    	map.put("权益类", qyList);
    	map.put("成本类", cbList);
    	map.put("损益类", syList);
    	return map;
    }
    
    public boolean saveSubject(Subject model){
    	if (StringUtils.isNotBlank(model.getId())) {//新增的是子科目
    		// 自关联，一对多级联保存
    		Subject parent = new Subject();
    		parent.setId(model.getId());
    		parent.getChildSubjects().add(model);
    		model.setParentSubject(parent);
    		subjectDaoImpl.save(model);
    		//更改分类的子类数目+1
    		String hql = "update Subject s set s.childNumber = (s.childNumber + 1) where s.id =:id ";
    		Map<String, Object> param = new HashMap<String, Object>();
    		param.put("id", parent.getId());
    		subjectDaoImpl.executeUpdateByHql(hql, false, param);
    	} else {//新增的是一级科目
    		model.setId(null);
    		model.setParentName(null);
    		subjectDaoImpl.save(model);
    	}
		return true;
    }
    
    @SuppressWarnings("unused")
	public void test() {
//    	String sql1 = "select id, subject_code subjectCode, subject_name subjectName, level from subject where subject_code = ?";
//    	Subject subject = subjectDaoImpl.uniqueResultBySql(sql1, "2711");
//    	Subject subject12 = subjectDaoImpl.getUniqueResultBySql(sql1, Subject.class, "2711");
//    	
//    	String hql1 = "select s from Subject s where s.subjectCode = ?1";
//    	Subject subject21 = subjectDaoImpl.uniqueResultByHql(hql1, false, "2711");
//    	List<Subject> subjectList1 = subjectDaoImpl.getEntityListByHql(hql1, "2711");
//    	
//    	String hql2 = "select s.id, s.subjectCode, s.subjectName, s.level from Subject s where s.subjectCode = ?1";
//    	//String hql2 = "select new JavaBean(s.id, s.subjectCode, s.subjectName, s.level) from Subject s where s.subjectCode = ?1";
//    	//List<Subject> subjects2 = subjectDaoImpl.getListByHql(hql2, null, "2711");//里面是个Object[]，所以要用下面的方式或者new JavaBean
//    	//Subject sub1 = subjects2.get(0);//会转型错误
//    	List<Subject> subjects = subjectDaoImpl.getListByHql(hql2, Subject.class, "2711");
//    	
//    	Subject subject2 = subjectDaoImpl.get(Subject.class, "00375b21-5a31-40a5-a7da-0b5d0437f3b2");
//    	
//    	//List<Subject> subjects3 = subjectDaoImpl.getAll(Subject.class);
//    	
//    	List<Subject> subjects4 = subjectDaoImpl.getEntityListBySql(sql1, "2711");
//    	
//    	String strSql = "select subject_name from subject where subject_code = ?";
//    	List<String> results = subjectDaoImpl.sqlQueryForList(strSql, String.class, "2711");
//    	String aa = results.get(0);
//    	
//    	String strSql2 = "select level from Subject where subject_code = ?";
//    	Double doubles = subjectDaoImpl.sqlQueryForObject(strSql2, Double.class, "2711");
//    	List<Double> doubleList = subjectDaoImpl.sqlQueryForList(strSql2, Double.class, "2711");
//    	
//    	String strHql2 = "select level from Subject where subjectCode = ? ";
//    	List<Long> longList = subjectDaoImpl.hqlQueryForList(strHql2, Long.class, "2711");
//    	
//    	String strSql3 = "select * from subject where subject_code = ?";
//    	List<Subject> subjects22 = subjectDaoImpl.getEntityListBySql(strSql3, "2711");
    	
    	InfinispanCache<String, QueryStatement> queryStatementCache = infinispanCacheManager.getCache(FinanceCacheName.QUERY_STATEMENT);
    	
    	QueryStatement statement = new QueryStatement();
    	statement.setSqlQueryAll(true);
    	queryStatementCache.put("sql1", statement);
    	
    	QueryStatement stmt = queryStatementCache.get("sql1");
    	
    	int times = 10;
    	long d = System.currentTimeMillis();
    	String sql1 = "select id, subject_code subjectCode, subject_name subjectName, level from subject where subject_code = ?";
    	for (int i=0; i< times; i++) {
    		List<Subject> subject12 = subjectDaoImpl.getEntityListBySql(sql1, "2711");
    	}
    	System.out.println("getEntityListBySql: " + (System.currentTimeMillis() - d));
    	
    	long d2 = System.currentTimeMillis();
    	String hql1 = "select s from Subject s where s.subjectCode = ?1";
    	for (int i=0; i< times; i++) {
    		List<Subject> subjectList212 = subjectDaoImpl.getEntityListByHql(hql1, "2711");
    	}
    	System.out.println("getEntityListByHql: " + (System.currentTimeMillis() - d2));
    	
    	long d3 = System.currentTimeMillis();
    	String sql22 = "select id, subject_code, subject_name, level from subject where subject_code = ?";
    	for (int i = 0; i < times; i++) {
//    		List<Subject> aList = springJdbcTemplate.query(sql22, new RowMapper<Subject>() {
//
//				@Override
//				public Subject mapRow(ResultSet rs, int rowNum)
//						throws SQLException {
//					Subject subject = new Subject();
//					subject.setId(rs.getString("id"));
//					subject.setSubjectCode(rs.getString("subject_code"));
//					subject.setSubjectName(rs.getString("subject_name"));
//					subject.setLevel(rs.getInt("level"));
//					return subject;
//				}
//			}, new Object[]{"2711"});
			List<Subject> bList = springJdbcTemplate.query(sql22, Subject.class, "2711");
    	}
    	System.out.println("spring jdbc : " + (System.currentTimeMillis() - d3));
    	
//    	long d4 = System.currentTimeMillis();
//    	Subject model = new Subject();
//    	model.setSubjectCode("2711");
//    	
//    	for (int i=0; i< times; i++) {
//    		List<Subject> subjectList212 = subjectDaoImpl.getListByPropertyEqual(Subject.class, model);
//    	}
//    	System.out.println("getListByPropertyEqual: " + (System.currentTimeMillis() - d4));
    	
    	long da = System.currentTimeMillis();
    	for (int i = 0; i < times; i++) {
    		Subject as = subjectDao.getSubject("2711");
//    		if (i == 0) {
//    			System.out.println(as);
//    		}
    	}
    	
    	System.out.println("MyBatis query: " + (System.currentTimeMillis() - da));
    	
    	EmpUser empUser = userDao.get(123);
    	
    	System.out.println();
    }
    
    public static void main(String[] a) {
    	System.out.println(int.class.isPrimitive());
    }
    
}

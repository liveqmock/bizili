package com.vteba.tm.jdbc.spring;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.vteba.tm.jdbc.spi.SpringGenericDao;
import com.vteba.util.reflection.BeanCopyUtils;
import com.vteba.util.reflection.ReflectUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SpringGenericDaoImpl<T, ID extends Serializable> implements SpringGenericDao<T, ID> {
	protected String tableName;
	protected Class<T> entityClass;
	
	private List<String> sqlColumnList = new ArrayList<String>();
	
	private static String INSERT = "insert into ${tableName}(${columns}) values(${placeholder})";
	private static String DELETE = "delete from ${tableName} where id = ?";
	private static String UPDATE = "update ${tableName} set ${?} where id = ?";
	private static String SELECT = "select * from ${tableName} where id = ?";
	private static String SELECT_ALL = "select * from ";
	
	private SpringJdbcTemplate springJdbcTemplate;
	
	public SpringGenericDaoImpl() {
		super();
		entityClass = ReflectUtils.getClassGenericType(this.getClass());
		init();
	}

	public SpringGenericDaoImpl(String tableName) {
		super();
		this.tableName = tableName;
		entityClass = ReflectUtils.getClassGenericType(this.getClass());
		init();
	}
	
	public SpringGenericDaoImpl(String tableName, Class<T> entityClass) {
		super();
		this.tableName = tableName;
		this.entityClass = entityClass;
		init();
	}

	protected void init() {
		Table table = entityClass.getAnnotation(Table.class);
		tableName = table.name();
		List<Column> columnList = getAnnotations(entityClass, Column.class);
		StringBuilder columnBuilder = new StringBuilder();
		StringBuilder placeholders = new StringBuilder();
		for (Column col : columnList) {
			columnBuilder.append(col.name()).append(", ");
			placeholders.append("?, ");
			sqlColumnList.add(col.name());
		}
		String columns = columnBuilder.substring(0, columnBuilder.length() - 2);
		String placeholder = placeholders.substring(0, placeholders.length() - 2);
		
		SELECT = SELECT.replace("${tableName}", tableName);
		SELECT_ALL = SELECT_ALL + tableName;
		
		INSERT = INSERT.replace("${tableName}", tableName)
				.replace("${columns}", columns)
				.replace("${placeholder}", placeholder);
		
		DELETE = DELETE.replace("${tableName}", tableName);
		
		
	}
	
	public String process(Object model) {
		String sql = null;
		try {
			Configuration configuration = new Configuration();
			configuration.setEncoding(Locale.CHINA, "UTF-8");
			
			Template template = new Template("/", new StringReader(SELECT), configuration);
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("tableName", "user_");
			sql = FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
		} catch (IOException e) {
			
		} catch (TemplateException e) {
			
		}
		return sql;
	}
	
	public static void main(String[] aa) {
		String sql = null;
		try {
			Configuration configuration = new Configuration();
			configuration.setEncoding(Locale.CHINA, "UTF-8");
			
			Template template = new Template("/", new StringReader(SELECT), configuration);
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("tableName", "user_");
			sql = FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
		} catch (IOException e) {
			
		} catch (TemplateException e) {
			
		}
		System.out.println(sql);
	}
	
	
	/**
	 * 获得clazz内所有被annotation注解标注的方法的注解信息。
	 * @param clazz 目标类
	 * @param annotation 目标注解
	 * @return 类中被标注了响应注解的方法的注解信息
	 */
	public static <T extends Annotation> List<T> getAnnotations(Class<?> clazz, Class<T> annotation){
		List<T> toReturn = new ArrayList<T>();
		for(Method m : clazz.getMethods()){
			if (m.isAnnotationPresent(annotation)) {
				toReturn.add(m.getAnnotation(annotation));
			} else if (m.isAnnotationPresent(Id.class)) {
				//toReturn.add(m.getAnnotation(annotation));
			}
		}
		return toReturn;
	}
	
	@Override
	public ID save(T entity) {
		Map<String, Object> toMap = new HashMap<String, Object>();
		BeanCopyUtils.getInstance().beanToMap(entity, toMap);
		springJdbcTemplate.update(INSERT, toMap);
		return null;
	}

	@Override
	public void update(T entity) {
		Map<String, Object> toMap = new HashMap<String, Object>();
		BeanCopyUtils.getInstance().beanToMap(entity, toMap);
		springJdbcTemplate.update(UPDATE, toMap);
		
	}

	public int update(T setValues, T where) {
		
		return 1;
	}
	
	@Override
	public T get(ID id) {
		GenericRowMapper<T> rowMapper = new GenericRowMapper<T>(entityClass, SELECT);
		return springJdbcTemplate.queryForObject(SELECT, rowMapper, id);
	}

	@Override
	public void delete(T entity) {
		Map<String, Object> toMap = new HashMap<String, Object>();
		BeanCopyUtils.getInstance().beanToMap(entity, toMap);
		
	}
	
	@Override
	public int delete(ID id) {
		return springJdbcTemplate.update(DELETE, id);
	}

}

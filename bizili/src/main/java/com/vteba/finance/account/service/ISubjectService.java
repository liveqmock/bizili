package com.vteba.finance.account.service;

import java.util.List;
import java.util.Map;

import com.vteba.finance.account.model.Subject;
import com.vteba.service.generic.IGenericService;
import com.vteba.tm.generic.Page;

/**
 * 会计科目Service接口。
 * @author yinlei
 * date 2012-6-24 下午10:40:05
 */
public interface ISubjectService extends IGenericService<Subject, String> {
	
	/**
     * 分页查询，使用criteria实现,立即第二次查询初始化延迟加载的集合
     * @param page 分页数据
     * @param entity 实体
     * @param objects 实体中延迟加载的集合的名字
     * @author yinlei
     * date 2012-6-26 下午4:51:48
     * @deprecated
     */
    public Page<Subject> getPageListByModelSelect(Page<Subject> page, Subject entity, Object... objects);
    
    /**
     * 保存新增科目，级联。同时更新科目的子分类数
     * @param model 保存的对象
     * @author yinlei
     * date 2012-6-29 下午5:12:26
     */
    public boolean saveSubject(Subject model);
    
    /**
     * 获得会计科目的树形结构list
     * @param objects 可携带参数
     * @author yinlei
     * date 2012-6-30 下午9:18:58
     */
    public Map<String, List<Subject>> getSubjectTreeList(Object... objects);
    
    /**
     * 测试的方法，勿用。
     */
    public void test();
    
    /**
     * 获取会计科目，树形结构，树形结构可转为Json格式。
     * @return List&lt;Node&gt; 
     */
    public String getSubjectJson();
    
    /**
     * 删除会计科目，使用中的无法删除。
     * @param subjectCode 科目代码
     * @return 1：使用中，0：会计科目不存在，2：删除成功
     */
    public int deleteSubject(String subjectCode);
    
}

package com.skmbw.user.dao;

import com.skmbw.user.model.User;
import com.skmbw.user.model.UserBean;
import com.vteba.tx.jdbc.mybatis.annotation.DaoMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@DaoMapper
public interface UserDao {
    /**
     * 根据Criteria所携带条件进行count计数。
     * @param example 查询条件
     *
     * @date 2014-07-06 11:03:58
     */
    int countByExample(UserBean example);

    /**
     * 根据Criteria所携带条件删除记录。
     * @param example 查询条件
     *
     * @date 2014-07-06 11:03:58
     */
    int deleteByExample(UserBean example);

    /**
     * 根据主键删除记录。
     * @param id 主键id
     *
     * @date 2014-07-06 11:03:58
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * 插入记录，所有字段都不能为空。
     * @param record 要被保存的数据
     *
     * @date 2014-07-06 11:03:58
     */
    int insert(User record);

    /**
     * 插入记录，只有非空字段才会插入到数据库。
     * @param record 要被保存的数据
     *
     * @date 2014-07-06 11:03:58
     */
    int insertSelective(User record);

    /**
     * 根据Criteria所携带条件查询数据，不含BLOB字段。
     * @param example 查询条件
     *
     * @date 2014-07-06 11:03:58
     */
    List<User> selectByExample(UserBean example);

    /**
     * 根据主键查询数据。
     * @param id 主键
     *
     * @date 2014-07-06 11:03:58
     */
    User selectByPrimaryKey(Long userId);

    /**
     * 根据Criteria所携带条件更新指定字段。
     * @param record 要更新的数据
     * @param example update的where条件
     *
     * @date 2014-07-06 11:03:58
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserBean example);

    /**
     * 根据Criteria所携带条件更新所有字段，不含BLOB字段。
     * @param record 要更新的数据
     * @param example update的where条件
     *
     * @date 2014-07-06 11:03:58
     */
    int updateByExample(@Param("record") User record, @Param("example") UserBean example);

    /**
     * 根据主键更新指定字段的数据。
     * @param record 要更新的数据，含有Id
     *
     * @date 2014-07-06 11:03:58
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据主键更新所有字段的数据，不含BLOB字段。
     * @param record 要更新的数据，含有Id
     *
     * @date 2014-07-06 11:03:58
     */
    int updateByPrimaryKey(User record);
}
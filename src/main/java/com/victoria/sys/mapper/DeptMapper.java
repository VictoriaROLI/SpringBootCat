package com.victoria.sys.mapper;

import com.victoria.sys.pojo.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-01-02
 */
@Repository
public interface DeptMapper extends BaseMapper<Dept> {

    @Select("select max(ordernum) from sys_dept")
    Integer deptMaxNum();



}

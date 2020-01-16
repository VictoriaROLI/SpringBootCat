package com.victoria.sys.mapper;

import com.victoria.sys.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-12-31
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    @Delete("delete from sys_role_permission where pid = #{id}")
    void deleteRolePermissionByPid(Serializable id);

    @Select("select max(ordernum) from sys_permission")
    Integer deptMaxNum();


}

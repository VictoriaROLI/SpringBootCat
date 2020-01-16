package com.victoria.sys.mapper;

import com.victoria.sys.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-01-03
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    @Delete("delete from sys_role_permission where rid = #{0}")
    void removeRolePermission(Serializable id);

    @Delete("delete from sys_role_user where rid = #{0}")
    void removeRoleUser(Serializable id);

    @Select("select pid from sys_role_permission where rid = #{0}")
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    @Insert("insert into sys_role_permission values(#{rid},#{pid})")
    void saveRolePermission(@Param("rid") Integer rid, @Param("pid") Integer pid);

    @Select("select rid from sys_role_user where uid=#{0}")
    List<Integer> queryUserRoleIdsByUid(Integer id);

    @Insert("insert into sys_role_user values(#{rid},#{uid})")
    void saveUserRole(@Param("rid") Integer rid,@Param("uid") Integer uid);

    @Delete("delete from sys_role_user where uid=#{0}")
    void removeUid(Integer uid);
}

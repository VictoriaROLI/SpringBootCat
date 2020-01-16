package com.victoria.sys.service;

import com.victoria.sys.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-01-03
 */
public interface IRoleService extends IService<Role> {

    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    void saveRolePermission(Integer rid, Integer[] ids);

    List<Integer> queryUserRoleIdsByUid(Integer id);

    void saveUserRole(Integer rid, Integer[] ids);

}

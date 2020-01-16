package com.victoria.sys.service.impl;

import com.victoria.sys.pojo.Role;
import com.victoria.sys.mapper.RoleMapper;
import com.victoria.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-01-03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean removeById(Serializable id) {
        roleMapper.removeRolePermission(id);
        roleMapper.removeRoleUser(id);

        return super.removeById(id);
    }


    public List<Integer> queryRolePermissionIdsByRid(Integer roleId){
        return  roleMapper.queryRolePermissionIdsByRid(roleId);
    }

    @Override
    public void saveRolePermission(Integer rid, Integer[] ids) {
        roleMapper.removeRolePermission(rid);
        if(ids.length>0){
            for(Integer pid:ids){
                roleMapper.saveRolePermission(rid,pid);
            }
        }
    }

    @Override
    public List<Integer> queryUserRoleIdsByUid(Integer id) {

        return roleMapper.queryUserRoleIdsByUid(id);
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {

        roleMapper.removeUid(uid);
        if(ids!=null){
            for(Integer rid:ids){
                roleMapper.saveUserRole(rid,uid);
            }
        }

    }


}

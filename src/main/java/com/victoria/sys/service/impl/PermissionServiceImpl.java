package com.victoria.sys.service.impl;

import com.victoria.sys.pojo.Permission;
import com.victoria.sys.mapper.PermissionMapper;
import com.victoria.sys.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-12-31
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Integer deptMaxNum() {
        return permissionMapper.deptMaxNum();
    }

    @Override
    public boolean removeById(Serializable id) {
        permissionMapper.deleteRolePermissionByPid(id);
        return super.removeById(id);
    }


    @Override
    public boolean save(Permission entity) {
        return super.save(entity);
    }
}

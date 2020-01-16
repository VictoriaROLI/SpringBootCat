package com.victoria.sys.service;

import com.victoria.sys.pojo.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-12-31
 */
public interface IPermissionService extends IService<Permission> {


    Integer deptMaxNum();
}

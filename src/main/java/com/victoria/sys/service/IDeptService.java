package com.victoria.sys.service;

import com.victoria.sys.pojo.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-01-02
 */
public interface IDeptService extends IService<Dept> {

    Integer deptMaxNum();


}

package com.victoria.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.victoria.sys.pojo.Dept;
import com.victoria.sys.mapper.DeptMapper;
import com.victoria.sys.service.IDeptService;
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
 * @since 2020-01-02
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {


    @Autowired
    private DeptMapper deptMapper;


    @Override
    public Dept getOne(Wrapper<Dept> queryWrapper, boolean throwEx) {
        return super.getOne(queryWrapper, throwEx);
    }


    @Override
    public boolean update(Dept entity, Wrapper<Dept> updateWrapper) {
        return super.update(entity, updateWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public Integer deptMaxNum() {
        return deptMapper.deptMaxNum();
    }
}

package com.victoria.sys.service.impl;

import com.victoria.sys.pojo.User;
import com.victoria.sys.mapper.UserMapper;
import com.victoria.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-12-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer userMaxNum() {
        return userMapper.userMaxNum();
    }



}

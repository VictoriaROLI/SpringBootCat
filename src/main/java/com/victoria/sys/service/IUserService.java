package com.victoria.sys.service;

import com.victoria.sys.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-12-30
 */
public interface IUserService extends IService<User> {


    Integer userMaxNum();


}

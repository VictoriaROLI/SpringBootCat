package com.victoria.sys.mapper;

import com.victoria.sys.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.plugin.Intercepts;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-12-30
 */

@Repository
public interface UserMapper extends BaseMapper<User> {


    @Select("select max(ordernum) from sys_user")
    Integer userMaxNum();



}

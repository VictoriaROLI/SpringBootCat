package com.victoria.bus.mapper;

import com.victoria.bus.pojo.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-01-07
 */

@Repository
public interface GoodsMapper extends BaseMapper<Goods> {

    @Select("select * from bus_goods where providerid = #{0}")
    List<Goods> loadGoodsByProviderId(Integer providerid);
}

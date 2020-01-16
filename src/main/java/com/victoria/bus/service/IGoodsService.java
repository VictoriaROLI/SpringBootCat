package com.victoria.bus.service;

import com.victoria.bus.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-01-07
 */
public interface IGoodsService extends IService<Goods> {

    List<Goods> loadGoodsByProviderId(Integer providerid);
}

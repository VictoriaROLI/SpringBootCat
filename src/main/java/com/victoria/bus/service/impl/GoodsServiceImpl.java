package com.victoria.bus.service.impl;

import com.victoria.bus.pojo.Goods;
import com.victoria.bus.mapper.GoodsMapper;
import com.victoria.bus.service.IGoodsService;
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
 * @since 2020-01-07
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public List<Goods> loadGoodsByProviderId(Integer providerid) {
        return goodsMapper.loadGoodsByProviderId(providerid);
    }
}

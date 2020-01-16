package com.victoria.bus.service.impl;

import com.victoria.bus.mapper.GoodsMapper;
import com.victoria.bus.pojo.Goods;
import com.victoria.bus.pojo.Inport;
import com.victoria.bus.mapper.InportMapper;
import com.victoria.bus.service.IInportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-01-10
 */
@Service
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements IInportService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private InportMapper inportMapper;

    @Override
    public boolean save(Inport entity) {
        Integer goodsid = entity.getGoodsid();
        Goods goods = goodsMapper.selectById(goodsid);
        goods.setNumber(goods.getNumber()+entity.getNumber());
        goodsMapper.updateById(goods);
        return super.save(entity);

    }

    @Override
    public boolean updateById(Inport entity) {

        Inport inport = inportMapper.selectById(entity.getId());

        Integer inport_number = inport.getNumber();

        Goods goods = goodsMapper.selectById(entity.getGoodsid());

        Integer goods_number = goods.getNumber();

        Integer new_number = goods_number-inport_number+entity.getNumber();

        goods.setNumber(new_number);

        goodsMapper.updateById(goods);


        return super.updateById(entity);
    }
}

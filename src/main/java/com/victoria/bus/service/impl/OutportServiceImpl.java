package com.victoria.bus.service.impl;

import com.victoria.bus.mapper.GoodsMapper;
import com.victoria.bus.mapper.InportMapper;
import com.victoria.bus.pojo.Goods;
import com.victoria.bus.pojo.Inport;
import com.victoria.bus.pojo.Outport;
import com.victoria.bus.mapper.OutportMapper;
import com.victoria.bus.service.IGoodsService;
import com.victoria.bus.service.IOutportService;
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
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements IOutportService {

    @Autowired
    private InportMapper inportMapper;

    @Autowired
    private GoodsMapper goodsMapper;


    public boolean addOutport(Integer id, Integer number, String remark) {

        Inport inport = inportMapper.selectById(id);
        Goods goods = goodsMapper.selectById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()-number);
        goodsMapper.updateById(goods);

        Outport outport = new Outport();
        outport.setProviderid(inport.getProviderid());
        outport.setPaytype(inport.getPaytype());
        outport.setOutputtime(new Date());
        outport.setOperateperson(inport.getOperateperson());
        outport.setOutportprice(inport.getInportprice());
        outport.setNumber(number);
        outport.setRemark(remark);
        outport.setGoodsid(inport.getGoodsid());
        return super.save(outport);

    }
}

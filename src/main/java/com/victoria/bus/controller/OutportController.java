package com.victoria.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.victoria.bus.pojo.Goods;
import com.victoria.bus.pojo.Inport;
import com.victoria.bus.pojo.Outport;
import com.victoria.bus.pojo.Provider;
import com.victoria.bus.service.IGoodsService;
import com.victoria.bus.service.IProviderService;
import com.victoria.bus.service.impl.OutportServiceImpl;
import com.victoria.bus.vo.OutportVo;
import com.victoria.sys.common.DataGridView;
import com.victoria.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-10
 */
@RestController
@RequestMapping("/outport")
public class OutportController {

    @Autowired
    private OutportServiceImpl outportService;

    @Autowired
    private IProviderService iProviderService;

    @Autowired
    private IGoodsService iGoodsService;




    @RequestMapping("/loadAllOutport")
    public DataGridView loadAllOutport(OutportVo outportVo){
        Page<Outport> page = new Page<>(outportVo.getPage(),outportVo.getLimit());
        QueryWrapper<Outport> queryWrapper = new QueryWrapper<>();
        outportService.page(page,queryWrapper);
        List<Outport> records = page.getRecords();
        for(Outport outport:records){
            Provider provider = iProviderService.getById(outport.getProviderid());
            outport.setProvidername(provider.getProvidername());
            Goods goods = iGoodsService.getById(outport.getGoodsid());
            outport.setGoodsname(goods.getGoodsname());
            outport.setSize(goods.getSize());
        }
        return new DataGridView(page.getTotal(),records);

    }


    @RequestMapping("/addOutport")
    public ResultObj addOutport(Integer id,Integer number,String remark){
        try {
            outportService.addOutport(id,number,remark);
            return ResultObj.OPERATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.OPERATE_ERROR;
        }
    }

}

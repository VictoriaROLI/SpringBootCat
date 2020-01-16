package com.victoria.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.victoria.bus.pojo.Goods;
import com.victoria.bus.pojo.Inport;
import com.victoria.bus.pojo.Provider;
import com.victoria.bus.service.IGoodsService;
import com.victoria.bus.service.IInportService;
import com.victoria.bus.service.IProviderService;
import com.victoria.bus.vo.InportVo;
import com.victoria.sys.common.Constast;
import com.victoria.sys.common.DataGridView;
import com.victoria.sys.common.ResultObj;
import com.victoria.sys.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
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
@RequestMapping("/inport")
public class InportController {

    @Autowired
    private IInportService iInportService;

    @Autowired
    private IProviderService iProviderService;

    @Autowired
    private IGoodsService iGoodsService;



    @RequestMapping("/loadAllInport")
    public DataGridView loadAllInport(InportVo inportVo){



        Page<Inport> page = new Page<>(inportVo.getPage(),inportVo.getLimit());
        QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(inportVo.getProviderid()!=null&&inportVo.getProviderid()!=0,"providerid",inportVo.getProviderid());
        queryWrapper.eq(inportVo.getGoodsid()!=null&&inportVo.getGoodsid()!=0,"goodsid",inportVo.getGoodsid());
        queryWrapper.ge(inportVo.getStartTime()!=null, "inporttime", inportVo.getStartTime());
        queryWrapper.le(inportVo.getEndTime()!=null, "inporttime", inportVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(inportVo.getOperateperson()), "operateperson", inportVo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(inportVo.getRemark()), "remark", inportVo.getRemark());
        queryWrapper.orderByDesc("inporttime");
        iInportService.page(page, queryWrapper);
        List<Inport> records = page.getRecords();
        for(Inport inport:records){
            Provider provider = iProviderService.getById(inport.getProviderid());
            inport.setProvidername(provider.getProvidername());

            Goods goods = iGoodsService.getById(inport.getGoodsid());
            inport.setGoodsname(goods.getGoodsname());
            inport.setSize(goods.getSize());
        }
        return new DataGridView(page.getTotal(),records);
    }


    @RequestMapping("addInport")
    public ResultObj addInport(InportVo inportVo, HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            inportVo.setOperateperson(user.getName());
            inportVo.setInporttime(new Date());
            iInportService.save(inportVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    @RequestMapping("/updateInport")
    public ResultObj updateInport(InportVo inportVo){
        try {
            iInportService.updateById(inportVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("/deleteInport")
    public ResultObj deleteInport(Integer id){
        try {
            iInportService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultObj.DELETE_ERROR;
        }
    }



}

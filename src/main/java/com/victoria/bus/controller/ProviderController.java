package com.victoria.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.victoria.bus.pojo.Provider;
import com.victoria.bus.service.IProviderService;
import com.victoria.bus.vo.ProviderVo;
import com.victoria.sys.common.Constast;
import com.victoria.sys.common.DataGridView;
import com.victoria.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {



    @Autowired
    private IProviderService iProviderService;

    @RequestMapping("/loadAllProvider")
    public DataGridView loadAllProvider(ProviderVo providerVo){
        Page<Provider> page = new Page<>(providerVo.getPage(),providerVo.getLimit());
        QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getProvidername()),"providername",providerVo.getProvidername());
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getTelephone()),"telephone",providerVo.getTelephone());
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getConnectionperson()),"connectionperson",providerVo.getConnectionperson());
        iProviderService.page(page,queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());

    }


    @RequestMapping("/addProvider")
    public ResultObj addProvider(ProviderVo providerVo){
        try {
            iProviderService.save(providerVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    @RequestMapping("/updateProvider")
    public ResultObj updateProvider(ProviderVo providerVo){
        try {
            iProviderService.updateById(providerVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    @RequestMapping("deleteProvider")
    public ResultObj deleteProvider(Integer id){
        try {
            iProviderService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultObj.DELETE_ERROR;
        }
    }


    @RequestMapping("/batchDeleteProvider")
    public ResultObj batchDeleteProvider(ProviderVo providerVo){
        try {
            List ids = new ArrayList();
            for(Integer id : providerVo.getIds()) {
                ids.add(id);
            }
            iProviderService.removeByIds(ids);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("/loadAllProviderForSelect")
    public DataGridView loadAllProviderForSelect(){
        QueryWrapper<Provider> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Provider> list = iProviderService.list(queryWrapper);
        return new DataGridView(list);
    }

}

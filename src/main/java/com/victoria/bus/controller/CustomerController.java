package com.victoria.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.victoria.bus.pojo.Customer;
import com.victoria.bus.service.ICustomerService;
import com.victoria.bus.vo.CustomerVo;
import com.victoria.sys.common.DataGridView;
import com.victoria.sys.common.ResultObj;
import com.victoria.sys.pojo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService iCustomerService;

    @RequestMapping("/loadAllCustomer")
    public DataGridView loadAllCustomer(CustomerVo customerVo){
        Page<Customer> page = new Page<>(customerVo.getPage(),customerVo.getLimit());
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getCustomername()),"customername",customerVo.getCustomername());
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getTelephone()),"telephone",customerVo.getTelephone());
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getConnectionperson()),"connectionperson",customerVo.getConnectionperson());
        iCustomerService.page(page,queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());

    }


    @RequestMapping("/addCustomer")
    public ResultObj addCustomer(CustomerVo customerVo){
        try {
            iCustomerService.save(customerVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    @RequestMapping("/updateCustomer")
    public ResultObj updateCustomer(CustomerVo customerVo){
        try {
            iCustomerService.updateById(customerVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    @RequestMapping("deleteCustomer")
    public ResultObj deleteCustomer(Integer id){
        try {
            iCustomerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultObj.DELETE_ERROR;
        }
    }


    @RequestMapping("/batchDeleteCustomer")
    public ResultObj batchDeleteCustomer(CustomerVo customerVo){
        try {
            List ids = new ArrayList();
            for(Integer id : customerVo.getIds()) {
                ids.add(id);
            }
            iCustomerService.removeByIds(ids);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }


    }

}

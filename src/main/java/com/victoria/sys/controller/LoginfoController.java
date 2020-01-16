package com.victoria.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.victoria.sys.common.DataGridView;
import com.victoria.sys.common.ResultObj;
import com.victoria.sys.pojo.Loginfo;
import com.victoria.sys.service.ILoginfoService;
import com.victoria.sys.vo.LoginfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-01
 */
@RestController
@RequestMapping("/loginfo")
public class LoginfoController {

    @Autowired
    private ILoginfoService iLoginfoService;


    @RequestMapping("/loadAllLoginfo")
    public DataGridView  loadAllLoginfo(LoginfoVo loginfoVo){
        Page<Loginfo> page = new Page<>(loginfoVo.getPage(),loginfoVo.getLimit());
        QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginname()),"loginname",loginfoVo.getLoginname());
        queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginip()),"loginip",loginfoVo.getLoginip());
        queryWrapper.ge(loginfoVo.getStartTime()!=null,"logintime",loginfoVo.getStartTime());
        queryWrapper.le(loginfoVo.getEndTime()!=null,"logintime",loginfoVo.getEndTime());
        queryWrapper.orderByDesc("loginTime");
        iLoginfoService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());

    }

    /**
     * 删除
     */
    @RequestMapping("/deleteLoginfo")
    public ResultObj deleteLoginfo(Integer id){
        try {
            iLoginfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }


    /**
     * 批量删除
     */
    @RequestMapping("/batchDeleteLoginfo")
    public ResultObj batchDeleteLoginfo(LoginfoVo loginfoVo){
        try {
            List ids = new ArrayList();
            for(Integer id:loginfoVo.getIds()){
                ids.add(id);
            }
            iLoginfoService.removeByIds(ids);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }





}

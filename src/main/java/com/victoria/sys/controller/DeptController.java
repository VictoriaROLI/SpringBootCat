package com.victoria.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.victoria.sys.common.DataGridView;
import com.victoria.sys.common.ResultObj;
import com.victoria.sys.common.TreeNode;
import com.victoria.sys.pojo.Dept;
import com.victoria.sys.service.IDeptService;
import com.victoria.sys.vo.DeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-02
 */
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private IDeptService iDeptService;

    @RequestMapping("/loadAllDeptLeftDtreeJson")
    public DataGridView loadAllDeptLeftDtreeJson(DeptVo deptVo){
        List<Dept> list =  iDeptService.list();
        List<TreeNode> treeNodes = new ArrayList<>();
        for(Dept dept :list){
            boolean spread = dept.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(dept.getId(),dept.getPid(),dept.getTitle(),spread));
        }
        System.out.println(treeNodes);
        return new DataGridView(treeNodes);
    }


    @RequestMapping("/loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo){
        Page<Dept> page = new Page<>(deptVo.getPage(),deptVo.getLimit());
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()),"title",deptVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getAddress()),"address",deptVo.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getRemark()),"remark",deptVo.getRemark());
        queryWrapper.eq(deptVo.getId()!=null,"pid",deptVo.getId()).or().eq(deptVo.getId()!=null,"id",deptVo.getId());
        queryWrapper.orderByAsc("ordernum");
        iDeptService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @RequestMapping("/deleteDept")
    public ResultObj deleteDept(Integer id){
        try {
            iDeptService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("/checkDeptPid")
    public Map<String,Object> checkDeptPid(DeptVo deptVo){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("pid",deptVo.getId());
        List<Dept> list = iDeptService.list(queryWrapper);
        if(list.size()>0){
            map.put("value",true);
        }else{
            map.put("value",false);
        }
        return map;

    }


    @RequestMapping("/addDept")
    public ResultObj addDept(DeptVo deptVo){
        try {
            deptVo.setCreatetime(new Date());
            iDeptService.save(deptVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }

    }

    @RequestMapping("/deptMaxNum")
    public Integer deptMaxNum(){
        return iDeptService.deptMaxNum();
    }



    @RequestMapping("/updateDept")
    public ResultObj updateDept(DeptVo deptVo){
        try {
            deptVo.setCreatetime(new Date());
            iDeptService.updateById(deptVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }

    }



}

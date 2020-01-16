package com.victoria.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.victoria.sys.common.Constast;
import com.victoria.sys.common.DataGridView;
import com.victoria.sys.common.ResultObj;
import com.victoria.sys.common.TreeNode;
import com.victoria.sys.pojo.Permission;
import com.victoria.sys.service.IPermissionService;
import com.victoria.sys.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-12-31
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    
    @Autowired
    private IPermissionService iPermissionService;


    //--------------------------菜单管理开始------------------------------

    @RequestMapping("/loadAllPermissionLeftDtreeJson")
    public DataGridView loadAllPermissionLeftDtreeJson(PermissionVo permissionVo){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constast.TYPE_MENU);
        List<Permission> list =  iPermissionService.list(queryWrapper);
        System.out.println(list);
        List<TreeNode> treeNodes = new ArrayList<>();
        for(Permission permission :list){
            System.out.println(permission);
            boolean spread = permission.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),spread));
        }
        System.out.println(treeNodes);
        return new DataGridView(treeNodes);
    }


    @RequestMapping("/loadAllPermission")
    public DataGridView loadAllPermission(PermissionVo permissionVo){
        Page<Permission> page = new Page<>(permissionVo.getPage(),permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getPercode()),"percode",permissionVo.getPercode());
        queryWrapper.eq(permissionVo.getId()!=null,"pid",permissionVo.getId());
        queryWrapper.eq("type", Constast.TYPE_PERMISSION);//只能查询菜单
        queryWrapper.orderByAsc("ordernum");
        iPermissionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }


    @RequestMapping("/permissionMaxNum")
    public Integer permissionMaxNum(){
        return iPermissionService.deptMaxNum();
    }



    @RequestMapping("/addPermission")
    public ResultObj addPermission(PermissionVo permissionVo){
        try {
            permissionVo.setType(Constast.TYPE_PERMISSION);
            iPermissionService.save(permissionVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("/updatePermission")
    public ResultObj updatePermission(PermissionVo permissionVo){
        try {
            iPermissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("/deletePermission")
    public ResultObj deletePermission(Integer id){
        try {
            iPermissionService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }



    //--------------------------菜单管理结束------------------------------


}

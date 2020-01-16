package com.victoria.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.victoria.sys.common.Constast;
import com.victoria.sys.common.DataGridView;
import com.victoria.sys.common.ResultObj;
import com.victoria.sys.common.TreeNode;
import com.victoria.sys.pojo.Notice;
import com.victoria.sys.pojo.Permission;
import com.victoria.sys.pojo.Role;
import com.victoria.sys.pojo.User;
import com.victoria.sys.service.IPermissionService;
import com.victoria.sys.service.IRoleService;
import com.victoria.sys.vo.RoleVo;
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
 * @since 2020-01-03
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IPermissionService iPermissionService;

    @RequestMapping("/loadAllRole")
    public DataGridView loadAllRole(RoleVo roleVo){
        Page<Role> page = new Page<>(roleVo.getPage(),roleVo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()), "name", roleVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRemark()), "remark", roleVo.getRemark());
        queryWrapper.eq(roleVo.getAvailable()!=null, "available", roleVo.getAvailable());
        queryWrapper.orderByDesc("createtime");
        iRoleService.page(page,queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());

    }


    @RequestMapping("/addRole")
    public ResultObj addRole(RoleVo roleVo){
        try {
            roleVo.setCreatetime(new Date());
            iRoleService.saveOrUpdate(roleVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    @RequestMapping("/updateRole")
    public ResultObj updateRole(RoleVo roleVo){
        try {
            iRoleService.saveOrUpdate(roleVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    @RequestMapping("deleteRole")
    public ResultObj deleteRole(Integer id){
        try {
            iRoleService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("/initPermissionByRoleId")
    public DataGridView initPermissionByRoleId(Integer roleId){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Permission> allPermissions = iPermissionService.list(queryWrapper);
        List<Integer> currentRolePermissions = iRoleService.queryRolePermissionIdsByRid(roleId);
        List<TreeNode> nodes=new ArrayList<>();
        for(Permission permission:allPermissions){
            String checkArr="0";
            for(Integer permissionid:currentRolePermissions){
                if(permissionid == permission.getId()){
                    checkArr="1";
                    break;
                }
            }
            Boolean spread=(permission.getOpen()==null||permission.getOpen()==1)?true:false;
            nodes.add(new TreeNode(permission.getId(), permission.getPid(), permission.getTitle(), spread, checkArr));
        }
        return new DataGridView(nodes);
    }

    @RequestMapping("/saveRolePermission")
    public ResultObj saveRolePermission(Integer rid,Integer[] ids){
        try {
            iRoleService.saveRolePermission(rid,ids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }

}

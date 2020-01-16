package com.victoria.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.victoria.sys.common.*;
import com.victoria.sys.pojo.Permission;
import com.victoria.sys.pojo.User;
import com.victoria.sys.service.IPermissionService;
import com.victoria.sys.service.IRoleService;
import com.victoria.sys.vo.DeptVo;
import com.victoria.sys.vo.MenuVo;
import com.victoria.sys.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  菜单管理控制器
 * </p>
 *
 * @author jobob
 * @since 2019-12-31
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IPermissionService iPermissionService;

    @Autowired
    private IRoleService iRoleService;


    @RequestMapping("loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo, HttpServletRequest request){
        QueryWrapper<Permission> queryWrapper;
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constast.TYPE_MENU);
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        User user = (User) request.getSession().getAttribute("user");
        List<Permission> list = null;
        if(user.getType()==Constast.USER_TYPE_SUPER){
            list = iPermissionService.list();
        }else{
            Integer userid = user.getId();
            List<Integer> rids = iRoleService.queryUserRoleIdsByUid(userid);

            Set<Integer> permissionSet = new HashSet<>();

            for(Integer rid:rids){
                List<Integer> permission = iRoleService.queryRolePermissionIdsByRid(rid);
                permissionSet.addAll(permission);
            }

            if(permissionSet.size()>0){
                queryWrapper.in("id",permissionSet);
                list = iPermissionService.list(queryWrapper);
            }else{
                list = new ArrayList<>();
            }

        }

        List<TreeNode> treeNodes = new ArrayList<>();

        for(Permission p : list){
            Integer id=p.getId();
            Integer pid=p.getPid();
            String title=p.getTitle();
            String icon=p.getIcon();
            String href=p.getHref();
            Boolean spread=p.getOpen()==Constast.OPEN_TRUE?true:false;
            treeNodes.add(new TreeNode(id,pid,title,icon,href,spread));
        }

        List<TreeNode> list2 = TreeNodeBuilder.builder(treeNodes, 1);


        return new DataGridView(list2);

    }


    //--------------------------菜单管理开始------------------------------

    @RequestMapping("/loadAllMenuLeftDtreeJson")
    public DataGridView loadAllMenuLeftDtreeJson(MenuVo menuVo){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constast.TYPE_MENU);
        List<Permission> list =  iPermissionService.list(queryWrapper);
        System.out.println(list);
        List<TreeNode> treeNodes = new ArrayList<>();
        for(Permission permission :list){
            boolean spread = permission.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),spread));
        }
        System.out.println(treeNodes);
        return new DataGridView(treeNodes);
    }


    @RequestMapping("/loadAllMenu")
    public DataGridView loadAllMenu(MenuVo menuVo){
        Page<Permission> page = new Page<>(menuVo.getPage(),menuVo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(menuVo.getTitle()),"title",menuVo.getTitle());
        queryWrapper.eq("type", Constast.TYPE_MENU);//只能查询菜单
        queryWrapper.eq(menuVo.getId()!=null,"pid",menuVo.getId()).or().eq(menuVo.getId()!=null,"id",menuVo.getId());
        queryWrapper.orderByAsc("ordernum");
        iPermissionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }


    @RequestMapping("/menuMaxNum")
    public Integer menuMaxNum(){
        return iPermissionService.deptMaxNum();
    }



    @RequestMapping("/addMenu")
    public ResultObj addMenu(MenuVo menuVo){
        try {
            menuVo.setType(Constast.TYPE_MENU);
            iPermissionService.save(menuVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("/updateMenu")
    public ResultObj updateMenu(MenuVo menuVo){
        try {
            iPermissionService.updateById(menuVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    @RequestMapping("/checkMenuPid")
    public Map<String,Object> checkMenuPid(MenuVo menuVo){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("pid",menuVo.getId());
        List<Permission> list = iPermissionService.list(queryWrapper);
        if(list.size()>0){
            map.put("value",true);
        }else{
            map.put("value",false);
        }
        return map;

    }

    @RequestMapping("/deleteMenu")
    public ResultObj deleteMenu(Integer id){
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

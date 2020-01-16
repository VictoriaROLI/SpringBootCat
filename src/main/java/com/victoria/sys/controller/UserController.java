package com.victoria.sys.controller;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.victoria.sys.common.Constast;
import com.victoria.sys.common.DataGridView;
import com.victoria.sys.common.PinyinUtils;
import com.victoria.sys.common.ResultObj;
import com.victoria.sys.pojo.Dept;
import com.victoria.sys.pojo.Role;
import com.victoria.sys.pojo.User;
import com.victoria.sys.service.IDeptService;
import com.victoria.sys.service.IRoleService;
import com.victoria.sys.service.IUserService;
import com.victoria.sys.vo.UserVo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-12-30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDeptService iDeptService;

    @Autowired
    private IRoleService iRoleService;


    @RequestMapping("/loadAllUser")
    public DataGridView loadAllUser(UserVo userVo){
        Page<User> page = new Page<>(userVo.getPage(),userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(userVo.getName()),"name",userVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(userVo.getAddress()),"address",userVo.getAddress());
        queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);//查询系统用户
        queryWrapper.eq(userVo.getDeptid()!=null,"deptid",userVo.getDeptid());
        iUserService.page(page, queryWrapper);

        List<User> list = page.getRecords();
        for( User user:list){
            Integer deptid = user.getDeptid();
            if(deptid!=null){
                Dept one = iDeptService.getById(deptid);
                user.setDeptname(one.getTitle());
            }

            Integer mgr = user.getMgr();
            if(mgr!=null){
                User one = iUserService.getById(mgr);
                user.setLeadername(one.getName());
            }
        }

        return new DataGridView(page.getTotal(),list);

    }


    @RequestMapping("/loadUsersByDeptId")
    public DataGridView loadUsersByDeptId(Integer deptid){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constast.USER_TYPE_NORMAL);
        queryWrapper.eq(deptid!=null,"deptid",deptid);
        List<User> list = iUserService.list(queryWrapper);
        return new DataGridView(list);
    }


    @RequestMapping("/loadUsersByMgrId")
    public DataGridView loadUsersByMgrId(Integer mgr){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(mgr!=null){
            queryWrapper.eq("type",Constast.USER_TYPE_NORMAL);
            queryWrapper.eq("id",mgr);
            List<User> list = iUserService.list(queryWrapper);

            return new DataGridView(list);
        }else{
            return new DataGridView(new ArrayList<>());
        }
    }


    @RequestMapping("/userMaxNum")
    public Integer userMaxNum(){
        return  iUserService.userMaxNum();
    }


    @RequestMapping("/addUser")
    public ResultObj addUser(UserVo userVo){
        try {
            userVo.setType(Constast.USER_TYPE_NORMAL);
            userVo.setHiredate(new Date());
            String Salt = IdUtil.simpleUUID().toUpperCase();
            userVo.setSalt(Salt);
            userVo.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,Salt,2).toString());
            iUserService.save(userVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    @RequestMapping("/updateUser")
    public ResultObj updateUser(UserVo userVo){
        try {
            this.iUserService.updateById(userVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    @RequestMapping("/changeChineseToPinyin")
    public Map<String,Object> changeChineseToPinyin(String username){
        String pingYin = PinyinUtils.getPingYin(username);
        Map<String,Object> map = new HashMap<>();
        map.put("value",pingYin);
        return map;
    }

    @RequestMapping("/deleteUser")
    public ResultObj deleteUser(Integer id){
        try {
            iUserService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }


    @RequestMapping("/resetPwd")
    public ResultObj resetPwd(Integer id,Integer newpwd,Integer cpwd){
        if(newpwd.intValue() == cpwd.intValue()){
            try {
                User user = new User();
                user.setId(id);
                String Salt = IdUtil.simpleUUID().toString();
                user.setSalt(Salt);
                user.setPwd(new Md5Hash(newpwd.toString(),Salt,2).toString());
                iUserService.updateById(user);
                return ResultObj.RESET_SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return ResultObj.RESET_ERROR;
            }
        }else {
            return ResultObj.NEWPWD_CPWD_ERROR;
        }
    }



    @RequestMapping("/initRoleByUserId")
    public DataGridView initRoleByUserId(Integer id){
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        List<Map<String, Object>> listMaps = iRoleService.listMaps(queryWrapper);
        List<Integer> currentUserRoleIds = iRoleService.queryUserRoleIdsByUid(id);

        for(Map<String, Object> map:listMaps){
            Boolean LAY_CHECKED=false;
            Integer roleId=(Integer) map.get("id");
            for(Integer rid :currentUserRoleIds){
                if(rid.intValue()==roleId.intValue()){
                    LAY_CHECKED=true;
                    break;
                }
            }
            map.put("LAY_CHECKED",LAY_CHECKED);
        }

        return  new DataGridView(Long.valueOf(listMaps.size()),listMaps);

    }

    @RequestMapping("/saveUserRole")
    public ResultObj saveUserRole(Integer uid,Integer[] ids){
        try {
            iRoleService.saveUserRole(uid,ids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }

    }






}

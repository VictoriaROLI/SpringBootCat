package com.victoria.sys.realm;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.victoria.sys.common.ActiverUser;
import com.victoria.sys.common.Constast;
import com.victoria.sys.pojo.Permission;
import com.victoria.sys.pojo.User;
import com.victoria.sys.service.IPermissionService;
import com.victoria.sys.service.IRoleService;
import com.victoria.sys.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IPermissionService iPermissionService;


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname", authenticationToken.getPrincipal().toString());
        User user = iUserService.getOne(queryWrapper);
        if (null != user) {
            ActiverUser activerUser = new ActiverUser();
            activerUser.setUser(user);


            QueryWrapper<Permission> qw = new QueryWrapper<>();
            qw.eq("type", Constast.TYPE_MENU);
            qw.eq("available", Constast.AVAILABLE_TRUE);

            Integer userid = user.getId();
            List<Integer> rids = iRoleService.queryUserRoleIdsByUid(userid);

            Set<Integer> permissionSet = new HashSet<>();

            for(Integer rid:rids){
                List<Integer> permission = iRoleService.queryRolePermissionIdsByRid(rid);
                permissionSet.addAll(permission);
            }

            List<Permission> list=new ArrayList<>();

            if(permissionSet.size()>0){
                queryWrapper.in("id",permissionSet);
                list = iPermissionService.list(qw);
            }
            List<String> percodes=new ArrayList<>();
            for (Permission permission : list) {
                percodes.add(permission.getPercode());
            }

            activerUser.setPermissions(percodes);



            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser, user.getPwd(), credentialsSalt,
                    this.getName());
            return info;
        }
        return null;
    }

    //权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        ActiverUser activerUser=(ActiverUser) principalCollection.getPrimaryPrincipal();
        User user=activerUser.getUser();
        List<String> permissions = activerUser.getPermissions();
        if(user.getType()==Constast.USER_TYPE_SUPER) {
            info.addStringPermission("*:*");
        }else {
            if(null!=permissions&&permissions.size()>0) {
                info.addStringPermissions(permissions);
            }
        }
        return info;
    }

}

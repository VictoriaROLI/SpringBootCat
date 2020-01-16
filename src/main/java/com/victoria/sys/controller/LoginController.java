package com.victoria.sys.controller;


import com.victoria.sys.common.ActiverUser;
import com.victoria.sys.common.ResultObj;
import com.victoria.sys.pojo.Loginfo;
import com.victoria.sys.service.ILoginfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@RequestMapping("/login")
public class LoginController  {

    @Autowired
    private ILoginfoService iLoginfoService;


    @RequestMapping("/login")
    public ResultObj login(String loginname, String loginpwd, HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(loginname,loginpwd);
        try {
            subject.login(token);
            ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
            request.getSession().setAttribute("user",activerUser.getUser());
            Loginfo loginfo = new Loginfo();
            loginfo.setLoginname(activerUser.getUser().getName()+activerUser.getUser().getLoginname());
            loginfo.setLoginip(request.getRemoteAddr());
            loginfo.setLogintime(new Date());
            iLoginfoService.save(loginfo);
            return ResultObj.LOGIN_SUCCESS;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResultObj.LOGIN_ERROR_PASS;
        }
    }




}

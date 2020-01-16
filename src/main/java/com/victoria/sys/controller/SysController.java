package com.victoria.sys.controller;

import com.victoria.sys.common.ResultObj;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequestMapping("/sys")
@Controller
public class SysController {

    @RequestMapping("/login")
    public String toLogin(){
        return "/sys/index/login";
    }

    @RequestMapping("/index")
    public String toIndex(){
        return "/sys/index/index";
    }

    @RequestMapping("/deskManager")
    public String deskManager(){
        return "/sys/index/deskManager";
    }


    @RequestMapping("/loginfoManager")
    public String infoManager(){
        return "/sys/loginfo/loginfoManager";
    }

    @RequestMapping("/noticeManager")
    public String noticeManager(){
        return "/sys/notice/noticeManager";
    }

    @RequestMapping("/deptManager")
    public String deptManager(){
        return "/sys/dept/deptManager";
    }

    @RequestMapping("/deptLeft")
    public String deptLeft(){
        return "sys/dept/deptLeft";
    }

    @RequestMapping("/deptRight")
    public String deptRight(){
        return "sys/dept/deptRight";
    }


    @RequestMapping("/menuManager")
    public String menuManager(){
    return "/sys/menu/menuManager";
    }
    
    @RequestMapping("/menuLeft")
    public String menuLeft(){
    return "sys/menu/menuLeft";
    }
    
    @RequestMapping("/menuRight")
    public String menuRight(){
    return "sys/menu/menuRight";
    }


    @RequestMapping("/permissionManager")
    public String permissionManager(){
        return "/sys/permission/permissionManager";
    }

    @RequestMapping("/permissionLeft")
    public String permissionLeft(){
        return "sys/permission/permissionLeft";
    }

    @RequestMapping("/permissionRight")
    public String permissionRight(){
        return "sys/permission/permissionRight";
    }


    @RequestMapping("/roleManager")
    public String roleManager(){
        return "sys/role/roleManager";
    }


    @RequestMapping("/userManager")
    public String userManager(){
        return "sys/user/userManager";
    }

    @RequestMapping("/userLeft")
    public String userLeft(){
        return "sys/user/userLeft";
    }

    @RequestMapping("/userRight")
    public String userRight(){
        return "sys/user/userRight";
    }




}

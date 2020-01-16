package com.victoria.bus.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/bus")
@Controller
public class Business {

    @RequestMapping("/customerManager")
    public String customerManager(){
        return "/business/customer/customerManager";
    }

    @RequestMapping("/providerManager")
    public String providerManager(){
        return "/business/provider/providerManager";
    }

    @RequestMapping("/goodsManager")
    public String goodsManager(){
        return "/business/goods/goodsManager";
    }

    @RequestMapping("/inportManager")
    public String inportManager(){
        return "/business/inport/inportManager";
    }

    @RequestMapping("/outportManager")
    public String outportManager(){
        return "/business/outport/outportManager";

    }
    
    
}

package com.daji.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限控制(Shiro)
 */
@Controller
public class AuthorityController extends BaseController{
    @RequestMapping("/unauth")
    public String unauthorized(){
        return "error/unauth";
    }
}

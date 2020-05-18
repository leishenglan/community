package com.lsl.kunmming.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
public class UserController {
    /**
     * 测试方法
     *
     * @return
     */
    @GetMapping("/testMethod")
    @ResponseBody
    public String testMethod() {
        return "hello world";
    }

    @GetMapping("/testMethod1")
    public String testMethod1() {
        return "hello";
    }


    @GetMapping("/testMethod2")
    public String testMethod1(@RequestParam("name") String name,Model model) {
        model.addAttribute("name",name);
        return "hello";
    }


}

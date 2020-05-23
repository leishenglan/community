package com.lsl.kunmming.community.controller;

import com.lsl.kunmming.community.mapper.UserMapper;
import com.lsl.kunmming.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;

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

    @GetMapping("/")
    public String testMethod1(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("token")) {
                String value = c.getValue();
                User user = userMapper.findByToken(value);
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        return "index";
    }


    @GetMapping("/testMethod2")
    public String testMethod1(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }


}

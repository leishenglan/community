package com.lsl.kunmming.community.controller;

import com.lsl.kunmming.community.mapper.QuestionMapper;
import com.lsl.kunmming.community.mapper.UserMapper;
import com.lsl.kunmming.community.pojo.PaginationDTO;
import com.lsl.kunmming.community.pojo.Question;
import com.lsl.kunmming.community.pojo.QuestionDTO;
import com.lsl.kunmming.community.pojo.User;
import com.lsl.kunmming.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */
@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

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
    public String testMethod1(HttpServletRequest request, Model model
            , @RequestParam(name = "page", defaultValue = "1") Integer page,
        @RequestParam(name = "size", defaultValue = "5" )Integer size) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("token")) {
                    String value = c.getValue();
                    User user = userMapper.findByToken(value);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        PaginationDTO questionDTOS = questionService.list(page,size);
        model.addAttribute("pageInfo", questionDTOS);
        return "index";

    }


    @GetMapping("/testMethod2")
    public String testMethod1(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }


}

package com.lsl.kunmming.community.controller;

import com.lsl.kunmming.community.mapper.QuestionMapper;
import com.lsl.kunmming.community.mapper.UserMapper;
import com.lsl.kunmming.community.pojo.Question;
import com.lsl.kunmming.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String Publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model)
    {
        if(title == null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null||description==""){
            model.addAttribute("error","问题填充不能为空");
            return "publish";
        }
        if(tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Cookie[] cookies = request.getCookies();
        User user = null;
        for (Cookie c : cookies) {
            if (c.getName().equals("token")) {
                String value = c.getValue();
                 user = userMapper.findByToken(value);
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        if(user==null){
        model.addAttribute("error","用户未登陆");
        return  "publish";

        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(sdf.format(new Date()));
        question.setGmtModified(question.getGmtCreate());
        questionMapper.insert(question);
        return "redirect:/";

    }

}

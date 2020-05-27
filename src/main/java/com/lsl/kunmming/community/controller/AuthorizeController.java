package com.lsl.kunmming.community.controller;

import com.lsl.kunmming.community.dto.AccessTokenDTO;
import com.lsl.kunmming.community.dto.GithupUser;
import com.lsl.kunmming.community.mapper.UserMapper;
import com.lsl.kunmming.community.pojo.User;
import com.lsl.kunmming.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    public UserMapper userMapper;

    @Value("${githup.client_id}")
    private String clientId;
    @Value("${githup.client_secret}")
    private String clientSecret;
    @Value("${githup.redirect_uri}")
    private String redirectUrl;

    @GetMapping("/callbacks")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithupUser githupUser = githubProvider.getUser(accessToken);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (githupUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githupUser.getName());
            user.setAccountId(String.valueOf(githupUser.getId()));
            user.setGmtCreate(sdf.format(new Date()));
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githupUser.getAvatarUrl());
            userMapper.insert(user);
            response.addCookie(new Cookie("token", token));
            //登录成功，写cookie和session
            return "redirect:/";

        } else {
            //登录失败 重新登录
            return "redirect:/";

        }
    }


}

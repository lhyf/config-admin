package org.lhyf.config.server.admin.controller;

import org.lhyf.config.server.constant.WebConstant;
import org.lhyf.config.server.model.Bo.RestResponseBo;
import org.lhyf.config.server.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/****
 * @author YF
 * @date 2018-07-10 16:35
 * @desc IndexController
 *
 **/

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping({"","/index"})
    public String index() {
        return "admin/index";
    }


    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }


    @ResponseBody
    @PostMapping("/login")
    public RestResponseBo login(String username, String password, boolean rememberme) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();

        if (subject.isAuthenticated()) {
            return RestResponseBo.ok();
        }

        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);

        try {
            upToken.setRememberMe(rememberme);
            subject.login(upToken);
            session.setAttribute(WebConstant.SESSION_USERNAME, username);

            // 更新用户登录时间
            accountService.updateAccountLoginTime(username);
            logger.info(session.getAttribute(WebConstant.SESSION_USERNAME).toString());
        } catch (AuthenticationException exception) {
            return RestResponseBo.fail("登录失败!");
        }

        return RestResponseBo.ok();
    }
}

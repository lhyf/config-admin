package org.lhyf.config.admin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.lhyf.config.constant.WebConstant;
import org.lhyf.config.model.Bo.RestResponseBo;
import org.lhyf.config.pojo.Account;
import org.lhyf.config.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @author YF
 * @date 2018-07-18 18:14
 * @desc UserController
 *
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AccountService accountService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Model model) {
        PageHelper.startPage(page, 20);
        List<Account> list = accountService.getAllAccount();
        PageInfo<Account> info = new PageInfo<>(list);
        model.addAttribute("users", info);
        return "admin/user-list";
    }

    @ResponseBody
    @PostMapping("/add-user")
    public RestResponseBo addUser(String username, String nickname, String password) {

        Account account = accountService.getAccountByUserName(username);
        if (account != null) {
            return RestResponseBo.fail("用户名: " + username + " 已存在!");
        }

        Md5Hash md5Hash = new Md5Hash(password, username, 100);
        accountService.saveAccount(username, md5Hash.toString(), nickname);

        return RestResponseBo.ok();
    }

    @ResponseBody
    @PostMapping("/update-user")
    public RestResponseBo updateUser(Integer id, String username, String nickname, String password) {
        Account account = accountService.getAccountByUserName(username);
        if (account != null && (account.getId() != id)) {
            return RestResponseBo.fail("用户名: " + username + " 已存在!");
        }

        // 如果前端没有传password,则表示不修改密码
        if (password != null && !"".equals(password)) {
            Md5Hash md5Hash = new Md5Hash(password, username, 100);
            accountService.updateAccount(id, username, md5Hash.toString(), nickname);
        } else {
            accountService.updateAccount(id, username, null, nickname);
        }

        return RestResponseBo.ok();
    }

    /**
     * 更新用户状态
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @PostMapping("/update-status")
    public RestResponseBo updateStatus(Integer id, Short status) {
        if (status == WebConstant.USER_STATUS_NORMAL) {
            accountService.updateAccountStatus(id, WebConstant.USER_STATUS_FORBID);
        } else {
            accountService.updateAccountStatus(id, WebConstant.USER_STATUS_NORMAL);
        }
        return RestResponseBo.ok();
    }

    @ResponseBody
    @PostMapping("/delete-user")
    public RestResponseBo deleteUser(Integer id){
        accountService.deleteById(id);
        return RestResponseBo.ok();
    }
}

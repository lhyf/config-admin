package org.lhyf.config.service;

import org.lhyf.config.constant.WebConstant;
import org.lhyf.config.mapper.AccountMapper;
import org.lhyf.config.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/****
 * @author YF
 * @date 2018-07-11 16:57
 * @desc AccountService
 *
 **/
@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    public Account getAccountByUserName(String username) {
       Account account = accountMapper.getAccountByUserName(username);
       return account;
    }

    /**
     * 查询所有用户
     * @return
     */
    public List<Account> getAllAccount() {
        return accountMapper.selectByExample(null);
    }

    /**
     * 添加用户
     * @param username
     * @param password 盐值加密后的密码
     * @param nickname
     * @return
     */
    public int saveAccount(String username, String password, String nickname) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setNickname(nickname);
        account.setAddTime(new Date());
        account.setUserStatus(WebConstant.USER_STATUS_NORMAL);
        return accountMapper.insertSelective(account);
    }

    public int updateAccount(Integer id, String username, String password, String nickname) {
        Account account = new Account();
        account.setId(id);
        account.setUsername(username);
        account.setNickname(nickname);
        account.setPassword(password);
        account.setUpdateTime(new Date());
        return accountMapper.updateByPrimaryKeySelective(account);
    }


    public int updateAccountStatus(Integer id, short status) {
        Account account = new Account();
        account.setId(id);
        account.setUserStatus(status);
        return accountMapper.updateByPrimaryKeySelective(account);
    }

    public int deleteById(Integer id) {
        return accountMapper.deleteByPrimaryKey(id);
    }

    public int updateAccountLoginTime(String username) {
        return accountMapper.updateAccountLoginTime(username);
    }
}

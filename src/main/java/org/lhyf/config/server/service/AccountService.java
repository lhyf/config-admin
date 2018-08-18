package org.lhyf.config.server.service;

import org.lhyf.config.server.constant.WebConstant;
import org.lhyf.config.server.mapper.TAccountMapper;
import org.lhyf.config.server.pojo.TAccount;
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
    private TAccountMapper accountMapper;

    public TAccount getAccountByUserName(String username) {
       TAccount account = accountMapper.getAccountByUserName(username);
       return account;
    }

    /**
     * 查询所有用户
     * @return
     */
    public List<TAccount> getAllAccount() {
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
        TAccount account = new TAccount();
        account.setUsername(username);
        account.setPassword(password);
        account.setNickname(nickname);
        account.setAddTime(new Date());
        account.setUserStatus(WebConstant.USER_STATUS_NORMAL);
        return accountMapper.insertSelective(account);
    }

    public int updateAccount(Integer id, String username, String password, String nickname) {
        TAccount account = new TAccount();
        account.setId(id);
        account.setUsername(username);
        account.setNickname(nickname);
        account.setPassword(password);
        account.setUpdateTime(new Date());
        return accountMapper.updateByPrimaryKeySelective(account);
    }


    public int updateAccountStatus(Integer id, short status) {
        TAccount account = new TAccount();
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

package org.lhyf.config.mapper;

import org.lhyf.config.pojo.Account;
import tk.mybatis.mapper.common.Mapper;

public interface AccountMapper extends Mapper<Account> {
    int updateAccountLoginTime(String username);

    Account getAccountByUserName(String username);
}
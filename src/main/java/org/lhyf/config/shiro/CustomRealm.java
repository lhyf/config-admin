package org.lhyf.config.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.lhyf.config.pojo.Account;
import org.lhyf.config.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

/****
 * @author YF
 * @date 2018-07-10 18:57
 * @desc CustomRealm
 *
 **/
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private AccountService accountService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken unToken = (UsernamePasswordToken) token;
        String username = unToken.getUsername();

        Account account = accountService.getAccountByUserName(username);

        if (account == null) {
            throw new UnknownAccountException("账号不存在!");
        }

        if(account.getUserStatus() == 0){
            throw new LockedAccountException("账号锁定!");
        }

        String password = account.getPassword();
        ByteSource salt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, salt, getName());
        return info;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","tom",100);
        System.out.println(md5Hash.toString());
    }
}

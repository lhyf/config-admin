package org.lhyf.config.server.constant;

/****
 * @author YF
 * @date 2018-07-11 15:29
 * @desc WebConstant
 *
 **/
public interface WebConstant {
    String SESSION_USERNAME = "shiro:session:username";

    // 用户状态 1:正常 0:禁用
    short USER_STATUS_NORMAL = 1;
    short USER_STATUS_FORBID = 0;
}

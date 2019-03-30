package org.lhyf.config.pojo;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 状态（1:正常；0:已失效；）
     */
    @Column(name = "user_status")
    private Short userStatus;

    private String nickname;

    @Column(name = "login_time")
    private Date loginTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return add_time
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * @param addTime
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取状态（1:正常；0:已失效；）
     *
     * @return user_status - 状态（1:正常；0:已失效；）
     */
    public Short getUserStatus() {
        return userStatus;
    }

    /**
     * 设置状态（1:正常；0:已失效；）
     *
     * @param userStatus 状态（1:正常；0:已失效；）
     */
    public void setUserStatus(Short userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return login_time
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
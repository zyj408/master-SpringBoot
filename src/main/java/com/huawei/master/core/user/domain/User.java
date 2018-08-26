package com.huawei.master.core.user.domain;

import org.springframework.data.annotation.Id;

public class User {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 登陆帐户
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型(1普通用户2管理员3系统用户)
     */
    private String type;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 地址
     */
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

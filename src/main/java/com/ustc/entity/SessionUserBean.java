
package com.ustc.entity;

/**
 * 用户Bean
 * @author 李芝赟
 */
public class SessionUserBean {
    private String userId;

    public SessionUserBean() {
    }

    public SessionUserBean(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
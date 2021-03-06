package com.ustc.login.service;

import com.ustc.entity.UserDO;

import java.util.List;

public interface UserService {
    // 根据id查询用户
    UserDO findOne(String id);

    // 保存用户
    void saveAdmin(UserDO userDO);

    // 删除用户
    void deleteAdminById(String id);

    // 查询所有用户，包括普通用户和管理员
    List<UserDO> findAllAdmin();

    // 查询所有普通用户
    public List<UserDO> findAllUser();

    // 查询所有管理员
    public List<UserDO> findAllManage();

    // 用于用户名和密码验证
    // 根据用户名查询密码
    public String getPasswordByUsername(String username);
}


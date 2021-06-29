package com.ustc.login.service.impl;

import com.ustc.entity.UserDO;
import com.ustc.login.dao.UserRepository;
import com.ustc.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    // 根据id查询用户
    public UserDO findOne(String id){
        Query id1 = Query.query(Criteria.where("id").is(id));
        UserDO one = mongoTemplate.findOne(id1, UserDO.class);
        return one;
    }

    // 保存用户
    public void saveAdmin(UserDO admin){
        // 可以设置一些默认值
        userRepository.save(admin);
    }
    //
//    // 更新用户信息
//    public void updateAdmin(Admin admin){
//        adminRepository.save(admin);
//    }
//
    // 删除用户
    public void deleteAdminById(String id){
        userRepository.deleteById(id);
    }

    // 查询所有用户，包括普通用户和管理员
    public List<UserDO> findAllAdmin(){
        return userRepository.findAll();
    }

    // 查询所有普通用户
    public List<UserDO> findAllUser(){
        // 查询条件
        Query query = Query.query(Criteria.where("type").is("user"));

        return mongoTemplate.find(query, UserDO.class);
    }

    // 查询所有管理员
    public List<UserDO> findAllManage(){
        // 查询条件
        Query query = Query.query(Criteria.where("type").is("manage"));

        return mongoTemplate.find(query, UserDO.class);
    }

    // 用于用户名和密码验证
    // 根据用户名查询密码
    public String getPasswordByUsername(String username){
        // 根据用户名查询用户
        Query query = Query.query(Criteria.where("username").is(username));
        UserDO target = mongoTemplate.findOne(query, UserDO.class);
        return target.getPassword();
    }
}


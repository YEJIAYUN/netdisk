package com.ustc.upload.dao;

import com.ustc.Application;
import com.ustc.entity.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserDaoTest {

    @Autowired
    public UserDao userDao;

    @Test
    public void testInsert() {
        UserDO user = new UserDO();
        user.setId(1);
        user.setUsername("number1");
        user.setPassword("no");
        user.setCreateTime(new Date());
        UserDO.Profile profile = new UserDO.Profile();
        profile.setGender("male");
        profile.setNickname("nick");
        user.setProfile(profile);
        // 插入数据
        userDao.insertOne(user);

        UserDO user1 = new UserDO();
        user1.setId(2);
        user1.setUsername("number2");
        user1.setPassword("no");
        user1.setCreateTime(new Date());
        UserDO.Profile profile1 = new UserDO.Profile();
        profile1.setGender("female");
        profile1.setNickname("dick");
        user.setProfile(profile1);
        // 插入数据
        userDao.insertOne(user1);
    }

    @Test
    public void testUpdate() {
        UserDO user = new UserDO();
        user.setId(2);
        user.setUsername("更新");
        user.setPassword("no");
        user.setCreateTime(new Date());
        UserDO.Profile profile = new UserDO.Profile();
        profile.setGender("male");
        profile.setNickname("nick");
        user.setProfile(profile);
        userDao.updateById(user);
    }

    @Test
    public void testFindAllById() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        List<UserDO> users = userDao.findAllById(arrayList);
        users.forEach(System.out::println);
    }

    @Test
    public void testDeleteById() {
        userDao.deleteById(1);
    }
}

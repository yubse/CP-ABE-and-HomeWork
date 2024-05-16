package com.lt.abe.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.abe.commen.JwtTokenUtils;
import com.lt.abe.dao.UserDao;
import com.lt.abe.entity.Params;
import com.lt.abe.entity.User;
import com.lt.abe.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 物联网2班刘婷
 * @Description UserService
 * @Date 2024/2/29
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public List<User>getUser(){
        return userDao.getUser();
        //使用吞引入的包
//        return userDao.selectAll();
    }

    public PageInfo<User> findBySearch(Params params) {
        System.out.println(params.getPageNum());
        System.out.println(params.getPageSize());
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        System.out.println("service层"+params);

        List<User> list= userDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(User user) {
        //不允许账号相同新增
        if (user.getSch_id() == null||"".equals(user.getSch_id())) {
            throw new CustomException("请填入学号");
        }
        User user1= userDao.findBySchId(user.getSch_id());
        if (user1!=null) {
            throw new CustomException("该学号已存在");
        }
        if (user.getPassword()==null) {
            user.setPassword(user.getSch_id());
        }

        userDao.add(user);
//        userDao.insertSelective(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void deleteUser(String sch_id) {
        userDao.deleteUser(sch_id);
    }

    public User login(User user) {
        //非空校验
        if (user.getSch_id() == null||"".equals(user.getSch_id())) {
            throw new CustomException("学号不能为空");
        }
        if (user.getPassword() == null||"".equals(user.getPassword())) {
            throw new CustomException("密码不能为空");
        }
        //数据库查询
        User user1 = userDao.findBySchIdAndPassword(user.getSch_id(),user.getPassword());
        if (user1 == null) {
            throw new CustomException("学号或密码输入错误");
        }
        // 生成jwt token给前端

        String token = JwtTokenUtils.genToken(user1.getId().toString(), user1.getPassword());

        user1.setToken(token);
        return user1;
    }

    public User findById(Integer id) {
        return userDao.findById(id);
    }
}

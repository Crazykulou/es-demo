package com.crazykulou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crazykulou.entity.User;
import com.crazykulou.mapper.UserMapper;
import com.crazykulou.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}

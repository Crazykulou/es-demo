package com.crazykulou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "es_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String nickName;

    private String email;

    private Integer age;
}

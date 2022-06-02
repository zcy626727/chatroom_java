package com.luyouqi.mcommunity.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luyouqi.mcommunity.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zcy
 * @since 2021-03-31
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    String test(String username);

    User selectByUserName(String username);

    List<User> getFriendsByUserId(String userId);

    void selectList(QueryWrapper<Object> wrapper);
}

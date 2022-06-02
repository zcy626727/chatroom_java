package com.luyouqi.mcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luyouqi.mcommunity.entity.Friends;
import com.luyouqi.mcommunity.entity.UserRoom;
import com.luyouqi.mcommunity.mapper.FriendsMapper;
import com.luyouqi.mcommunity.utils.Result;
import com.luyouqi.mcommunity.utils.ResultArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcy
 * @since 2021-04-07
 */
@RestController
@RequestMapping("/friends")
public class FriendsController {
    @Autowired
    FriendsMapper friendsMapper;

    /**
     * 删除好友
     * @param uId1
     * @param uId2
     * @return
     */
    @GetMapping("/deleteFriend/{uId1}/{uId2}")
    public Result outRoomsById(@PathVariable("uId1") String uId1, @PathVariable("uId2") String uId2){
        QueryWrapper<Friends> wrapper = new QueryWrapper<>();
        wrapper.or(qw->{
            qw.eq("u_id_from",uId1);
            qw.eq("u_id_to",uId2);
        });
        wrapper.or(qw->{
            qw.eq("u_id_from",uId2);
            qw.eq("u_id_to",uId1);
        });
        int i = friendsMapper.delete(wrapper);
        if (i>0){
            return new Result(ResultArgs.SUCCESS_CODE,"好友删除成功");
        }else {
            return new Result(ResultArgs.FAILURE_CODE,"好友删除失败");
        }

    }

}


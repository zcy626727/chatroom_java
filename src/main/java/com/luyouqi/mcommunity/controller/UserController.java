package com.luyouqi.mcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luyouqi.mcommunity.entity.Chatroom;
import com.luyouqi.mcommunity.entity.User;
import com.luyouqi.mcommunity.mapper.UserMapper;
import com.luyouqi.mcommunity.service.UserService;
import com.luyouqi.mcommunity.utils.Result;
import com.luyouqi.mcommunity.utils.ResultArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcy
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired(required = false)
    private UserService userService;
    @Autowired(required = false)
    private UserMapper userMapper;


    /**
     * 根据用户id查询他的所有好友
     * @param userId
     * @return
     */
    @GetMapping("/getFriendsByUserId/{userId}")
    public String getFriendsByUserId(@PathVariable("userId") String userId){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        List<User> friends = userMapper.getFriendsByUserId(userId);
        for (User u: friends) {
            u.setPassword(null);
        }
        return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,"查询成功").addKV("friends",friends));
    }



    /**
     * @PathVariable 获取url参数，支持get请求
     * @RequestParam 获取url、body，支持POST/PUT/DELETE/PATCH
     * @RequestBody 获取json参数，支持POST/PUT/DELETE/PATCH
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user){

        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userService.save(user);
        return new Result(ResultArgs.SUCCESS_CODE,"注册成功");
    }

    /**
     * 查询用户
     * @param nickname
     * @param uId
     * @return
     */
    @GetMapping("/getUserByIdOrName/{nickname}/{uId}")
    public String getUserByIdOrName(@PathVariable("nickname") String nickname,@PathVariable("uId") String uId){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id",uId.substring(1)).or().like("nickname",nickname.substring(1));
        List<User> list = userMapper.selectList(wrapper);

        return gson.toJson(new Result(ResultArgs.SUCCESS_CODE,"查询成功").addKV("users",list));
    }


    /**
     * 测试连接
     * @return
     */
    @GetMapping("/hello")
    public Result hello(){
        return new Result(ResultArgs.SUCCESS_CODE,"测试成功");
    }

    @GetMapping("/vip")
    public Result vip(){
        return new Result(ResultArgs.SUCCESS_CODE,ResultArgs.SUCCESS_MSG+" vip用户");
    }

}


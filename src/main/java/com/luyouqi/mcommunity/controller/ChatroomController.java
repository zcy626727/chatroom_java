package com.luyouqi.mcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luyouqi.mcommunity.entity.Chatroom;
import com.luyouqi.mcommunity.entity.UserRoom;
import com.luyouqi.mcommunity.mapper.ChatroomMapper;
import com.luyouqi.mcommunity.mapper.UserRoomMapper;
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
 * @since 2021-04-03
 */
@RestController
@RequestMapping("/chatroom")
@CrossOrigin
public class ChatroomController {


    @Autowired(required = false)
    ChatroomMapper chatroomMapper;

    @Autowired(required = false)
    UserRoomMapper userRoomMapper;


    /**
     * 根据id或name查询聊天室
     * @param roomName
     * @param roomId
     * @return
     */
    @GetMapping("/getRoomsByIdOrName/{roomName}/{roomId}")
    public Result getRoomsByIdOrName(@PathVariable("roomName") String roomName,@PathVariable("roomId") String roomId){
        QueryWrapper<Chatroom> wrapper = new QueryWrapper<>();
        wrapper.like("room_name",roomName.substring(1)).or().eq("r_id",roomId.substring(1));
        List<Chatroom> list = chatroomMapper.selectList(wrapper);
        return new Result(ResultArgs.SUCCESS_CODE,"查询成功").addKV("chatrooms",list);
    }


    /**
     * 获取用户的所有聊天室
     * @param userId
     * @return
     */
    @GetMapping("/getRoomsById/{userId}")
    public Result getRoomsById(@PathVariable("userId") String userId){
        List<Chatroom> chatrooms = chatroomMapper.selectRoomsByUserId(userId);

        return new Result(ResultArgs.SUCCESS_CODE,"查询成功").addKV("chatrooms",chatrooms);
    }


    /**
     * 解散聊天室
     * @param rId
     * @return
     */
    @GetMapping("/deleteRoomsById/{rId}")
    public Result deleteRoomsById(@PathVariable("rId") String rId){
        chatroomMapper.deleteById(rId);
        QueryWrapper<UserRoom> wrapper = new QueryWrapper<>();
        wrapper.eq("r_id",rId);
        //解除所有在该聊天室的人
        userRoomMapper.delete(wrapper);
        return new Result(ResultArgs.SUCCESS_CODE,"聊天室删除成功");
    }


    /**
     * 退出聊天室
     * @param rId
     * @param uId
     * @return
     */
    @GetMapping("/outRoomsById/{rId}/{uId}")
    public Result outRoomsById(@PathVariable("rId") String rId,@PathVariable("uId") String uId){
        QueryWrapper<UserRoom> wrapper = new QueryWrapper<>();
        wrapper.eq("r_id",rId);
        wrapper.eq("u_id",uId);
        userRoomMapper.delete(wrapper);
        return new Result(ResultArgs.SUCCESS_CODE,"聊天室退出成功");
    }


    /**
     * 注册聊天室
     * @param chatroom
     * @return
     */
    @PostMapping("/newRoom")
    public Result newRoom(@RequestBody Chatroom chatroom){

        //创建聊天室
        int i = chatroomMapper.insert(chatroom);
        //将创建者添加进聊天室
        userRoomMapper.insert(new UserRoom(chatroom.getUId(),chatroom.getRId()));
        if(i==1){
            return new Result(ResultArgs.SUCCESS_CODE,"聊天室创建成功");
        }else {
            return new Result(ResultArgs.FAILURE_CODE,"聊天室创建失败");
        }

    }

}


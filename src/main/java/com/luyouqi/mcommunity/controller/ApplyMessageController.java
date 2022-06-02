package com.luyouqi.mcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luyouqi.mcommunity.entity.ApplyMessage;
import com.luyouqi.mcommunity.entity.Chatroom;
import com.luyouqi.mcommunity.entity.Friends;
import com.luyouqi.mcommunity.entity.UserRoom;
import com.luyouqi.mcommunity.mapper.ApplyMessageMapper;
import com.luyouqi.mcommunity.mapper.ChatroomMapper;
import com.luyouqi.mcommunity.mapper.FriendsMapper;
import com.luyouqi.mcommunity.mapper.UserRoomMapper;
import com.luyouqi.mcommunity.utils.Result;
import com.luyouqi.mcommunity.utils.ResultArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcy
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/apply")
public class ApplyMessageController {

    @Autowired(required = false)
    ApplyMessageMapper applyMessageMapper;
    @Autowired(required = false)
    UserRoomMapper userRoomMapper;

    @Autowired(required = false)
    FriendsMapper friendsMapper;

    /**
     * 申请加入聊天室
     * @param applyMessage
     * @return
     */
    @PostMapping("/applyRoomOrUser")
    public Result applyRoomOrUser(@RequestBody ApplyMessage applyMessage){
        QueryWrapper<ApplyMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("sender_id",applyMessage.getSenderId());
        wrapper.eq("target_id",applyMessage.getTargetId());
        Integer count = applyMessageMapper.selectCount(wrapper);
        if (count >= 1) {//已经申请过
            return new Result(ResultArgs.FAILURE_CODE,"请不要重复申请");
        }
        applyMessageMapper.insert(applyMessage);
        return new Result(ResultArgs.SUCCESS_CODE,"申请成功");
    }



    /**
     * 接收申请
     * @param applyMessage
     * @return
     */
    @PostMapping("/acceptApply")
    public Result acceptApply(@RequestBody ApplyMessage applyMessage){
        //移除申请
        int i = applyMessageMapper.deleteById(applyMessage.getAId());
        if(i>0){
            //判断type同意信息插入
            if(applyMessage.getType()==0){//聊天室
                userRoomMapper.insert(new UserRoom(applyMessage.getSenderId(),applyMessage.getTargetId()));
            }else {//好友
                friendsMapper.insert(new Friends(applyMessage.getSenderId(),applyMessage.getTargetId()));
            }
            return new Result(ResultArgs.SUCCESS_CODE,"已同意申请");
        }
        return new Result(ResultArgs.FAILURE_CODE,"操作失败");

    }


    /**
     * 拒绝申请
     * @param applyMessage
     * @return
     */
    @PostMapping("/rejectApply")
    public Result rejectApply(@RequestBody ApplyMessage applyMessage){
        //移除申请
        int i = applyMessageMapper.deleteById(applyMessage.getAId());
        if(i>0){
            return new Result(ResultArgs.SUCCESS_CODE,"已拒绝申请");
        }else {
            return new Result(ResultArgs.SUCCESS_CODE,"操作失败");
        }


    }

    /**
     * 获取申请列表
     * @param uid
     * @return
     */
    @GetMapping("/getMyApply/{uid}")
    public Result getMyApply(@PathVariable("uid") String uid){
        QueryWrapper<UserRoom> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id", uid);
        List<UserRoom> userRooms = userRoomMapper.selectList(wrapper);

        //条件拼接
        QueryWrapper<ApplyMessage> wrapper1 = new QueryWrapper<>();
        if(userRooms.size()!=0){
            wrapper1.eq("type","0");
            wrapper1.and(wq->{
                for (UserRoom item : userRooms){
                    wq.or().eq("target_id",item.getRId());
                }
            });
        }

        wrapper1.or(qw->{
            qw.eq("type","1");
            qw.eq("target_id",uid);
        });

        List<ApplyMessage> applyMessages = applyMessageMapper.selectList(wrapper1);
        return new Result(ResultArgs.SUCCESS_CODE,"申请查询成功").addKV("applyMessages",applyMessages);
    }

}


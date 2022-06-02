package com.luyouqi.mcommunity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luyouqi.mcommunity.entity.*;
import com.luyouqi.mcommunity.mapper.*;
import com.luyouqi.mcommunity.utils.Result;
import com.luyouqi.mcommunity.utils.ResultArgs;
import com.luyouqi.mcommunity.webSocket.WebSocket;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;
import springfox.documentation.spring.web.json.Json;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class McommunityApplicationTests {

    @Autowired(required = false)
    UserMapper userMapper;
    @Autowired(required = false)
    ChatroomMapper chatroomMapper;
    @Autowired(required = false)
    UserRoomMapper userRoomMapper;
    @Autowired(required = false)
    ApplyMessageMapper applyMessageMapper;
    @Autowired(required = false)
    ChatMessageMapper chatMessageMapper;

    @Test
    void test5(){
        String targetId = "1";
        String senderId = "2";
        String type = "3";

        //(type = ? AND (target_id = ? AND sender_id = ?) OR (target_id = ? AND sender_id = ?))

        QueryWrapper<ChatMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("type",type);
        wrapper.and(qw->{
            qw.or(wq->{
                wq.eq("target_id",targetId);
                wq.eq("sender_id",senderId);
            });
            qw.or(wq->{
                wq.eq("target_id",senderId);
                wq.eq("sender_id",targetId);
            });
        });

        System.out.println(chatMessageMapper.selectList(wrapper));
    }

    @Test
    void test4(){

        //保存个人的websocket，用户id为key，websocket为value
         Map<String, String> singleClients = new ConcurrentHashMap<String, String>();

        //保存聊天室的websocket，聊天室id为key，value中Map<String, WebSocket>key为聊天室中的成员名，websocket为成员的websocket
         Map<String, Map<String, String>> roomClients = new ConcurrentHashMap<String, Map<String, String>>();

         Map<String,Integer> countOfRooms = new HashMap<>();

         ArrayList<String> roomList = new ArrayList<String>();

        String message = "{\"type\":\"2\",\"chatRooms\":[{\"rId\":\"1\"},{\"rId\":\"2\"}]}";

        JSONObject obj = JSON.parseObject(message);
        String type = obj.getString("type");//消息类型（私聊（0）/群发（1）/注册聊天室（2））
        if("2".equals(type)){//注册聊天室
            String chatRooms = obj.getString("chatRooms");
            JSONArray chatRoomArr = JSON.parseArray(chatRooms);
            for (int i=0;i<chatRoomArr.size();i++){
                JSONObject item = chatRoomArr.getJSONObject(i);
                String rId = item.getString("rId");

                Map<String, String> room = roomClients.get(rId);
                if(room==null){//没有记录当前聊天室
                    roomClients.put(rId,new ConcurrentHashMap<String, String>());//添加聊天室
                    if(countOfRooms.get(rId)==null){//设置当前聊天室人数
                        countOfRooms.put(rId,0);
                    }else {
                        Integer count = countOfRooms.get(rId);
                        countOfRooms.put(rId,count++);
                    }
                    room = roomClients.get(rId);//获取聊天室
                }
                roomList.add(rId);//保存聊天室id
                room.put("rId","this");//将当前用户添加到该聊天室
                Integer count = countOfRooms.get(rId);

                try {
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    //首次连接，将消息群发到当前聊天室
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("content","用户名"+"上线");//设置发送的消息内容
                    map.put("msgType","0");//（提示）1（普通消息）
                    map.put("senderName","发送者名");//设置发送人名字
                    map.put("sendTime",new Date());
                    String msg = gson.toJson(map);
                    System.out.println();
                    //给自己发消息，报告当前人数
                    HashMap<String, Object> map1 = new HashMap<>();
                    map1.put("content","当前聊天室人数："+count+1);//设置发送的消息内容
                    map1.put("senderName","发送者名字");//设置发送人名字
                    map1.put("sendTime",new Date());
                    map1.put("msgType","0");
                    String msg1 = gson.toJson(map1);
                    System.out.println("msg1："+msg1);
                }catch (Exception e){

                }
            }
        }
        System.out.println("解析消息并且添加聊天室后：");
        System.out.println("此时singleClients："+singleClients);
        System.out.println("此时roomClients："+roomClients);
        System.out.println("此时countOfRooms："+countOfRooms);
    }


    @Test
    void test3() {
        String json = "{\"type\":\"2\",\"rooms\":[{\"rId\":\"123456\",\"rname\":\"325\"},{\"rId\":\"18526\",\"rname\":\"315\"}]}";
        System.out.println(json);
        JSONObject obj = JSON.parseObject(json);//{"type":"2","rooms":[{"rId":"123456","rname":"325"}]}
//        System.out.println(obj.getString("type"));//2
        String rooms = obj.getString("rooms");//[{"rname":"325","rId":"123456"}]
        JSONArray array = JSON.parseArray(rooms);//[{"rname":"325","rId":"123456"}]
        JSONObject jsonObject = array.getJSONObject(1);
        System.out.println(jsonObject.getString("rId"));
//        JSONObject jsonObject = JSON.parseObject(item);
//        String rname = jsonObject.getString("rname");
//        System.out.println(rname);

//        HashMap<String, Integer> a = new HashMap<>();
//        Integer a1 = a.get("a");
//        System.out.println(a1);
    }

    /**
     * 数据库聊天室测试
     */
    @Test
    void test2() {
        //根据用户id查询全部所在的聊天室
//        System.out.println(chatroomMapper.selectRoomsByUserId("1377208937660669954"));

//        System.out.println(userMapper.getFriendsByUserId("1377208937660669954"));
        Chatroom chatroom = new Chatroom("这个聊天室", "1377061116336676865", "我们很年轻我们很直流");
        chatroomMapper.insert(chatroom);

    }


    /**
     * 数据库用户测试
     */
    @Test
    void contextLoads() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//            User user = new User("jimiwenjian", DigestUtils.md5DigestAsHex("123456789".getBytes()), "全村最大理发店", "2454dsds1@qq.com");
//            userMapper.insert(user);
//        System.out.println(userMapper.selectByUserName("wuhan"));
        List<User> friends = userMapper.getFriendsByUserId("1377208937660669954");
        Result result = new Result(ResultArgs.SUCCESS_CODE, "查询成功").addKV("friends", friends);

        System.out.println(gson.toJson(result));
    }

    /**
     * gson日期格式转换测试
     */
    @Test
    void test1() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String timeStyle = gson.toJson(userMapper.selectByUserName("wuhan").getRegTime());
        System.out.println(timeStyle);
    }

    /**
     * md5加密测试
     */
    @Test
    void testMD5(){
        System.out.println(DigestUtils.md5DigestAsHex("123456789".getBytes()));
    }

}

package com.luyouqi.mcommunity.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luyouqi.mcommunity.entity.ChatMessage;
import com.luyouqi.mcommunity.mapper.ChatMessageMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Component
@ServerEndpoint(value = "/connectWebSocket/{uId}/{username}")
public class WebSocket {


    @Autowired
    private ChatMessageMapper chatMessageMapper;

    //打印日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //保存个人的websocket，用户id为key，websocket为value
    private static Map<String, WebSocket> singleClients = new ConcurrentHashMap<String, WebSocket>();

    //保存聊天室的websocket，聊天室id为key，value中Map<String, WebSocket>key为聊天室中的成员名，websocket为成员的websocket
    private static Map<String, Map<String, WebSocket>> roomClients = new ConcurrentHashMap<String, Map<String, WebSocket>>();

    private static Map<String,Integer> countOfRooms = new HashMap<>();

    private ArrayList<String> roomList = new ArrayList<String>();

    private String username;

    private String uId;

    //会话
    private Session session;

    @OnOpen
    public void onOpen(@PathParam("uId") String uId, @PathParam("username") String username,Session session){
        this.username = username;
        this.session = session;
        this.uId = uId;
        singleClients.put(uId,this);
//        logger.info("open执行了uid和username："+uId+" -------- "+username);
//        System.out.println("此时singleClients："+singleClients);
//        System.out.println("此时roomClients："+roomClients);
//        System.out.println("此时countOfRooms："+countOfRooms);
    }

    @OnError
    public void onError(Session session, Throwable error){
        logger.info("error执行了-------"+error.getMessage());
    }

    @OnClose
    public void onClose(){
        logger.info("close执行了");

        for (String roomId:roomList){//用户关闭连接，更新所有聊天室
            try {
                Integer count = countOfRooms.get(roomId);
                countOfRooms.put(roomId,count-1);

                HashMap<String, Object> map = new HashMap<>();
                map.put("content",this.getUsername()+"下线");//设置发送的消息内容
                map.put("senderName",this.getUsername());//设置发送人昵称
                map.put("sendTime",new Date());
                map.put("msgType","0");
                map.put("rId",roomId);
                map.put("type","1");
                this.sendMessageAll(roomId,map);
                //给当前聊天室发送当前在线人数
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("onlineCount",count-1);//设置发送的消息内容
                map1.put("senderName",this.getUsername());//设置发送人名字
                map1.put("sendTime",new Date());
                map1.put("msgType","2");
                map1.put("type","1");
                map1.put("uId",uId);
                map1.put("rId",roomId);//发到聊天室需要rId
                this.sendMessageAll(roomId,map1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map<String, WebSocket> room = roomClients.get(roomId);
            room.remove(this.uId);
        }
        singleClients.remove(this.uId);

    }

    @OnMessage
    public void onMessage(String message, Session session){
        logger.info("message执行了message："+message);
        JSONObject obj = JSON.parseObject(message);
        String type = obj.getString("type");//消息类型（私聊（0）/群发（1）/注册聊天室（2））
        if("2".equals(type)){//初始化聊天室
            regChatRoom(obj);//注册聊天室
        }
        else{//转发消息
            routeMessage(obj,type);
        }
    }

    /**
     * 转发消息
     * @param obj
     * @param type
     */
    public void routeMessage(JSONObject obj,String type){
        String content = obj.getString("content");//消息内容
        String senderName = obj.getString("senderName");//发送者昵称
        String senderId = obj.getString("senderId");//发送者用户名
        String targetId = obj.getString("targetId");//目的地
        String dateTime = JSON.toJSONStringWithDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        //构造回复消息
        HashMap<String, Object> map = new HashMap<>();
        map.put("content",content);
        map.put("senderName",senderName);
        map.put("senderId",senderId);
        map.put("msgType","1");//普通消息
        map.put("sendTime",dateTime);
        map.put("targetId",targetId);


        if("0".equals(type)){//私聊

            map.put("type","0");
            map.put("uId",senderId);
            //向目标发送消息
            boolean suc = this.sendMessageTo(targetId, map);
            //向自己发送消息
            map.put("uId",targetId);
            if (!suc){
                map.put("sendSuccess","0");//0代表发送失败
            }else {
                map.put("sendSuccess","1");//1代表发送成功
            }
            this.sendMessageTo(senderId, map);
        }else if ("1".equals(type)){//群发
            // 将消息存入数据库

            map.put("type","1");
            map.put("rId",targetId);
            this.sendMessageAll(targetId,map);
        }else {
            logger.info("消息类型出错，为："+type);
        }
    }

    /**
     * 注册聊天室，初始化
     * @param obj
     */
    public void regChatRoom(JSONObject obj){
        String chatRooms = obj.getString("chatRooms");
        JSONArray chatRoomArr = JSON.parseArray(chatRooms);
        for (int i=0;i<chatRoomArr.size();i++){
            JSONObject item = chatRoomArr.getJSONObject(i);
            String rId = item.getString("rId");

            Map<String, WebSocket> room = roomClients.get(rId);
            if(room==null){//没有记录当前聊天室
                roomClients.put(rId,new ConcurrentHashMap<String, WebSocket>());//添加聊天室
                if(countOfRooms.get(rId)==null){//设置当前聊天室人数
                    countOfRooms.put(rId,0);
                }
                room = roomClients.get(rId);//获取聊天室
            }
            Integer count = countOfRooms.get(rId);
            countOfRooms.put(rId,count+1);
            roomList.add(rId);//保存聊天室id
            room.put(uId,this);//将当前用户添加到该聊天室
            count = countOfRooms.get(rId);



            //首次连接，将消息群发到当前聊天室
            HashMap<String, Object> map = new HashMap<>();
            map.put("content",username+"上线");//设置发送的消息内容
            map.put("msgType","0");//0（提示）1（普通消息）2（改变状态消息）
            map.put("senderName",this.getUsername());//设置发送人名字
            map.put("sendTime",new Date());
            map.put("type","1");//0私聊,1群发
            map.put("rId",rId);
            this.sendMessageAll(rId,map);
            //给当前聊天室发送当前在线人数
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("onlineCount",count);//设置发送的消息内容
            map1.put("senderName",this.getUsername());//设置发送人名字
            map1.put("sendTime",new Date());
            map1.put("msgType","2");
            map1.put("type","1");
            map1.put("uId",uId);
            map1.put("rId",rId);//发到聊天室需要rId
            this.sendMessageAll(rId,map1);
//            this.sendMessageTo(uId,map1);

        }
    }



    /**
     * 私发消息
     * @param uId
     * @param map
     * @throws IOException
     */
    public boolean sendMessageTo(String uId, HashMap<String, Object> map){
        logger.info("私发消息");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String message = gson.toJson(map);
        WebSocket webSocket = singleClients.get(uId);
        if(webSocket==null){
            return false;
        }
        try {
            webSocket.session.getAsyncRemote().sendText(message);
            return true;
        } catch (Exception e) {

            logger.info("发送消息报错: "+e.getMessage());
            return false;
        }
//        webSocket.session.getAsyncRemote().sendText();
    }


    /**
     * 群发消息（聊天室）
     * @param rId
     * @param map
     * @throws IOException
     */
    public void sendMessageAll(String rId, HashMap<String, Object> map){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String message = gson.toJson(map);
        logger.info("群发消息");
        Map<String, WebSocket> roomWebSocket = roomClients.get(rId);
        for (WebSocket item : roomWebSocket.values()) {
            synchronized(item.session){
                try {
                    item.session.getAsyncRemote().sendText(message);
                }catch (Exception e){
                    logger.info("群发消息错误: "+e.getMessage());
                }
            }

        }
    }

}

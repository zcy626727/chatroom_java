package com.luyouqi.mcommunity.security;

//import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luyouqi.mcommunity.utils.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SecurityHandlerUtil {
    /**
     * security处理返回结果
     * @param response 响应
     * @param result 结果
     * @throws IOException
     */
    public static void responseHandler(HttpServletResponse response, Result result) throws IOException {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();


        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(gson.toJson(result));
        writer.flush();
        writer.close();
    }

}

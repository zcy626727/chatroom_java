package com.luyouqi.mcommunity.utils;

public class ResultArgs {

    //操作成功
    public static String SUCCESS_MSG = "操作成功";
    public static Integer SUCCESS_CODE = 2000;

    //登录成功
    public static String USER_LOGIN_SUCCESS_MSG = "登录成功";
    public static Integer USER_LOGIN_SUCCESS_CODE = 2001;

    //注销成功
    public static String LOGOUT_SUCCESS_MSG = "注销成功";
    public static Integer LOGOUT_SUCCESS_CODE = 2002;

    //操作失败
    public static String FAILURE_MSG = "操作失败";
    public static Integer FAILURE_CODE = 4000;

    //登录验证失败
    public static String USER_NOT_EXIST_FAILURE_MSG = "账号或者密码错误";
    public static Integer USER_NOT_EXIST_FAILURE_CODE = 4001;

    //没有登录
    public static String USER_NOT_LOGIN_FAILURE_MSG = "未登录";
    public static Integer USER_NOT_LOGIN_FAILURE_CODE = 4002;

    //无权限
    public static String AUTHORIZE_FAILURE_MSG = "没有权限";
    public static Integer AUTHORIZE_FAILURE_CODE = 4003;



}

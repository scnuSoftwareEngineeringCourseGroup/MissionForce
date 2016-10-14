package com.cczq.missionforce.utils;

/**
 * 作为存储所有URL的类
 * Created by Shyuan on 2016/9/23.
 */

public class configURL {

    //服务器的基本地址
    private static String base_URL = "http://119.29.186.160/";
    //验证登陆的地址
    public static String URL_LOGIN = base_URL + "phalapi/Public/missionforce/?service=User.loginUser";
    //进行注册的地址
    public static String URL_REGISTER = base_URL + "phalapi/Public/missionforce/?service=User.registerUser";
    //进行任务查询地址
    public static String URL_CHEACKMISSION = base_URL + "/phalapi/Public/missionforce/?service=Mission.getMissionByUID";
    //提交任务时间
    public static String URL_COMMITMISSION = base_URL + "/phalapi/Public/missionforce/?service=Mission.ChangeMissionTime";

}

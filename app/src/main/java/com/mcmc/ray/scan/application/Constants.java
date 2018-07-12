package com.mcmc.ray.scan.application;


import com.mcmc.ray.scan.util.FileUtil;

/**
 * Created by oracleen on 2016/7/11.
 * 保存项目中用到的常量
 */
public class Constants {

    //public static final String GANHUO_API = "120.79.63.59:10015";

    public static final String dir = FileUtil.getDiskCacheDir(MyApplication.getIntstance()) + "/girls";

    public static final String DEMO_API="http://120.79.63.59:10015/";

    public static final String IPADDRESS_PRE = "ipaddress";
    public static final String INTERVAL_PRE = "";
}

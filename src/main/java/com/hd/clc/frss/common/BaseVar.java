package com.hd.clc.frss.common;

public class BaseVar {
    public final static String BASE_IP = "Z:\\idea\\workspace\\frss\\target\\frss/";
    public final static String IMAGE_URL = "upload/image/";
    public final static int TEST_FACESET_ID = 1;
    public final static String FACESET_TOKEN = "58d3ed737992c9e9396cfc2a4656f0e9";


    // 建立连接时间30000ms，超出时间未建立连接成功则报错
    public final static int CONNECT_TIME_OUT = 30000;
    // 建立连接50000ms后，服务器没有可被读取的数据则报错
    public final static int READ_OUT_TIME = 50000;
}

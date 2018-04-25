package com.hd.clc.frss.common;

import java.util.Calendar;
import java.util.Random;

public class StringUtil {

    public static String getRandomString(int length){
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //长度为几就循环几次
        for(int i=0; i<length; ++i){
            //产生0-61的数字
            int number=random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    public static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }

    /**
     * 生成文件名
     * @return
     */
    public static String getFilename() {
        StringBuffer fileName = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        fileName.append(String.valueOf(calendar.get(Calendar.YEAR)))
                .append(valueOfString(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2))
                .append(valueOfString(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2))
                .append(valueOfString(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2))
                .append(valueOfString(String.valueOf(calendar.get(Calendar.MINUTE)), 2))
                .append(valueOfString(String.valueOf(calendar.get(Calendar.SECOND)), 2))
                .append(valueOfString(String.valueOf(calendar.get(Calendar.MILLISECOND)), 3));
        return fileName.toString() + createNum(8);//时间加上8位随机数保证文件名的唯一
    }

    /**
     * 生成指定长度的字符串
     * @param str
     * @param len
     * @return
     */
    private static String valueOfString(String str, int len) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len - str.length(); i++) {
            sb.append("0");
        }
        return (sb.length() == 0) ? (str) : (sb.toString() + str);
    }

    /**
     * 生成指定位数的数字
     * @param length
     * @return
     */
    private static int createNum(int length){
        int max, min;
        max = (int) (Math.pow(10, length) - 1);
        min = (int) Math.pow(10, length - 1);
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 处理文件路径，得到系统的保存路径
     *
     * @param path
     * @return
     */
    public static String getPath(String path) {
        path = path.replace("\\", "/");
        String lastChar = path.substring(path.length() - 1);
        if (!"/".equals(lastChar)) {
            path += "/";
        }
        return path;
    }

}

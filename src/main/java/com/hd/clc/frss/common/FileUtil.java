package com.hd.clc.frss.common;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class FileUtil {

    /**
     * 上传文件的最大长度 2M
     */
    private static long maxFileSize = 1024 * 1024 * 2L;

    /**
     * 图片后缀名
     */
    private static Set<String> suffixSet = new HashSet<>();

    static {
        suffixSet.add("jpg");
        suffixSet.add("jpeg");
        suffixSet.add("bmp");
        suffixSet.add("png");
        suffixSet.add("gif");
    }

    /**
     * 通过流上传文件
     *
     * @param is   IO流
     * @param path 文件存放路径
     * @param name 文件原名
     * @return
     */
    public static String uploadStream(InputStream is, String path, String name) throws IOException {
        String suffix = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        if (suffixSet.contains(suffix)) {
            path = StringUtil.getPath(path);
            name = StringUtil.getFilename() + "." + suffix;
            String fileName = path + name;
            Thumbnails.of(is).size(4096, 4096).toFile(fileName);
            checkFileSize(fileName);
        }else {
            return null;
        }
        return name;
    }

    private static void checkFileSize(String fileName) throws IOException{
        File file = new File(fileName);
        if(file.length() < maxFileSize){
            return;
        }
        Thumbnails.of(file).scale(0.8).toFile(fileName);
        checkFileSize(fileName);
    }

    private static String compressBySize(String fileName, int width, int height) throws IOException {
        Thumbnails.of(fileName)
                .size(width, height)
                .toFile(fileName);
        return fileName;
    }

    private static String compressByScale(String fileName, double scale, double quality) throws IOException {
        Thumbnails.of(fileName) // 原文件路径
                .scale(scale) // 尺寸比例
                .outputQuality(quality) // 图片质量
                .toFile(fileName); // 新文件路径
        return fileName;
    }

}

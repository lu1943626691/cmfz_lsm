package com.baizhi.utils;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;

/**
 * @Author: buxy
 * @Date: 2019/4/16  1:03
 */
public class AudioUtil {
    public static Long getDuration(File source) {
        Encoder encoder = new Encoder();
        long ls = 0;
        try {
            MultimediaInfo m = encoder.getInfo(source);
            ls = m.getDuration() / 1000;
        } catch (EncoderException e) {
            e.printStackTrace();
            System.out.println("获取文件时长失败：" + e.getMessage());
        }
        return ls;
    }
}

package com.nationalchip.iot.common.io;

import com.google.common.base.Splitter;

import java.nio.file.Paths;
import java.util.List;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/1/18 5:23 PM
 * @Modified:
 */
public class IOHelper {
    public static String hashPath(String hash){
        if(hash==null || hash.isEmpty() || hash.length()<4)
            return hash;
        int length = hash.length();
        final List<String> folders = Splitter.fixedLength(2).splitToList(hash.substring(length - 4, length));
        final String folder1 = folders.get(0);
        final String folder2 = folders.get(1);

        return Paths.get(folder1,folder2).toString();
    }
}

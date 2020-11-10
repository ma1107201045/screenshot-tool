package com.lingyi.util;

import java.io.File;
import java.util.Properties;
import java.util.UUID;

public class FileNameUtil {


    public static String get() {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        String basePath = File.separator + "screenshot";
        if (os.startsWith("win") || os.startsWith("Win")) {
            basePath = "C:" + basePath;
        }
        File file = new File(basePath);
        if (!file.exists())
            if (file.mkdirs())
                System.out.println("创建文件夹成功");
        return basePath + File.separator + UUID.randomUUID() + ".png";
    }
}

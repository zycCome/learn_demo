package com.zyc.learn_demo.java8;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zyc66
 * @date 2024/02/12 19:57
 **/
public class DeleteMp3 {

    public static void main(String[] args) {
        // 遍历目录下的所有文件，找出mp3格式
        File rootFile = new File("D:\\百度网盘2");
        List<File> mp3Files = new ArrayList<>();

        findMp3(rootFile,mp3Files);

//        for (File mp3File : mp3Files) {
//            System.out.println(mp3File);
//        }
    }

    private static void findMp3(File parentFile,List<File> mp3Files) {
//        if(mp3Files.size() > 20) {
//            return;
//        }
        File[] files = parentFile.listFiles();
        for (File file1 : files) {
            if(file1.isDirectory()) {
                findMp3(file1,mp3Files);
            } else if(file1.getName().endsWith(".mp3")){
                System.out.println(file1.delete());

            }
        }
    }

}

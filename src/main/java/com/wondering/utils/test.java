package com.wondering.utils;

import com.wondering.submail.MailSend;

import java.io.*;
import java.util.Calendar;
import java.util.Random;

public class test {

    public static void main(String arg[]){

//            String str = "aaaaaaaaa&nbsp;&nbsp;&nbsp;&nbsp;baaaaaaaa";
//            String test = str.replaceAll("&nbsp;", " ");
//            System.out.println(test);
//
//        readToString();
        MailSend mailSend = new MailSend();
        //System.out.println("ttt"+email+"ttt");
        mailSend.SendEmailCode("654321", "wsgzjh@sina.cn");
//        String a = generateRandomFilename();
//        System.out.println(a);
    }

    public static void readToString() {
        String fileName = "D:/WonderingNotes/resource/txt/20190210110610236.txt";
        String encoding = "gbk";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(new String(filecontent, encoding));
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();

        }
    }

    public static String generateRandomFilename(){
        String RandomFilename = "";
        Random rand = new Random();//生成随机数
        int random = rand.nextInt();
        RandomFilename =  String.valueOf(random > 0 ? random : ( -1) * random) ;
        return RandomFilename;
    }
}

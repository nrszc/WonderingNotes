package com.wondering.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Component("dealWithFile")
public class DealWithFile {

    //上传文件
    public static String  uploadFile(HttpServletRequest request, MultipartFile uploadFile, String path1, String path2) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String res = sdf.format(new Date());
        //获取文件的扩展名
        String ext = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
        //设置图片上传路径
        String url = request.getSession().getServletContext().getRealPath(path1);
        System.out.println(url);
        //以绝对路径保存重名命后的图片
        uploadFile.transferTo(new File(url+res + "." + ext));
        //把图片存储路径保存到数据库
        return path2+res + "." + ext;
    }



    //将富文本内容用txt文件保存
    public static String createTXTFile(HttpServletRequest request, String article) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String res = sdf.format(new Date());
        String filePath = request.getSession().getServletContext().getRealPath("/resource/upload/txt/");
        File dir = new File(filePath);
        // 一、检查放置文件的文件夹路径是否存在，不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();// mkdirs创建多级目录
        }
        File checkFile = new File(filePath + res+".txt");
        FileWriter writer = null;
        try {
            // 二、检查目标文件是否存在，不存在则创建
            if (!checkFile.exists()) {
                checkFile.createNewFile();// 创建目标文件
            }
            // 三、向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
            writer = new FileWriter(checkFile, false);
            writer.append(article);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer)
                writer.close();
        }
        return filePath+res+".txt";
    }

    public static String  uploadFile1(HttpServletRequest request, MultipartFile uploadFile, String path1, String path2 ,Integer user_id) throws IOException {
        String res = user_id + "";
        //获取文件的扩展名
        String ext = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
        //设置图片上传路径
        String url = request.getSession().getServletContext().getRealPath(path1);
        System.out.println(url);
        File folder = new File(url+res + "." + ext);
        if(!folder.exists()) {
            System.out.println("文件不存在");
        } else {
            folder.delete();
            System.out.println("删除文件");
        }
        //以绝对路径保存重名命后的图片
        uploadFile.transferTo(new File(url+res + "." + ext));
        //把图片存储路径保存到数据库
        return path2+res + "." + ext;
    }

    public static String  uploadLoginImg(HttpServletRequest request, MultipartFile uploadFile, String path1, String path2) throws IOException {
        String res = generateRandomFilename();
        //获取文件的扩展名
        String ext = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
        //设置图片上传路径
        String url = request.getSession().getServletContext().getRealPath(path1);
        System.out.println(url);
        //以绝对路径保存重名命后的图片
        uploadFile.transferTo(new File(url+res + "." + ext));
        //把图片存储路径保存到数据库
        return path2+res + "." + ext;
    }

    public static String readToString(String fileName) {
        fileName = fileName.replaceAll("\\\\","/");
        System.out.println(fileName);
        String txt = null;
       // String fileName = "D:/WonderingNotes/resource/txt/20190210110610236.txt";
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
            txt = new String(filecontent, encoding);
            System.out.println(txt);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
        }
        return txt;
    }

    public static String generateRandomFilename(){
        String RandomFilename = "";
        Random rand = new Random();//生成随机数
        int random = rand.nextInt();
        RandomFilename =  String.valueOf(random > 0 ? random : ( -1) * random) ;
        return RandomFilename;
    }

}

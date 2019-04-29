package com.wondering.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Comment {

    private Integer Auser_id;

    private String Anickname;

    private String title;

    private Integer article_id;

    private String Acontent;

    private String Bcontent;

    private String Buser_id;

    private String Bnickname;

    private String Bavatar;

    private String Bsex;

    private Integer comment_id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date create_time;


    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getAcontent() {
        return Acontent;
    }

    public void setAcontent(String acontent) {
        Acontent = acontent;
    }

    public String getBcontent() {
        return Bcontent;
    }

    public void setBcontent(String bcontent) {
        Bcontent = bcontent;
    }

    public Integer getAuser_id() {
        return Auser_id;
    }

    public void setAuser_id(Integer auser_id) {
        Auser_id = auser_id;
    }

    public String getAnickname() {
        return Anickname;
    }

    public void setAnickname(String anickname) {
        Anickname = anickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }


    public String getBuser_id() {
        return Buser_id;
    }

    public void setBuser_id(String buser_id) {
        Buser_id = buser_id;
    }

    public String getBnickname() {
        return Bnickname;
    }

    public void setBnickname(String bnickname) {
        Bnickname = bnickname;
    }

    public String getBavatar() {
        return Bavatar;
    }

    public void setBavatar(String bavatar) {
        Bavatar = bavatar;
    }

    public String getBsex() {
        return Bsex;
    }

    public void setBsex(String bsex) {
        Bsex = bsex;
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }
}

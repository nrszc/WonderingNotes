package com.wondering.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Favor {

    private Integer Auser_id;

    private String Anickname;

    private String title;

    private String content;

    private Integer article_id;

    private Integer comment_id;

    private Integer Buser_id;

    private String Bavatar;

    private String Bsex;

    private String Bnickname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date create_time;

    public String getAnickname() {
        return Anickname;
    }

    public void setAnickname(String anickname) {
        Anickname = anickname;
    }

    public Integer getAuser_id() {
        return Auser_id;
    }

    public void setAuser_id(Integer auser_id) {
        Auser_id = auser_id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    public Integer getBuser_id() {
        return Buser_id;
    }

    public void setBuser_id(Integer buser_id) {
        Buser_id = buser_id;
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

    public String getBnickname() {
        return Bnickname;
    }

    public void setBnickname(String bnickname) {
        Bnickname = bnickname;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}

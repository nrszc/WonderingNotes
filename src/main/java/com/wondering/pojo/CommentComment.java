package com.wondering.pojo;

import java.util.Date;

public class CommentComment {
    private Integer id;

    private Integer user_id;

    private String content;

    private Date create_time;

    private Byte status;

    private Integer comment_id;

    private Byte depth;

    private Integer reply_id;

    private Byte isread;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    public Byte getDepth() {
        return depth;
    }

    public void setDepth(Byte depth) {
        this.depth = depth;
    }

    public Integer getReply_id() {
        return reply_id;
    }

    public void setReply_id(Integer reply_id) {
        this.reply_id = reply_id;
    }

    public Byte getIsread() {
        return isread;
    }

    public void setIsread(Byte isread) {
        this.isread = isread;
    }
}
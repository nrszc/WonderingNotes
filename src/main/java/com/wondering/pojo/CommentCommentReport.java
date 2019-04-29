package com.wondering.pojo;

import java.util.Date;

public class CommentCommentReport {
    private Integer id;

    private Integer cc_id;

    private Date create_time;

    private Date check_time;

    private Integer user_id;

    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCc_id() {
        return cc_id;
    }

    public void setCc_id(Integer cc_id) {
        this.cc_id = cc_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getCheck_time() {
        return check_time;
    }

    public void setCheck_time(Date check_time) {
        this.check_time = check_time;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
package com.wondering.pojo;

import java.util.Date;

public class UserFans {
    private Integer id;

    private Date create_time;

    private Integer follow_who;

    private Integer who_follow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getFollow_who() {
        return follow_who;
    }

    public void setFollow_who(Integer follow_who) {
        this.follow_who = follow_who;
    }

    public Integer getWho_follow() {
        return who_follow;
    }

    public void setWho_follow(Integer who_follow) {
        this.who_follow = who_follow;
    }
}
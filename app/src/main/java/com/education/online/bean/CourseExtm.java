package com.education.online.bean;

import java.io.Serializable;

/**
 * Created by Great Gao on 2016/10/24.
 */
public class CourseExtm implements Serializable{
    public CourseExtm() {
    }
    private String courseware_date = "";
    private String time_len = "";
    private String url = "";
    private String state = "";
    private String name ="";
    private String group_number="";
    private String id="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup_number() {
        return group_number;
    }

    public void setGroup_number(String group_number) {
        this.group_number = group_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseware_date() {
        return courseware_date;
    }

    public void setCourseware_date(String courseware_date) {
        this.courseware_date = courseware_date;
    }

    public String getTime_len() {
        return time_len;
    }

    public void setTime_len(String time_len) {
        this.time_len = time_len;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

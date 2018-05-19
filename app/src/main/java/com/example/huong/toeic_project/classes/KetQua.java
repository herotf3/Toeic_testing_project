package com.example.huong.toeic_project.classes;

import java.sql.Date;

/**
 * Created by Huong on 7/4/2016.
 */
public class KetQua {
    public static String KQ_Tablename="KETQUA";
    public static String KQ_Score="SOCRE";
    public static String KQ_time="TIME";
    public static String KQ_name="NAME";
    public static String KQ_id="ID";
    //----
    private int id;
    private String name;
    private int score;
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

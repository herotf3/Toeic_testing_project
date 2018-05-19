package com.example.huong.toeic_project.classes;

import android.graphics.Bitmap;

/**
 * Created by Huong on 6/26/2016.
 */
public class PartList {
    private String Stt;
    private String namePart;
    private Bitmap pic;

    public String getStt() {
        return Stt;
    }

    public void setStt(String stt) {
        Stt = stt;
    }

    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }
}

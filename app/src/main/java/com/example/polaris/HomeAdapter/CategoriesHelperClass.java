package com.example.polaris.HomeAdapter;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class CategoriesHelperClass {
    //Variables
    Drawable gradient;
    int picture;
    String desc;

    public CategoriesHelperClass(Drawable gradient, int picture, String desc) {
        this.gradient = gradient;
        this.picture = picture;
        this.desc = desc;
    }

    public Drawable getGradient() {
        return gradient;
    }

    public int getPicture() {
        return picture;
    }

    public String getDesc() {
        return desc;
    }
}

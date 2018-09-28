package com.soren.sagen.driemsfirebase;

import android.support.annotation.VisibleForTesting;

/**
 * Created by saGen on 21-04-2018.
 */

public class Details_card {
    public String title;
    public String desc;

    public Details_card(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public Details_card() {
    }
}

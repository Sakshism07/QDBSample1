package com.android.qdbsample;

/**
 * Created by Android on 04-Nov-17.
 */

public class GetSet {
    String name,ingurl;

    public GetSet(String name, String ingurl) {
        this.name = name;
        this.ingurl = ingurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngurl() {
        return ingurl;
    }

    public void setIngurl(String ingurl) {
        this.ingurl = ingurl;
    }
}

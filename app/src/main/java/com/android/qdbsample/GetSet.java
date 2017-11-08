package com.android.qdbsample;

/**
 * Created by Android on 04-Nov-17.
 */

public class GetSet {
    String name, ingurl;
    String industryid;

    public GetSet(String name, String ingurl,String industryid) {
        this.name = name;
        this.ingurl = ingurl;
        this.industryid = industryid;
    }

    public String getIndustryid() {
        return industryid;
    }

    public void setIndustryid(String industryid) {
        this.industryid = industryid;
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

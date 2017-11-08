package com.android.qdbsample;

/**
 * Created by Sakshi on 07-11-2017.
 */

public class ModelFilterList {

    String name, industryid;
    Boolean isChecked;


    public ModelFilterList(String name, Boolean isChecked, String industryid) {
        this.name = name;
        this.isChecked = isChecked;
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

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}

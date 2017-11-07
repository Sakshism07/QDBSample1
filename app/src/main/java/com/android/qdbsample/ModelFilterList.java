package com.android.qdbsample;

/**
 * Created by Sakshi on 07-11-2017.
 */

public class ModelFilterList {

    String name;
    Boolean isChecked;

    public ModelFilterList(String name, Boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
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

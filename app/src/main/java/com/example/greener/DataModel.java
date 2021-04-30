package com.example.greener;

public class DataModel
{
    String name, des, loc;

    public DataModel(String name, String des, String loc) {
        this.name = name;
        this.des = des;
        this.loc = loc;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}

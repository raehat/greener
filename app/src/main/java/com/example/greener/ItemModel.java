package com.example.greener;

public class ItemModel {
    String image;
    String heading, desc, city;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ItemModel(String heading, String desc, String image,String city) {
        this.image = image;
        this.heading = heading;
        this.desc = desc;
        this.city= city;
    }
}

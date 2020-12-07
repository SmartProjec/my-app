package com.example.locationimage;

public class DataModel {
    String Image;
    String Address;
    double lag;
    double lng;

    public DataModel(String image, String address, double lag, double lng) {
        Image = image;
        Address = address;
        this.lag = lag;
        this.lng = lng;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLag() {
        return lag;
    }

    public void setLag(double lag) {
        this.lag = lag;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}

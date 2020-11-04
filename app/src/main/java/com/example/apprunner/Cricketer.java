package com.example.apprunner;

import java.io.Serializable;

public class Cricketer implements Serializable {

    public String Price;
    public String Distance;

    public Cricketer() {
    }

    public Cricketer(String price, String distance) {
        Price = price;
        Distance = distance;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }
}

package com.example.leo.ramu;

/**
 * Created by leo on 27/07/17.
 */

public class Visiters {
    public Visiters(){}
    public String name,carNo,address,phoneNo,inTime,OutTime,duration;
    long inMilis;

    public Visiters(String name,
                     String carNo,
                     String address,
                     String phoneNo,
                     String inTime
    ) {
        this.name = name;
        this.carNo = carNo;
        this.address = address;
        this.phoneNo = phoneNo;
        this.inTime = inTime;

    }
}

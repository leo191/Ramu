package com.example.leo.ramu;

/**
 * Created by leo on 26/07/17.
 */

public class Residents {

    public Residents(){}
    public String name,id,address,phoneNo,inTime,OutTime,duration;
    long inMilis,outmilis;

    public Residents(String name,
                     String id,
                     String address,
                     String phoneNo,
                     String inTime
                 ) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.phoneNo = phoneNo;
        this.inTime = inTime;

    }
}

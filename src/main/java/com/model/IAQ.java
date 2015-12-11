package com.model;

import java.util.Date;

public class IAQ {
	private int id;
	private float value;
	private Date time;
	
public IAQ(){}
    
    public IAQ(int id, float value, Date time){
        this.id = id;
        this.value = value;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public float getValue() {
        return value;
    }

    public Date getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "IAQ{" + "idIAQ=" + id + ", value=" + value + ", time=" + time + '}';
    }
	
	

}

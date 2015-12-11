package com.model;

public class Fan {
	private int id;
	private boolean on;
	private float frequency;
	private float duration;
	
	public Fan(){}
	
	public Fan(int id){
		this.id = id;
	}
	
	public Fan(int id, boolean on, float frequency, float duration) {
		super();
		this.id = id;
		this.on = on;
		this.frequency = frequency;
		this.duration = duration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public float getFrequency() {
		return frequency;
	}

	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Fan [id=" + id + ", on=" + on + ", frequency=" + frequency + ", duration=" + duration + "]";
	}
	
	

}

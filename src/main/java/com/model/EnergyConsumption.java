package com.model;

public class EnergyConsumption {
	private float consumption;

	public EnergyConsumption(float consumption) {
		super();
		this.consumption = consumption;
	}

	public float getConsumption() {
		return consumption;
	}

	public void setConsumption(float consumption) {
		this.consumption = consumption;
	}

	@Override
	public String toString() {
		return "EnergyConsumption [consumption=" + consumption + "]";
	}
	
	

}

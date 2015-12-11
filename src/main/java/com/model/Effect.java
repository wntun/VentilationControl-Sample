package com.model;

public class Effect {
	private float improved;

	public Effect(float improved) {
		super();
		this.improved = improved;
	}

	public float getImproved() {
		return improved;
	}

	public void setImproved(float improved) {
		this.improved = improved;
	}

	@Override
	public String toString() {
		return "Effect [improved=" + improved + "]";
	}
	
	
	
}

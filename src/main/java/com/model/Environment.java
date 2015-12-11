package com.model;

import java.util.Date;

import com.sample.VentilationControl;

public class Environment {
	private IAQ iaq;
	private Fan fan;
	private Effect effect;
	private EnergyConsumption energyConsumption;
    private final float threshold = 150;
    
    public Environment(){}
    
    public Environment(IAQ iaq, Effect effect, Fan fan, EnergyConsumption energyConsumption){
        this.iaq = iaq;
        this.effect = effect;
        this.fan = fan;
        this.energyConsumption = energyConsumption;
    }

    public IAQ getIaq() {
        return iaq;
    }

    public Effect getEffect() {
        return effect;
    }

    public Fan getFan() {
        return fan;
    }

    public EnergyConsumption getEnergyConsumption() {
        return energyConsumption;
    }

    public void setIaq(IAQ iaq) {
        this.iaq = iaq;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public void setFan(Fan fan) {
        this.fan = fan;
    }

    public void setEnergyConsumption(EnergyConsumption energyConsumption) {
        this.energyConsumption = energyConsumption;
    }
    
    /*
     * re-instantiate fan object 
     */
    public void turnOffFan(){
        fan = new Fan(fan.getId());
        fan.setOn(false);
        energyConsumption.setConsumption(0);
        VentilationControl.insertFact(this);
        VentilationControl.counter = 0;
    }
    
    /*
     * sleep for 1s before firing any rule
     */    
    public void sleep(){
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    /*
     * reduce frequency half,duration
     * reset energy consumption
     */
    public void reduceEnergyConsumption(){
        float frequency = getDeltaFrequency()/2;
        fan.setFrequency(frequency);
        
        float duration = getDuration();
        fan.setDuration(duration);
        
        energyConsumption.setConsumption(getEnergyConsumptionValue()/2); 
        VentilationControl.counter = 0;
        VentilationControl.insertFact(this);
        updateOverTime();
        
    }
    
    /*
     * set frequency, duration 
     * calculate energy consumption
     */    
    public void calculateFanFacts(){
        float frequency = getDeltaFrequency();
        fan.setFrequency(frequency);
        float duration = getDuration();       
        fan.setDuration(duration);
        
        //assume
        energyConsumption.setConsumption(frequency/2); 
        VentilationControl.counter = 0;
        VentilationControl.insertFact(this);
        updateOverTime();
    }
    
    /*
     * reduce IAQ, duration 
     * increase energy consumption
     */
    public void updateOverTime(){
        float newIaq = getNewIAQ();
        iaq.setValue(newIaq);
        iaq.setTime(new Date(iaq.getTime().getTime()+1000*60));
        fan.setDuration(fan.getDuration()-1);
        energyConsumption.setConsumption(getEnergyConsumptionValue());
        VentilationControl.insertFact(this);
    }
    
    /*
     *  energy consumption = energy consumption + frequency * 1% (Assumption)
     */
    private float getEnergyConsumptionValue(){
        float consumption = energyConsumption.getConsumption() + fan.getFrequency()*0.01f;
        return consumption;
    }
    
    /*
     * get delta_frequency
     */
    private float getDeltaFrequency(){
        return iaq.getValue()-threshold;
    }
    
    /*
     * duration = delta_frequency/effect (Assumption)
     */
    private float getDuration(){
        float frequency = getDeltaFrequency();
        return frequency/effect.getImproved();
    }
    
    /*
     * calculate improved iaq
     */
    private float getNewIAQ(){
        return iaq.getValue()-effect.getImproved();
    }
    
    /*
     * display for all instances
     */
    public void display(){
        System.out.println(fan.toString());
        System.out.println(effect.toString());
        System.out.println(energyConsumption.toString());
        System.out.println(iaq.toString());
        System.out.println();
    }

}

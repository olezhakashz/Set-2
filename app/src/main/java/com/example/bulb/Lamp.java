package com.example.bulb;

// Name: [Your Name] | Student ID: [Your ID] | Lab: 1
public class Lamp {

    private boolean isOn;
    private int intensity;
    private Bulb bulb;

    public Lamp() {
        this.isOn = false;
        this.intensity = 0;
        this.bulb = new Bulb();
    }

    public void turnOn() {
        if (bulb.isBurned()) return;
        isOn = true;
        if (intensity == 0) intensity = 1;
        bulb.turnOn();
    }

    public void turnOff() {
        isOn = false;
        intensity = 0;
        bulb.turnOff();
    }

    public void brighten() {
        if (!isOn) return;
        if (intensity >= 10) {
            bulb.burn();
            isOn = false;
            intensity = 0;
        } else {
            intensity++;
        }
    }

    public void dim() {
        if (!isOn) return;
        intensity--;
        if (intensity == 0) {
            isOn = false;
            bulb.turnOff();
        }
    }

    public boolean replaceBulb() {
        if (isOn) return false;
        bulb = new Bulb();
        return true;
    }

    public boolean isOn()         { return isOn; }
    public boolean isShining()    { return isOn && intensity > 0 && bulb.isOn(); }
    public boolean isBulbBurned() { return bulb.isBurned(); }
    public int getIntensity()     { return intensity; }
}
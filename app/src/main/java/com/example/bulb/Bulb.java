package com.example.bulb;

// Name: [Your Name] | Student ID: [Your ID] | Lab: 1
public class Bulb {

    private boolean isOn;
    private boolean isBurned;

    public Bulb() {
        this.isOn = false;
        this.isBurned = false;
    }

    public void turnOn() {
        if (isBurned) return;
        isOn = true;
    }

    public void turnOff() {
        isOn = false;
    }

    public boolean isOn() { return isOn; }
    public boolean isBurned() { return isBurned; }

    void burn() {
        isBurned = true;
        isOn = false;
    }
}
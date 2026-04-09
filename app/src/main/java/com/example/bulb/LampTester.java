package com.example.bulb;

import java.util.ArrayList;
import java.util.List;

// Name: [Your Name] | Student ID: [Your ID] | Lab: 1
public class LampTester {

    public static class TestResult {
        public final String name;
        public final boolean passed;
        public final String details;

        public TestResult(String name, boolean passed, String details) {
            this.name = name;
            this.passed = passed;
            this.details = details;
        }
    }

    public List<TestResult> runAll() {
        List<TestResult> results = new ArrayList<>();
        results.add(test1_TurnOnAndOff());
        results.add(test2_BrightenTo10());
        results.add(test3_BrightenAbove10_BulbBurns());
        results.add(test4_DimTo0_LampTurnsOff());
        results.add(test5_ReplaceBulb_WhenOff());
        results.add(test6_ReplaceBulb_WhenOn_Fails());
        results.add(test7_TurnOn_WithBurnedBulb_NoLight());
        return results;
    }

    private TestResult test1_TurnOnAndOff() {
        Lamp lamp = new Lamp();
        lamp.turnOn();
        boolean onOk = lamp.isOn() && lamp.isShining() && lamp.getIntensity() == 1;
        lamp.turnOff();
        boolean offOk = !lamp.isOn() && !lamp.isShining() && lamp.getIntensity() == 0;
        boolean passed = onOk && offOk;
        return new TestResult("Test 1: Turn on and off", passed,
                passed ? "ON→intensity=1,shining=true | OFF→intensity=0,shining=false"
                        : "FAILED onOk=" + onOk + " offOk=" + offOk);
    }

    private TestResult test2_BrightenTo10() {
        Lamp lamp = new Lamp();
        lamp.turnOn();
        for (int i = 0; i < 9; i++) lamp.brighten();
        boolean passed = lamp.isOn() && lamp.getIntensity() == 10
                && lamp.isShining() && !lamp.isBulbBurned();
        return new TestResult("Test 2: Brighten to 10", passed,
                passed ? "intensity=10, on, not burned"
                        : "FAILED intensity=" + lamp.getIntensity() + " burned=" + lamp.isBulbBurned());
    }

    private TestResult test3_BrightenAbove10_BulbBurns() {
        Lamp lamp = new Lamp();
        lamp.turnOn();
        for (int i = 0; i < 9; i++) lamp.brighten();
        lamp.brighten();
        boolean passed = !lamp.isOn() && lamp.getIntensity() == 0
                && lamp.isBulbBurned() && !lamp.isShining();
        return new TestResult("Test 3: Brighten above 10 → bulb burns", passed,
                passed ? "Bulb burned, lamp off, intensity=0"
                        : "FAILED isOn=" + lamp.isOn() + " intensity=" + lamp.getIntensity());
    }

    private TestResult test4_DimTo0_LampTurnsOff() {
        Lamp lamp = new Lamp();
        lamp.turnOn();
        lamp.dim();
        boolean passed = !lamp.isOn() && lamp.getIntensity() == 0 && !lamp.isShining();
        return new TestResult("Test 4: Dim to 0 → lamp off", passed,
                passed ? "Dimmed to 0, lamp turned off automatically"
                        : "FAILED isOn=" + lamp.isOn() + " intensity=" + lamp.getIntensity());
    }

    private TestResult test5_ReplaceBulb_WhenOff() {
        Lamp lamp = new Lamp();
        lamp.turnOn();
        for (int i = 0; i < 10; i++) lamp.brighten();
        boolean replaced = lamp.replaceBulb();
        boolean passed = replaced && !lamp.isBulbBurned();
        return new TestResult("Test 5: Replace bulb while off → success", passed,
                passed ? "replaceBulb()=true, new bulb not burned"
                        : "FAILED replaced=" + replaced + " burned=" + lamp.isBulbBurned());
    }

    private TestResult test6_ReplaceBulb_WhenOn_Fails() {
        Lamp lamp = new Lamp();
        lamp.turnOn();
        boolean replaced = lamp.replaceBulb();
        boolean passed = !replaced && lamp.isOn();
        return new TestResult("Test 6: Replace bulb while on → fails", passed,
                passed ? "replaceBulb()=false, lamp still on"
                        : "FAILED replaced=" + replaced + " isOn=" + lamp.isOn());
    }

    private TestResult test7_TurnOn_WithBurnedBulb_NoLight() {
        Lamp lamp = new Lamp();
        lamp.turnOn();
        for (int i = 0; i < 10; i++) lamp.brighten();
        lamp.turnOn();
        boolean passed = !lamp.isOn() && !lamp.isShining() && lamp.getIntensity() == 0;
        return new TestResult("Test 7: Burned bulb → no light on turnOn()", passed,
                passed ? "Lamp stayed off with burned bulb"
                        : "FAILED isOn=" + lamp.isOn() + " shining=" + lamp.isShining());
    }
}
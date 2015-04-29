package com.teamf_bw.ist402.tiltnroll;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.Surface;

/**
 * A class for constructing a "Physics" object, which calculates and manages such data
 * for the game.
 *
 * A better solution would be more modular, but this current one heavily relies on the accelerometer
 * for input
 */
public class Physics {

    private double mass = 1000;
    private double gX = (-Accelerometer.sensorValueX * mass); //force of gravity, X direction
    private double gY = (-Accelerometer.sensorValueY * mass); //force of gravity, Y direction
    private double aX = gX/mass; //acceleration, x direction. F = ma -> A = F/m
    private double aY = gY/mass; //acceleration, y direction.



    public Physics() {

    }

    public void calculate() {

    }

    /*


     */


}

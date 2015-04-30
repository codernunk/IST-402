package com.teamf_bw.ist402.tiltnroll;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Surface;

/**
 * Class that constructs an object that encapsulates accelerometer workings
 */

public class Accelerometer implements SensorEventListener {

    private Sensor mAccelerometer;
    private SensorManager mSensorManager;

    public static double sensorValueX; //perhaps bad practice, but these values are needed outside of object instantiation
    public static double sensorValueY;

    private long mSensorTimeStamp; //maybe necessary for rendering
    private long mCpuTimeStamp;

    public Accelerometer() {

    }

    public Accelerometer(Context context) {
        //registers the sensor, essentially
        mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        start();
    }

    public void start() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;

        //takes into account current rotation so that accurate data is always retrieved
        switch (GameActivity.mDisplay.getRotation()) {
            case Surface.ROTATION_0:
                sensorValueX = event.values[0];
                sensorValueY = event.values[1];
                break;
            case Surface.ROTATION_90:
                sensorValueX = -event.values[1];
                sensorValueY = event.values[0];
                break;
            case Surface.ROTATION_180:
                sensorValueX = -event.values[0];
                sensorValueY = -event.values[1];
                break;
            case Surface.ROTATION_270:
                sensorValueX = event.values[1];
                sensorValueY = -event.values[0];
                break;
        }

        //alternate rendering option (probably not needed)
        mSensorTimeStamp = event.timestamp;
        mCpuTimeStamp = System.nanoTime();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //not entirely necessary to implement
    }

    /*
    public double getSensorValueX() {
        return sensorValueX;
    }

    public double getSensorValueY() {
        return sensorValueY;
    } */
}

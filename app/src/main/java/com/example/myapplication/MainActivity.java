package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ImageView image;
    private DisplayMetrics displayMetrics = new DisplayMetrics(); // taille de l'écran

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        image = findViewById(R.id.image);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);// récupère la taille de l'écran
    }

    @Override
    protected void onResume() {// quand on revient sur l'application
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {// quand on quitte l'application
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {// quand on bouge l'appareil
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float ivX = image.getX() - x * 5;
            float ivY = image.getY() + y * 5;
            if (ivX < 0) {
                ivX = 0;
            } else if (ivX > displayMetrics.widthPixels - image.getWidth()) {
                ivX = displayMetrics.widthPixels - image.getWidth();
            }
            if (ivY < 0) {
                ivY = 0;
            } else if (ivY > displayMetrics.heightPixels - image.getHeight()) {
                ivY = displayMetrics.heightPixels - image.getHeight();
            }
            image.setX(ivX);
            image.setY(ivY);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { //obligatoire mais inutile ici
    }

    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

}
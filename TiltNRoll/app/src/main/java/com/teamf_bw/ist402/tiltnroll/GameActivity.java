
        package com.teamf_bw.ist402.tiltnroll;

        import android.hardware.Sensor;
        import android.hardware.SensorManager;
        import android.os.PowerManager;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.view.Display;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.WindowManager;
        import android.os.PowerManager.WakeLock;


public class GameActivity extends ActionBarActivity {

    public static Display mDisplay; //stores display data for other classes to access

    private GameSurfaceView gameView;
    private WindowManager mWindowManager; //necessary to retrieve device window information
    private Accelerometer mAccelerometer;

    private WakeLock mWakeLock;
    private PowerManager mPowerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);

        //necessary for accelerometer workings
        mAccelerometer = new Accelerometer(this);


        //attempt to get the screen to remain bright.
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass()
                .getName());




        //initializing variables that enable one to get display workings
        mWindowManager =(WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();

        gameView = new GameSurfaceView(this);
        setContentView(gameView);
    }

    /* code to resume and pause accelerometer readings (battery efficiency)
    @Override
    protected void onResume() {
        mAccelerometer.start();
    }

    @Override
    protected void onPause() {
        mAccelerometer.stop();
    } */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            gameView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}

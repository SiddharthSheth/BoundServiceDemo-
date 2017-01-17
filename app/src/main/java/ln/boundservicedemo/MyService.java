package ln.boundservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by comp-1 on 17/1/17.
 */

public class MyService extends Service{

    long starttime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedtime = 0L;
    int t = 1;
    public static int secs = 0;
    public static int mins = 0;
    int milliseconds = 0;
    private Handler handler = new Handler();

    private final IBinder mBinder = new LocalService();

    @Override
    public void onCreate() {
        Log.d("sidd","onCreate");
        super.onCreate();
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.d("sidd","Binded");
        return mBinder;
    }

    public class LocalService extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("sidd","onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d("sidd","onDestroy");
        super.onDestroy();
        stop();
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d("sidd","onRebind");
        start();
        super.onRebind(intent);
    }

    public void start() {

        starttime = SystemClock.uptimeMillis();
        handler.postDelayed(updateTimer, 0);

    }

    public void stop() {

        timeSwapBuff += timeInMilliseconds;
        handler.removeCallbacks(updateTimer);

    }

    public void reset()
    {
        starttime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedtime = 0L;
        t = 1;
        secs = 0;
        mins = 0;
        milliseconds = 0;
        handler.removeCallbacks(updateTimer);
        MainActivity.txtTimer.setText("00 : 00");
    }

    public Runnable updateTimer = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - starttime;
            updatedtime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedtime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedtime % 1000);

            MainActivity.txtTimer.setText(String.format("%02d", mins) + " : " + String.format("%02d", secs));
            handler.postDelayed(this, 0);
        }};
}

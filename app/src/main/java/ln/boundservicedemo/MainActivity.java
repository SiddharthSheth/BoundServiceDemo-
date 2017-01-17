package ln.boundservicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MyService myService;
    boolean isBind = false;

    public static TextView txtTimer;
    private Button btnStart ,btnStop ,btnResume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByID();


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnStart.getText().toString().equals("Start"))
                {
                    myService.start();
                    btnStart.setText("Reset");
                }else if (btnStart.getText().toString().equals("Reset"))
                {
                    myService.reset();
                    btnStart.setText("Start");
                }

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myService.stop();
            }
        });

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myService.start();
            }
        });

        Intent intent = new Intent(MainActivity.this,MyService.class);
        startService(intent);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);

    }


    public void findViewByID()
    {
        txtTimer = (TextView)findViewById(R.id.txt_timer);
        btnStart =(Button)findViewById(R.id.btn_start);
        btnStop = (Button)findViewById(R.id.btn_stop);
        btnResume = (Button)findViewById(R.id.btn_resume);
    }

    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalService localService = (MyService.LocalService) service;
            myService = localService.getService();
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if(isBind){
            unbindService(serviceConnection);
            isBind = false;
        }
    }
}

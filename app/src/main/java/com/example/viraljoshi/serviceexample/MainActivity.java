package com.example.viraljoshi.serviceexample;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.viraljoshi.serviceexample.service.ForeGroundService;
import com.example.viraljoshi.serviceexample.utils.IConstants;

public class MainActivity extends AppCompatActivity {
    TextView txtForegroundservice;
    Button btnStartForegoundService, btnStopForegroundService;
    private IntentFilter mIntentFilter;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            txtForegroundservice.setText("status=" + intent.getAction());


        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartForegoundService = (Button) findViewById(R.id.btn_start_foreground_service);
        btnStopForegroundService = (Button) findViewById(R.id.btn_stop_foreground_service);
        txtForegroundservice = (TextView) findViewById(R.id.txt_foreground_Service);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(IConstants.Actions.NEXT_ACTION);
        mIntentFilter.addAction(IConstants.Actions.PLAY_ACTION);
        mIntentFilter.addAction(IConstants.Actions.PREV_ACTION);
        btnStartForegoundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(MainActivity.this, ForeGroundService.class);
                startIntent.setAction(IConstants.Actions.STARTFOREGROUND_ACTION);
                startService(startIntent);
            }
        });
        btnStopForegroundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(MainActivity.this, ForeGroundService.class);
                stopIntent.setAction(IConstants.Actions.STOPFOREGROUND_ACTION);
                startService(stopIntent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver,mIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }
}

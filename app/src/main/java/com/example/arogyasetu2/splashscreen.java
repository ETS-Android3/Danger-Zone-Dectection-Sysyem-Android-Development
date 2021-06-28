package com.example.arogyasetu2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class splashscreen extends AppCompatActivity {

    int counter=0;
    ProgressBar pb;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorm));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);


        pb=findViewById(R.id.progressBar);
        pb.setProgressDrawable(getDrawable(R.drawable.cosbtn3));

        final Timer t=new Timer();
        TimerTask tt=new TimerTask() {
            @Override
            public void run() {
                counter=counter+2;

                pb.setProgress(counter);

                if(counter==50)
                {
                    Intent hh=new Intent(splashscreen.this,MainActivity.class);
                    startActivity(hh);
                }
                else
                {

                }

            }
        };
        t.schedule(tt,0,30);

    }
}

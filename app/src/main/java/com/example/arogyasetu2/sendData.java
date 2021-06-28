package com.example.arogyasetu2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class sendData extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    TextView tvname,tvloc,tvind;
    String phoneNo = "7802085577",message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);

        tvname = findViewById(R.id.textView22);
        tvname.setText(MapsActivity.name);
        tvloc = findViewById(R.id.textView23);
        tvloc.setText(MapsActivity.userloc);
        tvind = findViewById(R.id.textView24);

        Intent ck = getIntent();
        String cks = ck.getStringExtra("Ck");
        if (cks.equals("In Danger Zone")) {
            tvind.setText("In Danger Zone :- yes");
        }

        Button panicbutton=findViewById(R.id.button6);
        panicbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String no="7802085577";
                String msg = "User Named : "+tvname.getText().toString()+" is at Location :- "
                        +tvloc.getText().toString()+" and need emergency help ,So please reach this location " + "as fast as you can";

                //Getting intent and PendingIntent instance
                Intent intent=new Intent(getApplicationContext(),sendData.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(no, null, msg, pi,null);

                Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
        
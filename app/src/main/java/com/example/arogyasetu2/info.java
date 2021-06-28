package com.example.arogyasetu2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


public class info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    public void onBackPressed() {
        Intent intomain=new Intent(info.this,MainActivity.class);
        startActivity(intomain);
    }
}

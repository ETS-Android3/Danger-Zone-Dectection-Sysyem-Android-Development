package com.example.arogyasetu2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addlocation extends AppCompatActivity {

    EditText lat,lon,cn;
    Button insert;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlocation);

        lat=findViewById(R.id.latitude_input);
        lon=findViewById(R.id.longitude_input);
        insert=findViewById(R.id.button5);
        cn=findViewById(R.id.cityname);

        window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        final FirebaseDatabase db=FirebaseDatabase.getInstance();

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(cn.getText()!=null){
                   DatabaseReference node=db.getReference("loc").child(cn.getText().toString());
                   float f1 = Float.parseFloat(lat.getText().toString());
                   float f2 = Float.parseFloat(lon.getText().toString());
                   node.child("lat").setValue(f1);
                   node.child("lon").setValue(f2).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           Toast.makeText(addlocation.this,"Location Declared as Danger Zone",Toast.LENGTH_LONG).show();
                       }
                   });

               }
               else{
                   cn.setError("Enter city name");
               }
            }
        });
    }
}
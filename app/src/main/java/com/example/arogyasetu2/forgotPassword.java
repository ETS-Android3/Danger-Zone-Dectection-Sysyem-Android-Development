package com.example.arogyasetu2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    Button rec;
    EditText mail;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        rec=findViewById(R.id.button12);
        mail=findViewById(R.id.editText8);
        firebaseAuth=FirebaseAuth.getInstance();
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=new ProgressDialog(forgotPassword.this);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("please wait, Don't panic sometimes it take a while!!");

                String email1=mail.getText().toString();
               if(email1.isEmpty())
               {
                   Toast.makeText(forgotPassword.this, "Feilds are empty", Toast.LENGTH_SHORT).show();
                   mail.setError("Insert mail");
                   mail.requestFocus();
               }
               else
               {
                   progressDialog.show();
                   firebaseAuth.sendPasswordResetEmail(mail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful())
                           {
                               progressDialog.hide();
                               Toast.makeText(forgotPassword.this, "Password reset link sent", Toast.LENGTH_SHORT).show();
                           }
                           else
                           {
                               progressDialog.hide();
                               Toast.makeText(forgotPassword.this, "Account does not exist", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
            }
        });

    }
}

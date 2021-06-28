package com.example.arogyasetu2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    EditText emailid,passwd,cnfpwd,name;
    Button signupbtn,signin;
    ProgressDialog progressDialog1;
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailid=findViewById(R.id.editText3);
        passwd=findViewById(R.id.editText4);
        cnfpwd=findViewById(R.id.editText5);
        signupbtn=findViewById(R.id.button3);
        mAuth = FirebaseAuth.getInstance();
        signin=findViewById(R.id.signinBtn);
        name=findViewById(R.id.editTexName);
        fstore = FirebaseFirestore.getInstance();

        window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this,MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email1=emailid.getText().toString();
                String pwd1=passwd.getText().toString();
                String pwd2=cnfpwd.getText().toString();
                progressDialog1=new ProgressDialog(register.this);
                progressDialog1.setIcon(R.drawable.icon1);
                progressDialog1.setTitle("Loading");
                progressDialog1.setMessage("please wait, Don't panic sometimes it take a while!!");
                if(email1.isEmpty())
                {
                    emailid.setError("Email not entered");
                    emailid.requestFocus();
                }
                else if(pwd1.isEmpty() && pwd2.isEmpty())
                {
                    passwd.setError("password not entered");
                    passwd.requestFocus();
                }
                else if(!(pwd1.equals(pwd2)))
                {
                    cnfpwd.setError("password not conformed");
                    cnfpwd.requestFocus();
                }
                else if(email1.isEmpty() || pwd2.isEmpty())
                {
                    Toast.makeText(register.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else if(pwd1.length()<=8)
                {
                    Toast.makeText(register.this,"password should be more than 8 char",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog1.show();
                    mAuth.createUserWithEmailAndPassword(email1,pwd2).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog1.hide();
                                FirebaseUser user = mAuth.getCurrentUser();

                                DocumentReference df=fstore.collection("User").document(user.getUid());
                                Map<String,String> userInfo=new HashMap<>();
                                userInfo.put("Fullname",name.getText().toString());
                                userInfo.put("Email",email1);
                                userInfo.put("isUser","1");
                                df.set(userInfo);

                                progressDialog1.hide();
                                Intent intohome=new Intent(register.this, MapsActivity.class);
                                startActivity(intohome);
                            }
                            else {
                                progressDialog1.hide();
                                Toast.makeText(register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
//    }
}

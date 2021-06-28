package com.example.arogyasetu2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.NativeActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int R_SIGN_IN = 0;
    EditText emailid,passwd1;
    Button signbtn,emengercybtn,signup,fblgin,glogin;
    private LoginButton fblogin;
    ProgressDialog progressDialog;
    TextView fgpass;
    private FirebaseAuth mAuth;
    CallbackManager callbackManager;
    private View backgroundMain;
    SignInButton googleLogin;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore fstore;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        fblgin=findViewById(R.id.performclk);
        glogin=findViewById(R.id.performclk2);
        emailid=findViewById(R.id.editText);
        passwd1=findViewById(R.id.editText2);
        signbtn=findViewById(R.id.button2);
        signup=findViewById(R.id.button4);
        mAuth = FirebaseAuth.getInstance();
        fblogin=findViewById(R.id.login_button);
        emengercybtn=findViewById(R.id.button7);
        googleLogin=findViewById(R.id.google);

        fstore = FirebaseFirestore.getInstance();


        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);

        glogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        fblgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fblogin.performClick();
            }
        });
        fgpass=findViewById(R.id.textView11);
        fgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intofgpass=new Intent(MainActivity.this, forgotPassword.class);
                startActivity(intofgpass);
            }
        });

        emengercybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoem=new Intent(MainActivity.this, emergency.class);
                startActivity(intoem);
            }
        });

//        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scaleandinvisible);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,register.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

            }
        });



        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=emailid.getText().toString();
                String pwd=passwd1.getText().toString();

                progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setIcon(R.drawable.icon1);
                progressDialog.setProgressStyle(R.drawable.icon1);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("please wait, Don't panic sometimes it take a while!!");

                if(email.isEmpty())
                {
                    emailid.setError("Enter your email");
                    emailid.requestFocus();
                }
                else if(pwd.isEmpty())
                {
                    passwd1.setError("Enter your password");
                    passwd1.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email,pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                                progressDialog.hide();
                                checkUserAdminlevel(authResult.getUser().getUid());
//                                Intent intomap=new Intent(MainActivity.this, Navigation.class);
//                                getIntent().putExtra("userName",user.getEmail());
//                                startActivity(intomap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                                progressDialog.hide();
                                Toast.makeText(MainActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        callbackManager=CallbackManager.Factory.create();
        fblogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();
                Intent intomap2=new Intent(MainActivity.this, Navigation.class);
                Bundle extras1 = new Bundle();
                extras1.putString("Username",null);
                extras1.putString("url",null);
                extras1.putString("Level","nUser");
                intomap2.putExtras(extras1);
                startActivity(intomap2);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }




    private void checkUserAdminlevel(String UID){
        DocumentReference df=fstore.collection("User").document(UID);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.getString("isAdmin")!=null){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent nav=new Intent(getApplicationContext(),Navigation.class);
                    Bundle extras = new Bundle();
                    extras.putString("Username",user.getEmail());
                    extras.putString("url",null);
                    extras.putString("Level","Admin");
                    nav.putExtras(extras);
                    startActivity(nav);
                }
                if (documentSnapshot.getString("isUser")!=null){
                    Intent nav1=new Intent(getApplicationContext(),Navigation.class);
                    FirebaseUser user = mAuth.getCurrentUser();
                    Bundle extras = new Bundle();
                    extras.putString("Username",user.getEmail());
                    extras.putString("url",null);
                    extras.putString("Level","nUser");
                    nav1.putExtras(extras);
                    startActivity(nav1);
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,R_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==R_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account=completedTask.getResult(ApiException.class);
            String url= Objects.requireNonNull(account.getPhotoUrl()).toString();
            Intent intent=new Intent(MainActivity.this, Navigation.class);
            Bundle extras = new Bundle();
            extras.putString("Username",account.getEmail());
            extras.putString("url",url);
            extras.putString("Level","nUser");
            intent.putExtras(extras);
            startActivity(intent);

        }catch (ApiException e){
            Toast.makeText(this,"failed",Toast.LENGTH_LONG).show();
        }
    }

//    public void onDestroy() {
//        LoginManager.getInstance().logOut();
//        super.onDestroy();
//        finishAffinity();
//    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
           checkUserAdminlevel(currentUser.getUid());

        }
    }
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_out,R.anim.slide_in);
//    }
}
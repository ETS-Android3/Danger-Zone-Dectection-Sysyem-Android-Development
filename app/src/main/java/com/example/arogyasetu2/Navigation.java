package com.example.arogyasetu2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Navigation extends AppCompatActivity{

    Button bt1,bt2,bt3,bt4,pay;
    ImageButton ibm1;
    ImageView profileImage;
    FloatingActionButton ft,add_loc;
    String url;
    Window window;
    NavigationView nav;
    DrawerLayout mDrawerLayout;
    Toolbar toolbar;

    FirebaseRemoteConfig remoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String username_string,level_string;
        username_string = extras.getString("Username");
        level_string = extras.getString("Level");
        url = extras.getString("url");
        setContentView(R.layout.activity_navegation);

        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorm));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        toolbar=findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        nav=findViewById(R.id.navview);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.game) {
                    try {
                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.game.ZombieeSmasher");
                        startActivity(launchIntent);
                    }catch (Exception e){
                        Toast.makeText(Navigation.this, "Game module not available", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(id==R.id.Change_Mode){
                    final AlertDialog.Builder ImageDialog = new AlertDialog.Builder(Navigation.this);
                    ImageDialog.setTitle("Module is under develop");
                    ImageView showImage = new ImageView(Navigation.this);
                    showImage.setImageResource(R.drawable.logoforscr);
                    showImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(Navigation.this, "Module not available", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ImageDialog.setView(showImage);

                    ImageDialog.setNegativeButton("ok", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface arg0, int arg1)
                        {

                        }
                    });
                    ImageDialog.show();
                }
                else if(id==R.id.ck_update){
                    remoteConfig = FirebaseRemoteConfig.getInstance();
                    FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                            .setMinimumFetchIntervalInSeconds(3600)
                            .build();
                    remoteConfig.setConfigSettingsAsync(configSettings);
                    remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
                    remoteConfig.fetchAndActivate().addOnCompleteListener(Navigation.this, new OnCompleteListener<Boolean>() {
                                @Override
                                public void onComplete(@NonNull Task<Boolean> task) {
                                    if (task.isSuccessful()) {
                                        boolean updated = task.getResult();
                                        String a=remoteConfig.getString("new_version_code");
                                        Log.d("TAG", "Config params updated: " + a);
                                        Toast.makeText(Navigation.this, "Fetch and activate succeeded",
                                                Toast.LENGTH_SHORT).show();
                                        showdialog("latest version is "+a);
                                    } else {
                                        Toast.makeText(Navigation.this, "Update not available", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ft=findViewById(R.id.floatingActionButton);
        ft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fed=new Intent(Navigation.this,feedbackPage.class);
                startActivity(fed);
            }
        });

        add_loc=findViewById(R.id.floatingActionButton5);
        add_loc.setVisibility(View.INVISIBLE);
        if(level_string.equals("Admin")) {
            add_loc.setVisibility(View.VISIBLE);
        }
        add_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fed1=new Intent(Navigation.this,addlocation.class);
                startActivity(fed1);
            }
        });


        profileImage = findViewById(R.id.userDetails);
        try{
            if(url != null) {
                Glide.with(this).load(url).into(profileImage);
            }
            else{
                int dimensionPixelSize = getResources().getDimensionPixelSize(com.facebook.R.dimen.com_facebook_profilepictureview_preset_size_large);
                Uri profilePictureUri = ImageRequest.getProfilePictureUri(Profile.getCurrentProfile().getId(), dimensionPixelSize , dimensionPixelSize );
                Glide.with(this).load(profilePictureUri).into(profileImage);
            }
        }catch (NullPointerException ignored){

        }

        bt1=findViewById(R.id.button13);
        bt2=findViewById(R.id.button8);
        bt3=findViewById(R.id.stastics);
        bt4=findViewById(R.id.button9);
        pay=findViewById(R.id.donation);
        ibm1 =findViewById(R.id.logout);
        TextView tv13,tv14,tv5;
        tv5=findViewById(R.id.textView5);

        tv13=findViewById(R.id.textView13);
        tv14=findViewById(R.id.textView14);
        tv13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt4.performClick();
            }
        });

        tv14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt4.performClick();
            }
        });
        ibm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                finishAffinity();
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intohome=new Intent(Navigation.this,ScrollingActivity.class);
                startActivity(intohome);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intotrack=new Intent(Navigation.this,MapsActivity.class);
                intotrack.putExtra("userName",username_string);
                startActivity(intotrack);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intostec=new Intent(Navigation.this,statistics.class);
                startActivity(intostec);
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intofeed=new Intent(Navigation.this, symptomChecker.class);
                startActivity(intofeed);
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Navigation.this,Donation.class);
                startActivity(in);
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            LoginManager.getInstance().logOut();
            finishAffinity();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to Exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void showdialog(final String appmessage){
        final AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle("Update")
                .setMessage(appmessage)
                .setPositiveButton("Update",null)
                .setNegativeButton("no Thanks",null)
                .show();
        Button pb=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        pb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("www.google.com")));
                }catch (android.content.ActivityNotFoundException anf){
                    Log.d("anf","anf");
                }
            }
        });
    }

    private int getVersionCode() {
        PackageInfo pinf=null;
        try{
            pinf=getPackageManager().getPackageInfo(getPackageName(),0);
        }catch (PackageManager.NameNotFoundException e){
            Log.i("updateerror","excaption update");
        }
        return pinf.versionCode;
    }

//    public void onStop() {
//        LoginManager.getInstance().logOut();
//        super.onStop();
//    }


}

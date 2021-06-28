package com.example.arogyasetu2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Donation extends AppCompatActivity implements PaymentResultListener {

    private View backgroundMain;
    Button donate;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        donate=findViewById(R.id.button14);

        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        backgroundMain = findViewById(R.id.donationpage);

        if (savedInstanceState == null) {
            backgroundMain.setVisibility(View.INVISIBLE);

            final ViewTreeObserver viewTreeObserver = backgroundMain.getViewTreeObserver();

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        backgroundMain.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                });
            }

        }

        String sAmount = "100";
        final int amount=Math.round(Float.parseFloat(sAmount)*100);

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String url = "https://rzp.io/l/2mXsriF";
//
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
                Checkout checkout=new Checkout();
                checkout.setKeyID("rzp_test_SDHu4dwbFDdtGW");
                checkout.setImage(R.drawable.logoround);
                JSONObject object=new JSONObject();
                try {
                    object.put("Name","Covid Fighters");
                    object.put("description","Donation");
                    object.put("theme.color","#FF267889");
                    object.put("currency","INR");
                    object.put("amount",amount);
                    object.put("prefill.contact","000000");
                    object.put("prefill.email","covid19fighters.gmail.com");
                    checkout.open(Donation.this,object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }


    private void circularRevealActivity() {
        int cx = backgroundMain.getRight() - getDips(44);
        int cy = backgroundMain.getBottom() - getDips(44);

        float finalRadius = Math.max(backgroundMain.getWidth(), backgroundMain.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                backgroundMain,
                -cx,
                cy,
                0,
                finalRadius);

        circularReveal.setDuration(500);
        backgroundMain.setVisibility(View.VISIBLE);
        circularReveal.start();

    }

    private int getDips(int dps) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        int cx = backgroundMain.getWidth() - getDips(44);
        int cy = backgroundMain.getBottom() - getDips(44);

        float finalRadius = Math.max(backgroundMain.getWidth(), backgroundMain.getHeight());
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(backgroundMain,-cx,cy, finalRadius, 0);

        circularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                backgroundMain.setVisibility(View.INVISIBLE);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        circularReveal.setDuration(1000);
        circularReveal.start();
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(Donation.this,"Payment successful",Toast.LENGTH_LONG).show();
        Toast.makeText(Donation.this,"your payment ID is :-"+" "+s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}
package com.example.arogyasetu2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class feedbackPage extends AppCompatActivity {

    EditText nameField,emailField,feedbackField,FeedbackBody;
    Spinner feedbackSpinner;
    Button btn;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackpage);
        String[] contains={"Content Related","Complains regarding Covid-19","Government Related","Help!! Related"};
        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.activity_feedbackpage,contains);

        nameField =  findViewById(R.id.EditTextName);
        String name = nameField.getText().toString();
        emailField =  findViewById(R.id.EditTextEmail);
        feedbackField = findViewById(R.id.EditTextFeedbackBody);
        feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackType);


        final CheckBox responseCheckbox = (CheckBox) findViewById(R.id.CheckBoxResponse);
        boolean bRequiresResponse = responseCheckbox.isChecked();

        FeedbackBody=findViewById(R.id.EditTextFeedbackBody);

        btn=findViewById(R.id.ButtonSendFeedback);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = "arogyasetu2@gmail.com";
                String subject = feedbackSpinner.getSelectedItem().toString();
                String message = feedbackField.getText().toString();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
                //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);
                //need this to prompts email client only
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    }
}

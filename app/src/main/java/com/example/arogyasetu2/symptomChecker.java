package com.example.arogyasetu2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class symptomChecker extends AppCompatActivity {

    QuestionLibrary mQuestionLibrary = new QuestionLibrary();
    TextView mScoreView,mQuestionView,scoredisplay;
    Button mButtonChoice1,mButtonChoice2,mButtonChoice3,mButtonChoice4,mButtonChoice5;
    String mAnswer;
    int mScore = 0;
    int mQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_checker);

        mScoreView = findViewById(R.id.textView20);
        mQuestionView = findViewById(R.id.textView21);
        mButtonChoice1 = findViewById(R.id.button15);
        mButtonChoice2 = findViewById(R.id.button16);
        mButtonChoice3 = findViewById(R.id.button17);
        mButtonChoice4 = findViewById(R.id.button18);
        mButtonChoice5 = findViewById(R.id.button19);
        scoredisplay=findViewById(R.id.textView28);

        updateQuestion();
        //Start of Button Listener for Button1
        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here

                if (mButtonChoice1.getText() == mAnswer){
                    mScore = mScore + 1;
                    updateScore(mScore);
                }else {
                    Toast.makeText(symptomChecker.this, "You need to contact emengercy services", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(symptomChecker.this,emergency.class));
                }
                updateQuestion();
            }
        });

        //End of Button Listener for Button1

        //Start of Button Listener for Button2
        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here

                if (mButtonChoice2.getText() == mAnswer){
                    mScore = mScore + 1;
                    updateScore(mScore);
                }else {
                    Toast.makeText(symptomChecker.this, "You need to contact emengercy services", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(symptomChecker.this,emergency.class));
                }
                updateQuestion();
            }
        });

        //End of Button Listener for Button2


        //Start of Button Listener for Button3
        mButtonChoice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here

                if (mButtonChoice3.getText() == mAnswer){
                    mScore = mScore + 1;
                    updateScore(mScore);

                }else {
                    Toast.makeText(symptomChecker.this, "You need to contact emengercy services", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(symptomChecker.this,emergency.class));
                }
                updateQuestion();
            }
        });

        mButtonChoice4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here

                if (mButtonChoice4.getText() == mAnswer){
                    mScore = mScore + 1;
                    updateScore(mScore);

                }else {
                    Toast.makeText(symptomChecker.this, "You need to contact emengercy services", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(symptomChecker.this,emergency.class));
                }
                updateQuestion();
            }
        });

        //End of Button Listener for Button2


        //Start of Button Listener for Button3
        mButtonChoice5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here

                if (mButtonChoice5.getText() == mAnswer){
                    mScore = mScore + 1;
                    updateScore(mScore);
                }else {
                    Toast.makeText(symptomChecker.this, "You need to contact emengercy services", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(symptomChecker.this,emergency.class));
                }
                updateQuestion();
            }
        });

        //End of Button Listener for Button3
//        verifyScore(mScore);
    }

    private void updateQuestion(){
        if(mQuestionNumber<4) {
            mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
            mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
            mButtonChoice3.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
            mButtonChoice4.setText(mQuestionLibrary.getChoice4(mQuestionNumber));
            mButtonChoice5.setText(mQuestionLibrary.getChoice5(mQuestionNumber));

            mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
            mQuestionNumber++;
        }else{
            onBackPressed();
        }
    }


    private void updateScore(int point) {
      if(mScore==4){
          Toast.makeText(symptomChecker.this, "you are safe", Toast.LENGTH_SHORT).show();
          onBackPressed();
      }
      else{
          scoredisplay.setText("Your Score is :- "+point);
      }
    }

}
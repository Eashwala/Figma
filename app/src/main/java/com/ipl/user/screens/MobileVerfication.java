package com.ipl.user.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.ipl.user.MainActivity;
import com.ipl.user.R;
import com.ipl.user.commonutils.MyCognito;

import java.util.ArrayList;

public class MobileVerfication extends AppCompatActivity {
    EditText e1, e2, e3, e4, e5, e6;
    TextView resendcode, mobileotpverify, verficationemail;
    String e11, e22, e33, e44, e55, e66;
    ArrayList<String> editlist = new ArrayList<>();
    MyCognito cognito;
    String user_id, otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verfication);

        Intent i = new Intent();
        if(i!=null ){
           // user_id = i.getStringExtra("");
             user_id = getIntent().getStringExtra("userId");
        }


        resendcode = findViewById(R.id.resendcode);
        e1 =findViewById(R.id.e1);
        e2 =findViewById(R.id.e2);
        e3 =findViewById(R.id.e3);
        e4 =findViewById(R.id.e4);
        e5 =findViewById(R.id.e5);
        e6 =findViewById(R.id.e6);

        mobileotpverify  =findViewById(R.id.mobileotpverify);
        verficationemail =findViewById(R.id.verficationemail);
        cognito = new MyCognito(getApplicationContext());


        verficationemail.setText(getResources().getString(R.string.enter_the_4_digit_code_just_sent_to, user_id));

        resendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
              //  finish();

             //   Toast.makeText(getApplicationContext(), editlist.toString(),Toast.LENGTH_LONG).show();
            }
        });

        editwatcher();

        mobileotpverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editlist.size()<6){
                    Toast.makeText(getApplicationContext(), "entercorrectotp",Toast.LENGTH_LONG).show();
                }else{

                    if(editlist!=null && !editlist.isEmpty())
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            otp = String.join("", editlist);
                        }
                   }


                GenericHandler confirmationCallback = new GenericHandler() {

                    @Override
                    public void onSuccess() {
                        // User was successfully confirmed
                        Toast.makeText(getApplicationContext(),"User Confirmed", Toast.LENGTH_LONG).show();
                        Intent ii = new Intent(MobileVerfication.this, SignUp.class);
                        ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ii);
                       finish();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        if (e instanceof AmazonServiceException) {

                            switch (((AmazonServiceException) e).getErrorCode()){
                                case "CodeMismatchException":
                                    Toast.makeText(getApplicationContext(), "Code mismatch",Toast.LENGTH_LONG).show();

                            }
                        }

                    }
                };
                 cognito.confirmUser(user_id, otp, confirmationCallback);


                //   Toast.makeText(getApplicationContext(), editlist.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void editwatcher() {

//        e1.addTextChangedListener(new OtpTextWatcher(e2, e1));
//        e2.addTextChangedListener(new OtpTextWatcher(e3, e1));
//        e3.addTextChangedListener(new OtpTextWatcher(e4, e2));
//        e4.addTextChangedListener(new OtpTextWatcher(e4, e3));

        e1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1){

                  e11 = s.toString();
                    editlist.add(e11);
                    e2.requestFocus();
                }else{
                    editlist.remove(e11);
                    e1.requestFocus();
                }
            }

        });
        e2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (e2.getText().toString().length() == 1){
                    e22 = s.toString();
                    editlist.add(e22);
                    e3.requestFocus();
                }else{
                    editlist.remove(e22);
                    e1.requestFocus();
                }
            }
        });
        e3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1){
                    e33 = s.toString();

                    editlist.add(e33);
                    e4.requestFocus();
                }else{
                    editlist.remove(e33);
                    e2.requestFocus();
                }
            }

        });
        e4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (e4.getText().toString().length() == 1){
                    e44 = s.toString();
                    editlist.add(e44);
                    e5.requestFocus();
                }else{
                    editlist.remove(e44);
                    e3.requestFocus();
                }
            }

        });

        e5.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (e5.getText().toString().length() == 1){
                    e55 = s.toString();
                    editlist.add(e55);
                    e6.requestFocus();
                }else{
                    editlist.remove(e55);
                    e4.requestFocus();
                }
            }

        });

        e6.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (e6.getText().toString().length() == 1){
                    e66 = s.toString();
                    editlist.add(e66);
                    e6.requestFocus();
                }else{
                    editlist.remove(e66);
                    e5.requestFocus();
                }
            }

        });


      //  Toast.makeText(getApplicationContext(), editlist.toString(),Toast.LENGTH_LONG).show();


    }

    @Override
    public void onBackPressed() {
      Toast.makeText(getApplicationContext(), "Verification not completed",Toast.LENGTH_LONG).show();
    }


}
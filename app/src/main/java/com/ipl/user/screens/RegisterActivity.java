package com.ipl.user.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.ipl.user.R;
import com.ipl.user.commonutils.MyCognito;

public class RegisterActivity extends AppCompatActivity {

    TextView registeruser;
    EditText firstnameregister,emailregister ,passwordregister;
    MyCognito cognito;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String passwordpattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    ProgressBar registerprogbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registeruser = findViewById(R.id.registeruser);

        firstnameregister = findViewById(R.id.firstnameregister);
        emailregister = findViewById(R.id.emailregister);
        registerprogbar = findViewById(R.id.registerprogbar);

        passwordregister= findViewById(R.id.passwordregister);
         cognito= new MyCognito(getApplicationContext());
         onclick();
    }

    private void onclick() {
        registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if(validate()){
                    registerUser();
                }

            }
        });

    }

    private void registerUser() {
        registeruser.setEnabled(false);
        registerprogbar.setVisibility(View.VISIBLE);

        CognitoUserAttributes userAttributes = new CognitoUserAttributes();
       userAttributes.addAttribute("name", firstnameregister.getText().toString().trim());

        SignUpHandler signUpCallback = new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser cognitoUser, boolean userConfirmed, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                // Sign-up was successful
                registerprogbar.setVisibility(View.GONE);
                registeruser.setEnabled(true);
                Intent ii = new Intent(getApplication(), MobileVerfication.class);
                ii.putExtra("userId", emailregister.getText().toString());
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ii);
                Toast.makeText(getApplicationContext(),"Code has been sent to registered account", Toast.LENGTH_LONG).show();
                // Check if this user (cognitoUser) needs to be confirmed
                if(!userConfirmed) {

                }
                else {
                    Toast.makeText(getApplicationContext(),"Error: User Confirmed before", Toast.LENGTH_LONG).show();
                    // The user has already been confirmed
                }
            }
            @Override
            public void onFailure(Exception e) {
                registerprogbar.setVisibility(View.GONE);
                registeruser.setEnabled(true);

                if (e instanceof AmazonServiceException) {

                    switch (((AmazonServiceException) e).getErrorCode()){

                        case "UsernameExistsException" :
                            Toast.makeText(getApplicationContext(), "User Already exist",Toast.LENGTH_LONG).show();

                    }
                }

            }
        };

        cognito.signUpInBackground(emailregister.getText().toString(),passwordregister.getText().toString(), userAttributes, signUpCallback);//mobilenumberregister.getText().toString(), passwordregister.getText().toString(), userAttributes);
    }
    private boolean validate() {
        boolean valid = true;
        String firstname1 = firstnameregister.getText().toString();
        String email1 =  emailregister.getText().toString();
        String pass1 = passwordregister.getText().toString();

        if (firstname1.isEmpty()) {
            firstnameregister.setError("enter first name");
            valid = false;
        } else {
            firstnameregister.setError(null);
        }
        if (email1.isEmpty() ) {
            emailregister.setError("enter email id ");
            valid = false;
        } else {
            emailregister.setError(null);
        }

        if (!email1.matches(emailPattern) ) {
            emailregister.setError("Enter valid email Id");
            valid = false;
        } else {
            emailregister.setError(null);
        }

        if (pass1.isEmpty() || pass1.length() < 5) {
            passwordregister.setError("password cannot be less than 6 characters");
            valid = false;
        } else {
            passwordregister.setError(null);
        }

        if (!pass1.matches(passwordpattern)) {
            passwordregister.setError("Password must contain one Uppercase letter, lower case letter, special character and number");
            valid = false;
        } else {
            passwordregister.setError(null);
        }

        return valid;
    }
}
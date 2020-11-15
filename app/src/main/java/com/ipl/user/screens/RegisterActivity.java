package com.ipl.user.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    EditText firstnameregister, lastnamerregister,mobilenumberregister, emailregister ,passwordregister,cnfPassword;
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
        lastnamerregister = findViewById(R.id.lastnamerregister);
        emailregister = findViewById(R.id.emailregister);
        registerprogbar = findViewById(R.id.registerprogbar);

        mobilenumberregister = findViewById(R.id.mobilenumberregister);
        passwordregister= findViewById(R.id.passwordregister);
        cnfPassword = findViewById(R.id.cnfPassword);
         cognito= new MyCognito(getApplicationContext());
         onclick();
    }

    private void onclick() {
        registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(validate()){
                    registerUser();
                }

            }
        });

    }

    private void registerUser() {
        registerprogbar.setVisibility(View.VISIBLE);
        CognitoUserAttributes userAttributes = new CognitoUserAttributes();
       userAttributes.addAttribute("name", firstnameregister.getText().toString().trim());
        userAttributes.addAttribute("family_name",  lastnamerregister.getText().toString().trim());
        userAttributes.addAttribute("phone_number", "+91"+mobilenumberregister.getText().toString().trim());


        SignUpHandler signUpCallback = new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser cognitoUser, boolean userConfirmed, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                // Sign-up was successful
                registerprogbar.setVisibility(View.GONE);
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
        String lastname1 = lastnamerregister.getText().toString();
        String email1 =  emailregister.getText().toString();
        String mobilenumber1 = mobilenumberregister.getText().toString();
        String mobilenumberpassword1 = passwordregister.getText().toString();
        String cnfpassword1 =  cnfPassword.getText().toString();

        if (firstname1.isEmpty()) {
            firstnameregister.setError("enter first name");
            valid = false;
        } else {
            firstnameregister.setError(null);
        }
        if (lastname1.isEmpty() ) {
            lastnamerregister.setError("enter last name");
            valid = false;
        } else {
            lastnamerregister.setError(null);
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

        if (mobilenumber1.isEmpty()) {
            mobilenumberregister.setError("enter mobile number");
            valid = false;
        } else {
            mobilenumberregister.setError(null);
        }

        if (mobilenumberpassword1.isEmpty() || mobilenumberpassword1.length() < 5) {
            passwordregister.setError("password cannot be less than 6 characters");
            valid = false;
        } else {
            passwordregister.setError(null);
        }
        if (cnfpassword1.isEmpty() || cnfpassword1.length() < 5) {
            cnfPassword.setError("password cannot be less than 6 characters");
            valid = false;
        } else {
            cnfPassword.setError(null);
        }

        if(!mobilenumberpassword1.equalsIgnoreCase(cnfpassword1)){
            cnfPassword.setError("Passwords didn't match");
        }

        if (!cnfpassword1.matches(passwordpattern)) {
            cnfPassword.setError("Password must contain one Uppercase letter, lower case letter, special character and number");
            valid = false;
        } else {
            cnfPassword.setError(null);
        }


        return valid;
    }
}
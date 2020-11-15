package com.ipl.user.screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ipl.user.MainActivity;
import com.ipl.user.R;
import com.ipl.user.commonutils.MyCognito;
import com.ipl.user.commonutils.SharedPreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner countries_spinner;
    EditText emaillogin, passwordlogin;
    TextView fbusersignin, googleusersignin, mobileusersignin, signuphere;
    LoginButton loginButton;
    CallbackManager callbackManager;
    private static final int RC_GMAIL_SIGN_IN = 1;
    private static final int RC_FB_SIGN_IN = 102;
    SharedPreferenceManager sharedPreferenceManager;
    MyCognito myCognito;
    String userid;
    ProgressBar signupprogbar;
    CognitoUserSession cognitoUserSession;
boolean isPasswordVisible;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String passwordpattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());
        myCognito = new MyCognito(getApplicationContext());

        if(sharedPreferenceManager.getUserLoggedIn()){
            Intent i = new Intent(SignUp.this, MainActivity.class);
            startActivity(i);
        }else{
            initdata();
        }

    }

    private void initdata() {
        countries_spinner = findViewById(R.id.countries_spinner);
        emaillogin = findViewById(R.id.emaillogin);
        passwordlogin = findViewById(R.id.passwordlogin);
        googleusersignin = findViewById(R.id.googleusersignin);
        fbusersignin = findViewById(R.id.fbusersignin);
        mobileusersignin = findViewById(R.id.mobileusersignin);
        signupprogbar = findViewById(R.id.signupprogbar);

        signuphere  = findViewById(R.id.signuphere);

        loginButton = findViewById(R.id.facebook_login_button);
        passwordlogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countries_spinner.setAdapter(adapter);
        countries_spinner.setOnItemSelectedListener(this);

        googleusersignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fbusersignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // loginButton.performClick();
            }
        });
        signuphere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(SignUp.this, RegisterActivity.class);
                startActivity(ii);
            }
        });
        passwordlogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordlogin.getRight() - passwordlogin.getCompoundDrawables()[RIGHT].getBounds().width())) {
                        int selection = passwordlogin.getSelectionEnd();
                        if (isPasswordVisible) {
                            // set drawable image
                            passwordlogin.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            // hide Password
                                passwordlogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisible = false;
                        } else  {
                            // set drawable image
                            passwordlogin.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                            // show Password
                            passwordlogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisible = true;
                        }
                        passwordlogin.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        mobileusersignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(validate()){
                    loginUser();
                }
            }
        });

    }

    private void loginUser() {
        signupprogbar.setVisibility(View.VISIBLE);

        AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
            @Override
            public void authenticationChallenge(ChallengeContinuation continuation) {

            }
            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                cognitoUserSession = userSession;
                sharedPreferenceManager.setUserLoggedIn(true);
                signupprogbar.setVisibility(View.GONE);
                Intent ii = new Intent(SignUp.this, MainActivity.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ii);
                finish();
                Toast.makeText(getApplicationContext(),"Sign in success", Toast.LENGTH_LONG).show();
            }
            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                AuthenticationDetails authenticationDetails = new AuthenticationDetails(emaillogin.getText().toString(),passwordlogin.getText().toString(), null);
                authenticationContinuation.setAuthenticationDetails(authenticationDetails);
                authenticationContinuation.continueTask();
            }
            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {

            }
            @Override
            public void onFailure(Exception e) {

                if (e instanceof AmazonServiceException ) {

                    switch (((AmazonServiceException) e).getErrorCode()){
                        case "UserNotConfirmedException":

                            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext(),  R.style.AlertDialogStyle)
                                    //.setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Confirm User!!")
                                    .setMessage("OTP will be sent to email id")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            VerificationHandler vh = new VerificationHandler() {
                                                @Override
                                                public void onSuccess(CognitoUserCodeDeliveryDetails verificationCodeDeliveryMedium) {
                                                    Intent ii = new Intent(SignUp.this, MobileVerfication.class);
                                                    ii.putExtra("userId",emaillogin.getText().toString());
                                                    startActivity(ii);
                                                }

                                                @Override
                                                public void onFailure(Exception exception) {

                                                }
                                            };
                                            myCognito.resendOtp(emaillogin.getText().toString(), vh);

                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .show();

                        case "NotAuthorizedException" :
                            Toast.makeText(getApplicationContext(), "Incorrect username or password",Toast.LENGTH_LONG).show();


                    }
                }
            }
        };
        myCognito.userLogin(emaillogin.getText().toString().trim(),passwordlogin.getText().toString().trim(), authenticationHandler, cognitoUserSession);//mobilenumber.getText().toString(), mobilenumberpassword.getText().toString());
    }

    private boolean validate() {

        boolean valid = true;
        String email1 = emaillogin.getText().toString();
        String password1 = passwordlogin.getText().toString();
        if (email1.isEmpty()) {
            emaillogin.setError("enter mobile number");
            valid = false;
        } else {
            emaillogin.setError(null);
        }
        if (!email1.matches(emailPattern) ) {
            emaillogin.setError("Enter valid email Id");
            valid = false;
        } else {
            emaillogin.setError(null);
        }

        if (password1.isEmpty() || password1.length() < 5) {
            passwordlogin.setError("password cannot be less than 6 characters");
            valid = false;
        } else {
            passwordlogin.setError(null);
        }
//        if (!password1.matches(passwordpattern)) {
//            passwordlogin.setError("Password must contain one Uppercase letter, lower case letter, special character and number");
//            valid = false;
//        } else {
//            passwordlogin.setError(null);
//        }

        return valid;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String text = parent.getItemAtPosition(i).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void fbLoginButtonClick(View view) {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                        final String accessToken = loginResult.getAccessToken().getToken();
                        Log.e("TAG", "onSuccess: "+accessToken );

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        getFacebookData(response, accessToken);
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,birthday,email,first_name,gender,last_name,link,location,name");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }


                    @Override
                    public void onCancel () {
                        Log.d("TAG", "Login attempt cancelled.");
                    }

                    @Override
                    public void onError (FacebookException e){
                        e.printStackTrace();
                        Log.d("TAG", "Login attempt failed.");
                        deleteAccessToken();
                    }
                }
        );
    }


    private void deleteAccessToken(){
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }
    private void getFacebookData(GraphResponse response, String accessToken) {

        if(response!=null){
            JSONObject jsonObject = response.getJSONObject();
            try {
                String tok =jsonObject.getString("id");
                String name =jsonObject.getString("first_name");
                //      myCognito.signUpInBackground(name,"1234");
                Log.e("TAG", "getFacebookData: "+tok );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //  {"id":"3434184853369660","email":"easwalanidumolu@gmail.com","first_name":"Easha","last_name":"Surendra","name":"Easha Surendra"}
            //   myCognito.signInFacebook(accessToken);
            //  sharedPreferenceManager.setUserLoggedIn(true);



//            AWSMobileClient.getInstance().federatedSignIn(
//                    IdentityProviders.FACEBOOK.toString(), accessToken, new Callback<UserState>() {
//                        @Override
//                        public void onResult(final UserState userState) {
//                            //Handle the result
//                        }
//
//                        @Override
//                        public void onError(Exception e) {
//                            Log.e("TAG", "sign-in error", e);
//                        });


//            Intent i = new Intent(SignUp.this, MainActivity.class);
//            startActivity(i);
        }


    }

//    private ProfileResponse getUserModelFromGraphResponse(GraphResponse graphResponse)
//    {
//        ProfileResponse profileResponse = new ProfileResponse();
//        try {
//            JSONObject jsonObject = graphResponse.getJSONObject();
//            profileResponse.setName(jsonObject.getString("first_name"));
//            profileResponse.setEmail(jsonObject.getString("email"));
//            String id = jsonObject.getString("id");
//            String profileImg = "http://graph.facebook.com/"+ id+ "/picture?type=large";
//            //     profileResponse.set(jsonObject.getString("email"));
//            //    userModel.profilePic = profileImg;
//            Log.i(TAG,profileImg);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return profileResponse;
//    }

//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            updateUI(account);
//        }
//        catch (ApiException e) {
//            Log.e("ErrorTag", "Reason " + e.getMessage());
//        }
//    }

//    private void updateUI(GoogleSignInAccount account) {
//        if (account == null) {
//            Toast.makeText(getApplicationContext(),"Sign in Failed",Toast.LENGTH_SHORT).show();
//        } else {
//           // socialLoginUpdate(account.getDisplayName(), account.getEmail(), "Gmail", "");
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == RC_GMAIL_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


}
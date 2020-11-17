package com.ipl.user.commonutils;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler;
import com.amazonaws.regions.Regions;

import java.util.HashMap;
import java.util.Map;

public class MyCognito {

    // ############################################################# Information about Cognito Pool
    private String poolID = Constants.poolID;
    private String clientID = Constants.clientID;
    private String clientSecret = Constants.clientSecret;
    private Regions awsRegion = Regions.US_EAST_1;         // Place your Region
    // ############################################################# End of Information about Cognito Pool
    private CognitoUserPool userPool;
    private CognitoUserAttributes userAttributes;       // Used for adding attributes to the user
    private Context appContext;
    private String userPassword;
    SharedPreferenceManager sharedPreferenceManager;
    private CognitoUserSession cognitoUserSession;


    // Used for Login
    public MyCognito(Context context) {
        this.appContext = context;
        userPool = new CognitoUserPool(context, this.poolID, this.clientID, this.clientSecret, this.awsRegion);
        userAttributes = new CognitoUserAttributes();
        sharedPreferenceManager = SharedPreferenceManager.getInstance(context);
    }

    public void signUpInBackground(final String userId, String password, CognitoUserAttributes userAttributes, SignUpHandler signUpCallback) {
        userPool.signUpInBackground(userId, password, userAttributes, null, signUpCallback);
        //userPool.signUp(userId, password, this.userAttributes, null, signUpCallback);
    }


    public void confirmUser(String userId, final String code, GenericHandler confirmationCallback) {
        CognitoUser cognitoUser = userPool.getUser(userId);
        cognitoUser.confirmSignUpInBackground(code, false, confirmationCallback);
    }

    public void resendOtp(String userId, VerificationHandler vh) {
        CognitoUser cognitoUser = userPool.getUser(userId);
        cognitoUser.resendConfirmationCodeInBackground( vh);
    }



    public void addAttribute(String key, String value) {
        userAttributes.addAttribute(key, value);
    }

    public void userLogin(String userId, String password, AuthenticationHandler authenticationHandler, CognitoUserSession cognitoUserSession) {
        CognitoUser cognitoUser = userPool.getUser(userId);
        this.userPassword = password;
        this.cognitoUserSession = cognitoUserSession;

        cognitoUser.getSessionInBackground(authenticationHandler);
    }


//        userPool.getCurrentUser().getUserId();
//        if (userPool != null) {
//            if (userPool.getCurrentUser() != null) {
//
//                Log.e("TAG", "userLogout: "+userPool.getCurrentUser().getUserId());
//                GenericHandler handler = new GenericHandler() {
//
//                    @Override
//                    public void onSuccess() {
//                        Toast.makeText(appContext,"Sign out success", Toast.LENGTH_LONG).show();
//
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        Toast.makeText(appContext,"failure" +e, Toast.LENGTH_LONG).show();
//
//                    }
//
//                };
//                userPool.getCurrentUser().globalSignOutInBackground(handler);
//            }
//        }

//        CognitoUser cognitoUser =  userPool.getUser(userId);
//        if (cognitoUser != null) {
//            cognitoUser.signOut();
//            Intent ii = new Intent(appContext, SignUp.class);
//            appContext.startActivity(ii);
//        }
//}
    // Callback handler for the sign-in process

    public String getUserIdFromCognito(){
        return userPool.getCurrentUser().getUserId();
    }
    public void userLogout() {

        if (null != userPool && null != userPool.getCurrentUser()) {
            userPool.getCurrentUser().signOut();
            sharedPreferenceManager.setUserId("");
            sharedPreferenceManager.setUserLoggedIn(false);
            cognitoUserSession = null;


        }
    }

    public void signInFacebook(String token){
        final CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(appContext, poolID, Regions.US_EAST_1);

     //  IdentityProvider fb = IdentityProvider.FACEBOOK;
        Map<String, String> logins = new HashMap<String, String>();
        logins.put("graph.facebook.com", token);
        credentialsProvider.setLogins(logins);
     //   credentialsProvider.refresh();

    }

}

package com.ipl.user.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.AppSyncSubscriptionCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.ipl.user.QuestionCreatedMainSubscription;
import com.ipl.user.R;
import com.ipl.user.customcomponents.ClientFactory;

import javax.annotation.Nonnull;

public class SubscriptionActivity extends AppCompatActivity {

    TextView question_subscribtion;
    private AWSAppSyncClient mAWSAppSyncClient;    private static final String TAG = "home";

    private AppSyncSubscriptionCall<QuestionCreatedMainSubscription.Data> subscriptionWatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        question_subscribtion = findViewById(R.id.question_subscribtion);

        startSubscription();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (subscriptionWatcher != null) {
            subscriptionWatcher.cancel();
        }
    }

    private void startSubscription() {


        if (mAWSAppSyncClient == null) {
            mAWSAppSyncClient = ClientFactory.getInstance(getApplicationContext());
        }

        QuestionCreatedMainSubscription subscription = QuestionCreatedMainSubscription.builder().build();
        subscriptionWatcher = mAWSAppSyncClient.subscribe(subscription);
        subscriptionWatcher.execute(subCallback);

    }

    private AppSyncSubscriptionCall.Callback<QuestionCreatedMainSubscription.Data> subCallback = new AppSyncSubscriptionCall.Callback<QuestionCreatedMainSubscription.Data>() {
        @Override
        public void onResponse(@Nonnull final Response<QuestionCreatedMainSubscription.Data> response) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    QuestionCreatedMainSubscription.QuestionCreated createQuestionsForMatch = response.data().QuestionCreated();

                    Log.e("subs", "Subscription response: " + createQuestionsForMatch.question());

                    question_subscribtion.setText(createQuestionsForMatch.question());


                }
            });

        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Subscription", e.toString());
        }

        @Override
        public void onCompleted() {
            Log.e("Subscription", "Subscription completed");
        }
    };

}
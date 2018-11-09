package tcss450.uw.edu.phishapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.Serializable;

import tcss450.uw.edu.phishapp.model.Credentials;

/**
 * Main Entry point that controls login and register activity.
 * This activity holds the login/register fragments.
 */
public class MainActivity extends AppCompatActivity
        implements LoginFragment.OnLoginFragmentInteractionListener,
        RegistrationFragment.OnRegisterFragmentInteractionListener {

    private boolean mLoadFromChatNotification = false;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("type")) {
                Log.d("MainActivity", "onCreate in if statement");
                Log.d(TAG, "type of message: " + getIntent().getExtras().getString("type"));
                mLoadFromChatNotification = getIntent().getExtras().getString("type").equals("contact");
                Log.d("MainActivity", String.valueOf(mLoadFromChatNotification));
            } else {
                Log.d(TAG, "NO MESSAGE");
            }
        }

        if (savedInstanceState == null) { // check to avoid overlapping fragments.
            if (findViewById(R.id.register_login_container) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.register_login_container, new LoginFragment())
                        .commit();
            }
        }
    }


    /**
     * If login or registration successful launch HomeActivity.
     */
    private void login(final Credentials credentials) {
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra(getString(R.string.keys_prefs_email), (Serializable) credentials);
        i.putExtra(getString(R.string.keys_intent_notification_msg), mLoadFromChatNotification);
        startActivity(i);
        //Ends this Activity and removes it from the Activity back stack.
        finish();
    }


    /**
     * Launch HomeActivity from LoginFragment. User will then be logged in.
     */
    @Override
    public void onLoginSuccess(Credentials credentials) {
        Log.d("MainActivity", "login button clicked");

        login(credentials);
        //todo: do something with the credentials? pass to next activity?
    }

    /**
     * Launch HomeActivity from RegistrationFragment. User will then be logged in.
     */
    @Override
    public void onRegisterSuccess(Credentials credentials) {
        Log.d("MainActivity",
                "user clicked registration button from registration screen");

        login(credentials);
        //todo: do something with the credentials? pass to next activity?
    }

    /**
     * Launch the RegistrationFragment.
     */
    @Override
    public void onRegisterClicked() {
        Log.d("MainActivity",
                "register button clicked to launch register fragment");

        RegistrationFragment userRegFragment = new RegistrationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.register_login_container, userRegFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onWaitFragmentInteractionShow() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.register_login_container, new WaitFragment(), "WAIT")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onWaitFragmentInteractionHide() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(getSupportFragmentManager().findFragmentByTag("WAIT"))
                .commit();
    }
}


package tcss450.uw.edu.phishapp;

import tcss450.uw.edu.phishapp.model.Credentials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

/**
 * Main Entry point that controls login and register activity.
 * This activity holds the login/register fragments.
 */
public class MainActivity extends AppCompatActivity
        implements LoginFragment.OnLoginFragmentInteractionListener,
        RegistrationFragment.OnRegisterFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.register_login_container) != null) {
            Log.d("MainActivity", "in the first if statement");

            // If being restored from a previous state don't do anything
            // check to avoid overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            Log.d("MainActivity", "past second if statement");

            // Create a new Fragment to be placed in the activity layout
            LoginFragment ulFragment = new LoginFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            ulFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.register_login_container, ulFragment).commit();
        }
    }

    /**
     * If login or registration successful launch HomeActivity.
     */
    private void launchHomeActivity() {
//        Bundle args = new Bundle();
//        String[] userInfo = new String[2];
//        userInfo[0] = username;
//        userInfo[1] = password;
//        args.putStringArray(getResources().getString(R.string.user_info_key), userInfo);

        Intent intent = new Intent(this, HomeActivity.class);
//        intent.putExtras(args); //set a bundle to be sent with intent
        startActivity(intent);
    }

    /**
     * Launch HomeActivity from LoginFragment. User will then be logged in.
     */
    @Override
    public void onLoginAttempt(Credentials credentials) {
        Log.d("MainActivity", "login button clicked");

        launchHomeActivity();
        //todo: do something with the credentials? pass to next activity?
    }

    /**
     * Launch HomeActivity from RegistrationFragment. User will then be logged in.
     */
    @Override
    public void onRegisterAttempt(Credentials credentials) {
        Log.d("MainActivity",
                "user clicked registration button from registration screen");

        launchHomeActivity();
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


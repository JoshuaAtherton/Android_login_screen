package tcss450.uw.edu.phishapp;

import android.app.FragmentManager;
import android.net.Credentials;
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
        setContentView(R.layout.activity_register_login);

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
     * try to launch the success fragment
     */
    private void launchHomeActivity() {

    }

    /**
     *  Launch HomeActivity from LoginFragment. User will then be logged in.

     */
    @Override
    public void onLoginAttempt(Credentials credentials) {
        Log.d("MainActivity", "login button clicked");

        launchHomeActivity();
    }

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

    /**
     * Launch HomeActivity from RegistrationFragment. User will then be logged in.

     */
    @Override
    public void onRegisterAttempt(Credentials credentials) {
        Log.d("MainActivity",
                "user clicked registration button from registration screen");

        //clear the back stack of fragments
//        int count = getSupportFragmentManager().getBackStackEntryCount();
//        if (count > 0) {
//            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        }

        launchHomeActivity();
    }

}


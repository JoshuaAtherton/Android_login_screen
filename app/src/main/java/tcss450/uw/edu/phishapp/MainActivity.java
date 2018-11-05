package tcss450.uw.edu.phishapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import tcss450.uw.edu.phishapp.model.Credentials;

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
            if (savedInstanceState != null) { // check to avoid overlapping fragments.
                return;
            }
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
    private void launchHomeActivity(Credentials credentials) {
        Intent intent = new Intent(this, HomeActivity.class);
        // his has line below mine didn't...need? mine didn't pass in credentials to this method
        // originally either since I do not use them
//        intent.putExtra(getString(R.string.key_email), (Serializable) credentials);
        startActivity(intent);
        // End this activity and remove it from the activity back stack
        finish();
    }

    /**
     * Launch HomeActivity from LoginFragment. User will then be logged in.
     */
    @Override
    public void onLoginSuccess(Credentials credentials) {
        Log.d("MainActivity", "login button clicked");

        launchHomeActivity(credentials);
        //todo: do something with the credentials? pass to next activity?
    }

    /**
     * Launch HomeActivity from RegistrationFragment. User will then be logged in.
     */
    @Override
    public void onRegisterSuccess(Credentials credentials) {
        Log.d("MainActivity",
                "user clicked registration button from registration screen");

        launchHomeActivity(credentials);
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


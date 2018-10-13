package tcss450.uw.edu.phishapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

/**
 * Controls the entry point that controls login and register activity.
 * This activity holds the login/register fragments
 */
public class RegisterLoginActivity extends AppCompatActivity
        implements UserLoginFragment.OnFragmentUserLoginInteractionListener,
                   UserRegistrationFragment.OnFragmentInteractionListenerUserReg{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.register_login_container) != null) {
            Log.d("RegisterLoginActivity", "in the first if statement");

            // If being restored from a previous state we don't need to do anything
            // check to avoid overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            Log.d("RegisterLoginActivity", "past second if statement");

            // Create a new Fragment to be placed in the activity layout
            UserLoginFragment ulFragment = new UserLoginFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            ulFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.register_login_container, ulFragment).commit();
        }
    }

    /**
     * Create a new bundle string array with the arguments, then create a new intent
     * to start the HomeActivity.
     * @param username
     * @param password
     */
    private void launchHomeActivity(String username, String password) {
        Bundle args = new Bundle();
        String[] userInfo = new String[2];
        userInfo[0] = username;
        userInfo[1] = password;
        args.putStringArray(getResources().getString(R.string.user_info_key), userInfo);

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtras(args); //set a bundle to be sent with intent
        startActivity(intent);
    }

    /**
     *  Launch HomeActivity from UserLoginFragment. User will then be logged in.
     * @param username
     * @param password
     */
    @Override
    public void onFragmentInteractionLoginClickedUserLogin(String username, String password) {
        Log.d("RegisterLoginActivity", "login button clicked");

        launchHomeActivity(username, password);
    }

    @Override
    public void onFragmentInteractionRegisterClickedFromUserLogin() {
        Log.d("RegisterLoginActivity",
                "register button clicked to launch register fragment");

        UserRegistrationFragment userRegFragment = new UserRegistrationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.register_login_container, userRegFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Launch HomeActivity from UserRegistrationFragment. User will then be logged in.
     * @param username
     * @param password
     */
    @Override
    public void onFragmentInteractionRegClickedFromUserReg(String username, String password) {
        Log.d("RegisterLoginActivity",
                "user clicked registration button from registration screen");

        //clear the back stack of fragments
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        launchHomeActivity(username, password);
    }

}


package tcss450.uw.edu.phishapp;

import android.app.FragmentManager;
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

    private void createDisplayFragment(String username, String password) {
        DisplayFragment displayFragment = new DisplayFragment();
        Bundle args = new Bundle();
        String[] userInfo = new String[2];
        userInfo[0] = username;
        userInfo[1] = password;

        args.putStringArray(getResources().getString(R.string.user_info_key), userInfo);
        displayFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        /*
        transaction.replace(R.id.register_login_container, displayFragment);
        // transaction.addToBackStack(null); // on back exit the app so omit call
        transaction.commit();
        */
        // above three lines are the same as line below:
        transaction.replace(R.id.register_login_container, displayFragment).commit();
    }

    @Override
    public void onFragmentInteractionLoginClickedUserLogin(String username, String password) {
        Log.d("RegisterLoginActivity", "login button clicked");

        createDisplayFragment(username, password);
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

    @Override
    public void onFragmentInteractionRegClickedFromUserReg(String username, String password) {
        Log.d("RegisterLoginActivity",
                "user clicked registration button from registration screen");
        //clear the back stack of fragments
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        createDisplayFragment(username, password);
    }

}


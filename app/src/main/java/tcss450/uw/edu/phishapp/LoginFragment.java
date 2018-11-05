package tcss450.uw.edu.phishapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import tcss450.uw.edu.phishapp.model.Credentials;
import tcss450.uw.edu.phishapp.model.UserLoginValidation;
import tcss450.uw.edu.phishapp.utils.SendPostAsyncTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnLoginFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LoginFragment extends Fragment {

    private OnLoginFragmentInteractionListener mListener;
    private Credentials mCredentials;
    private String mFirebaseToken;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        Log.d("LoginFragment", "inflating the fragment");

        //set up login and register buttons
        Button b = v.findViewById(R.id.button_login);
        b.setOnClickListener(this::loginClicked);

        b = v.findViewById(R.id.button_register);
        b.setOnClickListener(this::registerClicked);

        return v;
    }

    // get the shared preferences and login to app if they are found
    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs =
                getActivity().getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);
        //retrieve the stored credentials from SharedPrefs
        if (prefs.contains(getString(R.string.keys_prefs_email)) &&
                prefs.contains(getString(R.string.keys_prefs_password))) {

            // Get the credentials found in SharedPrefs
            final String email = prefs.getString(getString(R.string.keys_prefs_email), "");
            final String password = prefs.getString(getString(R.string.keys_prefs_password), "");

           getFirebaseToken(email, password);
        }
    }

    /**
     * Retrieves the apps unique firebase token
     * @param email
     * @param password
     */
    private void getFirebaseToken(final String email, final String password) {
        mListener.onWaitFragmentInteractionShow();

        //add this app on this device to listen for the topic all
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        //the call to getInstanceId happens asynchronously. task is an onCompleteListener
        //similar to a promise in JS.
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM: ", "getInstanceId failed", task.getException());
                        mListener.onWaitFragmentInteractionHide();
                        return;
                    }

                    // Get new Instance ID token
                    mFirebaseToken = task.getResult().getToken();

                    Log.d("FCM: ", mFirebaseToken);
                    //the helper method that initiates login service
                    doLogin(email, password);
                });
        //no code here. wait for the Task to complete.
    }

    public void doLogin(String email, String password) {
        //Load the two login EditTexts with correct credentials
        EditText emailEdit = getActivity().findViewById(R.id.edit_login_email);
        emailEdit.setText(email);
        EditText passwordEdit = getActivity().findViewById(R.id.edit_login_password);
        passwordEdit.setText(password);

        boolean userNameBlank,
                passwordBlank,
                hasAtSymbol;

        hasAtSymbol = !UserLoginValidation.hasAtSymbol(emailEdit, getView());
        userNameBlank = UserLoginValidation.isFieldBlank(emailEdit, getView());
        passwordBlank = UserLoginValidation.isFieldBlank(passwordEdit, getView());

        // notify listeners
        if (!userNameBlank && !passwordBlank && hasAtSymbol) {
            Log.d("loginFragment", "in check to attempt a connection with web service");
            Credentials credentials = new Credentials.Builder(emailEdit.getText().toString(),
                    passwordEdit.getText().toString()).build();

            //build the web service URL
            Uri uri = new Uri.Builder().scheme("https")
                    .appendPath(getString(R.string.ep_base_url))
                    .appendPath(getString(R.string.ep_login))
                    .build();

            //build the JSONObject
            JSONObject msg = credentials.asJSONObject();
            mCredentials = credentials;

            // instantiate and execute the AsyncTask.
            // Feel free to add a handler for onPreExecution so that a progress bar
            // is displayed or maybe disable buttons.
            new SendPostAsyncTask.Builder(uri.toString(), msg)
                    .onPreExecute(this::handleLoginOnPre)
                    .onPostExecute(this::handleLoginOnPost)
                    .onCancelled(this::handleErrorsInTask)
                    .build().execute();
        }
    }

    /**
     * User is attempting to login.
     * if all rules passed call MainActivity to launch display fragment
     *
     * @param buttonClicked the login button clicked
     */
    public void loginClicked(final View buttonClicked) {
        // get username & password fields text
        EditText email = getActivity().findViewById(R.id.edit_login_email);
        EditText password = getActivity().findViewById(R.id.edit_login_password);

        getFirebaseToken(email.getText().toString(), password.getText().toString());
    }

    /**
     * Handle the setup of the UI before the HTTP call to the webservice.
     */
    private void handleLoginOnPre() {
//        mListener.onWaitFragmentInteractionShow();
    }

    /**
     * Handle onPostExecute of the AsynceTask. The result from our webservice is
     * a JSON formatted String. Parse it for success or failure.
     *
     * @param result the JSON formatted String response from the web service
     */
    private void handleLoginOnPost(String result) {
        try {
            Log.d("JSON result", result);

            JSONObject resultsJSON = new JSONObject(result);
            boolean success = resultsJSON.getBoolean("success");

            mListener.onWaitFragmentInteractionHide();
            if (success) {
                //Login was successful. Inform the Activity so it can do its thing.
                saveCredentials(mCredentials);
                mListener.onLoginSuccess(mCredentials);
            } else {
                //Login was unsuccessful. Don’t switch fragments and inform the user
                ((TextView) getView().findViewById(R.id.edit_login_email))
                        .setError("Login Unsuccessful");
            }
        } catch (JSONException e) {
            //It appears that the web service didn’t return a JSON formatted String
            // or it didn’t have what we expected in it.
            Log.e("JSON_PARSE_ERROR", result
                    + System.lineSeparator()
                    + e.getMessage());

            mListener.onWaitFragmentInteractionHide();
            ((TextView) getView().findViewById(R.id.edit_login_email))
                    .setError("Login Unsuccessful");
        }
    }

    /**
     * Handle errors that may occur during the AsyncTask.
     *
     * @param result the error message provide from the AsyncTask
     */
    private void handleErrorsInTask(String result) {
        Log.e("ASYNCT_TASK_ERROR", result);
    }

    /**
     *  Save the user email and password in the shared preferences.
     * @param credentials user information
     */
    private void saveCredentials(final Credentials credentials) {
        SharedPreferences prefs =
                getActivity().getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);
        //Store the credentials in SharedPrefs
        prefs.edit().putString(getString(R.string.keys_prefs_email), credentials.getEmail()).apply();
        prefs.edit().putString(getString(R.string.keys_prefs_password), credentials.getPassword()).apply();
    }

    /**
     * Call MainActivity to launch RegistrationFragment.
     */
    public void registerClicked(View view) {
        mListener.onRegisterClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentInteractionListener) {
            mListener = (OnLoginFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSetListPostFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment.
     */
    public interface OnLoginFragmentInteractionListener extends
            WaitFragment.OnFragmentInteractionListener {

        void onLoginSuccess(Credentials credentials);
        void onRegisterClicked();
    }
}

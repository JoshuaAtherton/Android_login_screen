package tcss450.uw.edu.phishapp;

import android.content.Context;

import tcss450.uw.edu.phishapp.model.Credentials;

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

import org.json.JSONException;
import org.json.JSONObject;

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
    private EditText username;
    private EditText password;
    private View v;
    private Credentials mCredentials;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_login, container, false);
        Log.d("LoginFragment", "inflating the fragment");

        // set up username & password text fields
        username = v.findViewById(R.id.edit_login_email);
        password = v.findViewById(R.id.edit_login_password);

        //set up login and register buttons
        Button b = v.findViewById(R.id.button_login);
        b.setOnClickListener(this::loginClicked);

        b = v.findViewById(R.id.button_register);
        b.setOnClickListener(this::registerClicked);

        return v;
    }

    /**
     * User is attempting to login.
     * if all rules passed call MainActivity to launch display fragment
     *
     * @param buttonClicked the login button clicked
     */
    public void loginClicked(final View buttonClicked) {
        boolean userNameBlank,
                passwordBlank,
                hasAtSymbol;

        hasAtSymbol = !UserLoginValidation.hasAtSymbol(username, v);
        userNameBlank = UserLoginValidation.isFieldBlank(username, v);
        passwordBlank = UserLoginValidation.isFieldBlank(password, v);

        // notify listeners
        if (!userNameBlank && !passwordBlank && hasAtSymbol) {

            Credentials credentials = new Credentials.Builder(username.toString(),
                    password.toString()).build();

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
     * Handle errors that may occur during the AsyncTask.
     *
     * @param result the error message provide from the AsyncTask
     */
    private void handleErrorsInTask(String result) {
        Log.e("ASYNCT_TASK_ERROR", result);
    }

    /**
     * Handle the setup of the UI before the HTTP call to the webservice.
     */
    private void handleLoginOnPre() {
        mListener.onWaitFragmentInteractionShow();
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
                mListener.onLoginAttempt(mCredentials);
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
                    + " must implement OnFragmentInteractionListener");
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
        void onLoginAttempt(Credentials credentials);

        void onRegisterClicked();
    }
}
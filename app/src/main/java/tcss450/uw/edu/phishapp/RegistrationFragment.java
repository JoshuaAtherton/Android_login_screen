package tcss450.uw.edu.phishapp;

import android.content.Context;
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

import tcss450.uw.edu.phishapp.model.Credentials;
import tcss450.uw.edu.phishapp.model.UserLoginValidation;
import tcss450.uw.edu.phishapp.utils.SendPostAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRegisterFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RegistrationFragment extends Fragment {

    private static final int PASSWORD_LENGTH = 6;
    private OnRegisterFragmentInteractionListener mListener;
    //todo: refactor these into fields where needed like in loginFragment
    private EditText firstName;
    private EditText lastName;
    private EditText userName;
    private EditText email;
    private EditText password1;
    private EditText password2;
    private Credentials mCredentials;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        firstName = view.findViewById(R.id.editText_firstname_reg);
        lastName = view.findViewById(R.id.editText_lastname_reg);
        userName = view.findViewById(R.id.editText_username_reg);
        email = view.findViewById(R.id.editText_email_reg);
        password1 = view.findViewById(R.id.editText_password1_reg);
        password2 = view.findViewById(R.id.editText_password2_reg);

        // Add a listener for the register button
        Button b = view.findViewById(R.id.button_register_reg);
        b.setOnClickListener(this::registerButtonClicked);
        Log.d("RegistrationFragment", "creating view");

        return view;
    }

    /**
     * User clicked button to attempt to register.
     * @param view theButton clicked
     */
    public void registerButtonClicked(View view) {
        Log.d("RegistrationFragment", "in Register button Clicked");
        boolean atSymbol,
                isBlankFields,
                passMatching,
                passLength = false;

        atSymbol = !UserLoginValidation.hasAtSymbol(email, getView());
        isBlankFields = checkForBlankFields();
        passMatching =
                password1.getText().toString().equals(password2.getText().toString());

        if (passMatching) {
            passLength = password1.getText().toString().length() >= PASSWORD_LENGTH;
            if (!passLength) {
                password1.setError(getResources().getString(R.string.password_too_short_prompt));
                password2.setError(getResources().getString(R.string.password_too_short_prompt));
            }
        } else {
            password1.setError(getResources().getString(R.string.passwords_do_not_match_prompt));
            password2.setError(getResources().getString(R.string.passwords_do_not_match_prompt));
        }

        Log.d("registrationFragment", String.valueOf(isBlankFields));
        // if all fields are valid send message through interface
        if (!isBlankFields && atSymbol && passMatching && passLength) {
            Log.d("RegistrationFragment", "starting async task");

            // create a credential object with the entered email email and password
            Credentials credentials = new Credentials.Builder(email.getText().toString(),
                    password1.getText().toString())
                    .addFirstName(firstName.getText().toString())
                    .addLastName(lastName.getText().toString())
                    .addUsername(userName.getText().toString())
                    .build();

            //create the uri object with the web service URL
            Uri uri = new Uri.Builder().scheme("https")
                    .appendPath(getString(R.string.ep_base_url))
                    .appendPath(getString(R.string.ep_register))
                    .build();

            //build the Json object from the credentials
            JSONObject msg = credentials.asJSONObject();
            mCredentials = credentials;

            //attempt to login using the web service
            new SendPostAsyncTask.Builder(uri.toString(), msg)
                    .onPreExecute(this::handleRegisterOnPre)
                    .onPostExecute(this::handleRegisterOnPost)
                    .onCancelled(this::handleErrorsInTask)
                    .build().execute();

            //will be in handleRegisterOnPost method eventually
//            mCredentials = new Credentials.Builder(email.toString(),
//                    password1.toString()).build();
//            mListener.onRegisterSuccess(mCredentials);
        }
    }

    /** Alert the listener in app validation has been passed and the registrationButton has
     * been clicked. The async task has started. The listener will load the onWaitFragment
     * to the screen view.
     */
    private void handleRegisterOnPre() {
     mListener.onWaitFragmentInteractionShow();
    }

    /** Handle onPostExecute of the AsynceTask. The result from our webservice is
     * a JSON formatted String. Parse it for success or failure.
     * @param result the JSON formatted String response from the web service
     *               this comes from the value returned from doInBackground in
     *               sendPostAsyncTask
     */
    private void handleRegisterOnPost(String result) {
        try {
            Log.d("JSON result", result);

            JSONObject resultsJSON = new JSONObject(result);
            boolean success = resultsJSON.getBoolean("success");

            mListener.onWaitFragmentInteractionHide();
            if (success) {
                //Login was successful. Inform the Activity so it can do its thing.
                mListener.onRegisterSuccess(mCredentials);
            } else {
                //Login was unsuccessful. Don’t switch fragments and inform the user
                ((TextView) getView().findViewById(R.id.editText_email_reg))
                        .setError("Login Unsuccessful");
            }

        } catch (JSONException e) { // user will not be logged in
            // web service didn't return a the expected JSON formatted string
            Log.e("JSON_PARSE_ERROR", result + System.lineSeparator()
                    + e.getMessage());
            mListener.onWaitFragmentInteractionHide();
            ((TextView) getView().findViewById(R.id.editText_email_reg))
                    .setError("Login Unsuccessful");
        }
    }

    /** Report the error from the asynct task started from this.onRegisterClicked. */
    private void handleErrorsInTask(String message) {
        Log.e("ASYNCT_TASK_ERROR", message);
    }

    /**
     * Check if TextView fields are blank.
     *
     * @return true if no fields blank
     */
    private boolean checkForBlankFields() {

        boolean user, first, last,
                email, pass1, pass2;

        first = UserLoginValidation.isFieldBlank(this.firstName, getView());
        last = UserLoginValidation.isFieldBlank(this.lastName, getView());
        user = UserLoginValidation.isFieldBlank(this.userName, getView());
        email = UserLoginValidation.isFieldBlank(this.email, getView());
        pass1 = UserLoginValidation.isFieldBlank(this.password1, getView());
        pass2 = UserLoginValidation.isFieldBlank(this.password2, getView());

        return email || pass1 || pass2 || first || last || user;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterFragmentInteractionListener) {
            mListener = (OnRegisterFragmentInteractionListener) context;
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
     * Allert listeners when Register has sucecssfuly registered.
     * Also extends WaitFragement with its show and wait methods.
     */
    public interface OnRegisterFragmentInteractionListener extends
        WaitFragment.OnFragmentInteractionListener {

        void onRegisterSuccess(Credentials credentials);
    }
}

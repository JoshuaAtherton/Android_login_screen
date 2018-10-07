package tcss450.uw.edu.phishapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserRegistrationFragment.OnFragmentInteractionListenerUserReg} interface
 * to handle interaction events.
 */
public class UserRegistrationFragment extends Fragment {

    private OnFragmentInteractionListenerUserReg mListener;
    private EditText usernameText;
    private EditText password1Text;
    private EditText password2Text;

    public UserRegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_registration, container, false);

        usernameText = v.findViewById(R.id.editText_username_reg);
        password1Text = v.findViewById(R.id.editText_password1_reg);
        password2Text = v.findViewById(R.id.editText_password2_reg);

        // Add a listener for the register button
        Button b = (Button) v.findViewById(R.id.button_register_reg);
        v.setOnClickListener(this::registerButtonClicked);

        return v;
    }

    public void registerButtonClicked(View view) {
        boolean usernameFilled = true, password1Filled = true, password2Filled = true;
        boolean passwordsMatch = true, passwordLengthValid = true;
        //are no fields blank
        if (UserLoginValidation.isFieldBlank(usernameText.getText().toString())) {
            usernameText.setError(getResources().getString(R.string.empty_field_prompt));
            usernameFilled = false;
        }
        if (UserLoginValidation.isFieldBlank(password1Text.getText().toString())) {
            password1Text.setError(getResources().getString(R.string.empty_field_prompt));
            password1Filled = false;
        }
        if (UserLoginValidation.isFieldBlank((password2Text.getText().toString()))) {
            password2Text.setText(getResources().getText(R.string.empty_field_prompt));
            password2Filled = false;
        }
        //passwords are too short
        if (!UserLoginValidation.passwordLengthValid(password1Text.getText().toString()) ||
                !UserLoginValidation.passwordLengthValid(password2Text.getText().toString()) ) {
            password1Text.setError(getResources().getString(R.string.password_too_short_prompt));
            password2Text.setError(getResources().getString(R.string.password_too_short_prompt));
            passwordLengthValid = false;
        }
        //passwords do not match
        if (UserLoginValidation.passwordsMatch(password1Text.getText().toString(),
                password2Text.getText().toString())) {
            password1Text.setError(getResources().getString(R.string.passwords_do_not_match_prompt));
            password2Text.setError(getResources().getString(R.string.passwords_do_not_match_prompt));
            passwordsMatch = false;
        }
        //passwords do not match and are too short
        if (!passwordLengthValid && !passwordsMatch) {
            password1Text.setError(getResources()
                    .getString(R.string.pass_not_match_and_short_prompt));
            password2Text.setError(getResources()
                    .getString(R.string.pass_not_match_and_short_prompt));
        }
        //if all fields valid send message to activity to launch DisplayFragment
        if (usernameFilled && password1Filled && password2Filled && passwordsMatch
                && passwordLengthValid) {
            mListener.onFragmentInteractionRegClickedUserReg(usernameText.getText().toString(),
                    password1Text.getText().toString());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListenerUserReg) {
            mListener = (OnFragmentInteractionListenerUserReg) context;
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
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListenerUserReg {

        void onFragmentInteractionRegClickedUserReg(String username, String password);
    }
}

package tcss450.uw.edu.phishapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
        b.setOnClickListener(this::registerButtonClicked);
        Log.d("UserRegistrationFragment", "creating view");
        return v;
    }

    //for testing button
    public void registerButtonClicked(View view) {
        Log.d("UserRegistrationFragment", "Register button Clicked");

        boolean noBlankFields = checkForBlankFields();
        boolean passMatching, passlength = false;

        //validate passwords
        if (password1Text.getText().toString().equals(password2Text.getText().toString())) {
            passMatching = true;
            if (password1Text.getText().toString().length() >= 6) {
                passlength = true;
            } else {
                password1Text.setError(getResources()
                        .getString(R.string.password_too_short_prompt));
                password2Text.setError(getResources()
                        .getString(R.string.password_too_short_prompt));
                passlength = false;
            }
        } else {
            password1Text.setError(getResources()
                    .getString(R.string.passwords_do_not_match_prompt));
            password2Text.setError(getResources()
                    .getString(R.string.passwords_do_not_match_prompt));
            passMatching = false;
        }
        // if all fields are valid send message through interface
        if (noBlankFields && passMatching && passlength) {
            mListener.onFragmentInteractionRegClickedFromUserReg(
                    usernameText.getText().toString(), password1Text.getText().toString());
        }
    }

    private boolean checkForBlankFields() {
        boolean username = true, pass1 = true, pass2 = true;
        if (UserLoginValidation.isFieldBlank(usernameText.getText().toString())) {
            usernameText.setError(getResources().getString(R.string.empty_field_prompt));
            username = false;
        }
        if (UserLoginValidation.isFieldBlank(password1Text.getText().toString())) {
            password1Text.setError(getResources().getString(R.string.empty_field_prompt));
            pass1 = false;
        }
        if (UserLoginValidation.isFieldBlank(password2Text.getText().toString())) {
            password2Text.setError(getResources().getString(R.string.empty_field_prompt));
            pass2 = false;
        }
        return username && pass1 && pass2;
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

        void onFragmentInteractionRegClickedFromUserReg(String username, String password);
    }
}

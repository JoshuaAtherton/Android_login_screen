package tcss450.uw.edu.phishapp;

import android.content.Context;
import android.net.Credentials;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
 * {@link OnRegisterFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RegistrationFragment extends Fragment {

    private static final int PASSWORD_LENGTH = 6;
    private OnRegisterFragmentInteractionListener mListener;
    private EditText usernameText;
    private EditText password1Text;
    private EditText password2Text;
    private View v;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_registration, container, false);

        usernameText = v.findViewById(R.id.editText_username_reg);
        password1Text = v.findViewById(R.id.editText_password1_reg);
        password2Text = v.findViewById(R.id.editText_password2_reg);

        // Add a listener for the register button
        Button b = v.findViewById(R.id.button_register_reg);
        b.setOnClickListener(this::registerButtonClicked);
        Log.d("RegistrationFragment", "creating view");
        return v;
    }

    //for testing button
    public void registerButtonClicked(View view) {
        Log.d("RegistrationFragment", "in Register button Clicked");
        boolean atSymbol,
                noBlankFields,
                passMatching,
                passLength = false;

        atSymbol = !UserLoginValidation.hasAtSymbol(usernameText, v);
        noBlankFields = checkForBlankFields();
        passMatching =
                password1Text.getText().toString().equals(password2Text.getText().toString());

        if (passMatching) {
            passLength = password1Text.getText().toString().length() >= PASSWORD_LENGTH;
            if (!passLength) {
                password1Text.setError(getResources().getString(R.string.password_too_short_prompt));
                password2Text.setError(getResources().getString(R.string.password_too_short_prompt));
            }
        } else {
            password1Text.setError(getResources().getString(R.string.passwords_do_not_match_prompt));
            password2Text.setError(getResources().getString(R.string.passwords_do_not_match_prompt));
        }

        // if all fields are valid send message through interface
        if (noBlankFields && atSymbol && passMatching && passLength) {
            Log.d("RegistrationFragment", "Register button Clicked sending interface" +
                    "prompt");
            mListener.onRegisterAttempt(null);
        }
    }

    /**
     * Check if TextView fields are blank.
     * @return true if no fields blank
     */
    private boolean checkForBlankFields() {

        boolean username,
                pass1,
                pass2;

        username = UserLoginValidation.isFieldBlank(usernameText, v);
        pass1 = UserLoginValidation.isFieldBlank(usernameText, v);
        pass2 = UserLoginValidation.isFieldBlank(usernameText, v);

        return !(username && pass1 && pass2);
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnRegisterFragmentInteractionListener {

        void onRegisterAttempt(Credentials credentials);
    }
}

package tcss450.uw.edu.phishapp;

import android.content.Context;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserLoginFragment.OnFragmentUserLoginInteractionListener} interface
 * to handle interaction events.
 */
public class UserLoginFragment extends Fragment {

    private OnFragmentUserLoginInteractionListener mListener;
    private EditText username;
    private EditText password;
    private View v;

    public UserLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_user_login, container, false);
        Log.d("UserLoginFragment", "inflating the fragment");

        // set up username & password text fields
        username = v.findViewById(R.id.editText_username);
        password = v.findViewById(R.id.editText_password);

        //set up login and register buttons
        Button b = v.findViewById(R.id.button_login);
        b.setOnClickListener(this::loginClicked);

        b = v.findViewById(R.id.button_register);
        b.setOnClickListener(this::registerClicked);

        return v;
    }

    // if all rules passed call RegisterLoginActivity to launch display fragment
    public void loginClicked(View view) {
        boolean userNameBlank,
                passwordBlank,
                hasAtSymbol ;

        hasAtSymbol = !UserLoginValidation.hasAtSymbol(username, v);
        userNameBlank = UserLoginValidation.isFieldBlank(username, v);
        passwordBlank = UserLoginValidation.isFieldBlank(password, v);

        // notify listeners
        if (!userNameBlank && !passwordBlank && hasAtSymbol) {
            mListener.onFragmentInteractionLoginClickedUserLogin(username.getText().toString(),
                    password.getText().toString());
        }
    }

    // call RegisterLoginActivity to launch UserRegistrationFragment
    public void registerClicked(View view) {
        mListener.onFragmentInteractionRegisterClickedFromUserLogin();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentUserLoginInteractionListener) {
            mListener = (OnFragmentUserLoginInteractionListener) context;
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
    public interface OnFragmentUserLoginInteractionListener {
        void onFragmentInteractionLoginClickedUserLogin(String username, String password);
        void onFragmentInteractionRegisterClickedFromUserLogin();
    }
}

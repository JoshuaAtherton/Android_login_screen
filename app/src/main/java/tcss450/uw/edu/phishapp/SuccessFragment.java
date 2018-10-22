package tcss450.uw.edu.phishapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SuccessFragment extends Fragment {

    public SuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View v = inflater.inflate(R.layout.fragment_success, container, false);
        Log.d("SuccessFragment", "creating the Display Fragment");

        Button b = v.findViewById(R.id.LogoutButton_successFrag);
        b.setOnClickListener(this::logoutClickedSuccess);

        return v;
    }

    /**
     * Log the user out of the app and redirect to home screen.
     * @param button the logout button
     */
    public void logoutClickedSuccess(View button) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Log.d("SuccessFragment", "does it still run in method after call " +
                "to startActivity on a new intent?"); //the answer is yes!
        getActivity().finish(); //finish the current activity
    }

}

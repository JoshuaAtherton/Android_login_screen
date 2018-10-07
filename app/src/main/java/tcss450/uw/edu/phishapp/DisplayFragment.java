package tcss450.uw.edu.phishapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment {

    public DisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_display, container, false);
        Log.d("DisplayFragment", "creating the Display Fragment");

        Bundle args = getArguments();
        String[] userInfo = args.getStringArray(getResources().getString(R.string.user_info_key));

        TextView username = v.findViewById(R.id.textView_username_result);
        TextView password = v.findViewById(R.id.textView_password_result);

        //set the text fields
        username.setText(userInfo[0]);
        password.setText(userInfo[1]);

        return v;
    }

}

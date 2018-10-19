package tcss450.uw.edu.phishapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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

        // use to be for setting username and password
//        Intent intent = getActivity().getIntent(); // get the intent that started this
//        if (intent != null) {
//            Bundle args = intent.getExtras(); // get if the extras were set from starter
//            if (args != null) {
//                String[] userInfo = args.getStringArray(getResources().getString(R.string.user_info_key));
//
//            }
//        }

        return v;
    }

}

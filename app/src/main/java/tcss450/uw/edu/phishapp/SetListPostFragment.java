package tcss450.uw.edu.phishapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tcss450.uw.edu.phishapp.setlist.SetListPost;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSetListPostFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SetListPostFragment extends Fragment {

    private OnSetListPostFragmentInteractionListener mListener;
    private SetListPost mSetListPost;
    private TextView mLongDate;
    private TextView mLocation;
    private TextView mSetListData;
    private TextView mSetListNotes;

    public SetListPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_list_post, container, false);

        setUpTextFields();
        return view;
    }

    private void setUpTextFields() {
        if (mSetListPost != null) {

        }
    }

    /**
     * Action to take when the button is clicked to view full setList in browser.
     * @param viewSetListButton
     */
    public void viewFullSetListButton(View viewSetListButton) {
        if (mListener != null) {
            mListener.viewFullSetList(mSetListPost.getUrl());
        }
    }

    /** To set the SetListPost object of this fragment. */ //todo: needed????
    public void setSetListPost(SetListPost setListPost) { mSetListPost = setListPost; }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSetListPostFragmentInteractionListener) {
            mListener = (OnSetListPostFragmentInteractionListener) context;
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
     *
     */
    public interface OnSetListPostFragmentInteractionListener {

        void viewFullSetList(String url);
    }
}

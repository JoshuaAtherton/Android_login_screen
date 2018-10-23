package tcss450.uw.edu.phishapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSetListPostFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SetListPostFragment extends Fragment {

    private OnSetListPostFragmentInteractionListener mListener;

    public SetListPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_list_post, container, false);
    }

    /**
     * Clicked button to view full setList in browser.
     * @param viewSetListButton
     */
    public void viewFullSetListButton(View viewSetListButton) {
        if (mListener != null) {
            mListener.viewFullSetListFragmentInteraction();
        }
    }

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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSetListPostFragmentInteractionListener {

        void viewFullSetListFragmentInteraction();
    }
}

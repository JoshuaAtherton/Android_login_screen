package tcss450.uw.edu.phishapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tcss450.uw.edu.phishapp.SetListFragment.OnSetListFragmentInteractionListener;
import tcss450.uw.edu.phishapp.setlist.SetListPost;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SetListPost} and makes a call to the
 * specified {@link OnSetListFragmentInteractionListener}.
 */
public class MySetListRecyclerViewAdapter extends RecyclerView.Adapter<MySetListRecyclerViewAdapter.ViewHolder> {
    /** List of SetListPost obj that will make up the list on setList */
    private final List<SetListPost> mValues;
    private final SetListFragment.OnSetListFragmentInteractionListener mListener;

    public MySetListRecyclerViewAdapter(List<SetListPost> items, SetListFragment.OnSetListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_setlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mLongDate.setText(mValues.get(position).getLongDate());
        holder.mLocation.setText(mValues.get(position).getLocation());
        holder.mVenue.setText(SetListPost.stripStringOfHtml(mValues.get(position).getVenue()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSetListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mLongDate;
        public final TextView mLocation;
        public final TextView mVenue;
        public SetListPost mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mLongDate = (TextView) view.findViewById(R.id.textView_longDate_setListFrag);
            mLocation = (TextView) view.findViewById(R.id.textView_location_setListFrag);
            mVenue = (TextView) view.findViewById(R.id.textView_venue_setListFrag);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLocation.getText() + "'";
        }
    }
}

package tcss450.uw.edu.phishapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import tcss450.uw.edu.phishapp.BlogFragment.OnListBlogFragmentInteractionListener;
import tcss450.uw.edu.phishapp.blog.BlogPost;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BlogPost} and makes a call to the
 * specified {@link OnListBlogFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBlogRecyclerViewAdapter extends RecyclerView.Adapter<MyBlogRecyclerViewAdapter.ViewHolder> {

    private final List<BlogPost> mValues;
    private final OnListBlogFragmentInteractionListener mListener;

    public MyBlogRecyclerViewAdapter(BlogPost[] items, OnListBlogFragmentInteractionListener listener) {
        mValues = Arrays.asList(items);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_blog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mPublishDate.setText(mValues.get(position).getPubDate());
        //get the teaser with html removed
        holder.mBlogTeaser.setText(mValues.get(position).getStripedHtmlTeaser());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
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
        public final TextView mTitle;
        public final TextView mPublishDate;
        public final TextView mBlogTeaser;
        public BlogPost mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.textView_blogTitle_blogFrag);
            mPublishDate = (TextView) view.findViewById(R.id.textView_publishDate_blogFrag);
            mBlogTeaser = (TextView) view.findViewById(R.id.textView_blogTeaser_blogFrag);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mPublishDate.getText() + "'";
        }
    }
}

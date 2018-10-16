package tcss450.uw.edu.phishapp;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tcss450.uw.edu.phishapp.BlogFragment.OnListBlogFragmentInteractionListener;
import tcss450.uw.edu.phishapp.blog.BlogPost;

import java.util.Arrays;
import java.util.List;

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

        holder.mBlogTeaser.setText(stripHtml(mValues.get(position).getTeaser()));

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

    /**
     * Take in a string and return a new version of that string with html elements removed.
     * @param s the string to remove html from
     * @return a string cleaned of html tags
     */
    private String stripHtml(String s) {
        Spanned span = Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT);
        return span.toString();
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

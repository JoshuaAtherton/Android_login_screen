package tcss450.uw.edu.phishapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tcss450.uw.edu.phishapp.blog.BlogPost;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlogPostFragment.OnBlogPostFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class BlogPostFragment extends Fragment {

    private OnBlogPostFragmentInteractionListener mListener;
    private BlogPost mBlogPost;
    TextView mTitle;
    TextView mPublishDate;
    TextView mFullTeaser;

    public BlogPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blog_post, container, false);

        Button b = (Button) v.findViewById(R.id.button_fullPost_blogPost);
        b.setOnClickListener(this::viewFullPost);

        mTitle = v.findViewById(R.id.textView_title_blogPost);
        mPublishDate = v.findViewById(R.id.textView_publishDate_blogPost);
        mFullTeaser = v.findViewById(R.id.textView_fullTeaser_blogPost);

        setupTextFields();

        return v;
    }

    /**
     * Setup the text in blog posts from the Blog Post
     */
    private void setupTextFields() {
        if (mBlogPost != null) {
            mTitle.setText(mBlogPost.getTitle());
            mPublishDate.setText(mBlogPost.getPubDate());
//            mFullTeaser.setText(mBlogPost.getTeaser()); //get with html
            mFullTeaser.setText(mBlogPost.getStripedHtmlTeaser());
        }
    }

    /**
     * Notify the listeners that we want to see the full post on a website.
     * @param view
     */
    public void viewFullPost(View view) {
        if (mListener != null) {
            mListener.viewFullPostFragmentInteraction(mBlogPost.getUrl());
        }
    }

    /**
     * To set this instance BlogPost field.
     * @param blogPost
     */
    public void setBlogPost(BlogPost blogPost) {
        mBlogPost = blogPost;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBlogPostFragmentInteractionListener) {
            mListener = (OnBlogPostFragmentInteractionListener) context;
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
     * Must be implemented.
     */
    public interface OnBlogPostFragmentInteractionListener {

        void viewFullPostFragmentInteraction(String url);
    }
}

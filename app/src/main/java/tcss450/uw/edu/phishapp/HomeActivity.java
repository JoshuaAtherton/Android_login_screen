package tcss450.uw.edu.phishapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.phishapp.blog.BlogPost;
import tcss450.uw.edu.phishapp.setlist.SetListPost;
import tcss450.uw.edu.phishapp.utils.GetAsyncTask;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BlogFragment.OnListBlogFragmentInteractionListener,
        BlogPostFragment.OnBlogPostFragmentInteractionListener,
        SetListFragment.OnSetListFragmentInteractionListener,
        SetListPostFragment.OnSetListPostFragmentInteractionListener,
        WaitFragment.OnFragmentInteractionListener              {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // add display fragment to this frame
        if (savedInstanceState == null) {
            if (findViewById(R.id.homeActivity_fragmentContainer) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.homeActivity_fragmentContainer, new SuccessFragment()).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPreferences prefs =
                getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);
        //remove the saved credentials from StoredPrefs
        prefs.edit().remove(getString(R.string.keys_prefs_password)).apply();
        prefs.edit().remove(getString(R.string.keys_prefs_email)).apply();

        //close the app
        finishAndRemoveTask();

        //or close this activity and bring back the Login
        //Intent i = new Intent(this, MainActivity.class);
        //startActivity(i);
        //End this Activity and remove it from the Activity back stack.
        //finish();
    }


    /**
     * Load the fragment into the homeActivity frame.
     * @param frag
     */
    private void loadFragment(Fragment frag) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeActivity_fragmentContainer, frag)
                .addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.homeActivity_fragmentContainer, new SuccessFragment());
            // don't add to the back stack if already on display fragment
            // prevent multiple home button clicks adding to back stack
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.homeActivity_fragmentContainer);
            if(!(currentFragment instanceof SuccessFragment)) {
                  transaction.addToBackStack(null);
            }
            transaction.commit();

        } else if (id == R.id.nav_blog_posts) {
            Uri uri = new Uri.Builder()
                    .scheme("https")
                    .appendPath(getString(R.string.ep_base_url))
                    .appendPath(getString(R.string.ep_phish))
                    .appendPath(getString(R.string.ep_blog))
                    .appendPath(getString(R.string.ep_get))
                    .build();

            new GetAsyncTask.Builder(uri.toString())
                    .onPreExecute(this::onWaitFragmentInteractionShow)
                    .onPostExecute(this::handleBlogGetOnPostExecute)
                    .build().execute();
        } else if (id == R.id.nav_set_lists){
            // load set Lists
            Uri uri = new Uri.Builder().scheme("https")
                    .appendPath(getString(R.string.ep_base_url))
                    .appendPath(getString(R.string.ep_phish))
                    .appendPath(getString(R.string.ep_setlists))
                    .appendPath(getString(R.string.ep_recent))
                    .build();
            // get the setLists
            Log.d("HomeActivity", "Nav setList clicked");
            new GetAsyncTask.Builder(uri.toString())
                    .onPreExecute(this::onWaitFragmentInteractionShow)
                    .onPostExecute(this::handleSetListGetOnPostExecute)
                    .build().execute();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Get the blog posts from the web blog.
     * @param result
     */
    private void handleBlogGetOnPostExecute(final String result) {
        //parse JSON
        try {
            JSONObject root = new JSONObject(result);
            if (root.has("response")) {
                JSONObject response = root.getJSONObject("response");
                if (response.has("data")) {
                    JSONArray data = response.getJSONArray("data");

                    List<BlogPost> blogs = new ArrayList<>();

                    for(int i = 0; i < data.length(); i++) {
                        JSONObject jsonBlog = data.getJSONObject(i);
                        blogs.add(new BlogPost.Builder(jsonBlog.getString("pubdate"),
                                jsonBlog.getString("title"))
                                .addTeaser(jsonBlog.getString("teaser"))
                                .addUrl(jsonBlog.getString("url"))
                                .build());
                    }

                    BlogPost[] blogsAsArray = new BlogPost[blogs.size()];
                    blogsAsArray = blogs.toArray(blogsAsArray);


                    Bundle args = new Bundle();
                    args.putSerializable(BlogFragment.ARG_BLOG_LIST, blogsAsArray);
                    Fragment frag = new BlogFragment();
                    frag.setArguments(args);

                    onWaitFragmentInteractionHide();
                    loadFragment(frag);
                } else {
                    Log.e("ERROR!", "No data array");
                    //notify user
                    onWaitFragmentInteractionHide();
                }
            } else {
                Log.e("ERROR!", "No response");
                //notify user
                onWaitFragmentInteractionHide();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
            //notify user
            onWaitFragmentInteractionHide();
        }
    }

    /**
     * Get the setList posts.
     * @param result
     */
    private void handleSetListGetOnPostExecute(final String result) {
        //parse JSON
        Log.d("HomeActivity","On handleSetListGetOnPostExecute");
        try {
            JSONObject root = new JSONObject(result);
            if (root.has("response")) {
                JSONObject response = root.getJSONObject("response");
                if (response.has("data")) {
                    JSONArray data = response.getJSONArray("data");
                    List<SetListPost> setLists = new ArrayList<>();

                    //get the data from the json formatted object into a SetListPost object
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonSetList = data.getJSONObject(i);
                        setLists.add(new SetListPost
                                .Builder(jsonSetList.getString("long_date"),
                                    jsonSetList.getString("location"))
                                .addUrl(jsonSetList.getString("url"))
                                .addSetListData(jsonSetList.getString("setlistdata"))
                                .addSetListNotes(jsonSetList.getString("setlistnotes"))
                                .addVenue(jsonSetList.getString("venue"))
                                .build());
                    }
                    //convert the setLists List to an array object
                    SetListPost[] setListsAsArray = new SetListPost[setLists.size()];
                    setListsAsArray = setLists.toArray(setListsAsArray);
                    //Bundle the setListPost array for easy retrieval later
                    Bundle bArgs = new Bundle();
                    bArgs.putSerializable(SetListFragment.ARG_SET_LIST, setListsAsArray);

                    Fragment frag = new SetListFragment();
                    frag.setArguments(bArgs);
                    // hide waiting screen & start SetListFragment with the list view overview
                    onWaitFragmentInteractionHide();
                    loadFragment(frag);
                } else {
                    Log.e("HomeActivity ERROR", "No 'data' key");
                    onWaitFragmentInteractionHide();
                }
            } else {
                Log.e("HomeActivity ERROR", "No 'response' key");
                onWaitFragmentInteractionHide();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HomeActivity ERROR!", e.getMessage());
            onWaitFragmentInteractionHide();
        }
    }

    /**
     *  Method call from BlogFragment.
     * @param blogItem A BlogPost item
     */
    @Override
    public void onListBlogFragmentInteraction(BlogPost blogItem) {

        BlogPostFragment blogPostFrag = new BlogPostFragment();
        blogPostFrag.setBlogPost(blogItem);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.homeActivity_fragmentContainer, blogPostFrag)
                .addToBackStack(null).commit();
    }

    /**
     *  From BlogPostFragment open a website from the chosen url.
     * @param url String representation of the url to go to.
     */
    @Override
    public void viewFullPostFragmentInteraction(String url) {
        Log.d("HomeActivity", "blogPostFragment view full post button clicked");
        //launch blog post in browser
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    /**
     * Replace current fragment with current selected post of type SetListFragment.
     * @param item selected setListPost item to view
     */
    @Override
    public void onSetListFragmentInteraction(SetListPost item) {
        SetListPostFragment setListPostFrag = new SetListPostFragment();
        setListPostFrag.setSetListPost(item);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.homeActivity_fragmentContainer, setListPostFrag)
                .addToBackStack(null).commit();
    }

    /**
     * Button clicked to launch full post content in internet browser.
     * @param url
     */
    @Override
    public void viewFullSetList(String url) {
        Log.d("HomeActivity", "SetListPostFragment view full setList button clicked");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void onWaitFragmentInteractionShow() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.homeActivity_fragmentContainer, new WaitFragment(), "WAIT")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onWaitFragmentInteractionHide() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(getSupportFragmentManager().findFragmentByTag("WAIT"))
                .commit();
    }

}

package tcss450.uw.edu.phishapp.setlist;

import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;

/**
 * Must supply the show date and location to build, SetListData and SetListNotes
 * optional.
 */
public class SetListPost implements Serializable {

    private final String mLongDate;
    private final String mLocation;

    private final String mSetListData;
    private final String mSetListNotes;


    /**
     * Helper class using the builder pattern.
     */
    public static class Builder {
        private final String mLongDate;
        private final String mLocation;

        private String mSetListData = "";
        private String mSetListNotes = "";

        /**
         *  Construct the builder.
         * @param longDate the date of the show.
         * @param location the location of the show.
         */
        public Builder(String longDate, String location) {
            this.mLongDate = longDate;
            this.mLocation = location;
        }

        /**
         * Add optional set list data.
         * @param data optional set list data
         * @return the builder of setListPost
         */
        public Builder addSetListData(final String data) {
            mSetListData = data;
            return this;
        }

        public Builder addSetListNotes(final String notes) {
            mSetListNotes = notes;
            return this;
        }

        /**
         * Construct the SetListPost object.
         * @return fully initialized SetListPostObject
         */
        public SetListPost build() {
            return new SetListPost(this);
        }
    }

    /**
     * Private to prevent outer instantiation.
     * @param builder
     */
    private SetListPost(final Builder builder) {
        this.mLongDate = builder.mLongDate;
        this.mLocation = builder.mLocation;
        this.mSetListData = builder.mSetListData;
        this.mSetListNotes = builder.mSetListNotes;
    }

    /**
     * Take in a string and return a new version of that string with html elements removed.
     * @return a string cleaned of html tags
     */
    public String stripStringOfHtml(final String text) {
        Spanned span = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        return span.toString();
    } //todo: use as static of just do within classes?

    /** Getters */

    public String getLongDate() { return mLongDate; }

    public String getLocation() { return mLocation; }

    public String getSetListData() { return mSetListData; }

    public String getSetListNotes() { return mSetListNotes; }

}

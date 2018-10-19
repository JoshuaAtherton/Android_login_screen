package tcss450.uw.edu.phishapp.model;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import tcss450.uw.edu.phishapp.R;


/**
 * Provides methods for client side user name and password validation.
 */
public class UserLoginValidation {

    public static boolean isFieldBlank(EditText field, View view) {
        boolean isBlank = false;
        if (field.getText().toString().length() <= 0) {
            field.setError(view.getResources().getString(R.string.empty_field_prompt));
            isBlank = true;
        }
        return isBlank;
    }

    public static boolean hasAtSymbol(TextView field, View view) {
        boolean hasAtSymbol = false;
        if (!UserLoginValidation.searchForChar(field.getText().toString())) {
            field.setError(view.getResources().getString(R.string.missing_at_symbol_prompt));
            hasAtSymbol = true;
        }
        return hasAtSymbol;
    }

    private static boolean searchForChar(String text) {
        boolean found = false;
        for(int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '@') {
                found = true;
            }
        }
        return found;
    }

}

package tcss450.uw.edu.phishapp;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

class UserLoginValidation {

    static boolean isFieldBlank(EditText field, View view) {
        boolean isBlank = false;
        if (field.getText().toString().length() <= 0) {
            field.setError(view.getResources().getString(R.string.empty_field_prompt));
            isBlank = true;
        }
        return isBlank;
    }

    static boolean hasAtSymbol(TextView field, View view) {
        boolean hasAtSymbol = true;
        if (!UserLoginValidation.searchForChar(field.getText().toString())) {
            field.setError(view.getResources().getString(R.string.missing_at_symbol_prompt));
            hasAtSymbol = false;
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

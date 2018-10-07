package tcss450.uw.edu.phishapp;

public class UserLoginValidation {

    public static boolean isFieldBlank(String text) {
        return text.length() <= 0;
    }

    public static boolean passwordHasAtSymbol(String text) {
        boolean found = false;
        for(int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '@') {
                found = true;
            }
        }
        return found;
    }

}

package mx.edu.utez.SARTI_APIS.modules.auth.service;

public class AuthMessages {
    ////// GOOD RESPONSES ///////
    public static final String VERIFICATION_CODE_VALIDATED = "Verification code validated";
    public static final String ACTIVATED_USER = "User activated successfully";
    public static final String VERIFICATION_CODE = "Verification code sent";
    public static final String SIGN_IN = "User signed in successfully";
    public static final String SIGN_UP = "User signed up successfully";
    public static final String CONFIRMED = "User confirmed successfully";

    ////// BAD RESPONSES ///////

    public static final String VERIFICATION_CODE_NOT_FOUND = "Verification code not found";
    public static final String NOT_FOUND = "User not found";
    public static final String DISABLED = "The user is has not been confirmed";
    public static final String BLOCKED = "User is blocked";
    public static final String ALREADY_EXISTS = "User already exists";
    public static final String NOT_CONFIRMED = "Code incorrect or time expired";
    public static final String EMAIL_SENT = "Email sent correctly";
    public static final String EMAIL_NOT_SENT = "Email not sent";
    public static final String EMAIL_BAD_FORMAT = "Email bad format";
    public static final String PASSWORD_BAD_FORMAT = "Password bad format";
    public static final String ROLE_ILLEGAL = "Role not allowed";

    ///// ERROR RESPONSES /////

    public static final String ERROR_USER_NOT_SAVED = "Error saving user";
    public static final String PASSWORD_USER_NOT_MATCH = "Password and user do not match";
    public static final String ERROR_SIGNING_UP = "Error signing up";
    public static final String ERROR_CONFIRMING = "Error confirming user";
    public static final String ERROR_SENDING_EMAIL = "Error sending code";
    public static final String ERROR_ACTIVATING_USER = "Error activating user";


    //add a private constructor to hide the implicit public one
    private AuthMessages() {
        throw new IllegalStateException("Utility class");
    }
}

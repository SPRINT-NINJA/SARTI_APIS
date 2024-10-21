package mx.edu.utez.SARTI_APIS.kernel;

public enum ErrorMessages {

    // --------------- GENERAL ERRORS ---------------

    // Action Failures
    NO_DATA_FOUND,
    RECORD_NOT_REGISTERED,
    RECORD_NOT_UPDATED,
    RECORD_NOT_DELETED,
    RECORD_HAS_DEPENDENCIES,
    RECORDS_ARE_NOT_RELATED,

    // Constraint Violations
    DUPLICATED_RECORD,

    // Payload Errors
    DATA_TYPE_WRONG,
    INVALID_DATE,
    INVALID_FIELDS,
    MISSING_FIELDS,
    MISSING_PARAMETERS,
    REVIEW_REQUEST,
    MULTIPLE_ERRORS,


    // --------------- AUTHENTICATION ERRORS ---------------

    // Action Failures
    CREDENTIALS_MISMATCH,
    CANNOT_ACCESS_TO_RESOURCE,
    USER_IS_NOT_VERIFIED,
    ERROR_SENDING_EMAIL,

    // Constraint Violations
    USER_IS_BLOCKED,
    USER_IS_INACTIVE,


    // --------------- USER ERRORS ---------------

    // Action Failures
    NO_USER_FOUND,
    USER_HAS_DEPENDENCIES,
    DUPLICATED_USER,
    INCORRECT_CURRENT_PASSWORD,
    PASSWORDS_ARE_THE_SAME,


    // --------------- VERIFICATION CODE ERRORS ---------------

    // Action Failures
    NO_VERIFICATION_CODE_FOUND,
    VERIFICATION_CODE_IS_EXPIRED,
    VERIFICATION_CODE_IS_INVALID,
    VERIFICATION_CODE_WAS_ALREADY_USED,
    ACCOUNT_NOT_ACTIVATED,
    ERROR_SENDING_CODE,
    NO_CODES_REMAINING,


    // --------------- SERVER ERRORS ---------------

    SERVER_ERROR
}

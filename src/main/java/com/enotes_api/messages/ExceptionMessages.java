package com.enotes_api.messages;

public class ExceptionMessages {

    // Category Related Exception Messages
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category Not Found With ID = ";

    public static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "Category already exists";

    // Notes Related Exception Messages
    public static final String NOTES_NOT_FOUND_MESSAGE = "Notes Not Found With ID = ";

    //File Related Exception Messages
    public static final String FILE_EXTENSION_MISSING_MESSAGE = "File must have an extension.";

    public static final String INVALID_FILE_NAME_MESSAGE = "Invalid file name format. File name should contain only one dot.";

    public static final String UNSUPPORTED_FILE_EXTENSION_MESSAGE = "Unsupported file extension: ";

    public static final String FILE_NOT_EXISTS_MESSAGE = "Requested file not found";

    public static final String UNABLE_TO_PROCESS_FILE = "Unable to process file";

    // To Do Related Exception Messages
    public static final String TODO_NOT_FOUND_MESSAGE = "Todo Not Found With ID = ";

    // Role Related Exception Messages
    public static final String ROLE_NOT_FOUND_MESSAGE = "Role Not Found With ID = ";

    public static final String ROLE_ALREADY_EXISTS_MESSAGE = "Role already exists";

    public static final String SOME_ROLE_NOT_FOUND_MESSAGE = "One or more roles not found.";

    // User Related Exception Messages
    public static final String USER_EMAIL_ALREADY_EXISTS_MESSAGE = "Email already registered";

    public static final String MOBILE_ALREADY_EXISTS_MESSAGE = "Mobile already exists";

    // User Related Exception Messages
    public static final String UNABLE_TO_SEND_EMAIL = "Error sending email for the action.";

}

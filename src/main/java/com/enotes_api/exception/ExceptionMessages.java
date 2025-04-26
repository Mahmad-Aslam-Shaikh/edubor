package com.enotes_api.exception;

public class ExceptionMessages {

    // Category Related Exception Messages
    public static String CATEGORY_NOT_FOUND_MESSAGE = "Category Not Found With ID = ";

    public static String CATEGORY_ALREADY_EXISTS_MESSAGE = "Category already exists";

    // Notes Related Exception Messages
    public static String NOTES_NOT_FOUND_MESSAGE = "Notes Not Found With ID = ";

    //File Related Exception Messages
    public static String FILE_EXTENSION_MISSING_MESSAGE = "File must have an extension.";

    public static String INVALID_FILE_NAME_MESSAGE = "Invalid file name format. File name should contain only one dot.";

    public static String UNSUPPORTED_FILE_EXTENSION_MESSAGE = "Unsupported file extension: ";

    public static String FILE_NOT_EXISTS_MESSAGE = "Requested file not found";

    public static final String UNABLE_TO_PROCESS_FILE = "Unable to process file";

    // To Do Related Exception Messages
    public static String TODO_NOT_FOUND_MESSAGE = "Todo Not Found With ID = ";

    // Role Related Exception Messages
    public static String ROLE_NOT_FOUND_MESSAGE = "Role Not Found With ID = ";

    public static String ROLE_ALREADY_EXISTS_MESSAGE = "Role already exists";

    public static String SOME_ROLE_NOT_FOUND_MESSAGE = "One or more roles not found.";

}

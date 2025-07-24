package com.enotes_api.messages;

public class EmailMessages {

    // Registration Mail Details
    public static final String REGISTRATION_EMAIL_TITLE = "Edubor Registration";

    public static final String REGISTRATION_EMAIL_SUBJECT = "Registration Successful";

//    public static final String REGISTRATION_EMAIL_CONTENT = "Congratulations on Creating account with Us...!";

    public static final String REGISTRATION_EMAIL_CONTENT = """
            <html>
            <body>
                <p>Hi <strong>%s</strong>,</p>
                <p>Congratulations on creating an account with us!</p>
                <p>Please verify your account using the link below:</p>
                <p><a href="%s">Click here to verify your account</a></p>
            </body>
            </html>
            """;

    // Password Reset Mail Details
    public static final String PASSWORD_RESET_EMAIL_TITLE = "Password Reset - Edubor";

    public static final String PASSWORD_RESET_EMAIL_SUBJECT = "Password Reset Request - Edubor";

    public static final String PASSWORD_RESET_EMAIL_CONTENT = """
            <html>
            <body>
                <p>Hi <strong>%s</strong>,</p>
                <p>You have requested to reset your password.</p>
                <p>Please reset your password using the link below:</p>
                <p><a href="%s">RESET PASSWORD</a></p>
                <br>
                <p>** IGNORE THIS MAIL IF YOU DO REMEMBER YOUR PASSWORD OR YOU HAVE NOT MADE THIS REQUEST **</p>
                <br><br>Thanks
                <br>Edubor Team
            </body>
            </html>
            """;

}

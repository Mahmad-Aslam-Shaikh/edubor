package com.enotes_api.messages;

public class EmailMessages {

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


}

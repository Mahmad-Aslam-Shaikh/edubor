package com.enotes_api.constants;

public class RouteConstants {

    public static final String[] OPEN_APIs = {
            RouteConstants.HOME + "/**",
            RouteConstants.USER,
            RouteConstants.USER + RouteConstants.LOGIN
    };

    public static final String API = "/api";
    public static final String VERSION = API + "/v1";

    // Open APIs
    public static final String HOME = VERSION + "/home";
    public static final String USER_VERIFY = "/user/verify";
    public static final String USER_PASSWORD_RESET_EMAIL = "/user/password/email";
    public static final String USER_PASSWORD_VERIFY = "/user/password/verify";
    public static final String USER_PASSWORD_RESET = "/user/reset";

    // User Endpoints
    public static final String USER = VERSION + "/user";

    public static final String LOGIN = "/login";

}

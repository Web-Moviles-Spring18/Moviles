package com.cooper.cooper;

/**
 * Created by marco on 15/02/2018.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Utils {
    //Email Validation pattern
    public static final String regEx = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
    public static final String Coops_Create_Fragment = "Coops_Create_Fragment";
    public static final String Coops_List_Fragment = "Coops_List_Fragment";
    public static final String Invite_toPool_Fragment = "Invite_toPool_Fragment";

    public static final String PROPERTIES_FILE = "properties.xml";

    public static final String URL = "https://cooperapp.me";
}

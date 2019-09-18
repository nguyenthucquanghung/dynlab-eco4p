package unicorn.hust.myapplication.utils;

import okhttp3.MediaType;

public class Constant {
    public static final String USER = "user";
    public static final String LOGIN = "login";
    public static final String USERNAME = "username";
    public static final String NAME = "name";
    public static final String DOB = "dateOfBirth";

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static final String URL_LOGIN = "https://script.google.com/macros/s/AKfycbyuDu-9Qree930dObAfJWBWa-lsN210NGWFzqTCAc_90VRkrfk/exec";
    public static final String URL_REGISTER = "https://script.google.com/macros/s/AKfycby2kQE8gh0m0S8mERZxFXWfXeK55FfXTMApjy_uLWV4fJJSlsKq/exec";
    public static final String URL_VERIFY = "https://script.google.com/macros/s/AKfycbxX9sJByKmoJET9ZN0RC49mhmIKwoJdsf4RiESSwxtMzbz-M4Bb/exec";
    public static final String URL_RESEND = "https://script.google.com/macros/s/AKfycbx87tcpkCNXH7Y33ZIjqn9r5526aLfz7K0bpyr1qZH6Glv4P43m/exec";

}

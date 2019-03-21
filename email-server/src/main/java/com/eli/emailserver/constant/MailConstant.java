package com.eli.emailserver.constant;

public class MailConstant {

    public static final String SENDING = "0";

    public static final String SENDING_SUCCESS = "1";

    public static final String SENDING_FAIL = "2";

    public static final int TIMEOUT = 1;

    public static final String REGISTER_SUBJECT = "新用户注册";
    public static final String REGISTER_ROUTING_KEY="email.register";

    public static final String FROM = "357449971@qq.com";
    public static final int MAX_RETRY_COUNT = 3;
}

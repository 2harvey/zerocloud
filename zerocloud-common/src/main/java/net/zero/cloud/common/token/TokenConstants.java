package net.zero.cloud.common.token;

public class TokenConstants {
    public final static String RESOURCE_TYPE_MENU = "menu";
    public final static String RESOURCE_TYPE_BTN = "button";
    public static final Integer EX_TOKEN_ERROR_CODE = 40101;
    // 用户token异常
    public static final Integer EX_USER_INVALID_CODE = 40102;
    // 客户端token异常
    public static final Integer EX_CLIENT_INVALID_CODE = 40131;
    public static final Integer EX_CLIENT_FORBIDDEN_CODE = 40331;
    public static final Integer EX_OTHER_CODE = 500;
    public static final String CONTEXT_KEY_USER_ID = "currentUserId";
    public static final String CONTEXT_KEY_USERNAME = "currentUserName";
    public static final String CONTEXT_KEY_USER_NAME = "currentUser";
    public static final String CONTEXT_KEY_USER_TOKEN = "currentUserToken";
    public static final String JWT_KEY_X_ID = "XId";
    public static final String JWT_KEY_NAME = "name"; //微服务客户端名称或是登陆用户名称
    public static final String JWT_KEY_CODE = "code";
    
    //平台编号
    public static final String JWT_KEY_PLATFORMID = "platformId";
    //签名盐值
    public static final String JWT_KEY_SALTKEY = "saltKey";

    //新增外部服务应用id
    public static final String JWT_KEY_APPID = "appid";
}

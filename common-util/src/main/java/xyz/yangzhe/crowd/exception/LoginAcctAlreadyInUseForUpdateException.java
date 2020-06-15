package xyz.yangzhe.crowd.exception;

/**
 * @Description: 保存或更新Admin时如果检测到登录账号重复抛出这个异常
 * @Author: Yangzhe
 * @Data: 2020/6/15
 */
public class LoginAcctAlreadyInUseForUpdateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LoginAcctAlreadyInUseForUpdateException() {
        super();
    }

    public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause, boolean enableSuppression,
                                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseForUpdateException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseForUpdateException(Throwable cause) {
        super(cause);
    }
}

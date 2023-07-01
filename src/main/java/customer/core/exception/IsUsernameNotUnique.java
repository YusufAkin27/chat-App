package customer.core.exception;


public class IsUsernameNotUnique extends BusinessException {

    public IsUsernameNotUnique() {
        super("bu kullanıcı adını başka bir kullanıcıda bulunmakta");
    }
}

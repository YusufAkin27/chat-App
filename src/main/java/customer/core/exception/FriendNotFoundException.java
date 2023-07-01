package customer.core.exception;


public class FriendNotFoundException extends BusinessException {

    public FriendNotFoundException() {
        super("arkadaş bulunamadı");
    }
}

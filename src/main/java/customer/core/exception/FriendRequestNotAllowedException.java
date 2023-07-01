package customer.core.exception;


public class FriendRequestNotAllowedException extends BusinessException {

    public FriendRequestNotAllowedException() {
        super("Arkadaşlık isteği gönderme izni yok.");
    }
}

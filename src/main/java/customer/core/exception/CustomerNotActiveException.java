package customer.core.exception;


public class CustomerNotActiveException extends BusinessException {

    public CustomerNotActiveException() {
        super("Kullanıcının hesabı henüz etkinleştirilmemiştir");
    }
}

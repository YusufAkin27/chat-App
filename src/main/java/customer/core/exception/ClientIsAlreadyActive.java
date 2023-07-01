package customer.core.exception;

public class ClientIsAlreadyActive extends BusinessException {

    public ClientIsAlreadyActive() {
        super("Müşteri, zaten aktif durumda bulunmaktadır.");
    }
}

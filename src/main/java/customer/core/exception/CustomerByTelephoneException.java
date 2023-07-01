package customer.core.exception;


public class CustomerByTelephoneException extends BusinessException {

    public CustomerByTelephoneException() {
        super("Bu telefon numarasını kullanan başka bir kayıt daha bulunmaktadır");
    }
}

package customer.core.exception;



public class CustomerNotFoundException extends BusinessException {

    public CustomerNotFoundException() {
        super(" kayıtlı kullanıcı bulunamadı");
    }
}
package customer.core.exception;



public class Is10TelephoneException extends BusinessException {

    public Is10TelephoneException() {
        super("geçerli bir telefon numarası giriniz.telefon numarasını 10 haneden oluşucak şekilde ayarlayınız.");
    }
}

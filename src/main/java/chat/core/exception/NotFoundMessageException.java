package chat.core.exception;


import customer.core.exception.BusinessException;

public class NotFoundMessageException extends BusinessException {

    public NotFoundMessageException() {
        super("mesaj bulunmadı");
    }
}

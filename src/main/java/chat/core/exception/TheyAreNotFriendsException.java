package chat.core.exception;


import customer.core.exception.BusinessException;

public class TheyAreNotFriendsException extends BusinessException {

    public TheyAreNotFriendsException() {
        super("bu kişi arkdaşınız değil");
    }
}

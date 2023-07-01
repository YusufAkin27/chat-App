package chat.business.abstracts;


import chat.core.exception.NotFoundMessageException;
import chat.core.exception.TheyAreNotFriendsException;
import chat.core.request.SendMessage;
import chat.entity.Chat;
import result.Result;

public interface ChatService {
    Result send(SendMessage sendMessage) throws TheyAreNotFriendsException;

    Result update(long messageId, String newContent) throws NotFoundMessageException;

    Result deleteMessage(long messageId) throws NotFoundMessageException;

    Chat getchat(long chatId) throws NotFoundMessageException;

}

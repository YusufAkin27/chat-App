package chat.controller;

import chat.business.abstracts.ChatService;
import chat.core.exception.NotFoundMessageException;
import chat.core.exception.TheyAreNotFriendsException;
import chat.core.request.SendMessage;
import chat.entity.Chat;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import result.Result;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chatApp/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/send")
    public Result send(@RequestBody SendMessage sendMessage) throws TheyAreNotFriendsException {
        return chatService.send(sendMessage);
    }

    @DeleteMapping("/message/{messageId}")
    public Result deleteMessage(@PathVariable long messageId) throws NotFoundMessageException {
        return chatService.deleteMessage(messageId);
    }

    @PutMapping("/message/{messageId}")
    public Result updateMessage(@PathVariable long messageId, @RequestBody String newContent) throws NotFoundMessageException {
        return chatService.update(messageId, newContent);

    }

    @GetMapping("/chat/{chatId}")
    public Chat getchat(@PathVariable long chatId) throws NotFoundMessageException {
        return chatService.getchat(chatId);
    }
}

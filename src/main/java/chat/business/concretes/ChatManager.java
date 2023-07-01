package chat.business.concretes;

import chat.business.abstracts.ChatService;
import chat.core.exception.NotFoundMessageException;
import chat.core.exception.TheyAreNotFriendsException;
import chat.core.request.SendMessage;
import chat.data.ChatRepository;
import chat.data.MessageRepository;
import chat.entity.Chat;
import chat.entity.base.Message;
import chat.rules.ChatRules;
import customer.data.CustomerRepository;
import customer.entity.Customer;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import result.Result;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatManager implements ChatService {
    private final ChatRepository chatRepository;
    private final CustomerRepository customerRepository;
    private final MessageRepository messageRepository;
    private final ChatRules chatRules;

    @Override
    @Transactional
    public Result send(SendMessage sendMessage) throws TheyAreNotFriendsException {
        Optional<Customer> customer1 = customerRepository.findById(sendMessage.getCustomerId());
        Optional<Customer> customer2 = customerRepository.findById(sendMessage.getFriendId());

        if (customer1.isPresent() && customer2.isPresent()) {
            Customer customer = customer1.get();
            Customer friend = customer2.get();

            if (chatRules.areFriends(sendMessage.getCustomerId(), sendMessage.getFriendId())) {
                Chat chat = createOrUpdateChat(customer, friend);

                Message message = Message.builder()
                        .customerId(sendMessage.getCustomerId())
                        .message(sendMessage.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build();

                chat.addMessage(message);
                chatRepository.save(chat);

                return new Result("Mesaj gönderildi.", true);
            }
        }

        throw new TheyAreNotFriendsException();
    }

    @Override
    @Transactional
    public Result update(long messageId, String newContent) throws NotFoundMessageException {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessage(newContent);
            messageRepository.save(message);
            return new Result("Mesaj güncellendi.", true);
        }

        throw new NotFoundMessageException();
    }

    @Override
    @Transactional
    public Result deleteMessage(long messageId) throws NotFoundMessageException {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            messageRepository.save(message);
            return new Result("Mesaj silindi.", true);
        }

        throw new NotFoundMessageException();

    }

    @Override
    public Chat getchat(long chatId) throws NotFoundMessageException {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);

        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            List<Message> messages = chat.getMessages();
            messages.sort(Comparator.comparing(Message::getTimestamp));
            return chat;
        }

        throw new NotFoundMessageException();
    }


    private Chat createOrUpdateChat(Customer customer, Customer friend) {
        Optional<Chat> existingChat = chatRepository.findByMembersContaining(customer);
        if (existingChat.isPresent()) {
            return existingChat.get();
        } else {
            List<Customer> members = new ArrayList<>();
            members.add(customer);
            members.add(friend);

            return Chat.builder()
                    .messages(new ArrayList<>())
                    .members(members)
                    .build();
        }
    }

}

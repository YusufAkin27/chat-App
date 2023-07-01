package chat.rules;

import chat.core.exception.TheyAreNotFriendsException;
import customer.data.CustomerRepository;
import customer.entity.Customer;
import customer.entity.base.Friendship;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatRules {
    private final CustomerRepository customerRepository;

    public boolean areFriends(long user1, long user2) throws TheyAreNotFriendsException {
        Customer customer1 = customerRepository.findById(user1).orElse(null);
        if (customer1 == null) {
            return false;
        }

        List<Friendship> friendships = customer1.getFriends();
        for (Friendship friendship : friendships) {
            if (friendship.getFriend().getId() == user2) {
                return true;
            }
        }

        throw new TheyAreNotFriendsException();

    }


}

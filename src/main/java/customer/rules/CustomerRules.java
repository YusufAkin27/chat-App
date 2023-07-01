package customer.rules;


import customer.core.exception.CustomerByTelephoneException;
import customer.core.exception.FriendRequestNotAllowedException;
import customer.core.exception.IsUsernameNotUnique;
import customer.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerRules {

    public static boolean isUsernameUnique(String username, List<Customer> customers) throws IsUsernameNotUnique {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                throw new IsUsernameNotUnique();
            }
        }
        return true;
    }

    public static boolean canSendFriendRequest(Customer customer, Customer friend) throws FriendRequestNotAllowedException {
        if (customer.getFriends().contains(friend) || customer.getFriendRequests().contains(friend)) {
            throw new FriendRequestNotAllowedException();
        }
        return true;
    }

    public static boolean isTelephoneNumberUnique(String telephoneNumber, List<Customer> customers) throws CustomerByTelephoneException {
        for (Customer customer : customers) {
            if (customer.getTelephoneNumber().equals(telephoneNumber)) {
                throw new CustomerByTelephoneException();
            }
        }
        return true;
    }
}

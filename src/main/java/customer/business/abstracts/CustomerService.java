package customer.business.abstracts;




import customer.core.exception.CustomerNotFoundException;
import customer.core.exception.FriendNotFoundException;
import customer.core.exception.FriendRequestNotAllowedException;
import customer.core.exception.FriendRequestNotFoundException;
import customer.core.request.CreateCustomerRequest;
import customer.core.request.UpdateCustomerRequest;
import customer.core.response.CustomerDto;
import result.Result;

import java.util.List;

public interface CustomerService {
    Result add(CreateCustomerRequest createCustomerRequest);


    CustomerDto getAllCustomers();

    Result deleteCustomer(long id) throws CustomerNotFoundException;

    Result sendFriendRequest(long customerId, long friendId) throws CustomerNotFoundException, FriendRequestNotAllowedException;

    Result acceptFriendRequest(long customerId, long friendId) throws CustomerNotFoundException, FriendRequestNotFoundException;

    Result cancelFriendRequest(long customerId, long friendId) throws FriendRequestNotFoundException, CustomerNotFoundException;

    Result removeFriend(long customerId, long friendId) throws CustomerNotFoundException, FriendNotFoundException;

    List<CustomerDto> getFriends(long customerId) throws FriendNotFoundException;

    Result updateCustomer(long id, UpdateCustomerRequest updateCustomerRequest) throws CustomerNotFoundException;

    CustomerDto getById(long id) throws CustomerNotFoundException;
}

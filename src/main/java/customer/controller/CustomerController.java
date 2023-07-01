package customer.controller;


import customer.business.abstracts.CustomerService;
import customer.core.exception.CustomerNotFoundException;
import customer.core.exception.FriendNotFoundException;
import customer.core.exception.FriendRequestNotAllowedException;
import customer.core.exception.FriendRequestNotFoundException;
import customer.core.request.CreateCustomerRequest;
import customer.core.request.UpdateCustomerRequest;
import customer.core.response.CustomerDto;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import result.Result;

import java.util.List;

@RestController
@RequestMapping("/v1/chatApp/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/add")
    public Result add(@RequestBody CreateCustomerRequest createCustomerRequest){
        return customerService.add(createCustomerRequest);
    }

    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable long id) throws CustomerNotFoundException {
        return customerService.getById(id);
    }

    @GetMapping("/all")
    public CustomerDto getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PutMapping("/{id}")
    public Result updateCustomer(@PathVariable long id, @RequestBody UpdateCustomerRequest updateCustomerRequest) throws CustomerNotFoundException {
        return customerService.updateCustomer(id, updateCustomerRequest);
    }

    @DeleteMapping("/{id}")
    public Result deleteCustomer(@PathVariable long id) throws CustomerNotFoundException {
        return customerService.deleteCustomer(id);
    }

    @PostMapping("/{customerId}/sendFriendRequest/{friendId}")
    public Result sendFriendRequest(@PathVariable long customerId, @PathVariable long friendId) throws FriendRequestNotAllowedException, CustomerNotFoundException {
        return customerService.sendFriendRequest(customerId, friendId);
    }

    @PostMapping("/{customerId}/acceptFriendRequest/{friendId}")
    public Result acceptFriendRequest(@PathVariable long customerId, @PathVariable long friendId) throws FriendRequestNotFoundException, CustomerNotFoundException {
        return customerService.acceptFriendRequest(customerId, friendId);
    }

    @PostMapping("/{customerId}/cancelFriendRequest/{friendId}")
    public Result cancelFriendRequest(@PathVariable long customerId, @PathVariable long friendId) throws FriendRequestNotFoundException, CustomerNotFoundException {
        return customerService.cancelFriendRequest(customerId, friendId);
    }

    @PostMapping("/{customerId}/removeFriend/{friendId}")
    public Result removeFriend(@PathVariable long customerId, @PathVariable long friendId) throws CustomerNotFoundException, FriendNotFoundException {
        return customerService.removeFriend(customerId, friendId);
    }

    @GetMapping("/{customerId}/friends")
    public List<CustomerDto> getFriends(@PathVariable long customerId) throws FriendNotFoundException {
        return customerService.getFriends(customerId);
    }
}

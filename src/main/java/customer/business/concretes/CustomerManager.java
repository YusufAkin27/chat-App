package customer.business.concretes;


import customer.business.abstracts.CustomerService;
import customer.core.converter.CustomerConverter;
import customer.core.exception.CustomerNotFoundException;
import customer.core.exception.FriendNotFoundException;
import customer.core.exception.FriendRequestNotAllowedException;
import customer.core.exception.FriendRequestNotFoundException;
import customer.core.request.CreateCustomerRequest;
import customer.core.request.UpdateCustomerRequest;
import customer.core.response.CustomerDto;
import customer.data.CustomerRepository;
import customer.entity.Customer;
import customer.entity.base.FriendRequest;
import customer.entity.base.Friendship;
import customer.rules.CustomerRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import result.Result;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerManager implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;
    private final CustomerRules customerRules;


    @Override
    @Transactional
    public Result add(CreateCustomerRequest createCustomerRequest) {
        Customer customer=customerConverter.createToConverter(createCustomerRequest);

        return new Result("kayıt işlemi başarılı",true);
    }

    @Override
    public CustomerDto getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> activeCustomers = customers.stream()
                .filter(Customer::isActive)
                .map(customerConverter::customerToDto)
                .collect(Collectors.toList());
        return (CustomerDto) activeCustomers;
    }


    @Override
    @Transactional
    public Result deleteCustomer(long id) throws CustomerNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setActive(false);
            return new Result("Müşteri başarıyla silindi.", true);
        }
        throw new CustomerNotFoundException();
    }

    @Override
    @Transactional
    public Result sendFriendRequest(long customerId, long friendId) throws CustomerNotFoundException, FriendRequestNotAllowedException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Optional<Customer> optionalFriend = customerRepository.findById(friendId);

        if (optionalCustomer.isPresent() && optionalFriend.isPresent()) {
            Customer customer = optionalCustomer.get();
            Customer friend = optionalFriend.get();

            if (customerRules.canSendFriendRequest(customer, friend)) {
                boolean isExistingFriend = friend.getFriends().stream()
                        .anyMatch(friendship -> friendship.getFriend().getId() == customer.getId());

                boolean isExistingFriendRequest = friend.getFriendRequests().stream()
                        .anyMatch(request -> request.getId() == customer.getId());

                if (!isExistingFriendRequest && !isExistingFriend ) {
                    FriendRequest friendRequest = new FriendRequest();
                    friendRequest.setSender(customer);
                    friendRequest.setReceiver(friend);

                    friend.getFriendRequests().add(friendRequest);
                    customerRepository.save(friend);
                    return new Result("Arkadaşlık isteği gönderildi.", true);
                } else {
                    return new Result("Arkadaşlık isteği gönderme işlemi başarısız. İstek zaten var veya kullanıcı zaten arkadaşınız.", false);
                }
            } else {
                throw new FriendRequestNotAllowedException();
            }
        }

        throw new CustomerNotFoundException();
    }


    @Override
    @Transactional
    public Result acceptFriendRequest(long customerId, long friendId) throws FriendRequestNotFoundException, CustomerNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Optional<Customer> optionalFriend = customerRepository.findById(friendId);

        if (optionalCustomer.isPresent() && optionalFriend.isPresent()) {
            Customer customer = optionalCustomer.get();
            Customer friend = optionalFriend.get();

            boolean friendRequestExists = customer.getFriendRequests().stream()
                    .anyMatch(request -> request.getSender().getId() == friendId);

            if (friendRequestExists) {
                List<FriendRequest> friendRequests = customer.getFriendRequests();
                friendRequests.removeIf(request -> request.getSender().getId() == friendId);

                List<Friendship> friendships = customer.getFriends();
                Friendship friendship = new Friendship();
                friendship.setOwner(customer);
                friendship.setFriend(friend);
                friendships.add(friendship);

                List<Friendship> friendFriends = friend.getFriends();
                Friendship friendFriendship = new Friendship();
                friendFriendship.setOwner(friend);
                friendFriendship.setFriend(customer);
                friendFriends.add(friendFriendship);


                customerRepository.save(customer);
                customerRepository.save(friend);

                return new Result("Arkadaşlık isteği kabul edildi.", true);
            } else {
                throw new FriendRequestNotFoundException();
            }
        }

        throw new CustomerNotFoundException();
    }

    @Override
    @Transactional
    public Result cancelFriendRequest(long customerId, long friendId) throws FriendRequestNotFoundException, CustomerNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Optional<Customer> optionalFriend = customerRepository.findById(friendId);

        if (optionalCustomer.isPresent() && optionalFriend.isPresent()) {
            Customer customer = optionalCustomer.get();
            Customer friend = optionalFriend.get();

            if (customer.getFriendRequests().contains(friend)) {
                customer.getFriendRequests().remove(friend);
                customerRepository.save(customer);
                return new Result("Arkadaşlık isteği iptal edildi.", true);
            } else {
                throw new FriendRequestNotFoundException();
            }
        }

        throw new CustomerNotFoundException();
    }


    @Override
    @Transactional
    public Result removeFriend(long customerId, long friendId) throws CustomerNotFoundException, FriendNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Optional<Customer> optionalFriend = customerRepository.findById(friendId);

        if (optionalCustomer.isPresent() && optionalFriend.isPresent()) {
            Customer customer = optionalCustomer.get();
            Customer friend = optionalFriend.get();

            if (customer.getFriends().contains(friend)) {
                customer.getFriends().remove(friend);
                friend.getFriends().remove(customer);
                customerRepository.save(customer);
                customerRepository.save(friend);
                return new Result("Arkadaşlıktan çıkıldı.", true);
            } else {
                throw new FriendNotFoundException();
            }
        }

        throw new CustomerNotFoundException();
    }


    @Override
    public List<CustomerDto> getFriends(long customerId) throws FriendNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            List<CustomerDto> friendsDto = new ArrayList<>();

            for (int i = 0; i < customer.getFriends().size(); i++) {
                Customer friend = customer.getFriends().get(i).getFriend();
                if (friend.isActive()) {
                    CustomerDto friendDto = customerConverter.customerToDto(friend);
                    friendsDto.add(friendDto);
                }
            }

            return friendsDto;
        }

        throw new FriendNotFoundException();
    }



    @Override
    @Transactional
    public Result updateCustomer(long id, UpdateCustomerRequest updateCustomerRequest) throws CustomerNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            if (updateCustomerRequest.getNameSurname() != null) {
                customer.setNameSurname(updateCustomerRequest.getNameSurname());
            }
            if (updateCustomerRequest.getUsername() != null) {
                customer.setUsername(updateCustomerRequest.getUsername());
            }
            if (updateCustomerRequest.getTelephoneNumber() != null) {
                customer.setTelephoneNumber(updateCustomerRequest.getTelephoneNumber());
            }
            if (updateCustomerRequest.getPassword() != null) {
                customer.setPassword(updateCustomerRequest.getPassword());
            }
            if (updateCustomerRequest.getDescription() != null) {
                customer.setDescription(updateCustomerRequest.getDescription());
            }
            if (updateCustomerRequest.getRegistrationDate() != null) {
                customer.setRegistrationDate(updateCustomerRequest.getRegistrationDate());
            }

            customerRepository.save(customer);
            return new Result("Kullanıcı güncellendi.", true);
        }

        throw new CustomerNotFoundException();
    }

    @Override
    public CustomerDto getById(long id) throws CustomerNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return customerConverter.customerToDto(customer);
        }

        throw new CustomerNotFoundException();
    }
}

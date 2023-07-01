package customer.core.converter;

;
import customer.core.request.CreateCustomerRequest;
import customer.core.response.CustomerDto;
import customer.entity.Customer;
import customer.entity.base.FriendRequest;
import customer.entity.base.Friendship;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
@Component
public class CustomerConverterImpl implements CustomerConverter {



    @Override
    public Customer createToConverter(CreateCustomerRequest createCustomerRequest) {
        Date currentDate = new Date(); // Şu anki tarih ve saat
        return Customer.builder()
                .nameSurname(createCustomerRequest.getNameSurname())
                .username(createCustomerRequest.getUsername())
                .telephoneNumber(createCustomerRequest.getTelephoneNumber())
                .password(createCustomerRequest.getPassword())
                .description(createCustomerRequest.getDescription())
                .friendRequests(null)
                .isActive(true)
                .friends(null)
                .registrationDate(currentDate)
                .build();
    }



    @Override
    public CustomerDto customerToDto(Customer customer) {
        List<Friendship> friends = customer.getFriends();
        List<FriendRequest> friendRequests = customer.getFriendRequests();


        return CustomerDto.builder()
                .id(customer.getId())
                .nameSurname(customer.getNameSurname())
                .username(customer.getUsername())
                .telephoneNumber(customer.getTelephoneNumber())
                .password(customer.getPassword())
                .description(customer.getDescription())
                .registrationDate(customer.getRegistrationDate())
                .isActive(customer.isActive())
                .friends(friends)
                .friendRequests(friendRequests)
                .build();
    }

}

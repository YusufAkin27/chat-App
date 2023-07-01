package customer.core.converter;


import customer.core.request.CreateCustomerRequest;
import customer.core.response.CustomerDto;
import customer.entity.Customer;

public interface CustomerConverter {
    Customer createToConverter(CreateCustomerRequest createCustomerRequest);

    CustomerDto customerToDto(Customer customer);

}

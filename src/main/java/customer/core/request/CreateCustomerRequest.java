package customer.core.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest {

    private String nameSurname;
    private String username;
    private String telephoneNumber;
    private String password;
    private String description;

}

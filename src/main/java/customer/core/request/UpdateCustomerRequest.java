package customer.core.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCustomerRequest {
    private String nameSurname;
    private String username;
    private String telephoneNumber;
    private String password;
    private String description;
    private Date registrationDate;
}

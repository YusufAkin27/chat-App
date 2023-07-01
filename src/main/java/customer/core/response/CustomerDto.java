package customer.core.response;

import customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import customer.entity.base.FriendRequest;
import customer.entity.base.Friendship;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private long id;
    private String nameSurname;
    private String username;
    private String telephoneNumber;
    private String password;
    private String description;
    private Date registrationDate;
    private boolean isActive;
    private List<Friendship> friends;
    private List<FriendRequest> friendRequests;
}

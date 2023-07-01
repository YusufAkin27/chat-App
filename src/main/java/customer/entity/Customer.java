package customer.entity;

import chat.entity.Chat;
import customer.entity.base.FriendRequest;
import customer.entity.base.Friendship;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nameSurname;
    private String username;
    private String telephoneNumber;
    private String password;
    private String description;
    private Date registrationDate;
    private boolean isActive;
    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL)
    private List<Friendship> friends;


    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<FriendRequest> friendRequests;

    @ManyToMany
    private List<Chat>chats;



}

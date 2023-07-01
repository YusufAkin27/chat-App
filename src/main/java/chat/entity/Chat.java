package chat.entity;

import chat.entity.base.Message;
import customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("timestamp ASC")
    private List<Message> messages;

    @ManyToMany
    private List<Customer> members;

    public void addMessage(Message message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
        sortMessagesByTimestamp();
    }

    public void deleteMessage(Message message) {
        if (messages != null) {
            message.setMessage("Bu mesaj silindi.");
            sortMessagesByTimestamp();
        }
    }

    private void sortMessagesByTimestamp() {
        if (messages != null) {
            Collections.sort(messages, Comparator.comparing(Message::getTimestamp));
        }
    }
}

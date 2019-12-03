package ideationworks.votes;

import ideationworks.ideas.Idea;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import springblack.identity.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "Vote")
public class Vote {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @CreationTimestamp
    private LocalDateTime stampCreated;

    @UpdateTimestamp
    private LocalDateTime stampUpdated;

    @OneToOne
    private User user;

    @OneToOne
    private Idea idea;

    private VoteType type;

}

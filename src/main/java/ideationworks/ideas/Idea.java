package ideationworks.ideas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ideationworks.ideas.objects.IdeaUser;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import springblack.categories.Category;
import springblack.identity.organizations.Organization;
import springblack.identity.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonIgnore
    @OneToOne
    private Organization organization;

    @JsonIgnore
    @OneToOne
    private User user;

    @CreationTimestamp
    private LocalDateTime stampCreated;

    @UpdateTimestamp
    private LocalDateTime stampUpdated;

    private String name;
    private String description;

    @Transient
    private IdeaUser _user;

    @OneToOne
    private Category category;

}

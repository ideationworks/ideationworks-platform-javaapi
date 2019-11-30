package ideationworks.ideas.tags;

import ideationworks.ideas.Idea;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import springblack.tags.Tag;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "ideas_tags")
public class IdeaTag {

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
    private Idea idea;

    @OneToOne
    private Tag tag;

}

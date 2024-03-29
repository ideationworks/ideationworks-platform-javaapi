package ideationworks.ideas.categories;

import ideationworks.ideas.Idea;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import springblack.categories.Category;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "ideas_categories")
public class IdeaCategory {

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
    private Category category;

    public IdeaCategory(Idea idea, Category category) {

        this.idea = idea;
        this.category = category;

    }

}
